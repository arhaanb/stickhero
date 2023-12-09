package com.arhaanb.stickhero.stickhero;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Utility {

  public static double getRandomNumberBetween(double min, double max) {
    if (min > max) {
      throw new IllegalArgumentException("Max must be greater than min");
    }

    Random random = new Random();
    return min + (max - min) * random.nextDouble();
  }

  public static void saveToFile(
    String filename,
    Integer score,
    Integer cherries
  ) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
      writer.write(String.valueOf(score));
      writer.newLine();
      writer.write(String.valueOf(cherries));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void loadFromFile(
    String filename,
    Integer score,
    Integer cherries
  ) {
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
      score = Integer.parseInt(reader.readLine());
      cherries = Integer.parseInt(reader.readLine());
    } catch (IOException | NumberFormatException e) {
      e.printStackTrace();
    }
  }

  public static void updateCherries(Integer cherries) throws IOException {
    try {
      BufferedReader reader = new BufferedReader(
        new FileReader("cherries.txt")
      );
      Integer old_val = Integer.parseInt(reader.readLine());

      BufferedWriter writer = new BufferedWriter(
        new FileWriter("cherries.txt")
      );
      writer.write(String.valueOf(cherries + old_val));
      reader.close();
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {}
  }

  public static Integer getTotalCherries() {
    Integer cherries = 0;
    try (
      BufferedReader reader = new BufferedReader(new FileReader("cherries.txt"))
    ) {
      cherries = Integer.parseInt(reader.readLine());
    } catch (IOException | NumberFormatException e) {
      e.printStackTrace();
    }
    return cherries;
  }

  public static Boolean randomBoolean() {
    Random random = new Random();
    return random.nextBoolean();
  }
}
