package rpereira.sondage.network;

import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Created by Romain on 06/04/2017.
 */

public class Session {

    private Type type = Type.None;
    private String token = "{TOKEN}";
    private String userID = "{USER_ID}";

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

    public Session(AccessToken token) {
        this.type = Type.Facebook;
        this.token = token.getToken();
        this.userID = token.getUserId();
    }

    public Session(GoogleSignInAccount acc) {
        this.type = Type.Google;
        this.userID = acc.getId();
        this.token = acc.getIdToken();
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
