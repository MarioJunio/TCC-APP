package service;

/**
 * Created by MarioJ on 22/03/16.
 */
public class Log {

    public static void d(final String TAG, final String TRACE) {
        System.out.printf("\n%s\t%s\n", TAG, TRACE);
    }
}
