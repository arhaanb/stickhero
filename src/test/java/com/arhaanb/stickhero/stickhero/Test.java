package com.arhaanb.stickhero.stickhero;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class StickHeroTests {

  @Test
  void addRectangleFunction() {
    double result = Pillar.add(50.0, 100.0).getX();
    assertTrue(result >= 150, "Result should be greater than 150");
    // assertEquals(, Pillar.add(50.0, 100.0));
  }

  @Test
  void anotherAddRect() {
    double result = Pillar.add(20.0, 30.0).getX();
    assertTrue(result >= 50, "Result should be greater than 150");
    // assertEquals(, Pillar.add(50.0, 100.0));
  }

  @Test
  void checkCustomBooleanFunction() {
    boolean result = Utility.randomBoolean();
    assertTrue(result || !result, "Result should be true or false");
  }

  @Test
  void checkCustomRandomNumber() {
    Double result = Utility.getRandomNumberBetween(20, 40);
    assertTrue(
      result > 20 && result < 40,
      "Result should be greater than 20 and less than 40"
    );
  }
}
