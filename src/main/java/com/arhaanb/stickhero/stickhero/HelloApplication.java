package com.arhaanb.stickhero.stickhero;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class HelloApplication extends Application {

  Player player;

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    Application.launch(args);
    // String basePath = "./src/main/resources/com/arhaanb/stickhero/themes/";
    // File file = new File(basePath + "1");
    // for (String fileNames : file.list()) System.out.println(fileNames);
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    primaryStage.setTitle("Stick Hero");

    FXMLLoader fxmlLoader = new FXMLLoader(
      HelloApplication.class.getResource("hello-view.fxml")
    );
    Scene scene;
    scene = new Scene(fxmlLoader.load());

    Theme theme1 = new Theme.ThemeBuilder()
      .setBackgroundImage(new Image(new FileInputStream("themes/1/bg.png")))
      .setSpriteImage(new Image(new FileInputStream("themes/1/sprite.png")))
      .setRectangleColor(Color.RED)
      .build();

    Theme theme2 = new Theme.ThemeBuilder()
      .setBackgroundImage(new Image(new FileInputStream("themes/2/bg.png")))
      .setSpriteImage(new Image(new FileInputStream("themes/2/sprite.png")))
      .setRectangleColor(Color.RED)
      .build();

    Theme.getThemes().add(theme1);
    Theme.getThemes().add(theme2);
    Theme.setSelectedTheme(theme2);

    String path = "bg-music.mp3";
    Media media = new Media(new File(path).toURI().toString());
    MediaPlayer mediaPlayer = new MediaPlayer(media);
    mediaPlayer.setAutoPlay(true);
    mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    mediaPlayer.setVolume(0.1);

    primaryStage.setScene(scene);
    primaryStage.setResizable(false);
    primaryStage.show();
  }
}
