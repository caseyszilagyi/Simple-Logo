package slogo.view;

import java.lang.Math;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;

/**
 * Creates the view for where the turtle will be displayed
 */
public class ViewPane {
  public static final String TURTLE_IMAGE = "Turtle2.gif";
  public static final double TURTLE_WIDTH = 70.0;
  public static final double TURTLE_HEIGHT = 70.0;

  public static final int rows = 21;
  public static final int cols = 21;

  private AnchorPane paneBox;
  private ImageView turtle;

  private double screenWidth;
  private double screenHeight;
  private double centerX = 325.75;
  private double centerY = 198;
  private double direction = 90;
  private boolean penUP = false;

  public ViewPane() {
    paneBox = new AnchorPane();
    // change once there is css file only used for testing
    paneBox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
            + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
            + "-fx-border-radius: 5;" + "-fx-border-color: purple;");
    screenWidth = paneBox.getWidth();
    screenHeight = paneBox.getHeight();
    createTurtle();
  }

  private void createTurtle() {
    Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(TURTLE_IMAGE));
    turtle = new ImageView(image);
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


    double coordinateWidth = screenWidth / cols;
    double coordinateHeight = screenHeight / cols;

    centerX = screenWidth / 2;
    centerY = screenHeight / 2;

    double x = centerX + xCoordinate * coordinateWidth - turtleCenterX;
    double y = centerY - yCoordinate * coordinateHeight - turtleCenterY;

    System.out.println(x);
    turtle.setX(x);
    System.out.println(turtle.getX());
    turtle.setY(y);
  }

  private double convertX(double xCoordinate){
    return centerX + xCoordinate * (screenWidth / cols) - (TURTLE_WIDTH / 2);
  }
  private double convertY(double yCoordinate){
    return centerY - yCoordinate * (screenHeight / cols) - (TURTLE_HEIGHT / 2);
  }

  public void moveTurtleByDistance(double distance){
    // do the calculations to make the turtle go forward
    // THIS WAS WAY HARDER THAN I THOGUGHT
    // because the angles/getrotate are all messed up
    double turtleX;
    double turtleY;
    System.out.println(turtle.getRotate());
    double turtleAngle = ((-turtle.getRotate() - 90) * Math.PI) / (180);
    System.out.println("sin: " + Math.sin(turtleAngle));
    System.out.println("cos: " + Math.cos(turtleAngle));
    System.out.println(turtleAngle);
    turtleX = turtle.getX() - Math.cos(turtleAngle) * distance;
    turtleY = turtle.getY() + Math.sin(turtleAngle) * distance;
    if(!penUP){
      Line line1 = new Line(turtle.getX() + 35, turtle.getY()+ 65, turtleX+ 35, turtleY+ 65);
      paneBox.getChildren().add(line1);
    }


    turtle.setX(turtleX);
    turtle.setY(turtleY);

  }

  public void turnTurtle(double d){
    turtle.setRotate(turtle.getRotate() - d);
    System.out.println(90 - turtle.getRotate());
  }

  public AnchorPane getBox() {
    return paneBox;
  }


  public void switchPenState() {
    penUP = !penUP;
  }
}
