package protocol;

import com.google.gson.Gson;
import model.Message;

/**
 * Created by MarioJ on 22/03/16.
 */
public class Protocol {

    public static final String ALL = "all";

    private static Gson gson;

    static  {
        gson = new Gson();
    }

    public static Message toMessage(String json) {
        return gson.fromJson(json, Message.class);
    }
}
