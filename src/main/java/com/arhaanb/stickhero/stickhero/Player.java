package com.arhaanb.stickhero.stickhero;

import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Player implements Movable {

  private static Player instance;
  private ImageView sprite;

  private Player(ImageView sprite) {
    this.sprite = sprite;
  }

  public static Player getInstance(ImageView sprite) {
    if (instance == null) {
      instance = new Player(sprite);
    }
    return instance;
  }

  public void move(double targetX, double targetY, Runnable onFinished) {
    TranslateTransition transition = new TranslateTransition(
      Duration.millis(750),
      sprite
    );
    transition.setToX(targetX);
    transition.setToY(targetY);
    transition.setOnFinished(event -> onFinished.run());
    transition.play();
  }

  public void flipSprite() {
    double currentScaleY = sprite.getScaleY();

    if (currentScaleY == 1) {
      sprite.setY(sprite.getFitHeight());
      sprite.setScaleY(-1);
    } else {
      sprite.setY(0.0);
      sprite.setScaleY(1);
    }
  }

  public double getX() {
    return sprite.getX();
  }

  public double getY() {
    return sprite.getY();
  }

  public void setX(double x) {
    sprite.setX(x);
  }

  public void setY(double y) {
    sprite.setY(y);
  }
}
