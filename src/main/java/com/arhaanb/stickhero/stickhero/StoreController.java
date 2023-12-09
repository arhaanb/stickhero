package com.arhaanb.stickhero.stickhero;

import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StoreController {

  private Stage stage;
  private Scene scene;

  @FXML
  private Button buy_theme_one;

  @FXML
  private Button buy_theme_two;

  @FXML
  private Text theme1txt;

  @FXML
  private Text theme2txt;

  @FXML
  private Text yourcherries;

  @FXML
  public void switchToPlay(ActionEvent event) throws IOException {
    Player.resetInstance();
  }

  public void initialize() {
    ArrayList<Integer> owned = Utility.getOwnedThemes();
    yourcherries.setText(
      "You have " + Integer.toString(Utility.getTotalCherries()) + " cherries"
    );

    if (Utility.getTotalCherries() < 4) {
      buy_theme_one.setDisable(true);
      buy_theme_one.setText("Not enough cherries");
      buy_theme_one.setFont(Font.font("Arial", 10));

      buy_theme_two.setDisable(true);
      buy_theme_two.setText("Not enough cherries");
      buy_theme_two.setFont(Font.font("Arial", 10));
    }

    if (Utility.contains(owned, 1)) {
      buy_theme_one.setDisable(false);
      theme1txt.setText("OWNED");
      buy_theme_one.setText("Use");
      buy_theme_one.setFont(Font.font("Arial", 24));
    }

    if (Utility.contains(owned, 2)) {
      buy_theme_two.setDisable(false);
      buy_theme_two.setVisible(true);
      theme2txt.setText("OWNED");
      buy_theme_two.setText("Use");
      buy_theme_two.setFont(Font.font("Arial", 24));
    }
    // Parent root = FXMLLoader.load(getClass().getResource("play.fxml"));
    // stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    // scene = new Scene(root);
    // stage.setScene(scene);
    // stage.show();
  }

  @FXML
  public void buyTheme1(ActionEvent event) throws IOException {
    System.out.println("buying theme 1");
    ArrayList<Integer> owned = Utility.getOwnedThemes();

    if (Utility.getTotalCherries() > 4) {
      if (!Utility.contains(owned, 1)) {
        Utility.updateCherries(-4);
        Utility.addTheme(1);
      }

      Theme.setSelectedTheme(Theme.getThemes().get(1));
      Utility.setSelectedTheme(1);
    }

    Player.resetInstance();

    Stage currentStage = (Stage) ((Node) event.getSource()).getScene()
      .getWindow();
    currentStage.close();

    Parent root = FXMLLoader.load(getClass().getResource("play.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  public void buyTheme2(ActionEvent event) throws IOException {
    ArrayList<Integer> owned = Utility.getOwnedThemes();

    if (!Utility.contains(owned, 2)) {
      Utility.updateCherries(-4);
      Utility.addTheme(2);
    }

    Theme.setSelectedTheme(Theme.getThemes().get(2));
    Utility.setSelectedTheme(2);

    Player.resetInstance();

    Stage currentStage = (Stage) ((Node) event.getSource()).getScene()
      .getWindow();
    currentStage.close();

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
  public void saveExit(ActionEvent event) throws IOException {
    Platform.exit();
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
