package service;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

/**
 * Created by MarioJ on 24/02/16.
 */
public class Audio {

    public static MediaPlayer play(String filename) throws FileNotFoundException {

        File audioFile = new File(filename);

        if (!audioFile.exists())
            throw new FileNotFoundException("Arquivo de audio não existe");

        Media hit = new Media(Paths.get(filename).toUri().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play();

        return mediaPlayer;
    }

}
