package rpereira.sondage.network.session;

import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONException;
import org.json.JSONObject;

import rpereira.sondage.util.Logger;

/**
 * Created by Romain on 12/04/2017.
 */

public class FacebookSession extends Session {

    public FacebookSession(AccessToken accessToken) {
        super(accessToken.getToken(), accessToken.getUserId());
    }

    @Override
    public boolean updateUserInformations() {

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken == null) {
            return (false);
        }

        // App code

        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Logger.get().log(Logger.Level.DEBUG, response.toString());

                try {
                    String name = object.getString("name");
                    FacebookSession.super.setDisplayName(name);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    String email = object.getString("email");
                    FacebookSession.super.setEmail(email);
                } catch (JSONException e) {
                }

                try {
                    String birthday = object.getString("birthday");
                    FacebookSession.super.setBirthdate(birthday);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    String gender = object.getString("gender");
                    FacebookSession.super.setGender(gender.equals("male") ? 0 : 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "name,email,gender,birthday");
        request.setParameters(parameters);
        request.executeAndWait();
        return (true);
    }

    @Override
    public void destroy() {

    }
}
