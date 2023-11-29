package com.arhaanb.stickhero.stickhero;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class GameOverController {

  @FXML
  private Text scoreText;

  public void setScore(int score) {
    scoreText.setText("Score: " + score);
  }
}
