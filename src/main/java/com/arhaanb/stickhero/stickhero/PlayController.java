package com.arhaanb.stickhero.stickhero;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PlayController {

  private Stage stage;
  private Scene scene;
  private Integer score = 0;
  private Integer cherries = 0;
  private Integer blockNum = 0;

  public static double getRandomNumberBetween(double min, double max) {
    if (min > max) {
      throw new IllegalArgumentException("Max must be greater than min");
    }

    Random random = new Random();
    return min + (max - min) * random.nextDouble();
  }

  @FXML
  private AnchorPane pane;

  @FXML
  private AnchorPane over_pane;

  @FXML
  private Button closebtn;

  @FXML
  private Text scoretext; // top bar text

  @FXML
  private Text scorenum; // game over score text

  @FXML
  private Text total_cherries; // game over total cherries

  @FXML
  private Text cherrytext;

  private Boolean cherryCollected = false;

  public ImageView cherryView;

  Boolean mouseHoldEnabled = true;
  Boolean walking = false;

  // @FXML
  // private Rectangle rect;

  // private Rectangle firstrectangle;

  @FXML
  private ImageView sprite;

  @FXML
  private Line line;

  private Timer timer;
  private boolean isMouseDown;
  private double startTime;
  ArrayList<Rectangle> rects = new ArrayList<>();

  boolean ignoreClick = false;

  public void updateText(String newText, Text where) {
    where.setText(newText);
  }

  @FXML
  public void revive() {
    System.out.println("reviving player");

    mouseHoldEnabled = true;

    //reset player position
    // sprite.setX(rects.get(rects.size() - 1).getBoundsInLocal().getMinX());
    // sprite.setX(0);

    attachMouseHandlers();

    // pane.setOnMousePressed(event -> {
    //   System.out.println("bro?");
    // });

    TranslateTransition undoFall = new TranslateTransition(
      Duration.millis(100),
      sprite
    );

    undoFall.setByY(-400 - sprite.getY()); // Set to the negative of the previous translation
    undoFall.setToX(rects.get(rects.size() - 2).getX() - sprite.getX()); // Set to the negative of the previous translation

    undoFall.setOnFinished(afterUndo -> {
      updateText(Integer.toString(cherries - 2), cherrytext);

      line.setStartX(
        rects.get(rects.size() - 2).getX() +
        (rects.get(rects.size() - 2).getWidth() / 4) *
        3
      );
      line.setEndX(
        rects.get(rects.size() - 2).getX() +
        (rects.get(rects.size() - 2).getWidth() / 4) *
        3
      );

      line.setStartY(234.5);
      line.setEndY(234.5);

      //minus 2 cherries
      cherries -= 2;
      over_pane.setVisible(false);
    });

    // To play the undo animation, call play() on undoFall
    // For example, if you want to undo the translation after a certain condition is met:
    undoFall.play();
  }

  private void attachMouseHandlers() {
    pane.setOnMousePressed(event -> {
      if (event.getButton() == MouseButton.PRIMARY) {
        System.out.println("heloleolerol");
        startTime = System.currentTimeMillis();
        if (mouseHoldEnabled) {
          // addRectangle();
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
            );
          }
          System.out.println("blockNum: " + blockNum);
        }
      }
    });

    pane.setOnMouseClicked(event -> {
      if (event.getButton() == MouseButton.SECONDARY) {
        double currentScaleY = sprite.getScaleY();

        System.out.println(sprite.getY());
        if (currentScaleY == 1) {
          sprite.setY(sprite.getFitHeight());
          sprite.setScaleY(-1);
        } else {
          sprite.setY(0.0);
          sprite.setScaleY(1);
        }
      }
    });

    pane.setOnMouseReleased(event -> {
      if (event.getButton() == MouseButton.PRIMARY) {
        ignoreClick = System.currentTimeMillis() - startTime < 100;
        if (mouseHoldEnabled) {
          mouseHoldEnabled = false;
          isMouseDown = false;
          timer.cancel();
          timer = null;

          // rotating the stick
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

          // System.out.println("line end: " + line.getEndX());
          // System.out.println(rects.get(blockNum + 1).getX());
          // System.out.println(
          //   rects.get(blockNum + 1).getX() + rects.get(blockNum + 1).getWidth()
          // );

          if (
            line.getEndX() > rects.get(rects.size() - 1).getX() &&
            line.getEndX() <
            rects.get(rects.size() - 1).getX() +
            rects.get(rects.size() - 1).getWidth()
          ) {
            walking = true;

            double newX = rects.get(rects.size() - 1).getX();
            double translationXbro = newX - sprite.getX();

            TranslateTransition transition = new TranslateTransition(
              Duration.millis(750),
              sprite
            );

            transition.setToX(translationXbro);

            Timeline positionPrintTimeline = new Timeline(
              new KeyFrame(
                Duration.millis(100),
                posevent -> {
                  if (
                    checkCherryCollision(sprite, cherryView) &&
                    sprite.getScaleY() == -1
                  ) {
                    System.out.println("cherry collected");
                    cherryCollected = true;
                    pane.getChildren().remove(cherryView);
                  }
                }
              )
            );

            positionPrintTimeline.setCycleCount(Timeline.INDEFINITE);

            // Node nodeToExclude = closebtn;

            transition.setOnFinished(boo -> {
              blockNum += 1;

              if (cherryCollected) {
                cherryCollected = false;
                cherries += 1;
                cherrytext.setText("CHERRIES: " + cherries);
              }
              walking = false;
              positionPrintTimeline.stop();
              if (sprite.getScaleY() == -1) {
                try {
                  dieded();
                } catch (IOException e) {
                  e.printStackTrace();
                }
              } else {
                score++;
                updateText("SCORE: " + score, scoretext);

                // reset stick
                line.setStartX(
                  rects.get(blockNum).getX() +
                  (rects.get(blockNum).getWidth() / 4) *
                  3
                );
                line.setEndX(
                  rects.get(blockNum).getX() +
                  (rects.get(blockNum).getWidth() / 4) *
                  3
                );

                line.setStartY(234.5);
                line.setEndY(234.5);

                addRectangle();

                TranslateTransition movePane = new TranslateTransition(
                  Duration.seconds(0.5),
                  pane
                );

                double targetX = rects.get(blockNum).getX() - 100;
                movePane.setToX(-targetX);
                movePane.setOnFinished(finishedEvent -> {
                  mouseHoldEnabled = true;
                });

                movePane.play();
              }
            });
            // Play the animation
            transition.play();
            positionPrintTimeline.play();
          } else {
            //dies
            try {
              dieded();
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        }
      }
    });
  }

  private boolean checkCherryCollision(ImageView player, ImageView fruit) {
    double x1 = player.getBoundsInParent().getMinX();
    double x2 = fruit.getBoundsInParent().getMinX();
    double width1 = player.getBoundsInParent().getWidth();
    double width2 = fruit.getBoundsInParent().getWidth();
    boolean crossedXBounds = (x1 < x2 + width2) && (x1 + width1 > x2);

    return crossedXBounds;
  }

  public void addRectangle() {
    Rectangle lastRect = rects.get(rects.size() - 1);
    System.out.println("get min x: " + lastRect.getX());
    Double firstRectGap = lastRect.getX() + lastRect.getWidth();
    Double randomVal = firstRectGap + getRandomNumberBetween(50, 200);
    Rectangle newRectangle = new Rectangle(
      randomVal,
      437,
      getRandomNumberBetween(50, 200),
      283.0
    );
    newRectangle.setFill(Color.RED);
    rects.add(newRectangle);

    Random random = new Random();
    boolean randomBoolean = random.nextBoolean();

    if (randomBoolean) {
      // first rect gao to random val - add cherry to a random value between these
      double cherryXvalue = getRandomNumberBetween(
        firstRectGap,
        randomVal - 50
      );

      Image image = new Image(getClass().getResourceAsStream("cherry.png"));
      cherryView = new ImageView(image);
      cherryView.setFitWidth(50); // Set the width of the image
      cherryView.setFitHeight(50); // Set the height of the image
      cherryView.setY(445);
      cherryView.setX(cherryXvalue);
      System.out.println("cherryXvalue: " + cherryXvalue);
      pane.getChildren().add(cherryView);
    }

    // Add the new rectangle to the AnchorPane
    pane.getChildren().add(newRectangle);
    newRectangle.toBack();
  }

  public void dieded() throws IOException {
    System.out.println("died");
    // moving the sprite to the edge of the next rectangle
    TranslateTransition transition = new TranslateTransition(
      Duration.millis(500),
      sprite
    );

    double newX = line.getEndX();
    double translationXbro = newX - sprite.getX();

    transition.setToX(translationXbro);

    TranslateTransition fall = new TranslateTransition(
      Duration.millis(500),
      sprite
    );

    double newY = 400;
    double translationY = newY - sprite.getY();

    fall.setByY(translationY);

    fall.setOnFinished(afterFall -> {
      updateText(Integer.toString(score), scorenum);

      over_pane.setVisible(true);
    });

    transition.setOnFinished(moveTransOver -> {
      fall.play();
    });

    // Play the first animation
    transition.play();
  }

  public void initialize() {
    Rectangle firstRectangle = new Rectangle(40, 437, 100, 283.0);
    sprite.toFront();
    System.out.println(sprite.getY());

    // System.out.println("line.setStartX(" + line.getStartX() + ");");
    // System.out.println("line.setEndX(" + line.getEndX() + ");");
    // System.out.println("line.setStartY(" + line.getStartY() + ");");
    // System.out.println("line.setEndY(" + line.getEndY() + ");");

    Image image = new Image(getClass().getResourceAsStream("cherry.png"));
    cherryView = new ImageView(image);
    cherryView.setFitWidth(50);
    cherryView.setFitHeight(50);
    cherryView.setY(445);

    pane.getChildren().add(cherryView);

    pane.getChildren().add(firstRectangle);

    sprite.setX(40);
    rects.add(firstRectangle);

    addRectangle();

    attachMouseHandlers();
  }

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
