package rpereira.sondage.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import io.fabric.sdk.android.Fabric;

import rpereira.sondage.R;
import rpereira.sondage.activities.informations.InformationActivity;
import rpereira.sondage.network.Session;
import rpereira.sondage.util.Logger;

import static java.security.AccessController.getContext;
import static rpereira.sondage.network.Session.Type.Twitter;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private static final String TWITTER_KEY = "q2CLPkdyG8LkrtfQsVyWriQ0E";
    private static final String TWITTER_SECRET = "9vwLYK0OtIopoRHSFzM1XdBk1oMJKo42OVjoCZUtmlBc0KwSYT";

    //the layout
    private LinearLayout linearLayout;
    private TextView textView;
    private int margin;

    //facebook
    private CallbackManager facebookCallbackManager;
    private AccessTokenTracker facebookAccessTokenTracker;
    private LoginButton facebookLoginButton;

    //google
    private static final int RC_SIGN_IN = 42;
    private SignInButton googleSignInButton;
    private GoogleApiClient googleApiClient;

    //twitter
    private TwitterLoginButton twitterLoginButton;

    //the session
    private Session session = new Session();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //init social networks
        this.initFacebook();
        this.initGoogle();
        this.initTwitter();

        //margin
        this.margin = (int) getResources().getDimension(R.dimen.login_button_margin);

        //init the layout
        this.linearLayout = new LinearLayout(this);
        this.linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.linearLayout.setOrientation(LinearLayout.VERTICAL);
        this.linearLayout.setGravity(Gravity.CENTER);

        //textview
        this.textView = new TextView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(this.margin, this.margin, this.margin, this.margin);
        this.textView.setLayoutParams(layoutParams);
        this.textView.setGravity(Gravity.CENTER);

        //update buttons
        this.setContentView();
    }

    private void setContentView() {

        this.linearLayout.removeAllViews();
        this.linearLayout.addView(this.textView);

        //create login buttons
        boolean logged = this.session.getType() != Session.Type.None;

        //if not already logged-in, create buttons and add them
        if (!logged) {

            this.textView.setText(R.string.voc_login_message);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(this.margin, 0, this.margin, this.margin);

            this.createFacebookButton();
            this.createGoogleButton();
            this.createTwitterButton();

            this.facebookLoginButton.setLayoutParams(layoutParams);
            this.googleSignInButton.setLayoutParams(layoutParams);
            this.twitterLoginButton.setLayoutParams(layoutParams);

            this.linearLayout.addView(this.facebookLoginButton);
            this.linearLayout.addView(this.googleSignInButton);
            this.linearLayout.addView(this.twitterLoginButton);

        } else {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(this.margin, 0, this.margin, this.margin);
            this.textView.setText(R.string.voc_logout_message);
            if (this.session.getType() == Session.Type.Facebook) {
                this.createFacebookButton();
                this.facebookLoginButton.setLayoutParams(layoutParams);
                this.linearLayout.addView(this.facebookLoginButton);
            } else if (this.session.getType() == Session.Type.Google) {
                this.createGoogleButton();
                this.googleSignInButton.setLayoutParams(layoutParams);
                this.linearLayout.addView(this.googleSignInButton);
            } else if (this.session.getType() == Session.Type.Twitter) {
                this.createTwitterButton();
                this.twitterLoginButton.setLayoutParams(layoutParams);
                this.linearLayout.addView(this.twitterLoginButton);
            }
        }

        //set content view
        this.setContentView(this.linearLayout);
    }

    //session creation failed
    private void sessionCreationFailed() {
        Toast.makeText(this.getApplicationContext(), "Authentification failed", Toast.LENGTH_LONG).show();
    }

    //create session sucessfully
    private final void onLogin(String token, String userID, Session.Type type) {
        Toast.makeText(this.getApplicationContext(), "Logged-in", Toast.LENGTH_SHORT).show();
        this.session.set(token, userID, type);
        this.setContentView();
    }

    private final void onLogout() {
        Toast.makeText(this.getApplicationContext(), "Logged-out", Toast.LENGTH_SHORT).show();
        this.session.reset();
        this.setContentView();
    }

    //google connection failed
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        this.sessionCreationFailed();
    }

    /**
     * initialize facebook sdk
     */
    private void initFacebook() {
        this.facebookCallbackManager = CallbackManager.Factory.create();

        this.facebookAccessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
                                                       AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    onLogout();
                }
            }
        };

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        session.set(accessToken);
    }

    /**
     * initialize google sdk
     */
    private void initGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        this.googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    /**
     * initialize twitter sdk
     */
    private void initTwitter() {
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
    }

    //setting up facebook connection
    private void createFacebookButton() {

        this.facebookLoginButton = new LoginButton(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(this.margin, this.margin, this.margin, this.margin);
        this.facebookLoginButton.setLayoutParams(layoutParams);

        LoginManager.getInstance().registerCallback(this.facebookCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        onLogin(loginResult.getAccessToken().getToken(), loginResult.getAccessToken().getUserId(), Session.Type.Facebook);
                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        sessionCreationFailed();
                    }
                });
    }

    //setting up facebook connection
    private void createGoogleButton() {
        this.googleSignInButton = new SignInButton(this.getApplication());
        this.googleSignInButton.setSize(SignInButton.SIZE_WIDE);
        this.googleSignInButton.setOnClickListener(this);
    }

    private void createTwitterButton() {
        this.twitterLoginButton = new TwitterLoginButton(this) {
            @Override
            public Activity getActivity() {
                return (LoginActivity.this);
            }
        };
        this.twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                onLogin(result.data.getAuthToken().token, Long.toString(result.data.getUserId()), Session.Type.Twitter);
            }

            @Override
            public void failure(TwitterException exception) {
                sessionCreationFailed();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
        this.twitterLoginButton.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                this.onLogin(result.getSignInAccount().getIdToken(), result.getSignInAccount().getId(), Session.Type.Google);
            } else {
                sessionCreationFailed();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.equals(this.googleSignInButton)) {
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(this.googleApiClient);
            this.startActivityForResult(signInIntent, RC_SIGN_IN);
        }
    }
}
