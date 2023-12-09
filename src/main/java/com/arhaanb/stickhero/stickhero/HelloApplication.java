package com.arhaanb.stickhero.stickhero;

import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class HelloApplication extends Application {

  Player player;

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    Application.launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    primaryStage.setTitle("Stick Hero");

    FXMLLoader fxmlLoader = new FXMLLoader(
      HelloApplication.class.getResource("hello-view.fxml")
    );
    Scene scene;
    scene = new Scene(fxmlLoader.load());

    // File file = new File(".");
    // for (String fileNames : file.list()) System.out.println(fileNames);

    String path = "bg-music.mp3";
    Media media = new Media(new File(path).toURI().toString());
    MediaPlayer mediaPlayer = new MediaPlayer(media);
    mediaPlayer.setAutoPlay(true);
    mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    mediaPlayer.setVolume(0.1);

    FXMLLoader helloLoader = new FXMLLoader(
      HelloApplication.class.getResource("hello-view.fxml")
    );
    Parent root = helloLoader.load();

    HelloController helloController = helloLoader.getController();
    helloController.setMediaPlayer(mediaPlayer);

    primaryStage.setScene(scene);
    primaryStage.setResizable(false);
    primaryStage.show();
  }
}
