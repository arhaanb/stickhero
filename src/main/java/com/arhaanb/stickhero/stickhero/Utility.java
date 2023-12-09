package com.arhaanb.stickhero.stickhero;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Utility {

  public static double getRandomNumberBetween(double min, double max) {
    if (min > max) {
      throw new IllegalArgumentException("Max must be greater than min");
    }

    Random random = new Random();
    return min + (max - min) * random.nextDouble();
  }

  public static <T> boolean contains(ArrayList<T> array, T target) {
    for (T element : array) {
      if (element.equals(target)) {
        return true;
      }
    }
    return false;
  }

  public static void addTheme(Integer themeId) throws IOException {
    try (
      BufferedWriter writer = new BufferedWriter(
        new FileWriter("owned.txt", true)
      )
    ) {
      writer.write(themeId.toString());
      writer.newLine();
    } catch (IOException e) {
      e.printStackTrace();
      throw e;
    }
  }

  public static ArrayList<Integer> getOwnedThemes() {
    ArrayList<Integer> integerList = new ArrayList<>();

    try (
      BufferedReader reader = new BufferedReader(new FileReader("owned.txt"))
    ) {
      String line;
      while ((line = reader.readLine()) != null) {
        int intValue = Integer.parseInt(line.trim());
        integerList.add(intValue);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return integerList;
  }

  public static Integer readTheme() {
    Integer theme = 0;
    try (
      BufferedReader reader = new BufferedReader(
        new FileReader("selected_theme.txt")
      )
    ) {
      theme = Integer.parseInt(reader.readLine());
    } catch (IOException | NumberFormatException e) {
      e.printStackTrace();
    }
    return theme;
  }

  public static Integer getHighScore() {
    Integer high_score = 0;
    try (
      BufferedReader reader = new BufferedReader(
        new FileReader("high_score.txt")
      )
    ) {
      high_score = Integer.parseInt(reader.readLine());
    } catch (IOException | NumberFormatException e) {
      e.printStackTrace();
    }
    return high_score;
  }

  public static void updateScore(Integer score) throws IOException {
    try {
      BufferedWriter writer = new BufferedWriter(
        new FileWriter("high_score.txt")
      );
      writer.write(String.valueOf(score));
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {}
  }

  public static void setSelectedTheme(Integer score) throws IOException {
    try {
      BufferedWriter writer = new BufferedWriter(
        new FileWriter("selected_theme.txt")
      );
      writer.write(String.valueOf(score));
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void lastScore(Integer score) throws IOException {
    try {
      BufferedWriter writer = new BufferedWriter(
        new FileWriter("last_score.txt")
      );
      writer.write(String.valueOf(score));
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {}
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
