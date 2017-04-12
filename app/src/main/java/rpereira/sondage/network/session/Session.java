package rpereira.sondage.network.session;

import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterSession;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Romain on 06/04/2017.
 */

public abstract class Session {

    public static final int GENDER_MALE     = 0;
    public static final int GENDER_FEMALE   = 1;

    private String token;
    private String userID;
    private String email;
    private String birthdate;
    private String displayName;
    private String profilPicture;
    private int gender;

/*    public enum Type {
        None(""),
        Facebook("localhost/Sondage/login/facebook.php"),
        Google("localhost/Sondage/login/google.php"),
        Twitter("localhost/Sondage/login/twitter.php");

        String url;

        Type(String url) {
            this.url = url;
        }
    }*/

    public Session() {
        this.reset();
    }

    public Session(String token, String userID) {
        this.set(token, userID);
    }

    public void set(String token, String userID) {
        this.token = token;
        this.userID = userID;
    }

    public void reset() {
        this.set(null, null);
    }

    public final String getToken() {
        return (this.token);
    }

    public final String getUserID() {
        return (this.userID);
    }

    public String getEmail() {
        return (this.email);
    }

    public String getProfilePicture() {
        return (this.profilPicture);
    }

    public String getDisplayName() {
        return (this.displayName);
    }

    public String getBirthdate() {
        return (this.birthdate);
    }

    public int getGender() {
        return (this.gender);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProfilePictureURI(String profilPicture) {
        this.profilPicture = profilPicture;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public abstract boolean updateUserInformations();

    public abstract void destroy();
}
