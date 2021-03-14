package slogo.model.turtle;

import java.util.Arrays;
import java.util.List;

/**
 * This turtle class is the backend representation of the turtle so that commands can be called on
 * the turtle to move/rotate it
 */
public class Turtle implements TurtleState {

  private double angle;
  private double xPosition;
  private double yPosition;
  private double isVisible;
  private double penState;
  private double clearScreen;

  /**
   * Default constructor
   */
  public Turtle() {
    xPosition = 0;
    yPosition = 0;
    angle = 90;
    isVisible = 1;
    penState = 1;
    clearScreen = 0;
  }

  /**
   * Constructor used to put the turtle at a specific location. Useful for testing
   *
   * @param x         The x position
   * @param y         The y position
   * @param direction The angle the turtle is facing
   */
  public Turtle(double x, double y, double direction) {
    angle = direction;
    xPosition = x;
    yPosition = y;
    isVisible = 1;
    penState = 1;
  }

  /**
   * Gets the angle that the turtle is facing
   *
   * @return The angle
   */
  public double getAngle() {
    return angle;
  }

  /**
   * Sets the angle that the turtle is facing
   */
  public void setAngle(double newAngle) {
    angle = newAngle;
    angle = angle % 360;
    if (angle < 0) {
      angle += 360;
    }
  }

  /**
   * Gets the X position of the turtle
   *
   * @return The X position of the turtle
   */
  public double getXPosition() {
    return xPosition;
  }

  /**
   * Sets the X position of the turtle
   */
  public void setXPosition(double newXPosition) {
    xPosition = newXPosition;
  }

  /**
   * Gets the Y position of the turtle
   *
   * @return The Y position of the turtle
   */
  public double getYPosition() {
    return yPosition;
  }

  /**
   * Sets the Y position of the turtle
   */
  public void setYPosition(double newYPosition) {
    yPosition = newYPosition;
  }

  /**
   * Changes the X position of the turtle by a certain amount
   *
   * @param changeX The change in X position
   */
  public void changeXPosition(double changeX) {
    xPosition += changeX;
  }

  /**
   * Changes the Y position of the turtle by a certain amount
   *
   * @param changeY The change in Y position
   */
  public void changeYPosition(double changeY) {
    yPosition += changeY;
  }

  ;

  /**
   * Changes the angle of the turtle by a certain amount
   *
   * @param rotate The change in the angle, in a counterclockwise direction
   */
  public void rotateCounterClockwise(double rotate) {
    angle += rotate;
    angle = angle % 360;
    if (angle < 0) {
      angle += 360;
    }
  }

  ;


  /**
   * Gets whether or not the turtle is visible
   *
   * @return The turtle's visibility
   */
  public double getVisibility() {
    return isVisible;
  }

  /**
   * Sets whether or not the turtle is visible
   *
   * @param visibility The visibility
   */
  public void setVisibility(double visibility) {
    isVisible = visibility;
  }

  /**
   * Gets the current pen state
   *
   * @return The pen state
   */
  public double getPenState() {
    return penState;
  }

  /**
   * Sets the pen state
   *
   * @param userPenState The pen state
   */
  public void setPenState(double userPenState) {
    penState = userPenState;
  }

  /**
   * Sets the parameter to clear the screen to a 1
   */
  public void clearScreen() {
    clearScreen = 1;
  }

  /**
   * Sets the parameter to clear the screen to a 0, which allows lines to be drawn
   */
  public void allowLines() {
    clearScreen = 0;
  }


  /**
   * Gets the parameters to pass to the front end to display
   *
   * @return The list of parameters
   */
  public List<Double> getFrontEndParameters() {
    return Arrays
        .asList(xPosition, yPosition, angle, penState, isVisible, clearScreen);
  }


}