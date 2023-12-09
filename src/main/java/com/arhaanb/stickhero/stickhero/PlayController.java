package com.arhaanb.stickhero.stickhero;

import java.io.File;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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

  private Boolean clickEventsEnabled = true;

  @FXML
  private AnchorPane pane;

  @FXML
  private AnchorPane over_pane;

  @FXML
  private Button closebtn;

  @FXML
  private ImageView bg_image;

  @FXML
  private Text scoretext; // top bar text

  @FXML
  private Text scorenum; // game over score text

  @FXML
  private Text total_cherries; // game over total cherries

  @FXML
  private Text cherrytext;

  private Boolean cherryCollected = false;

  Image cherimg = new Image(getClass().getResourceAsStream("cherry.png"));
  public ImageView cherryView = new ImageView(cherimg);

  Boolean mouseHoldEnabled = true;
  Boolean walking = false;

  @FXML
  private ImageView sprite;

  private Player player;

  @FXML
  private Line line;

  private Timer timer;
  private boolean isMouseDown;
  private double startTime;
  ArrayList<Rectangle> rects = Pillar.getRects();

  boolean ignoreClick = false;

  public void updateText(String newText, Text where) {
    where.setText(newText);
  }

  @FXML
  public void revive() {
    System.out.println("reviving player");

    mouseHoldEnabled = true;

    //reset player position
    // sprite.setX(rects.get(Pillar.numrects() - 1).getBoundsInLocal().getMinX());
    // sprite.setX(0);

    attachMouseHandlers();

    // pane.setOnMousePressed(event -> {
    //   System.out.println("bro?");
    // });

    TranslateTransition undoFall = new TranslateTransition(
      Duration.millis(100),
      player.getSprite()
    );

    undoFall.setByY(-400 - player.getY()); // Set to the negative of the previous translation
    undoFall.setToX(rects.get(Pillar.numrects() - 2).getX() - player.getX()); // Set to the negative of the previous translation

    undoFall.setOnFinished(afterUndo -> {
      updateText(Integer.toString(cherries - 2), cherrytext);

      setClickEventsEnabled(true);
      line.setStartX(
        rects.get(Pillar.numrects() - 2).getX() +
        (rects.get(Pillar.numrects() - 2).getWidth() / 4) *
        3
      );
      line.setEndX(
        rects.get(Pillar.numrects() - 2).getX() +
        (rects.get(Pillar.numrects() - 2).getWidth() / 4) *
        3
      );

      line.setStartY(234.5);
      line.setEndY(234.5);

      // minus 2 cherries
      cherries -= 2;
      over_pane.setVisible(false);
    });

    undoFall.play();
  }

  private void attachMouseHandlers() {
    pane.setOnMousePressed(event -> {
      if (getClickEventsEnabled() && event.getButton() == MouseButton.PRIMARY) {
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
                    double lineHeight = elapsedTime * 0.01; // adjust this factor to control the height growth rate

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
      if (
        getClickEventsEnabled() && event.getButton() == MouseButton.SECONDARY
      ) {
        System.out.println("is this detected");
        player.flipSprite();
      }
    });

    pane.setOnMouseReleased(event -> {
      String path = "run.mp3";
      Media media = new Media(new File(path).toURI().toString());
      MediaPlayer mediaPlayer = new MediaPlayer(media);
      mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
      mediaPlayer.setVolume(0.1);

      if (getClickEventsEnabled() && event.getButton() == MouseButton.PRIMARY) {
        ignoreClick = System.currentTimeMillis() - startTime < 100;
        if (mouseHoldEnabled) {
          mouseHoldEnabled = false;
          isMouseDown = false;
          timer.cancel();
          timer = null;

          double angle = Math.toRadians(90.0);

          double dx = line.getEndX() - line.getStartX();
          double dy = line.getEndY() - line.getStartY();

          double newEndX =
            line.getStartX() + dx * Math.cos(angle) - dy * Math.sin(angle);
          double newEndY =
            line.getStartY() + dx * Math.sin(angle) + dy * Math.cos(angle);

          line.setEndX(newEndX);
          line.setEndY(newEndY);

          if (
            line.getEndX() > rects.get(Pillar.numrects() - 1).getX() &&
            line.getEndX() <
            rects.get(Pillar.numrects() - 1).getX() +
            rects.get(Pillar.numrects() - 1).getWidth()
          ) {
            walking = true;
            mediaPlayer.play();

            double newX = rects.get(Pillar.numrects() - 1).getX();
            double translationXbro = newX - player.getX();

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
              mediaPlayer.stop();

              if (cherryCollected) {
                cherryCollected = false;
                cherries += 1;
                cherrytext.setText("CHERRIES: " + cherries);
              }
              walking = false;
              positionPrintTimeline.stop();
              if (sprite.getScaleY() == -1) {
                try {
                  dieded("upside");
                } catch (IOException e) {
                  e.printStackTrace();
                }
              } else {
                score++;
                updateText("SCORE: " + score, scoretext);

                // reset stick
                line.setStartX(
                  rects.get(Pillar.numrects() - 1).getX() +
                  (rects.get(Pillar.numrects() - 1).getWidth() / 4) *
                  3
                );
                line.setEndX(
                  rects.get(Pillar.numrects() - 1).getX() +
                  (rects.get(Pillar.numrects() - 1).getWidth() / 4) *
                  3
                );

                line.setStartY(234.5);
                line.setEndY(234.5);

                System.out.println("im here");

                addRectangle();

                TranslateTransition movePane = new TranslateTransition(
                  Duration.seconds(0.5),
                  pane
                );

                double targetX = rects.get(Pillar.numrects() - 2).getX() - 100;
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
              dieded("straight");
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
    Rectangle lastRect = rects.get(Pillar.numrects() - 1);
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
      // first rect gap to random val - add cherry to a random value between these
      double cherryXvalue = getRandomNumberBetween(
        firstRectGap,
        randomVal - 50
      );

      Image image = new Image(getClass().getResourceAsStream("cherry.png"));
      cherryView = new ImageView(image);
      cherryView.setFitWidth(50);
      cherryView.setFitHeight(50);
      cherryView.setY(445);
      cherryView.setX(cherryXvalue);
      System.out.println("cherryXvalue: " + cherryXvalue);
      pane.getChildren().add(cherryView);
    }

    // Add the new rectangle to the AnchorPane
    pane.getChildren().add(newRectangle);
    newRectangle.toBack();
  }

  public void dieded(String how) throws IOException {
    String path = "run.mp3";
    Media media = new Media(new File(path).toURI().toString());
    MediaPlayer mediaPlayer = new MediaPlayer(media);
    mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    mediaPlayer.setVolume(0.1);
    mediaPlayer.play();

    System.out.println("died");

    setClickEventsEnabled(false);

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

    fall.setOnFinished(afterFall -> {
      updateText(Integer.toString(score), scorenum);

      over_pane.setVisible(true);
    });

    transition.setOnFinished(moveTransOver -> {
      mediaPlayer.stop();
      sprite.setY(0.0);
      sprite.setScaleY(1);
      double translationY = newY - sprite.getY();

      fall.setByY(translationY);
      fall.play();
    });

    // Play the first animation
    transition.play();
  }

  public void initialize() {
    bg_image.setImage(Theme.getSelectedTheme().getBackgroundImage());
    sprite.setImage(Theme.getSelectedTheme().getSpriteImage());
    setClickEventsEnabled(true);
    player = Player.getInstance(sprite);

    Rectangle firstRectangle = new Rectangle(40, 437, 100, 283.0);
    sprite.toFront();
    System.out.println(sprite.getY());

    pane.getChildren().add(firstRectangle);

    sprite.setX(40);
    rects.add(firstRectangle);

    addRectangle();

    attachMouseHandlers();
  }

  @FXML
  public void switchToPlay(ActionEvent event) throws IOException {
    Player.resetInstance();

    // close the current stage
    Stage currentStage = (Stage) ((Node) event.getSource()).getScene()
      .getWindow();
    currentStage.close();

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

  public Boolean getClickEventsEnabled() {
    return clickEventsEnabled;
  }

  public void setClickEventsEnabled(Boolean clickEventsEnabled) {
    this.clickEventsEnabled = clickEventsEnabled;
  }
}
