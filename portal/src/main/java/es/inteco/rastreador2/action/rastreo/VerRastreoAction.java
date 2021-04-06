/*******************************************************************************
* Copyright (C) 2012 INTECO, Instituto Nacional de Tecnologías de la Comunicación, 
* This program is licensed and may be used, modified and redistributed under the terms
* of the European Public License (EUPL), either version 1.2 or (at your option) any later 
* version as soon as they are approved by the European Commission.
* Unless required by applicable law or agreed to in writing, software distributed under the 
* License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF 
* ANY KIND, either express or implied. See the License for the specific language governing 
* permissions and more details.
* You should have received a copy of the EUPL1.2 license along with this program; if not, 
* you may find it at http://eur-lex.europa.eu/legal-content/EN/TXT/?uri=CELEX:32017D0863
* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
* Modificaciones: MINHAFP (Ministerio de Hacienda y Función Pública) 
* Email: observ.accesibilidad@correo.gob.es
******************************************************************************/
package es.inteco.rastreador2.action.rastreo;

import es.inteco.common.Constants;
import es.inteco.common.logging.Logger;
import es.inteco.common.properties.PropertiesManager;
import es.inteco.plugin.dao.DataBaseManager;
import es.inteco.rastreador2.actionform.rastreo.VerRastreoForm;
import es.inteco.rastreador2.dao.rastreo.RastreoDAO;
import es.inteco.rastreador2.utils.ActionUtils;
import es.inteco.rastreador2.utils.CrawlerUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.util.ArrayList;

import static es.inteco.common.Constants.CRAWLER_PROPERTIES;

/**
 * The Class VerRastreoAction.
 */
public class VerRastreoAction extends Action {

    /**
	 * Execute.
	 *
	 * @param mapping  the mapping
	 * @param form     the form
	 * @param request  the request
	 * @param response the response
	 * @return the action forward
	 */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        if (CrawlerUtils.hasAccess(request, "view.crawler")) {

            String idRastreoParam = request.getParameter(Constants.ID_RASTREO);
            if (idRastreoParam == null || idRastreoParam.trim().equals("")) {
                return mapping.findForward(Constants.LOGIN);
            }

            try (Connection c = DataBaseManager.getConnection()) {
                final String user = (String) request.getSession().getAttribute(Constants.USER);
                long idRastreo = 0;
                if (request.getParameter(Constants.ID_RASTREO) != null) {
                    idRastreo = Long.parseLong(request.getParameter(Constants.ID_RASTREO));
                }
                //Comprobamos que el usuario esta asociado con el rastreo que quiere ver
                if (RastreoDAO.crawlerToUser(c, idRastreo, user) || RastreoDAO.crawlerToClientAccount(c, idRastreo, user)) {
                    final PropertiesManager pmgr = new PropertiesManager();
                    final VerRastreoForm verRastreoForm = (VerRastreoForm) form;
                    final HttpSession sesion = request.getSession();
                    final ArrayList<String> rolesesion = (ArrayList<String>) sesion.getAttribute(Constants.ROLE);
                    if (rolesesion.contains(pmgr.getValue(CRAWLER_PROPERTIES, "role.configurator.id"))) {
                        if (!RastreoDAO.rastreoValidoParaUsuario(c, idRastreo, (String) sesion.getAttribute(Constants.USER))) {
                            ActionUtils.setSuccesActionAttributes(request, "mensaje.error.noPermisos", "volver.cargar.rastreos");
                            return mapping.findForward(Constants.NO_RASTREO_PERMISO);
                        }
                    }
                    RastreoDAO.cargarRastreoVer(c, Integer.parseInt(idRastreoParam), verRastreoForm);
                    verRastreoForm.setNormaAnalisisSt(RastreoDAO.getNombreNorma(c, verRastreoForm.getNormaAnalisis()));
                } else {
                    return mapping.findForward(Constants.NO_PERMISSION);
                }
            } catch (Exception e) {
                Logger.putLog("Exception: ", VerRastreoAction.class, Logger.LOG_LEVEL_ERROR, e);
                CrawlerUtils.warnAdministrators(e, this.getClass());
                return mapping.findForward(Constants.ERROR_PAGE);
            }

            return mapping.findForward(Constants.EXITO_VER);
        } else {
            return mapping.findForward(Constants.NO_PERMISSION);
        }
    }

}
