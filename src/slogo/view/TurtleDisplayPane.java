package slogo.view;

import java.util.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class TurtleDisplayPane {
  private static final double TURTLE_WIDTH = 50;
  private static final double TURTLE_HEIGHT = 50;

  private static final String PANE_BOX_ID = "TurtleView";
  private static final String LINE_ID = "Line";
  private final double centerX;
  private final double centerY;
  private BorderPane viewPane;
  private AnchorPane turtleViewPane;
  private ImageView turtle;

  private double penUP = 1;
  double x;
  double y;
  private Color penColor;
  private Deque<Double> commandsToBeExecuted;
  private Deque<String> typeToBeUpdated;
  private int INCREMENT_FACTOR = 10;
  private double lastXPosition = 0;
  private double lastYPosition = 0;
  private double lastAngle = 90;
  private double rows;
  private double cols;
  private double penThickness = 1.0;
  private Set<Integer> activeTurtles;
  private Set<Integer> inactiveTurtles;

  public TurtleDisplayPane(BorderPane root, double r, double c) {
    viewPane = root;
    rows = r;
    cols = c;

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

    commandsToBeExecuted = new ArrayDeque<>();
    typeToBeUpdated = new ArrayDeque<>();
    activeTurtles = new HashSet<>();
    inactiveTurtles = new HashSet<>();


    createTurtle();

  }

  public void updateTurtlePosition() {
    String nextUpdate;
//
//    System.out.println("Pen State: " + penUP);
    if(!typeToBeUpdated.isEmpty()) {
      nextUpdate = typeToBeUpdated.removeFirst();

      if (nextUpdate.equals("Positions")) {
        double nextX = commandsToBeExecuted.pop();
        double nextY = commandsToBeExecuted.pop();

        if (penUP == 1) {
          createLine(nextX, nextY, penColor);
        }
        turtle.setX(nextX);
        turtle.setY(nextY);
      } else if (nextUpdate.equals("Angles")) {

        turtle.setRotate(90 - commandsToBeExecuted.pop());
      } else if (nextUpdate.equals("Pen")){
        penUP = commandsToBeExecuted.removeFirst();
      } else if (nextUpdate.equals("Visibility")){
        turtle.setVisible(commandsToBeExecuted.removeFirst() == 1);
      } else if (nextUpdate.equals("Clearscreen")){
        clearScreen();
      }
    }
  }


  private void createTurtle() {

    turtleViewPane.getChildren().clear();
    String turtleImageFile = "Turtle2.gif";
    Image turtleImage = new Image(turtleImageFile);
    turtle = new ImageView(turtleImage);
    turtle.setFitWidth(TURTLE_WIDTH);
    turtle.setFitHeight(TURTLE_HEIGHT);
    turtle.setId("Turtle");
    turtleViewPane.getChildren().add(turtle);
    turtle.setX(centerX);
    turtle.setY(centerY);
    turtle.setRotate(0);
    lastXPosition = centerX;
    lastYPosition = centerY;
  }

  public void moveTurtle(double xCoordinate, double yCoordinate, Color penColor) {
    this.penColor = penColor;

    x = turtleViewPane.getWidth() / 2 + xCoordinate * turtleViewPane.getWidth() / rows - TURTLE_WIDTH / 2;
    y = turtleViewPane.getHeight() / 2 - yCoordinate * turtleViewPane.getHeight() / cols - TURTLE_HEIGHT / 2;

    double xIncrement = (x - lastXPosition)/ INCREMENT_FACTOR;
    double yIncrement = (y - lastYPosition)/ INCREMENT_FACTOR;

    for(int i = 1; i <= INCREMENT_FACTOR; i++){
      commandsToBeExecuted.add(lastXPosition + xIncrement * i);
      commandsToBeExecuted.add(lastYPosition + yIncrement * i);
      typeToBeUpdated.add("Positions");
    }

    lastXPosition = x;
    lastYPosition = y;
  }

  private void createLine(double x, double y, Color penColor) {
    Line line1 = new Line(turtle.getX() + TURTLE_WIDTH / 2, turtle.getY() + TURTLE_WIDTH / 2,
            x + TURTLE_HEIGHT / 2, y + TURTLE_HEIGHT / 2);
    line1.setStroke(penColor);
    line1.setId(LINE_ID);
    line1.setStrokeWidth(penThickness);
    turtleViewPane.getChildren().add(line1);
  }

  void clearScreen() {
    commandsToBeExecuted.clear();
    typeToBeUpdated.clear();

    turtleViewPane.getChildren().clear();
    createTurtle();
  }

  public void setBackground(Background background) {
    turtleViewPane.setBackground(background);
  }

  public void updateTurtle(List<Double> parameters) {
    if (parameters.get(5) == 1) {
      clearScreen();
    }
    if(lastAngle != parameters.get(2)){
      lastAngle = parameters.get(2);
      commandsToBeExecuted.add(90 - parameters.get(2));
      typeToBeUpdated.add("Angles");
    }

    //   turtle.setRotate(90 - parameters.get(2));

    commandsToBeExecuted.add(parameters.get(3));
    typeToBeUpdated.add("Pen");

    commandsToBeExecuted.add(parameters.get(4));
    typeToBeUpdated.add("Visibility");

  }

  public void setTurtleImage(Image turtleImage) {
    turtle.setImage(turtleImage);
    turtle.setFitWidth(TURTLE_WIDTH);
    turtle.setFitHeight(TURTLE_HEIGHT);
    turtle.setId("Turtle");
  }

  public void updateCommandQueue(String commandType, List<Double> commandValues) {
    typeToBeUpdated.add(commandType);
    commandsToBeExecuted.addAll(commandValues);
  }

}