package slogo.model.execution;

import java.util.Arrays;
import java.util.List;
import slogo.controller.BackEndExternalAPI;
import slogo.controller.ModelController;

/**
 * This turtle class is the backend representation of the turtle so that commands can be called on
 * the turtle to move/rotate it
 */
public class Turtle {

  private final BackEndExternalAPI MODEL_CONTROLLER;

  private double angle = 90;
  private double xPosition = 0;
  private double yPosition = 0;
  private double isVisible = 1;
  private double penState = 1;
  private double clearScreen = 0;
  private int ID;

  /**
   * Default constructor
   */
  public Turtle(int turtleID, BackEndExternalAPI modelController) {
    MODEL_CONTROLLER = modelController;
    ID = turtleID;
    tellFrontEnd();
  }

  // Tells a front end when a new turtle is made
  private void tellFrontEnd(){
    MODEL_CONTROLLER.setActiveTurtle(ID);
    MODEL_CONTROLLER.setTurtlePosition(xPosition, yPosition);
    MODEL_CONTROLLER.setTurtleAngle(angle);
    MODEL_CONTROLLER.setPenState(penState);
    MODEL_CONTROLLER.setTurtleVisibility(isVisible);
  }

  /**
   * Gets the ID of the turtle
   *
   * @return The ID
   */
  public int getID() {
    return ID;
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
    MODEL_CONTROLLER.setTurtleAngle(getAngle());
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
    MODEL_CONTROLLER.setTurtleAngle(getAngle());
  }

  /**
   * Changes the turtle's position by a certain amount
   *
   * @param changeX The change in X position
   * @param changeY The change in Y position
   */
  public void changePosition(double changeX, double changeY) {
    xPosition += changeX;
    yPosition += changeY;
    MODEL_CONTROLLER.setTurtlePosition(getXPosition(), getYPosition());
  }

  /**
   * Sets the turtle's position to certain x and y values
   *
   * @param xPos The X position
   * @param yPos The Y position
   */
  public void setPosition(double xPos, double yPos) {
    xPosition = xPos;
    yPosition = yPos;
    MODEL_CONTROLLER.setTurtlePosition(getXPosition(), getYPosition());
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
   * Gets the Y position of the turtle
   *
   * @return The Y position of the turtle
   */
  public double getYPosition() {
    return yPosition;
  }

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
    MODEL_CONTROLLER.setTurtleVisibility(visibility);
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
    MODEL_CONTROLLER.setPenState(userPenState);
  }

  /**
   * Sets the parameter to clear the screen to a 1
   */
  public void clearScreen() {
    clearScreen = 1;
    MODEL_CONTROLLER.passInputToFrontEnd(getFrontEndParameters());
    MODEL_CONTROLLER.clearScreen();
    clearScreen = 0;
  }

  /**
   * Gets the parameters to pass to the front end to display
   *
   * @return The list of parameters
   */
  public List<Double> getFrontEndParameters() {
    return Arrays
        .asList(xPosition, yPosition, angle, penState, isVisible, clearScreen, ID/1.0);
  }

}