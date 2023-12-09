package com.arhaanb.stickhero.stickhero;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Cherry {

  private ImageView imageView;
  private static Integer number;

  public static Double getCherryCoords(Double firstRectGap, Double lastRectX) {
    return Utility.getRandomNumberBetween(firstRectGap, lastRectX - 50);
  }

  public static void add() {}

  public Cherry(double initialX) {
    Image image = new Image(getClass().getResourceAsStream("cherry.png"));
    imageView = new ImageView(image);
    imageView.setFitWidth(50);
    imageView.setFitHeight(50);
    imageView.setY(445);
    imageView.setX(initialX);
  }

  public ImageView getImageView() {
    return imageView;
  }

  public boolean checkCollision(ImageView player) {
    double x1 = player.getBoundsInParent().getMinX();
    double x2 = imageView.getBoundsInParent().getMinX();
    double width1 = player.getBoundsInParent().getWidth();
    double width2 = imageView.getBoundsInParent().getWidth();
    return (x1 < x2 + width2) && (x1 + width1 > x2);
  }

  public void setImageView(ImageView imageView) {
    this.imageView = imageView;
  }

  public static Integer getNumber() {
    return number;
  }

  public static void setNumber(Integer number) {
    Cherry.number = number;
  }
}
