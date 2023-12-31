package com.arhaanb.stickhero.stickhero;

import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/* 
A singleton design pattern has been used here since the player always 
remains the same throughout the game and there is only one being used
*/

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

  @Override
  public void move(double targetX, double targetY) {
    TranslateTransition transition = new TranslateTransition(
      Duration.millis(750),
      sprite
    );
    transition.setToX(targetX);
    transition.setToY(targetY);
    transition.play();
  }

  public static void resetInstance() {
    instance = null;
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

  public ImageView getSprite() {
    return sprite;
  }
}
