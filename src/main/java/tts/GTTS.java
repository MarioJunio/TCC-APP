package tts;

import app.App;
import service.Audio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by MarioJ on 24/02/16.
 */
public class GTTS {

    public void speak(String text) throws Exception {

        System.out.println(getClass().getResource("/scripts/main.py").getPath());

        // command terminal
        String command = "python " + getClass().getResource("/scripts/main.py").getPath() + " " + text + " " + App.DEFAULT_LANG;

        // process to execute command
        Process p = Runtime.getRuntime().exec(command);

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

        String fileName = getInput(stdInput);

        // checa se arquivo com a mensagem de voz foi criado, se nao checa se houve algum erro
        if (fileName == null || fileName.isEmpty()) {

            String error = getInput(stdError);
            throw new Exception(error);

        }

        // Verifica se o arquivo existe no disco, e reproduz o audio
        Audio.play(fileName);
    }

    private String getInput(BufferedReader std) throws IOException {

        String message = "";
        String line = null;

        while ((line = std.readLine()) != null)
            message += line;

        return message;
    }

}
