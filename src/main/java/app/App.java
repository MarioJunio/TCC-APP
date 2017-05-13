package app;

import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.net.URL;

/**
 * Created by MarioJ on 24/02/16.
 */
public class App {

    public static final String DEFAULT_LANG = "pt-br";

    public enum Dimension {

        WIDTH(1024), HEIGHT(768);

        private int size;

        Dimension(int size) {
            this.size = size;
        }

        public int getSize() {
            return size;
        }
    }

    private static App app = null;

    public static App getInstance() {

        if (app == null)
            app = new App();

        return app;
    }

    public Parent load(String fxml) throws IOException {
        return FXMLLoader.load(getClass().getResource(fxml));
    }

    public URL loadURL(String fxml) {
        return getClass().getResource(fxml);
    }

    public static Thread runBackground(Task task) {
        return new Thread(task);
    }

    public static Alert simpleAlert(Alert.AlertType type, String title, String header, String body) {

        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(body);

        return alert;
    }

}
