package com.arhaanb.stickhero.stickhero;

import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Stick implements Movable {

  private ImageView line;
  private ImageView sprite;

  public Stick(ImageView line, ImageView sprite) {
    this.line = line;
    this.sprite = sprite;
  }

  public void move(
    double newX,
    double newY,
    double translationX,
    double translationY,
    Runnable onFinished
  ) {
    TranslateTransition transition = new TranslateTransition(
      Duration.millis(500),
      sprite
    );
    transition.setToX(translationX);

    TranslateTransition fall = new TranslateTransition(
      Duration.millis(500),
      sprite
    );
    fall.setByY(translationY);

    transition.setOnFinished(moveTransOver -> {
      sprite.setY(newY);
      fall.setOnFinished(afterFall -> onFinished.run());
      fall.play();
    });

    transition.play();
  }

  @Override
  public void move(double targetX, double targetY) {
  }

  public ImageView getLine() {
    return line;
  }

  public void setLine(ImageView line) {
    this.line = line;
  }
}
