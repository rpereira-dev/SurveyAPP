package rpereira.sondage.network.session;

/**
 * Created by Romain on 13/04/2017.
 */

/** a listener called when the session changed, NULL if logout */
public interface OnSessionChangedListener {
    public void onSessionChanged(Session session);
}
