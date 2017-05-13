package controller;

import app.App;
import app.Main;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;
import model.Message;
import net.Client;
import protocol.Protocol;
import service.Log;
import tts.GTTS;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

/**
 * Created by MarioJ on 23/03/16.
 */
public class AuthController implements Initializable, Observer {

    private static final String TAG = "AuthController";

    private GTTS gtts;
    private Client client;
    private Thread tListenMessages;
    private boolean connected;

    @FXML
    private TextField username, message;

    @FXML
    private Button btConnect;

    @FXML
    private VBox messagesBox;

    public void initialize(URL location, ResourceBundle resources) {

        // instancia tts
        gtts = new GTTS();

        Main.mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

            public void handle(WindowEvent event) {

                if (tListenMessages != null) {

                    try {

                        client.close();
                        tListenMessages.interrupt();
                        Log.d(TAG, "Listen Messages interrupted !!!");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    @FXML
    public void conectar(ActionEvent event) {

        if (!connected) {

            try {

                if (username.getText().isEmpty()) {
                    App.simpleAlert(Alert.AlertType.WARNING, "Usuário inválido", null, "Informe o nome do usuário corretamente.").showAndWait();
                    return;
                }

                if (client == null)
                    client = new Client(username.getText());

                if (!client.isConnected())
                    client.connect();

                // se a thread ja esta rodando, pare-a
                if (tListenMessages != null)
                    tListenMessages.interrupt();

                // observa as novas mensagens, recebidas da rede
                client.addObserver(this);

                // instancia uma nova thread
                tListenMessages = new Thread(new Runnable() {

                    public void run() {

                        try {
                            // escuta mensagens da rede
                            client.listenMessages();
                        } catch (Exception e) {
                            tListenMessages.interrupt();

                            Platform.runLater(new Runnable() {
                                public void run() {
                                    conectar(false);
                                }
                            });

                            Log.d(TAG, e.getMessage());
                        }
                    }
                });

                // inicia thread para escutar as mensagens da rede
                tListenMessages.start();

                connected = !connected;

            } catch (ConnectException ce) {
                App.simpleAlert(Alert.AlertType.ERROR, "Erro de conexão", null, "Não foi possível se conectar ao servidor: " + ce.getMessage()).showAndWait();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

        } else {

            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // altera estado de conexao
            connected = !connected;

        }

        // cliente autenticado então
        conectar(connected);

    }

    public void enviar(ActionEvent event) {

        Message m = new Message(client.key(), Protocol.ALL, message.getText());

        try {
            client.submitMessage(m);
            message.setText("");

            Log.d(TAG, m.getMessage() + " enviado !");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Atualiza componentes UI, indica que o cliente está autenticado
     */
    private void conectar(boolean v) {
        username.setDisable(v);
        btConnect.setText(v ? "Disconectar" : "Conectar");
    }

    /**
     * Atualiza UI a cada nova mensagem recebida da rede
     *
     * @param o       objeto observavel que notificou
     * @param message mensagem recebida da rede
     */
    public void update(Observable o, Object message) {

        final Message receiveMessage = Protocol.toMessage(message.toString());

        final MessageController messageController = new MessageController(gtts);
        messageController.setUsername(receiveMessage.getFromAddress());
        messageController.setMessage(receiveMessage.getMessage());

        // thread para executar o audio em background
        App.runBackground(new Task() {

            @Override
            protected Object call() throws Exception {
                gtts.speak(receiveMessage.getMessage());
                return null;
            }
        }).start();

        Platform.runLater(new Runnable() {
            public void run() {
                messagesBox.getChildren().add(messageController.getPane());
            }
        });
    }

}
