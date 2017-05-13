package test;

import tts.GTTS;

/**
 * Created by MarioJ on 23/03/16.
 */
public class ClientConnection {

    private static final String TAG = "ClientConnection";

    public static void main(String[] args) {

        GTTS gtts = new GTTS();

        try {
            gtts.speak("teste");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
