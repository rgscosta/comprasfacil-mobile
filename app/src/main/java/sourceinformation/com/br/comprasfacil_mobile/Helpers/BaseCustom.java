package sourceinformation.com.br.comprasfacil_mobile.Helpers;

import android.util.Base64;

public class BaseCustom{

    public static String codificarBase (String texto) {
        return Base64.encodeToString(texto.getBytes(), Base64.DEFAULT).replaceAll("(\\n\\r)","");
    }

    public static String decodificarBase (String textoCodificado) {
        return  new String(Base64.decode(textoCodificado, Base64.DEFAULT));
    }
}
