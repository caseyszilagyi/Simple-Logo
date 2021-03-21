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
  private Map<Integer, ImageView> activeTurtles;
  private Map<Integer, ImageView> inactiveTurtles;
  private Map<Integer, Coordinates> lastPositions;
  private int FIRST_TURTLE = 1;
  private int currentID = 1;

  String turtleImageFile = "Turtle2.gif";
  String inactiveTurtleImageFile = "Turtle3.gif";
  String movingTurtleImageFile = "Turtle4.gif";


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
    activeTurtles = new HashMap<>();
    inactiveTurtles = new HashMap<>();
    lastPositions = new HashMap<>();


    createTurtle(FIRST_TURTLE);

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
        activeTurtles.get(currentID).setX(nextX);
        activeTurtles.get(currentID).setY(nextY);
      } else if (nextUpdate.equals("Angles")) {

        activeTurtles.get(currentID).setRotate(90 - commandsToBeExecuted.pop());
      } else if (nextUpdate.equals("Pen")){
        penUP = commandsToBeExecuted.removeFirst();
      } else if (nextUpdate.equals("Visibility")){
        activeTurtles.get(currentID).setVisible(commandsToBeExecuted.removeFirst() == 1);
      } else if (nextUpdate.equals("Clearscreen")){
        clearScreen();
      } else if (nextUpdate.equals("SetID")){
        currentID = (int) Math.round(commandsToBeExecuted.pop());

   //     updateTurtleImages();
      }

    }

  }

  private void updateTurtleImages() {
    for(Map.Entry<Integer,ImageView> entry : activeTurtles.entrySet()){
      entry.getValue().setImage(new Image(turtleImageFile));
    }
    for(Map.Entry<Integer,ImageView> entry : inactiveTurtles.entrySet()){
      entry.getValue().setImage(new Image(inactiveTurtleImageFile));
    }
  }


  private void createTurtle(int id) {

    Image turtleImage = new Image(turtleImageFile);
    String imageID = "Turtle" + id;
    ImageView turtle = new ImageView(turtleImage);
    turtle.setFitWidth(TURTLE_WIDTH);
    turtle.setFitHeight(TURTLE_HEIGHT);
    turtle.setId(imageID);
    turtleViewPane.getChildren().add(turtle);

    turtle.setX(centerX);
    turtle.setY(centerY);
    turtle.setRotate(0);

    Coordinates turtleCoordinates = new Coordinates(centerX, centerY);

    activeTurtles.put(id, turtle);
    lastPositions.put(id, turtleCoordinates);

//    lastXPosition = centerX;
//    lastYPosition = centerY;
  }

  public void moveTurtle(double xCoordinate, double yCoordinate, Color penColor) {
    this.penColor = penColor;

    x = turtleViewPane.getWidth() / 2 + xCoordinate * turtleViewPane.getWidth() / rows - TURTLE_WIDTH / 2;
    y = turtleViewPane.getHeight() / 2 - yCoordinate * turtleViewPane.getHeight() / cols - TURTLE_HEIGHT / 2;

    double xIncrement = (x - lastPositions.get(currentID).getxCoord())/ INCREMENT_FACTOR;
    double yIncrement = (y - lastPositions.get(currentID).getyCoord())/ INCREMENT_FACTOR;

    for(int i = 1; i <= INCREMENT_FACTOR; i++){
      commandsToBeExecuted.add(lastPositions.get(currentID).getxCoord() + xIncrement * i);
      commandsToBeExecuted.add(lastPositions.get(currentID).getyCoord() + yIncrement * i);
      typeToBeUpdated.add("Positions");
    }

    lastPositions.get(currentID).setxCoord(x);
    lastPositions.get(currentID).setyCoord(y);
//    lastXPosition = x;
//    lastYPosition = y;
  }

  private void createLine(double x, double y, Color penColor) {
    Line line1 = new Line(activeTurtles.get(currentID).getX() + TURTLE_WIDTH / 2, activeTurtles.get(currentID).getY() + TURTLE_WIDTH / 2,
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
    createTurtle(FIRST_TURTLE);
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
    activeTurtles.get(currentID).setImage(turtleImage);
    activeTurtles.get(currentID).setFitWidth(TURTLE_WIDTH);
    activeTurtles.get(currentID).setFitHeight(TURTLE_HEIGHT);
    activeTurtles.get(currentID).setId("Turtle" + currentID);
  }

  public void updateCommandQueue(String commandType, List<Double> commandValues) {
    typeToBeUpdated.add(commandType);
    commandsToBeExecuted.addAll(commandValues);
  }

  public void setActiveTurtle(int turtleID) {
    if(!activeTurtles.containsKey(turtleID) && !inactiveTurtles.containsKey(turtleID)){
      createTurtle(turtleID);
    }
    commandsToBeExecuted.add((double) turtleID);
    typeToBeUpdated.add("SetID");
  }
}