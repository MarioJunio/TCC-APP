package net;

import model.Message;
import service.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Observable;

/**
 * O cliente tem como objetivo estabelecer a conexao com o host e trocar mensagens
 */
public class Client extends Observable {

    public static final String TAG = "Client";

    public static final String HOST = "localhost";
    public static final int PORT = 9897;

    private String username;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    /**
     * Constroi objeto Client
     *
     * @param username nome do usuário
     */
    public Client(String username) {
        this.username = username;
    }

    /**
     * Estabelece conexao com o HOST e cria os objetos de entrada e saída de mensagens na rede
     * Realiza autenticação do usuário
     *
     * @throws IOException
     */
    public void connect() throws IOException {

        // estabelece conexao com o host
        socket = new Socket(HOST, PORT);

        if (socket.isConnected()) {

            // entrada e saída de pacotes da conexao
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            // autentica nome do usuario no servidor
            doAuth();
        }

    }

    /**
     * Envia nome do usuario para HOST
     */
    private void doAuth() throws IOException {

        out.writeUTF(username);
        out.flush();

        Log.d(TAG, key() + ": " + username);
    }

    /**
     * Envia mensagem para o HOST
     *
     * @param message
     */
    public void submitMessage(Message message) throws IOException {
        out.writeUTF(message.toString());
        out.flush();
    }

    /**
     * Lê mensagens do HOST
     */
    public String readMessage() throws IOException {
        return in.readUTF();
    }

    /**
     * Escuta novas mensagens da rede e notifica observadores
     */
    public void listenMessages() throws Exception {

        try {

            while (true) {

                String message = readMessage();

                setChanged();
                notifyObservers(message);
            }

        } catch (IOException ioe) {
            throw new Exception("Disconnect");
        }
    }

    public boolean isConnected() {

        if (socket == null)
            return false;

        return socket.isConnected();
    }

    public void close() throws IOException {

        if (socket != null && socket.isConnected())
            socket.close();
    }

    public String key() {
        return socket.getLocalSocketAddress().toString();
    }
}
