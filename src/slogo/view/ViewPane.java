package slogo.view;

import java.io.File;
import java.lang.Math;

import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Creates the view for where the turtle will be displayed
 * @author Kathleen Chen
 * @author Ji Yun Hyo
 */
public class ViewPane {
  private static final double TURTLE_WIDTH = 70.0;
  private static final double TURTLE_HEIGHT = 70.0;

  private static final int rows = 1010;
  private static final int cols = 1010;

  private static final String VIEW_PANE_ID = "ViewPane";
  private static final String PANE_BOX_ID = "TurtleView";
  private static final String CHOICE_PANE_ID = "ChoicePane";
  private static final String BACKGROUND_COLOR_PICKER_ID = "BackgroundColorPicker";
  private static final String COLOR_PICKER = "color-picker";
  private static final String BUTTON = "button";
  private static final String TURTLE_IMAGE_BUTTON = "Choose turtle image";

  private BorderPane viewPane;
  private AnchorPane paneBox;
  private ImageView turtle;
  private HBox choicePane;
  private Image turtleImage;
  private ColorPicker penColorPicker;
  private ColorPicker backgroundColorPicker;
  private Stage stage;

  private double centerX = 358.0;
  private double centerY = 258.5;
  private boolean penUP = false;
  private Color penColor = Color.BLACK;
  private String turtleImageFile = "Turtle2.gif";
  private Color defaultPenColor = Color.BLACK;

  public ViewPane(Stage s) {
    stage = s;
    viewPane = new BorderPane();
    viewPane.setId(VIEW_PANE_ID);
    viewPane.getStyleClass().add(VIEW_PANE_ID);
    paneBox = new AnchorPane();
    viewPane.setCenter(paneBox);
    paneBox.setId(PANE_BOX_ID);
    paneBox.getStyleClass().add(PANE_BOX_ID);
    createChoicePane();
    viewPane.setTop(choicePane);
    createTurtle();
  }

  private void createChoicePane() {
    choicePane = new HBox();
    choicePane.getStyleClass().add(CHOICE_PANE_ID);

    String defaultBackgroundColor = "d3d3d3";
    backgroundColorPicker = new ColorPicker(Color.valueOf(defaultBackgroundColor));
    backgroundColorPicker.setOnAction(event -> changeBackgroundColor());
    backgroundColorPicker.setId(BACKGROUND_COLOR_PICKER_ID);
    backgroundColorPicker.getStyleClass().add(COLOR_PICKER);
    choicePane.getChildren().add(backgroundColorPicker);

    penColorPicker = new ColorPicker(defaultPenColor);
    penColorPicker.setOnAction(event -> penColor = penColorPicker.getValue());
    penColorPicker.getStyleClass().add(COLOR_PICKER);
    choicePane.getChildren().add(penColorPicker);

    Button turtleImageButton = new Button(TURTLE_IMAGE_BUTTON);
    turtleImageButton.getStyleClass().add(BUTTON);
    choicePane.getChildren().add(turtleImageButton);
    turtleImageButton.setOnAction(event -> uploadTurtleImage());
  }

  private void changeBackgroundColor() {
    Paint fill = backgroundColorPicker.getValue();
    BackgroundFill backgroundFill =
            new BackgroundFill(fill,
                    new CornerRadii(10),
                    new Insets(10));
    Background background = new Background(backgroundFill);
    paneBox.setBackground(background);
  }

  // TODO: check for inappropriate file type (not .gif)
  private void uploadTurtleImage() {
    FileChooser turtleImageChooser = new FileChooser();
    File file = turtleImageChooser.showOpenDialog(stage);
    turtleImageFile = file.toURI().toString();
    turtleImage = new Image(turtleImageFile);
    turtle.setImage(turtleImage);
    turtle.setFitWidth(TURTLE_WIDTH);
    turtle.setFitHeight(TURTLE_HEIGHT);
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

    double screenWidth = paneBox.getWidth();
    double screenHeight = paneBox.getHeight();

    double coordinateWidth = screenWidth / rows;
    double coordinateHeight = screenHeight / cols;

    double x = screenWidth / 2 + xCoordinate * coordinateWidth - turtleCenterX;
    double y = screenHeight / 2 - yCoordinate * coordinateHeight - turtleCenterY;
    centerX = xCoordinate;
    centerY = yCoordinate;

    if(!penUP) {
      createLine(x, y);
    }

    turtle.setX(x);
    turtle.setY(y);

    System.out.println("x center: " + (screenWidth / 2 + 0 * coordinateWidth - turtleCenterX));
    System.out.println("y center: " + (screenHeight / 2 - 0 * coordinateHeight - turtleCenterY));
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

  //These magic index values need to be processed in some other way
  //Current set up for these parameters is not SHY enough since we have to have
  // prior knowledge about the order of these parameters
  public void updateTurtle(List<Double> parameters) {
    moveTurtle(parameters.get(0), parameters.get(1));
    turtle.setRotate(90 - parameters.get(2));
  }
}