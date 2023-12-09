package com.arhaanb.stickhero.stickhero;

import java.util.ArrayList;
import javafx.scene.shape.Rectangle;

public class Pillar extends Obstacle {

  private static ArrayList<Rectangle> rects = new ArrayList<>();

  public void move() {
    throw new UnsupportedOperationException("Unimplemented method 'move'");
  }

  @Override
  public void onTouch() {
    throw new UnsupportedOperationException("Unimplemented method 'onTouch'");
  }

  @Override
  public void draw() {
    throw new UnsupportedOperationException("Unimplemented method 'draw'");
  }

  public static ArrayList<Rectangle> getRects() {
    return rects;
  }

  public static Integer numrects() {
    return rects.size();
  }
}
