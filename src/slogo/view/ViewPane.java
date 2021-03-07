package slogo.view;

import java.lang.Math;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

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

    turtle.setX(x);
    turtle.setY(y);
  }

  public AnchorPane getBox() {
    return paneBox;
  }


}
