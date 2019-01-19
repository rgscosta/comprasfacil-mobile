package sourceinformation.com.br.comprasfacil_mobile.Helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class Referencias {

        private Context context;
        private SharedPreferences preferences;
        private String NOME_ARQUIVO = "comprasfacil-mobile.preferencias";
        private int MODE = 0;
        private SharedPreferences.Editor editor;
        private final String CHAVE_IDENTIFICADOR = "identificarUsuarioLogado";
        private final String CHAVE_NOME = "nomeUsuarioLogado";

    public Referencias(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(NOME_ARQUIVO, MODE);

        editor = preferences.edit();
    }

    public void salvarUsuariosPreferencias(String identificadorUsuario, String nomeUsuario){
        editor.putString(CHAVE_IDENTIFICADOR, identificadorUsuario);
        editor.putString(CHAVE_NOME, nomeUsuario);
        editor.commit();

    }

    public String getidentificador(){
        return preferences.getString(CHAVE_IDENTIFICADOR, null);
    }
    public String getNome(){
        return preferences.getString(CHAVE_NOME, null);
    }

}
