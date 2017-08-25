package es.inteco.common.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtils {

    private static final byte[] NBSP_BYTE = {-62, -96};
    private static final byte[] WHITE_CHARS_BYTE = {-62, -96, 10, 9};

    private StringUtils() {
    }

    // El string no está vacío?
    public static boolean isNotEmpty(String string) {
        return string != null && string.trim().length() > 0;
    }

    // El string está vacío?
    public static boolean isEmpty(String string) {
        return string == null || string.trim().length() == 0;
    }

    public static boolean isOnlyWhiteChars(String string) {
        byte[] bytes = string.getBytes();
        for (byte aByte : bytes) {
            boolean isWhite = false;
            for (byte whiteCharsByte : WHITE_CHARS_BYTE) {
                if (whiteCharsByte == aByte) {
                    isWhite = true;
                }
            }
            if (!isWhite) {
                return false;
            }
        }
        return true;
    }

    // Comprueba si una cadena está formada únicamente por espacios en blanco
    public static boolean isOnlyBlanks(String string) {
        return (string.length() > 0 && (string.trim().length() == 0 || hasOnlyNbspEntities(string)));
    }

    // Cuando en el html detecta un &nbsp;, en lugar de devolver el caracter vacío devuelve un caracter con
    // código -96
    public static boolean hasOnlyNbspEntities(String string) {
        if (string.length() == 1 && string.codePointAt(0) == 0xA0) {
            return true;
        }

        final byte[] bytes = string.trim().getBytes();
        for (byte aByte : bytes) {
            boolean isNbsp = false;
            for (byte nbspByte : NBSP_BYTE) {
                if (nbspByte == aByte) {
                    isNbsp = true;
                }
            }
            if (!isNbsp) {
                return false;
            }
        }

        return true;
    }

    /**
     * Comprueba si en un texto existen al menos n repeticiones (o más) seguidas de la entidad &amp;nbsp;.
     *
     * @param text           el texto a comprobar.
     * @param numRepetitions el número de repeticiones seguidas que al menos deben ocurrir.
     * @return true si se repite la entidad &amp;nbsp; al menos el numero de repeticiones indicado seguidas o false en caso contrario.
     */
    public static boolean hasNbspRepetitions(final String text, final int numRepetitions) {
        final String regexp = "(" + new String(NBSP_BYTE) + "){" + numRepetitions + ",}";
        final Pattern pattern = Pattern.compile(regexp, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        final Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }

    /**
     * Convierte un InputStream en un String usando la codificación de caracteres por defecto
     *
     * @param in - el stream de entrada que se convertirá en una cadena
     * @return una cadena con el contenido del InputStream de entrada
     * @throws IOException si se produce algún fallo durante la lectura del stream de entrada
     */
    public static String getContentAsString(InputStream in) throws IOException {
        return getContentAsString(in, Charset.defaultCharset().name());
    }

    /**
     * Convierte un InputStream en un String usando la codificación de caracteres indicada como parámetro o la codificación por defecto si es null
     *
     * @param in      - el stream de entrada que se convertirá en una cadena
     * @param charset - cadena que representa el nombre de la codificación de caracteres que se quiere usar
     * @return una cadena con el contenido del InputStream de entrada
     * @throws IOException si se produce algún fallo durante la lectura del stream de entrada o si la codificación de caracteres indicada no está soportada
     */
    public static String getContentAsString(final InputStream in, final String charset) throws IOException {
        final StringBuilder out = new StringBuilder();
        final byte[] b = new byte[4096];
        int n = in.read(b);
        while (n != -1) {
            if (charset != null) {
                out.append(new String(b, 0, n, charset));
            } else {
                out.append(new String(b, 0, n));
            }
            n = in.read(b);
        }
        return out.toString().trim();
    }

    public static String removeHtmlTags(String htmlCode) {
        return htmlCode != null ? htmlCode.replaceAll("<.*?>", "") : "";
    }

    public static boolean isUrl(String string) {
        try {
            new URL(string);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    /**
     * El validador CSS de la W3C devuelve un error en el XML que lo hace imposible de parsear. Este
     * método resolverá ese error. Consiste en sustituir el caracter "&" a secas por la entidad "&amp;"
     *
     * @param content
     * @return
     * @throws java.io.IOException
     */
    public static InputStream fixBugInCssValidator(InputStream content) throws IOException {
        String text = StringUtils.getContentAsString(content);

        text = text.replaceAll("&", "&amp;");
        return new ByteArrayInputStream(text.getBytes());
    }

    public static boolean textMatchs(String text, String regexp) {
        Pattern pattern = Pattern.compile(regexp, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher matcher = pattern.matcher(text);

        return matcher.find();
    }

    public static String truncateText(String text, int numChars) {
        if (text != null && text.length() > numChars) {
            return text.substring(0, numChars - 1) + " ...";
        } else {
            return text;
        }
    }

    public static String normalizeWhiteSpaces(final String text) {
        final StringBuilder sb = new StringBuilder(text.replace("&nbsp;", " ").replace("&#160;", " "));
        for (int i = 0; i < sb.length(); i++) {
            if (Character.isWhitespace(sb.charAt(i)) || sb.charAt(i) == 0xA0) {
                sb.setCharAt(i, ' ');
            }
        }
        return sb.toString();
    }
    
	/**
	 * Corregir encoding.
	 *
	 * @param searchForm
	 *            the search form
	 * @throws UnsupportedEncodingException
	 *             the unsupported encoding exception
	 */
	public static String corregirEncoding(String originalString) throws UnsupportedEncodingException {
		Charset utf8charset = Charset.forName("UTF-8");
		Charset iso88591charset = Charset.forName("ISO-8859-1");

		// decode UTF-8
		CharBuffer data = utf8charset.decode(ByteBuffer.wrap(originalString.getBytes("UTF-8")));

		// encode ISO-8559-1
		ByteBuffer outputBuffer = iso88591charset.encode(data);
		byte[] outputData = outputBuffer.array();

		String fixedString = new String(outputData);

		return fixedString;
	}
}
