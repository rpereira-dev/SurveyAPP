package rpereira.sondage.network.session;


import com.twitter.sdk.android.core.Result;

/**
 * Created by Romain on 12/04/2017.
 */

public class TwitterSession extends Session {

    public TwitterSession(Result<com.twitter.sdk.android.core.TwitterSession> result) {
        super(result.data.getAuthToken().token, Long.toString(result.data.getUserId()));
        super.setDisplayName(result.data.getUserName());
    }

    @Override
    public boolean updateUserInformations() {
        return (false);
    }

    @Override
    public void destroy() {

    }
}
