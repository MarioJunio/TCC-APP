package controller;

import app.App;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import tts.GTTS;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private GTTS gtts;

    @FXML
    private TextField message;

    public void initialize(URL location, ResourceBundle resources) {
        gtts = new GTTS();
    }

    @FXML
    public void speech(ActionEvent event) throws Exception {

        Thread t = App.runBackground(new Task() {

            @Override
            protected Object call() throws Exception {
                System.out.println("Call");
                gtts.speak(message.getText());
                return null;
            }
        });

        t.setName("Received Message");
        t.start();

    }
}
