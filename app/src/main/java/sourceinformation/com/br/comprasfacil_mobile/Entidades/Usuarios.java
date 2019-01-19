package sourceinformation.com.br.comprasfacil_mobile.Entidades;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

import sourceinformation.com.br.comprasfacil_mobile.CONF.FireBase;

public class Usuarios {
    private String id;
    private String nome;
    private String senha;
    private String email;
    private String sobrenome;


    public Usuarios() {
    }

    public void salvar() {
        DatabaseReference referenciaFirebase = FireBase.getFirebase();
        referenciaFirebase.child("usuario").child(String.valueOf(getId())).setValue(this);
    }

    @Exclude

    public Map <String, Object> toMap() {
        HashMap <String, Object> hashMapUsuario = new HashMap <>();

        hashMapUsuario.put("id", getId());
        hashMapUsuario.put("nome", getNome());
        hashMapUsuario.put("senha", getSenha());
        hashMapUsuario.put("email", getEmail());
        hashMapUsuario.put("sobrenome", getSobrenome());

        return hashMapUsuario;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }
}

