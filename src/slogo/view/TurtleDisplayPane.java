package slogo.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.List;

public class TurtleDisplayPane {
  private static final double TURTLE_WIDTH = 50;
  private static final double TURTLE_HEIGHT = 50;

  private static final int rows = 700;
  private static final int cols = 600;

  private static final String PANE_BOX_ID = "TurtleView";
  private static final String LINE_ID = "Line";
  private final double centerX;
  private final double centerY;
  private BorderPane viewPane;
  private AnchorPane turtleViewPane;
  private ImageView turtle;
  private boolean penUP = false;

    public TurtleDisplayPane(BorderPane root) {
    viewPane = root;
    turtleViewPane = new AnchorPane();
    viewPane.setCenter(turtleViewPane);
    turtleViewPane.setId(PANE_BOX_ID);
    turtleViewPane.getStyleClass().add(PANE_BOX_ID);

    //set size of the ViewPane
    turtleViewPane.setMaxHeight(cols);
    turtleViewPane.setMaxWidth(rows);
    turtleViewPane.setMinHeight(cols);
    turtleViewPane.setMinWidth(rows);

    //Get the center
    centerX = rows / 2 - TURTLE_HEIGHT / 2;
    centerY = cols / 2 - TURTLE_WIDTH / 2;

    createTurtle();
  }

  private void createTurtle() {
    String turtleImageFile = "Turtle2.gif";
    Image turtleImage = new Image(turtleImageFile);
    turtle = new ImageView(turtleImage);
    turtle.setFitWidth(TURTLE_WIDTH);
    turtle.setFitHeight(TURTLE_HEIGHT);
    turtleViewPane.getChildren().add(turtle);
    turtle.setX(centerX);
    turtle.setY(centerY);
  }

  public void moveTurtle(double xCoordinate, double yCoordinate, Color penColor) {
    double turtleCenterX = TURTLE_WIDTH / 2;
    double turtleCenterY = TURTLE_HEIGHT / 2;

    double screenWidth = turtleViewPane.getWidth();
    double screenHeight = turtleViewPane.getHeight();

    double coordinateWidth = screenWidth / rows;
    double coordinateHeight = screenHeight / cols;

    double x = screenWidth / 2 + xCoordinate * coordinateWidth - turtleCenterX;
    double y = screenHeight / 2 - yCoordinate * coordinateHeight - turtleCenterY;

    if (!penUP) {
      createLine(x, y, penColor);
    }

    turtle.setX(x);
    turtle.setY(y);
  }

  private void createLine(double x, double y, Color penColor) {
    Line line1 = new Line(turtle.getX() + TURTLE_WIDTH / 2, turtle.getY() + TURTLE_WIDTH / 2,
      x + TURTLE_HEIGHT / 2, y + TURTLE_HEIGHT / 2);
    line1.setStroke(penColor);
    line1.setId(LINE_ID);
    turtleViewPane.getChildren().add(line1);
  }

  private void reset() {
    turtleViewPane.getChildren().clear();
    createTurtle();
  }

  public void setBackground(Background background) {
    turtleViewPane.setBackground(background);
  }

  public void updateTurtle(List<Double> parameters) {
    turtle.setRotate(90 - parameters.get(2));
    setPenState(!(parameters.get(3) == 1));
    turtle.setVisible(parameters.get(4) == 1);
    if (parameters.get(5) == 1) {
      reset();
    }
  }

  private void setPenState(boolean penState) {
    penUP = penState;
  }

  public void setTurtleImage(Image turtleImage) {
    turtle.setImage(turtleImage);
    turtle.setFitWidth(TURTLE_WIDTH);
    turtle.setFitHeight(TURTLE_HEIGHT);
    turtle.setId("Turtle");
  }
}