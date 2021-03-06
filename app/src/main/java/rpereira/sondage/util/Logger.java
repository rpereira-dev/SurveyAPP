package rpereira.sondage.util;

import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logger {
    private static final String ANSI_RESET = "\u001B[0m";
    //	private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    //	private static final String ANSI_BLUE = "\u001B[34m";
//	private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
//	private static final String ANSI_WHITE = "\u001B[37m";
//	private static final String ANSI_BOLD = "\u001B[1m";

    private static final DateFormat _date_format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static final Calendar _calendar = Calendar.getInstance();

    private static Logger INSTANCE = new Logger(System.out);
//	private static Logger INSTANCE = new Logger(System.out);

    private PrintStream _print_stream;
    private int indentation;
    private boolean use;

    public Logger(PrintStream stream) {
        this._print_stream = stream;
        this.use = true;
        this.indentation = 0;
    }

    public static Logger get() {
        return (INSTANCE);
    }

    public PrintStream getPrintStream() {
        return (this._print_stream);
    }

    public void setPrintStream(PrintStream stream) {
        this._print_stream = stream;
        ;
    }

    public void use(boolean use) {
        this.use = use;
    }

    public void print(String string) {
        this._print_stream.println(string);
    }

    public void log(Logger.Level level, String message) {
        if (this.use == false) {
            return;
        }

        Thread thrd = Thread.currentThread();

        this._print_stream.printf("%s[%s] [%s] [Thread: %s(%d)]%s ",
                "",//level.getColor(),
                _date_format.format(_calendar.getTime()),
                level.getLabel(),
                thrd.getName(),
                thrd.getId(),
                "");//ANSI_RESET););
        if (this.indentation != 0) {
            this._print_stream.printf("%" + (this.indentation * 4) + "s", "");
        }

        this._print_stream.printf("%s\n", message);
    }

    public void log(Logger.Level level, Object... objs) {
        if (this.use == false) {
            return;
        }

        int i = 0;
        StringBuilder builder = new StringBuilder();
        for (Object obj : objs) {
            builder.append(obj);
            if (i != objs.length - 1) {
                builder.append(" : ");
            }
            ++i;
        }
        log(level, builder.toString());
    }

    public void indent(int i) {
        this.indentation += i;
        if (this.indentation < 0) {
            this.indentation = 0;
        }
    }

    public enum Level {
        FINE(ANSI_GREEN, "fine"),
        WARNING(ANSI_YELLOW, "warning"),
        ERROR(ANSI_RED, "error"),
        DEBUG(ANSI_CYAN, "debug");

        String _color;
        String _label;

        Level(String color, String label) {
            this._color = color;
            this._label = label.toUpperCase();
        }

        String getLabel() {
            return (this._label);
        }

        String getColor() {
            return (this._color);
        }
    }
}