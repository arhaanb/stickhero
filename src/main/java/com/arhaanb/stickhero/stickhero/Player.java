package com.arhaanb.stickhero.stickhero;

// Player class
public class Player extends Entity implements Movable {

  // private Stick stick;

  public Player() {}

  public void generateStick() {
    // stick = new Stick();
  }

  public void releaseStick() {}

  @Override
  public void draw() {
    throw new UnsupportedOperationException("Unimplemented method 'draw'");
  }

  @Override
  public void move() {
    throw new UnsupportedOperationException("Unimplemented method 'move'");
  }
}
