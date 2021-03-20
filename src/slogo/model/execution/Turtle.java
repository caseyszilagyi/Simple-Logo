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
  }

  /**
   * Gets the ID of the turtle
   * @return The ID
   */
  public int getID(){
    return ID;
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
    MODEL_CONTROLLER.setTurtleAngle(getAngle());
  }

  public void changePosition(double changeX, double changeY){
    changeXPosition(changeX);
    changeYPosition(changeY);
    MODEL_CONTROLLER.setTurtlePosition(getXPosition(), getYPosition());
  }

  public void setPosition(double xPosition, double yPosition){
    setXPosition(xPosition);
    setYPosition(yPosition);
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
    MODEL_CONTROLLER.setTurtleAngle(getAngle());
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

  public void updateFrontEnd(){
    MODEL_CONTROLLER.passInputToFrontEnd(getFrontEndParameters());
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