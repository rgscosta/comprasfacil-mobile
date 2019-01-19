package sourceinformation.com.br.comprasfacil_mobile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginGoogleNowActivity extends AppCompatActivity {

    // TAG is for show some tag logs in LOG screen.
    public static final String TAG = "LoginGoogleNowActivity";
    // Request sing in code. Could be anything as you required.
    public static final int RequestSignInCode = 7;
    // Firebase Auth Object.
    public FirebaseAuth firebaseAuth;
    // Google API Client object.
    public GoogleApiClient googleApiClient;
    // Sing out button.
    Button SignOutButton;
    // Google Sign In button .
    com.google.android.gms.common.SignInButton signInButton;
    // TextView to Show Login User Email and Name.
    TextView LoginUserName, LoginUserEmail;
    ImageView ImagemUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_google_now);

        inicialize();
        signInButton = (com.google.android.gms.common.SignInButton) findViewById(R.id.sign_in_button);

        // Getting Firebase Auth Instance into firebaseAuth object.
        firebaseAuth = FirebaseAuth.getInstance();

        // Hiding the TextView on activity start up time.
        LoginUserEmail.setVisibility(View.GONE);
        LoginUserName.setVisibility(View.GONE);

        // Creating and Configuring Google Sign In object.
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                //.requestIdToken("1025450071869-fu14ii4trh71i58fv6tbs328t180c279.apps.googleusercontent.com")
                .requestEmail()
                .build();

        // Creating and Configuring Google Api Client.
        googleApiClient = new GoogleApiClient.Builder(LoginGoogleNowActivity.this)
                .enableAutoManage(LoginGoogleNowActivity.this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();


//        // Adding Click listener to User Sign in Google button.
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserSignInMethod();

            }
        });

        // Adding Click Listener to User Sign Out button.
        SignOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserSignOutFunction();

            }
        });

    }

    private void inicialize() {
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        SignOutButton = (Button) findViewById(R.id.sign_out);
        LoginUserName = (TextView) findViewById(R.id.textViewName);
        LoginUserEmail = (TextView) findViewById(R.id.textViewEmail);
        ImagemUrl = (ImageView) findViewById(R.id.imageViewUrl);
    }


    // Sign In function Starts From Here.
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

        Toast.makeText(LoginGoogleNowActivity.this, "" + authCredential.getProvider(), Toast.LENGTH_LONG).show();

        firebaseAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(LoginGoogleNowActivity.this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task AuthResultTask) {

                        if (AuthResultTask.isSuccessful()) {

                            // Getting Current Login user details.
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
//
//                            SignOutButton.setVisibility(View.VISIBLE);
//                            signInButton.setVisibility(View.GONE);
//                            LoginUserEmail.setVisibility(View.VISIBLE);
//                            LoginUserName.setVisibility(View.VISIBLE);
//                            ImagemUrl.setVisibility(View.VISIBLE);

                            // Setting up name into TextView.
                            LoginUserName.setText(firebaseUser.getDisplayName().toString());
//
//                            String imagem = firebaseUser.getPhotoUrl().toString();
//                            ImagemUrl.setTag(imagem);
//                            DownloadImagesTask down = new DownloadImagesTask();
//                            down.execute(ImagemUrl);
//                            Toast.makeText(getApplicationContext(), firebaseUser.getEmail().toString(), Toast.LENGTH_SHORT).show();

                            // Setting up Email into TextView.
                            LoginUserEmail.setText(firebaseUser.getEmail().toString());
                            abrirTelaPrincipal();

                        } else {
                            Toast.makeText(LoginGoogleNowActivity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    public void abrirTelaPrincipal() {
        Intent intentAbrirTelaescolha = new Intent(LoginGoogleNowActivity.this, EscolhaActivity.class);
        startActivity(intentAbrirTelaescolha);

    }

    public void UserSignOutFunction() {

        // Realiza o Logout do usuário.
        firebaseAuth.signOut();
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        Toast.makeText(LoginGoogleNowActivity.this, "Logout Successfully", Toast.LENGTH_LONG).show();
                    }

                });




        // Depois do logout o botão não fica mais visível.
      //  SignOutButton.setVisibility(View.GONE);
       //ImagemUrl.setVisibility(View.GONE);

        // Depois do logout os componentes email e a senha recebem o valor nulo.
      //  LoginUserName.setText(null);
       // LoginUserEmail.setText(null);

        // Depois do logout o botão de login volta a ficar visível.
        signInButton.setVisibility(View.VISIBLE);
    }

    public void teste(View view) {
        UserSignInMethod();
    }

    //Classe que converte a imagemWeb em Bitmap
//    public class DownloadImagesTask extends AsyncTask<ImageView, Void, Bitmap> {
//
//        ImageView imageView = null;
//
//        @Override
//        protected Bitmap doInBackground(ImageView... imageViews) {
//            ImagemUrl = imageViews[0];
//            return download_Image((String) ImagemUrl.getTag());
//        }
//
//        @Override
//        protected void onPostExecute(Bitmap result) {
//            ImagemUrl.setImageBitmap(result);
//        }
//
//        private Bitmap download_Image(String url) {
//
//            Bitmap bmp = null;
//            try {
//                URL ulrn = new URL(url);
//                HttpURLConnection con = (HttpURLConnection) ulrn.openConnection();
//                InputStream is = con.getInputStream();
//                bmp = BitmapFactory.decodeStream(is);
//                if (null != bmp)
//                    return bmp;
//
//            } catch (Exception e) {
//            }
//            return bmp;
//        }
//    }

}


