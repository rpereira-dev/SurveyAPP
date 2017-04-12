package rpereira.sondage.network.session;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import java.util.Arrays;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import rpereira.sondage.activities.MainActivity;

/**
 * Created by Romain on 12/04/2017.
 */

public class SessionManager implements GoogleApiClient.OnConnectionFailedListener {

    //twitter constants
    private static final String TWITTER_KEY = "q2CLPkdyG8LkrtfQsVyWriQ0E";
    private static final String TWITTER_SECRET = "9vwLYK0OtIopoRHSFzM1XdBk1oMJKo42OVjoCZUtmlBc0KwSYT";

    /** the main activity reference */
    private MainActivity mainActivity;

    /** the session */
    private Session session;

    /** the current session fragment in use */
    private SessionFragment sessionFragment;

    //facebook
    private CallbackManager facebookCallbackManager;
    private AccessTokenTracker facebookAccessTokenTracker;

    //google
    private GoogleApiClient googleApiClient;

    public SessionManager(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.session = null;
    }

    /** initialize the session system */
    public void init() {

        //init social networks
        this.initFacebook();
        this.initGoogle();
        this.initTwitter();
    }

    /** set the session */
    public void setSession(Session session) {
        if (this.session != null) {
            this.session.destroy();
        }
        this.session = session;

        if (this.sessionFragment != null) {
            this.hideSessionFragment();
        }
    }

    /** return the main activity instance */
    public MainActivity getMainActivity() {
        return (this.mainActivity);
    }

    /** pop a new session fragment */
    public void showSessionFragment() {
        this.sessionFragment = new SessionFragment(this);
        FragmentManager fragmentManager = mainActivity.getSupportFragmentManager();
        this.sessionFragment.show(fragmentManager, "Session Fragment");
    }

    /** hide session fragment */
    public void hideSessionFragment() {
        this.sessionFragment.dismiss();
        this.sessionFragment = null;
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
                    setSession(null);
                } else {
                    //LOGIN
                    setSession(new FacebookSession(currentAccessToken));
                }
            }
        };

        LoginManager.getInstance().logInWithReadPermissions(this.getMainActivity(), Arrays.asList("email", "user_birthday"));
    }

    /**
     * initialize google sdk
     */
    private void initGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        this.googleApiClient = new GoogleApiClient.Builder(this.mainActivity)
                .enableAutoManage(this.mainActivity, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    /** initialize twitter sdk */
    private void initTwitter() {
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this.getMainActivity(), new Twitter(authConfig));
    }

    /** called when the session creation fails */
    public void onSessionCreationFailed() {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        this.onSessionCreationFailed();
    }

    /** return the google api */
    public GoogleApiClient getGoogleClientAPI() {
        return (this.googleApiClient);
    }

    /** get facebook callback manager */
    public CallbackManager getFacebookCallbackManager() {
        return (this.facebookCallbackManager);
    }

    /** get the current session */
    public Session getSession() {
        return (this.session);
    }

    /** on main activity result */
    public void onMainActivityResult(int requestCode, int resultCode, Intent data) {
        this.getFacebookCallbackManager().onActivityResult(requestCode, resultCode, data);
    }

}
