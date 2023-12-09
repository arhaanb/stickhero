package com.arhaanb.stickhero.stickhero;

import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Theme {

  private Image backgroundImage;
  private Image spriteImage;
  private Color rectangleColor;

  private static ArrayList<Theme> themes = new ArrayList<>();
  private static Theme selectedTheme;

  private Theme() {
    // private constructor to enforce the use of the builder
  }

  public Image getBackgroundImage() {
    return backgroundImage;
  }

  public Image getSpriteImage() {
    return spriteImage;
  }

  public Color getRectangleColor() {
    return rectangleColor;
  }

  public static class ThemeBuilder {

    private Theme theme;

    public ThemeBuilder() {
      theme = new Theme();
    }

    public ThemeBuilder setBackgroundImage(Image backgroundImage) {
      theme.backgroundImage = backgroundImage;
      return this;
    }

    public ThemeBuilder setSpriteImage(Image spriteImage) {
      theme.spriteImage = spriteImage;
      return this;
    }

    public ThemeBuilder setRectangleColor(Color rectangleColor) {
      theme.rectangleColor = rectangleColor;
      return this;
    }

    public Theme build() {
      return theme;
    }
  }

  public static Theme getSelectedTheme() {
    return selectedTheme;
  }

  public static void setSelectedTheme(Theme selectedTheme) {
    Theme.selectedTheme = selectedTheme;
  }

  public static ArrayList<Theme> getThemes() {
    return themes;
  }
}
