package main;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class MediaController {

    private static MediaPlayer introPlayer;
    private static MediaPlayer buttonPlayer;
    private static MediaPlayer harvestPlayer;
    private static MediaPlayer plantPlayer;
    private static MediaPlayer gameOverPlayer;
    private static MediaPlayer winPlayer;

    public static void playIntro() {
        Media sound = new Media(new File("./Sounds/IntroSound.wav").toURI().toString());
        introPlayer = new MediaPlayer(sound);
        introPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Always loop on intro + config screen
        introPlayer.play();
    }

    public static void playClick() {
        Media sound = new Media(new File("./Sounds/ButtonClickSound.wav").toURI().toString());
        buttonPlayer = new MediaPlayer(sound);
        buttonPlayer.play();
    }

    public static void playHarvest() {
        Media sound = new Media(new File("./Sounds/HarvestSound.wav").toURI().toString());
        harvestPlayer = new MediaPlayer(sound);
        harvestPlayer.play();
    }

    public static void playPlant() {
        Media sound = new Media(new File("./Sounds/PlantSound.wav").toURI().toString());
        plantPlayer = new MediaPlayer(sound);
        plantPlayer.play();
    }

    public static void playGameOver() {
        Media sound = new Media(new File("./Sounds/GameOverSound.wav").toURI().toString());
        gameOverPlayer = new MediaPlayer(sound);
        gameOverPlayer.play();
    }

    public static void playWin() {
        Media sound = new Media(new File("./Sounds/Win.wav").toURI().toString());
        winPlayer = new MediaPlayer(sound);
        winPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        winPlayer.play();
    }

    public static void stopIntro() {
        if (introPlayer != null) {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(3),
                            new KeyValue(introPlayer.volumeProperty(), 0)
                    )
            );
            timeline.play();
            timeline.setOnFinished(finished -> {
                introPlayer.stop();
            });
        }
    }

    public static void stopGameOver() {
        if (gameOverPlayer != null) {
            gameOverPlayer.stop();
        }
        if (winPlayer != null) {
            winPlayer.stop();
        }
    }

}
