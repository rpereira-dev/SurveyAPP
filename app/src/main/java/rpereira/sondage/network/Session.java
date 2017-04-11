package rpereira.sondage.network;

import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterSession;

/**
 * Created by Romain on 06/04/2017.
 */

public class Session {

    private Type type;
    private String token;
    private String userID;

    //TODO:
    //private String birthdate;
    //private String gender;

    public enum Type {
        None(""),
        Facebook("localhost/Sondage/login/facebook.php"),
        Google("localhost/Sondage/login/google.php"),
        Twitter("localhost/Sondage/login/twitter.php");

        String url;

        Type(String url) {
            this.url = url;
        }
    }

    public Session() {
        this.reset();
    }

    public void reset() {
        this.type = Type.None;
        this.token = "{TOKEN}";
        this.userID = "{USER_ID}";
    }

    public void set(String token, String userID, Type type) {
        this.type = type;
        this.token = token;
        this.userID = userID;
    }

    public void set(AccessToken token) {
        if (token == null) {
            this.reset();
            return ;
        }
        this.type = Type.Facebook;
        this.token = token.getToken();
        this.userID = token.getUserId();
    }

    public void set(GoogleSignInAccount acc) {
        this.type = Type.Google;
        this.userID = acc.getId();
        this.token = acc.getIdToken();
    }

    public void set(Result<TwitterSession> result) {
        this.type = Type.Google;
        this.userID = Long.toString(result.data.getUserId());
        this.token = result.data.getAuthToken().token;
    }

    public final Type getType() {
        return (this.type);
    }

    public final String getToken() {
        return (this.token);
    }

    public final String getUserID() {
        return (this.userID);
    }
}
