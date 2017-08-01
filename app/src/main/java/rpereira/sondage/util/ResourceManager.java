package rpereira.sondage.util;

/**
 * Created by Romain on 15/04/2017.
 */

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Environment;
import android.preference.PreferenceManager;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Created by rpereira on 05/12/15.
 */
public class ResourceManager {
    private static final Point SIZE = new Point();

    private static String DIRPATH = null;
    private static File SAVE_DIR = null;
    private static SharedPreferences SHARED_PREFERENCES = null;
    private static SharedPreferences.Editor SHARED_PREFERENCES_EDITOR = null;

    public static void onCreate(Activity activity) {
        activity.getWindowManager().getDefaultDisplay().getSize(SIZE);
        DIRPATH = getFilepath(Environment.getExternalStorageDirectory().toString(), "SurveyAPP");
        SAVE_DIR = new File(DIRPATH);
        initializeLogger();

        if (!SAVE_DIR.exists()) {
            SAVE_DIR.mkdir();
        }
        SHARED_PREFERENCES = PreferenceManager.getDefaultSharedPreferences(activity);
        SHARED_PREFERENCES_EDITOR = SHARED_PREFERENCES.edit();
    }

    private static void initializeLogger() {
        try {
            File logfile = new File(getFilepath(DIRPATH, "log.txt"));
            if (!logfile.exists()) {
                logfile.createNewFile();
            }
            Logger.get().setPrintStream(new PrintStream(logfile));
            Logger.get().use(true);
        } catch (IOException exception) {
            System.err.println("Couldnt iniatialize logger: " + exception.getLocalizedMessage());
            Logger.get().use(false);
        }
    }

    public static void stop() {
        commitPreferences();
    }

    /**
     * return the path for the given file relative to the app resource folder
     */
    public static String getFilepath(String dirpath, String file) {
        StringBuilder builder = new StringBuilder();

        builder.append(dirpath);
        if (!dirpath.endsWith("/") && !file.startsWith("/")) {
            builder.append("/");
        }

        builder.append(file);

        return (builder.toString());
    }

    public static void putPreferences(String key, boolean value) {
        SHARED_PREFERENCES_EDITOR.putBoolean(key, value);
    }

    public static void putPreferences(String key, String value) {
        SHARED_PREFERENCES_EDITOR.putString(key, value);
    }

    public static void putPreferences(String key, int value) {
        SHARED_PREFERENCES_EDITOR.putInt(key, value);
    }

    public static void putPreferences(String key, float value) {
        SHARED_PREFERENCES_EDITOR.putFloat(key, value);
    }

    public static void putPreferences(String key, long value) {
        SHARED_PREFERENCES_EDITOR.putLong(key, value);
    }

    public static boolean getPreferences(String key, boolean defvalue) {
        return (SHARED_PREFERENCES.getBoolean(key, defvalue));
    }

    public static int getPreferences(String key, int defvalue) {
        return (SHARED_PREFERENCES.getInt(key, defvalue));
    }

    public static float getPreferences(String key, float defvalue) {
        return (SHARED_PREFERENCES.getFloat(key, defvalue));
    }

    public static long getPreferences(String key, long defvalue) {
        return (SHARED_PREFERENCES.getLong(key, defvalue));
    }

    public static String getPreferences(String key, String defvalue) {
        return (SHARED_PREFERENCES.getString(key, defvalue));
    }

    public static void commitPreferences() {
        SHARED_PREFERENCES_EDITOR.commit();
    }

    public static int getScreenWidth() {
        return (SIZE.x);
    }

    public static int getScreenHeight() {
        return (SIZE.y);
    }
}