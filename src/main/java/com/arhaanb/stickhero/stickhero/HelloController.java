package com.arhaanb.stickhero.stickhero;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloController {

  private Stage stage;
  private Scene scene;

  @FXML
  public void switchToPlay(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("play.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  public void switchToHome(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  public void switchToSettings(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("settings.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  public void onHelloButtonClick(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}
