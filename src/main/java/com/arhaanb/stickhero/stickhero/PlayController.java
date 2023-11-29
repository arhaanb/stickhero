package com.arhaanb.stickhero.stickhero;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PlayController {

  private Stage stage;
  private Scene scene;

  @FXML
  private AnchorPane pane;

  @FXML
  private Rectangle rect;

  @FXML
  private ImageView sprite;

  @FXML
  private Line line;

  private Timer timer;
  private boolean isMouseDown;
  private double startTime;

  public void addRectangle() {
    Double firstRectGap = rect.getLayoutX() + rect.getWidth();
    Rectangle newRectangle = new Rectangle(
      firstRectGap + Math.random() * 100,
      437,
      135.0,
      283.0
    );
    newRectangle.setFill(Color.RED);

    // Add the new rectangle to the AnchorPane
    pane.getChildren().add(newRectangle);
  }

  public void initialize() {
    Rectangle currentRectangle = rect;
    double currentX = currentRectangle.getX();
    double currentY = currentRectangle.getY();
    double currentWidth = currentRectangle.getWidth();

    // Define the range for the random offsets
    double minOffset = 50.0; // Minimum distance between rectangles
    double maxOffset = 100.0; // Maximum distance between rectangles

    // Define the range for the random widths
    double minWidth = 20.0; // Minimum width of rectangles
    double maxWidth = 50.0; // Maximum width of rectangles

    ArrayList<Rectangle> rects = new ArrayList<>();

    rects.add(new Rectangle(185, 437, 135.0, 283.0));

    // Generate more rectangles
    for (int i = 0; i < 1; i++) {
      // Generate random offsets and widths
      // double offsetX = minOffset + Math.random() * (maxOffset - minOffset);
      // double offsetY = minOffset + Math.random() * (maxOffset - minOffset);
      // double width = minWidth + Math.random() * (maxWidth - minWidth);

      // // Calculate the position of the new rectangle
      // double newX = currentX + offsetX;
      // double newY = currentY + offsetY;

      addRectangle();
    }

    pane.setOnMousePressed(event -> {
      System.out.println("Mouse clicked");

      startTime = System.currentTimeMillis();
      isMouseDown = true;

      if (timer == null) {
        timer = new Timer();
        timer.scheduleAtFixedRate(
          new TimerTask() {
            @Override
            public void run() {
              if (isMouseDown) {
                double elapsedTime = System.currentTimeMillis() - startTime;
                double lineHeight = elapsedTime * 0.01; // Adjust this factor to control the height growth rate

                line.setEndY(line.getEndY() - lineHeight);
              }
            }
          },
          0,
          10
        ); // Adjust the delay and period to control the update rate
      }
    });

    pane.setOnMouseReleased(event -> {
      isMouseDown = false;
      timer.cancel();
      timer = null;

      double angle = Math.toRadians(90.0); // Angle of rotation in degrees

      // Calculate the offsets from the start point
      double dx = line.getEndX() - line.getStartX();
      double dy = line.getEndY() - line.getStartY();

      // Rotate the end point around the start point
      double newEndX =
        line.getStartX() + dx * Math.cos(angle) - dy * Math.sin(angle);
      double newEndY =
        line.getStartY() + dx * Math.sin(angle) + dy * Math.cos(angle);

      // Update the line's end coordinates
      line.setEndX(newEndX);
      line.setEndY(newEndY);

      TranslateTransition transition = new TranslateTransition(
        Duration.millis(500),
        sprite
      );
      transition.setByX(newEndX - line.getStartX() + 40);
      transition.play();
    });
  }

  // public void initialize() {
  //   double lineHeight = 200.0;
  //   // Create an animation object
  //   Timeline animation = new Timeline(
  //     new KeyFrame(
  //       Duration.ZERO,
  //       new KeyValue(line.endYProperty(), line.getEndY())
  //     ),
  //     new KeyFrame(
  //       Duration.millis(1000),
  //       new KeyValue(line.endYProperty(), line.getEndY() - lineHeight)
  //     )
  //   );

  //   // Play the animation
  //   animation.play();
  // }

  @FXML
  public void switchToPlay(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("play.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  public void switchToHome(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  public void switchToSettings(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("settings.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  public void onHelloButtonClick(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}
