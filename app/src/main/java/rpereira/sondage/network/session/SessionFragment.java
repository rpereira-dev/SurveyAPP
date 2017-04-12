package rpereira.sondage.network.session;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import rpereira.sondage.R;
import rpereira.sondage.activities.MainActivity;

import static android.R.attr.data;
import static android.R.attr.type;

/**
 * Created by Romain on 12/04/2017.
 */

public class SessionFragment extends DialogFragment implements View.OnClickListener  {

    /** session manager instance */
    private SessionManager sessionManager;

    /** the layout */
    private LinearLayout linearLayout;

    //facebook
    private LoginButton facebookLoginButton;

    //google
    private static final int RC_SIGN_IN = 42;
    private SignInButton googleSignInButton;

    //twitter
    private TwitterLoginButton twitterLoginButton;

    /** the textview header */
    private TextView textView;

    /** the margin */
    private int margin;

    public SessionFragment(SessionManager sessionManager) {
        super();
        this.sessionManager = sessionManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //margin
        this.margin = (int) sessionManager.getMainActivity().getResources().getDimension(R.dimen.login_button_margin);

        //the layout
        this.createLayout();

        //text view
        this.createTextView();

        //add the login or logout buttons
        this.createLoginButtons();

        this.updateButtons();

        return (this.linearLayout);
    }

    private void createLayout() {
        //layout initialisation
        this.linearLayout = new LinearLayout(sessionManager.getMainActivity());
        this.linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.linearLayout.setOrientation(LinearLayout.VERTICAL);
        this.linearLayout.setGravity(Gravity.CENTER);
    }

    /** create the textview */
    private void createTextView() {
        //textview initialisation
        this.textView = new TextView(sessionManager.getMainActivity());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(this.margin, this.margin, this.margin, this.margin);
        this.textView.setLayoutParams(layoutParams);
        this.textView.setGravity(Gravity.CENTER);

        //add the text view
        this.linearLayout.addView(this.textView);
    }

    /** update the layout content (login ou logout) */
    public void createLoginButtons() {

        //create buttons
        this.createFacebookButton();
        this.createGoogleButton();
        this.createTwitterButton();
    }

    private void updateButtons() {

        //if not logged in
        if (this.getSession() == null) {
            this.textView.setText(R.string.voc_login_message);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(this.margin, 0, this.margin, this.margin);

            this.getFacebookLoginButton().setLayoutParams(layoutParams);
            this.getGoogleSignInButton().setLayoutParams(layoutParams);
            this.getTwitterLoginButton().setLayoutParams(layoutParams);

            this.linearLayout.addView(this.getFacebookLoginButton());
            this.linearLayout.addView(this.getGoogleSignInButton());
            this.linearLayout.addView(this.getTwitterLoginButton());

        } else {
            //if logged in
            Session session = this.getSession();
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(this.margin, 0, this.margin, this.margin);
            this.textView.setText(R.string.voc_logout_message);
            if (session instanceof FacebookSession) {
                this.facebookLoginButton.setLayoutParams(layoutParams);
                this.linearLayout.addView(this.facebookLoginButton);
            } else if (session instanceof GoogleSession) {
                this.googleSignInButton.setLayoutParams(layoutParams);
                this.linearLayout.addView(this.googleSignInButton);
            } else if (session instanceof TwitterSession) {
                this.twitterLoginButton.setLayoutParams(layoutParams);
                this.linearLayout.addView(this.twitterLoginButton);
            }
        }
    }

    //setting up facebook connection
    private void createFacebookButton() {

        this.facebookLoginButton = new LoginButton(this.getMainActivity());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(this.margin, this.margin, this.margin, this.margin);
        this.facebookLoginButton.setLayoutParams(layoutParams);

        LoginManager.getInstance().registerCallback(this.getSessionManager().getFacebookCallbackManager(),
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        setSession(new FacebookSession(loginResult.getAccessToken()));
                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        onSessionCreationFailed();
                    }
                });
    }

    //setting up facebook connection
    private void createGoogleButton() {
        this.googleSignInButton = new SignInButton(this.getMainActivity().getApplication());
        this.googleSignInButton.setSize(SignInButton.SIZE_WIDE);
        this.googleSignInButton.setOnClickListener(this);
    }

    private void createTwitterButton() {
        this.twitterLoginButton = new TwitterLoginButton(this.getMainActivity()) {
            @Override
            public Activity getActivity() {
                return (SessionFragment.this.getActivity());
            }
        };
        this.twitterLoginButton.setCallback(new Callback<com.twitter.sdk.android.core.TwitterSession>() {
            @Override
            public void success(Result<com.twitter.sdk.android.core.TwitterSession> result) {
                setSession(new rpereira.sondage.network.session.TwitterSession(result));
            }

            @Override
            public void failure(TwitterException exception) {
                onSessionCreationFailed();
            }
        });
    }

    private void onSessionCreationFailed() {
        this.sessionManager.onSessionCreationFailed();
    }

    /** return the main activity instance */
    public MainActivity getMainActivity() {
        return (this.sessionManager.getMainActivity());
    }

    /** get facebook login button */
    public LoginButton getFacebookLoginButton() {
        return (this.facebookLoginButton);
    }

    /** get google login button */
    public SignInButton getGoogleSignInButton() {
        return (this.googleSignInButton);
    }

    /** get twitter login button */
    public TwitterLoginButton getTwitterLoginButton() {
        return (this.twitterLoginButton);
    }

    /** get the session manager */
    public SessionManager getSessionManager() {
        return (this.sessionManager);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(this.googleSignInButton)) {
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(this.getGoogleClientAPI());
            this.startActivityForResult(signInIntent, RC_SIGN_IN);
        }
    }

    public GoogleApiClient getGoogleClientAPI() {
        return (this.getSessionManager().getGoogleClientAPI());
    }

    public void setSession(Session session) {
        this.getSessionManager().setSession(session);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (this.getTwitterLoginButton() != null) {
            this.twitterLoginButton.onActivityResult(requestCode, resultCode, data);
        }

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                this.setSession(new GoogleSession(result.getSignInAccount()));
            } else {
                this.onSessionCreationFailed();
            }
        }
    }

    public Session getSession() {
        return (this.getSessionManager().getSession());
    }
}
