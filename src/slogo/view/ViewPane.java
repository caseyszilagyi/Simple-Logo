package slogo.view;

import java.io.File;
import java.io.InputStream;
import java.lang.Math;

import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Creates the view for where the turtle will be displayed
 */
public class ViewPane {
  public static final String TURTLE_IMAGE = "Turtle2.gif";
  public static final double TURTLE_WIDTH = 70.0;
  public static final double TURTLE_HEIGHT = 70.0;

  public static final int rows = 101;
  public static final int cols = 101;

  private BorderPane viewPane;
  private AnchorPane paneBox;
  private ImageView turtle;
  private HBox choicePane;
  private Image turtleImage;
  private ColorPicker penColorPicker;
  private ColorPicker backgroundColorPicker;
  private FileChooser turtleImageChooser;
  private Stage stage;

  private double screenWidth;
  private double screenHeight;
  private double centerX = 280.5;
  private double centerY = 164.0;
  private double direction = 90;
  private boolean penUP = false;
  private Color penColor = Color.BLACK;
  private String turtleImageFile = "Turtle2.gif";

  public ViewPane(Stage s) {
    stage = s;
    viewPane = new BorderPane();
    paneBox = new AnchorPane();
    viewPane.setCenter(paneBox);
    // TODO: change once there is css file only used for testing
    viewPane.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
            + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
            + "-fx-border-radius: 5;" + "-fx-border-color: purple;");
    paneBox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
            + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
            + "-fx-border-radius: 5;" + "-fx-border-color: green;");
    createChoicePane();
    viewPane.setTop(choicePane);
    createTurtle();
  }

  private void createChoicePane() {
    double spacing = 5.0;
    choicePane = new HBox();
    choicePane.setSpacing(spacing);
    choicePane.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
            + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
            + "-fx-border-radius: 5;" + "-fx-border-color: aquamarine;");
    backgroundColorPicker = new ColorPicker(Color.WHITE);
    choicePane.getChildren().add(backgroundColorPicker);
    penColorPicker = new ColorPicker(Color.BLACK);
    choicePane.getChildren().add(penColorPicker);
    Button turtleImageButton = new Button("Choose turtle image");
    turtleImageChooser = new FileChooser();
    choicePane.getChildren().add(turtleImageButton);
    turtleImageButton.setOnAction(event -> uploadTurtleImage());
  }

  private void uploadTurtleImage() {
    File file = turtleImageChooser.showOpenDialog(stage);
    turtleImageFile = file.getName();
    turtleImage = new Image(turtleImageFile);
    turtle.setImage(turtleImage);
  }

  private void createTurtle() {
    turtleImage = new Image(turtleImageFile);
    turtle = new ImageView(turtleImage);
    turtle.setFitWidth(TURTLE_WIDTH);
    turtle.setFitHeight(TURTLE_HEIGHT);
    paneBox.getChildren().add(turtle);
    turtle.setX(centerX);
    turtle.setY(centerY);
  }

  public void moveTurtle(double xCoordinate, double yCoordinate) {
    double turtleCenterX = TURTLE_WIDTH / 2;
    double turtleCenterY = TURTLE_HEIGHT / 2;

    screenWidth = paneBox.getWidth();
    screenHeight = paneBox.getHeight();


    double coordinateWidth = screenWidth / rows;
    double coordinateHeight = screenHeight / cols;

    centerX = screenWidth / 2;
    centerY = screenHeight / 2;

    double x = centerX + xCoordinate * coordinateWidth - turtleCenterX;
    double y = centerY - yCoordinate * coordinateHeight - turtleCenterY;

    if(!penUP) {
      createLine(x, y);
    }

    turtle.setX(x);
    System.out.println(x);
    turtle.setY(y);
    System.out.println(y);
  }

  public void moveTurtleByDistance(double distance){
    // do the calculations to make the turtle go forward
    // THIS WAS WAY HARDER THAN I THOGUGHT
    // because the angles/getrotate are all messed up
    double turtleX;
    double turtleY;
    double turtleAngle = ((-turtle.getRotate() - 90) * Math.PI) / (180);
    turtleX = turtle.getX() - Math.cos(turtleAngle) * distance;
    turtleY = turtle.getY() + Math.sin(turtleAngle) * distance;
    if(!penUP){
      createLine(turtleX, turtleY);
    }

    turtle.setX(turtleX);
    turtle.setY(turtleY);

  }

  private void createLine(double x, double y) {
    Line line1 = new Line(turtle.getX() + TURTLE_WIDTH / 2, turtle.getY() + TURTLE_WIDTH / 2,
            x + TURTLE_HEIGHT / 2, y + TURTLE_HEIGHT / 2);
    line1.setStroke(penColor);
    paneBox.getChildren().add(line1);
  }

  public void turnTurtle(double d){
    turtle.setRotate(turtle.getRotate() - d);
  }

  public BorderPane getBox() {
    return viewPane;
  }

  public void switchPenState() {
    penUP = !penUP;
  }
}