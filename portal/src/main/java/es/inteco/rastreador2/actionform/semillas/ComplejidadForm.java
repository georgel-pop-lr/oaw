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
package es.inteco.rastreador2.actionform.semillas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorForm;

/**
 * The Class ComplejidadForm.
 */
public class ComplejidadForm extends ValidatorForm implements Serializable {
    
    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1752910926145527308L;

	/** The id. */
    private String id;
    
    /** The name. */
    private String name;

    /** The profundidad. */
    private int profundidad;
    
    /** The amplitud. */
    private int amplitud;
    
	/** The nombre antiguo. */
	private String nombreAntiguo;
    
    /** The file seeds. */
    private FormFile fileSeeds;
    
    /** The seeds. */
    private List<SemillaForm> seeds;
    

    /**
     * Gets the id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    
    /**
     * Gets the profundidad.
     *
     * @return the profundidad
     */
    public int getProfundidad() {
        return profundidad;
    }

    /**
     * Sets the profundidad.
     *
     * @param  the new profundidad
     */
    public void setProfundidad(int profundidad) {
        this.profundidad = profundidad;
    }
    
    
    /**
     * Gets the amplitud.
     *
     * @return the amplitud
     */
    public int getAmplitud() {
        return amplitud;
    }

    /**
     * Sets the amplitud.
     *
     * @param  the new amplitud
     */
    public void setAmplitud(int amplitud) {
        this.amplitud = amplitud;
    }
    
    
	/**
	 * Gets the nombre antiguo.
	 *
	 * @return the nombre antiguo
	 */
	public String getNombreAntiguo() {
		return nombreAntiguo;
	}

	/**
	 * Sets the nombre antiguo.
	 *
	 * @param nombreAntiguo
	 *            the new nombre antiguo
	 */
	public void setNombreAntiguo(String nombreAntiguo) {
		this.nombreAntiguo = nombreAntiguo;
	}
	
    /**
     * Gets the file seeds.
     *
     * @return the file seeds
     */
    public FormFile getFileSeeds() {
        return fileSeeds;
    }

    /**
     * Sets the file seeds.
     *
     * @param fileSeeds the new file seeds
     */
    public void setFileSeeds(FormFile fileSeeds) {
        this.fileSeeds = fileSeeds;
    }

    /**
     * Gets the seeds.
     *
     * @return the seeds
     */
    public List<SemillaForm> getSeeds() {
        return seeds;
    }

    /**
     * Sets the seeds.
     *
     * @param seeds the new seeds
     */
    public void setSeeds(List<SemillaForm> seeds) {
        this.seeds = seeds;
    }


    /**
     * Reset.
     *
     * @param mapping the mapping
     * @param request the request
     */
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        if (seeds == null) {
            seeds = new ArrayList<>();
        }
    }

	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * Equals.
	 *
	 * @param obj the obj
	 * @return true, if successful
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComplejidadForm other = (ComplejidadForm) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
    
    
    
}