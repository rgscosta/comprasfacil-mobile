package sourceinformation.com.br.comprasfacil_mobile;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import sourceinformation.com.br.comprasfacil_mobile.CONF.FireBase;
import sourceinformation.com.br.comprasfacil_mobile.Entidades.Usuarios;

import static sourceinformation.com.br.comprasfacil_mobile.LoginGoogleNowActivity.RequestSignInCode;


public class LoginUserActivity extends AppCompatActivity   {



        private EditText editTextEmail;
        private EditText editTextSenha;
        private Button buttonFazerLogin;
        private TextView textViewNaotemcadastro;
        private FirebaseAuth autenticacao;
        private Usuarios usuarios;
        private SignInButton signInButton2;
        public LoginGoogleNowActivity loginGoogleNowActivity;
        private Button buttonCadastro;



    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login_user);


            editTextEmail = (EditText) findViewById(R.id.editTextEmail);
            editTextSenha = (EditText)findViewById(R.id.editTextSenha);
            buttonFazerLogin = (Button)findViewById(R.id.buttonFazerLogin);
            textViewNaotemcadastro = (TextView)  findViewById(R.id.textViewNaotemcadastro);
            final SignInButton signInButton2 = findViewById(R.id.signInButton);
            signInButton2.setSize(SignInButton.SIZE_STANDARD);


        signInButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(LoginUserActivity.this, LoginGoogleNowActivity.class);
                startActivity(it);

//               loginGoogleNowActivity = new LoginGoogleNowActivity();
//               loginGoogleNowActivity.



                }

        });

            buttonFazerLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!editTextEmail.getText().toString().equals("") && !editTextSenha.getText().toString().equals("")){

                        usuarios = new Usuarios();
                        usuarios.setEmail(editTextEmail.getText().toString());
                        usuarios.setSenha(editTextSenha.getText().toString());

                        validarLogin();

                    }else {
                        Toast.makeText(LoginUserActivity.this, "Preencha os campos de e-mail e senha!", Toast.LENGTH_LONG).show();
                    }
                }
            });


        textViewNaotemcadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCadastro();
            }
        });

        }


    private void validarLogin(){


        autenticacao = FireBase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(usuarios.getEmail(),usuarios.getSenha()).addOnCompleteListener( new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    abrirTelaPrincipal();

                    Toast.makeText(LoginUserActivity.this, "Login Efetuado com Sucesso!", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(LoginUserActivity.this, "Usuário ou senha inválidos!", Toast.LENGTH_SHORT).show();
                }
            }
        });


}
    public void abrirTelaPrincipal(){
        Intent intentAbrirTelaescolha = new Intent(LoginUserActivity.this, EscolhaFragmentActivity.class);
        startActivity(intentAbrirTelaescolha);

    }



    public void abrirCadastro(){
        Intent abrircadastro = new Intent(LoginUserActivity.this, CadastroActivity.class);
        startActivity(abrircadastro);

    }

}

