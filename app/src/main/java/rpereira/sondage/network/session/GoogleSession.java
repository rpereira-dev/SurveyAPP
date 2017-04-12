package rpereira.sondage.network.session;

import android.net.Uri;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Created by Romain on 12/04/2017.
 */

public class GoogleSession extends Session {

    public GoogleSession(GoogleSignInAccount googleSignInAccount) {
        super(googleSignInAccount.getIdToken(), googleSignInAccount.getId());
        super.setEmail(googleSignInAccount.getEmail());
        super.setDisplayName(googleSignInAccount.getDisplayName());
        super.setProfilePictureURI(googleSignInAccount.getPhotoUrl().toString());
    }

    @Override
    public boolean updateUserInformations() {
        return (false);
    }

    @Override
    public void destroy() {

    }
}
