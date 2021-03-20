package slogo.view;

import java.util.ArrayDeque;
import java.util.Deque;
import javafx.animation.Timeline;
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
  private double penUP;
  double x;
  double y;
  private int frameDelay = 0;
  private int sleepTimer = 0;
  private Color penColor;
  private Deque<Double> xPosition;
  private Deque<Double> yPosition;
  private Deque<Double> angles;
  private Deque<String> typeToBeUpdated;
  private Deque<Double> penStates;
  private int INCREMENT_FACTOR = 10;
  private double lastXPosition = 0;
  private double lastYPosition = 0;
  private boolean canUpdateAngle = false;
  private double lastAngle = 90;
  private Timeline timeline;

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

    xPosition = new ArrayDeque<>();
    yPosition = new ArrayDeque<>();
    angles = new ArrayDeque<>();
    typeToBeUpdated = new ArrayDeque<>();
    penStates = new ArrayDeque<>();


    createTurtle();

//    animationTimer.start();
  }



  public void updateTurtlePosition() {
    String nextUpdate = "";
//
//    System.out.println("Pen State: " + penUP);
    if(!typeToBeUpdated.isEmpty()) {
      nextUpdate = typeToBeUpdated.removeFirst();

      if (!xPosition.isEmpty() && !yPosition.isEmpty() && nextUpdate.equals("Positions")) {
        double nextX = xPosition.pop();
        double nextY = yPosition.pop();
//
//
//        System.out.println("Current Turtle X Positions: " + turtle.getX());
//        System.out.println("Current Turtle Y Positions: " + turtle.getY());
//        System.out.println("Next Turtle X Position: " + nextX);
//        System.out.println("Next Turtle Y Position: " + nextY);
        if (penUP == 1) {
          createLine(nextX, nextY, penColor);
        }
        turtle.setX(nextX);
        turtle.setY(nextY);
      } else if (!angles.isEmpty() && nextUpdate.equals("Angles")) {

        turtle.setRotate(angles.pop());
      } else if (!penStates.isEmpty() && nextUpdate.equals("Pen")){
        penUP = penStates.removeFirst();
      } else if (nextUpdate.equals("Reset")){
        clearScreen();
      }
    }
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
    turtle.setRotate(0);
    lastXPosition = centerX;
    lastYPosition = centerY;
  }

  public void moveTurtle(double xCoordinate, double yCoordinate, Color penColor) {
    this.penColor = penColor;

    double turtleCenterX = TURTLE_WIDTH / 2;
    double turtleCenterY = TURTLE_HEIGHT / 2;

    double screenWidth = turtleViewPane.getWidth();
    double screenHeight = turtleViewPane.getHeight();

    double coordinateWidth = screenWidth / rows;
    double coordinateHeight = screenHeight / cols;

    x = screenWidth / 2 + xCoordinate * coordinateWidth - turtleCenterX;
    y = screenHeight / 2 - yCoordinate * coordinateHeight - turtleCenterY;



    double xIncrement = (x - lastXPosition)/ INCREMENT_FACTOR;
    double yIncrement = (y - lastYPosition)/ INCREMENT_FACTOR;

//    System.out.println("X Increment: " + xIncrement);

      for(int i = 1; i <= INCREMENT_FACTOR; i++){
//        System.out.println(turtle.getX() + xIncrement * i);
//        System.out.println(turtle.getY() + yIncrement * i);
        xPosition.add(lastXPosition + xIncrement * i);
        yPosition.add(lastYPosition + yIncrement * i);
        typeToBeUpdated.add("Positions");
      }



//    xPosition.add(x);
//    yPosition.add(y);

    lastXPosition = x;
    lastYPosition = y;

  }

  private void createLine(double x, double y, Color penColor) {
    Line line1 = new Line(turtle.getX() + TURTLE_WIDTH / 2, turtle.getY() + TURTLE_WIDTH / 2,
      x + TURTLE_HEIGHT / 2, y + TURTLE_HEIGHT / 2);
    line1.setStroke(penColor);
    line1.setId(LINE_ID);
    turtleViewPane.getChildren().add(line1);
  }

  private void clearScreen() {

    xPosition.clear();
    yPosition.clear();
    angles.clear();
    typeToBeUpdated.clear();
    penStates.clear();


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
      angles.add(90 - parameters.get(2));
      typeToBeUpdated.add("Angles");
    }
 //   turtle.setRotate(90 - parameters.get(2));
      penStates.add(parameters.get(3));
      typeToBeUpdated.add("Pen");
    turtle.setVisible(parameters.get(4) == 1);

  }

  public void setTurtleImage(Image turtleImage) {
    turtle.setImage(turtleImage);
    turtle.setFitWidth(TURTLE_WIDTH);
    turtle.setFitHeight(TURTLE_HEIGHT);
    turtle.setId("Turtle");
  }
//
//  public void moveTurtleByDistance(double distance) {
//    // do the calculations to make the turtle go forward
//    // THIS WAS WAY HARDER THAN I THOGUGHT
//    // because the angles/getrotate are all messed up
//    double turtleX;
//    double turtleY;
//    double turtleAngle = ((-turtle.getRotate() - 90) * Math.PI) / (180);
//    turtleX = turtle.getX() - Math.cos(turtleAngle) * distance;
//    turtleY = turtle.getY() + Math.sin(turtleAngle) * distance;
//    if (!penUP) {
//      //createLine(turtleX, turtleY);
//    }
//
//    turtle.setX(turtleX);
//    turtle.setY(turtleY);
//  }
}