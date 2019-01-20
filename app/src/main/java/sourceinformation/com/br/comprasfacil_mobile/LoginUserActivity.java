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
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import sourceinformation.com.br.comprasfacil_mobile.CONF.FireBase;
import sourceinformation.com.br.comprasfacil_mobile.Entidades.Usuarios;




public class LoginUserActivity extends AppCompatActivity {


    private EditText editTextEmail;
    private EditText editTextSenha;
    private Button buttonFazerLogin;
    private TextView textViewNaotemcadastro;
    private FirebaseAuth autenticacao;
    private Usuarios usuarios;



    //Google
    public static final int RequestSignInCode = 7;
    public GoogleApiClient googleApiClient;
    // Google Sign In button .
    com.google.android.gms.common.SignInButton signInButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        //Google
        autenticacao = FirebaseAuth.getInstance();


        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextSenha = (EditText) findViewById(R.id.editTextSenha);
        buttonFazerLogin = (Button) findViewById(R.id.buttonFazerLogin);
        textViewNaotemcadastro = (TextView) findViewById(R.id.textViewNaotemcadastro);
        signInButton = (com.google.android.gms.common.SignInButton) findViewById(R.id.sign_in_button);

//            final SignInButton signInButton2 = findViewById(R.id.signInButton);
//            signInButton2.setSize(SignInButton.SIZE_STANDARD);

        //Google
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("1025450071869-q144n08lkqqj6qejeark69k4b4dvudjd.apps.googleusercontent.com")
                .requestEmail()
                .build();

        // Creating and Configuring Google Api Client.
        googleApiClient = new GoogleApiClient.Builder(LoginUserActivity.this)
                .enableAutoManage(LoginUserActivity.this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();


        // Adding Click listener to User Sign in Google button.
          signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserSignInMethod();

            }
        });

//        // Adding Click Listener to User Sign Out button.
//        SignOutButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                UserSignOutFunction();
//
//            }
//        });

        buttonFazerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextEmail.getText().toString().equals("") && !editTextSenha.getText().toString().equals("")) {

                    usuarios = new Usuarios();
                    usuarios.setEmail(editTextEmail.getText().toString());
                    usuarios.setSenha(editTextSenha.getText().toString());

                    validarLogin();

                } else {
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

    //Google
    public void UserSignInMethod() {

        // Passing Google Api Client into Intent.
        Intent AuthIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);

        startActivityForResult(AuthIntent, RequestSignInCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RequestSignInCode) {

            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (googleSignInResult.isSuccess()) {

                GoogleSignInAccount googleSignInAccount = googleSignInResult.getSignInAccount();

                FirebaseUserAuth(googleSignInAccount);
            }

        }
    }

    public void FirebaseUserAuth(GoogleSignInAccount googleSignInAccount) {

        AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);

        Toast.makeText(LoginUserActivity.this, "" + authCredential.getProvider(), Toast.LENGTH_LONG).show();

       autenticacao.signInWithCredential(authCredential)
                .addOnCompleteListener(LoginUserActivity.this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task AuthResultTask) {

                        if (AuthResultTask.isSuccessful()) {

                            // Getting Current Login user details.
                            FirebaseUser firebaseUser = autenticacao.getCurrentUser();

//                            SignOutButton.setVisibility(View.VISIBLE);
//                            signInButton.setVisibility(View.GONE);
//                            LoginUserEmail.setVisibility(View.VISIBLE);
//                            LoginUserName.setVisibility(View.VISIBLE);
//                            ImagemUrl.setVisibility(View.VISIBLE);

                            // Setting up name into TextView.
                            //LoginUserName.setText(firebaseUser.getDisplayName().toString());

//                            String imagem = firebaseUser.getPhotoUrl().toString();
//                            ImagemUrl.setTag(imagem);
//                            DownloadImagesTask down = new DownloadImagesTask();
//                            down.execute(ImagemUrl);
//                            Toast.makeText(getApplicationContext(), firebaseUser.getEmail().toString(), Toast.LENGTH_SHORT).show();

                            // Setting up Email into TextView.
                            //LoginUserEmail.setText(firebaseUser.getEmail().toString());
                            abrirTelaPrincipal();

                        } else {
                            Toast.makeText(LoginUserActivity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

//    public void UserSignOutFunction() {
//
//        // Realiza o Logout do usuário.
//        autenticacao.signOut();
//        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
//                new ResultCallback<Status>() {
//                    @Override
//                    public void onResult(@NonNull Status status) {
//                        Toast.makeText(LoginUserActivity.this, "Logout Successfully", Toast.LENGTH_LONG).show();
//                    }
//
//                });

    // Depois do logout o botão não fica mais visível.
    // SignOutButton.setVisibility(View.GONE);
    // ImagemUrl.setVisibility(View.GONE);

    // Depois do logout os componentes email e a senha recebem o valor nulo.
    // LoginUserName.setText(null);
    // LoginUserEmail.setText(null);

    // Depois do logout o botão de login volta a ficar visível.
     //     signInButton.setVisibility(View.VISIBLE);
//}

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

