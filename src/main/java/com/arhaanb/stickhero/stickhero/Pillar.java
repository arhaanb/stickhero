package com.arhaanb.stickhero.stickhero;

import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Pillar {

  private static ArrayList<Rectangle> pillars = new ArrayList<>();
  private static Pane pane;

  public static ArrayList<Rectangle> getRects() {
    return pillars;
  }

  public static Integer numrects() {
    return pillars.size();
  }

  public static Rectangle add(Double rectX, Double rectWidth) {
    Pillar.setPane(pane);

    Double firstRectGap = rectX + rectWidth;
    Double randomVal = firstRectGap + Utility.getRandomNumberBetween(50, 200);

    Rectangle newRectangle = new Rectangle(
      randomVal,
      437,
      Utility.getRandomNumberBetween(50, 200),
      283.0
    );

    newRectangle.setFill(Color.RED);
    newRectangle.toBack();

    return newRectangle;
  }

  public static Pane getPane() {
    return pane;
  }

  public static void setPane(Pane pane) {
    Pillar.pane = pane;
  }
}
