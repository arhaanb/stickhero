package com.arhaanb.stickhero.stickhero;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Theme {

  private ImageView backgroundImage;
  private ImageView spriteImage;
  private Color rectangleColor;

  private Theme() {
    // private constructor to enforce the use of the builder
  }

  public ImageView getBackgroundImage() {
    return backgroundImage;
  }

  public ImageView getSpriteImage() {
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

    public ThemeBuilder setBackgroundImage(ImageView backgroundImage) {
      theme.backgroundImage = backgroundImage;
      return this;
    }

    public ThemeBuilder setSpriteImage(ImageView spriteImage) {
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
}
