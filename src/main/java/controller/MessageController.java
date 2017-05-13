package controller;

import app.App;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import tts.GTTS;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by MarioJ on 26/03/16.
 */
public class MessageController implements Initializable {

    private static final String TAG = "MessageController";

    private GTTS gtts;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label username;

    @FXML
    private Text message;

    @FXML
    private Button btPlay;

    public MessageController(GTTS gtts) {

        this.gtts = gtts;

        FXMLLoader fxmlLoader = new FXMLLoader(App.getInstance().loadURL("/MessagesListCell.fxml"));
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setUsername(String username) {
        this.username.setText(username);
    }

    public void setMessage(String message) {

        if (message.length() > 126)
            this.message.setText(message.substring(0, 126).concat("..."));
        else
            this.message.setText(message);

    }

    @FXML
    public void play(ActionEvent event) {

        App.runBackground(new Task() {

            @Override
            protected Object call() throws Exception {
                gtts.speak(message.getText());
                return null;
            }

        }).start();
    }

    public AnchorPane getPane() {
        return anchorPane;
    }

}
