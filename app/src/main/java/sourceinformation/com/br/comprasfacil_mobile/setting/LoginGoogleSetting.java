package sourceinformation.com.br.comprasfacil_mobile.setting;

import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;

import sourceinformation.com.br.comprasfacil_mobile.LoginGoogleNowActivity;

public class LoginGoogleSetting {

    // Request sing in code. Could be anything as you required.
    public static final int REQUEST_SIGN_IN_CODE = 7;
    // Firebase Auth Object.
    public FirebaseAuth firebaseAuth;
    // Google API Client object.
    public GoogleApiClient googleApiClient;


    public LoginGoogleSetting() {
    }

    public void authenticationGoogle(LoginGoogleNowActivity fragmentActivity, String tokenId) {
        firebaseAuth = FirebaseAuth.getInstance();

        // Creating and Configuring Google Sign In object.
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(tokenId)
                //.requestIdToken("1025450071869-fu14ii4trh71i58fv6tbs328t180c279.apps.googleusercontent.com")
                .requestEmail()
                .build();

        // Creating and Configuring Google Api Client.
        googleApiClient = new GoogleApiClient.Builder(fragmentActivity)
                .enableAutoManage(fragmentActivity, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();

    }

}
