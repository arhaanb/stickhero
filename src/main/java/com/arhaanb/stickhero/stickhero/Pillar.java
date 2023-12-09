package com.arhaanb.stickhero.stickhero;

import java.util.ArrayList;
import javafx.scene.shape.Rectangle;

public class Pillar {

  private static ArrayList<Rectangle> pillars = new ArrayList<>();

  public static ArrayList<Rectangle> getRects() {
    return pillars;
  }

  public static Integer numrects() {
    return pillars.size();
  }

  
}
