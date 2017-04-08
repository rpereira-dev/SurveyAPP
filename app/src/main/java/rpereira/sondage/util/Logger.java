package rpereira.sondage.util;

/**
 * Created by Romain on 03/04/2017.
 */

public class Logger {

    public static final void log(String msg) {
        System.out.println(msg);
    }
    public static final void log(String ... msg) {
        StringBuilder builder = new StringBuilder();
        for (String str : msg) {
            builder.append(str);
            builder.append(' ');
        }
        log(builder.toString());
    }
}
