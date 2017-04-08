package rpereira.sondage.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import rpereira.sondage.R;
import rpereira.sondage.network.Session;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private CallbackManager facebookCallbackManager;
    private GoogleApiClient googleApiClient;
    private static final int RC_SIGN_IN = 42;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_login);
        this.createFacebookButton();
        this.createGoogleButton();
    }

    //session creation failed
    private void sessionCreationFailed() {

    }

    //create session sucessfully
    private final void createSession(Session session) {
        this.session = session;
        this.startActivity(new Intent(this, MainActivity.class));
    }

    private final void destroySession() {
        this.session = null;
    }

    //google connection failed
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        this.sessionCreationFailed();
    }

    //setting up facebook connection
    private void createGoogleButton() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        this.googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        SignInButton signInButton = (SignInButton) this.findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(this);
    }

    //setting up facebook connection
    private void createFacebookButton() {
        this.facebookCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) this.findViewById(R.id.login_button);
        loginButton.registerCallback(this.facebookCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                createSession(new Session(loginResult.getAccessToken()));
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
                sessionCreationFailed();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.facebookCallbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                this.createSession(new Session(result.getSignInAccount()));
            } else {
                this.destroySession();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(this.googleApiClient);
                this.startActivityForResult(signInIntent, RC_SIGN_IN);
                break;
        }
    }
}
