package com.arhaanb.stickhero.stickhero;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
    player = new Player();

    FXMLLoader fxmlLoader = new FXMLLoader(
      HelloApplication.class.getResource("hello-view.fxml")
    );
    Scene scene;
    scene = new Scene(fxmlLoader.load());

    primaryStage.setTitle("Hello!");
    primaryStage.setScene(scene);
    // primaryStage.setResizable(false);
    primaryStage.show();
  }
}
