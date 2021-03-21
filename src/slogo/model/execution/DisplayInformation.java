package slogo.model.execution;

import slogo.controller.BackEndExternalAPI;

/**
 * This class holds all of the information that modifies the display
 *
 * @author Casey Szilagyi
 */
public class DisplayInformation {

  private BackEndExternalAPI MODEL_CONTROLLER;
  private int backgroundColor = 0;
  private int penColor = 0;
  private int turtleShape = 0;
  private double penSize = 0;

  /**
   * Initializes this object to hold display information
   * @param modelController The model controller to communicate changes with the front end
   */
  public DisplayInformation(BackEndExternalAPI modelController){
    MODEL_CONTROLLER = modelController;
  }

  /**
   * Gets the current background color index
   * @return The index
   */
  public int getBackgroundColor() {
    return backgroundColor;
  }

  /**
   * Sets the current background color
   * @param backgroundColor The index of the color
   */
  public void setBackgroundColor(int backgroundColor) {
    MODEL_CONTROLLER.setBackgroundColor(backgroundColor);
    this.backgroundColor = backgroundColor;
  }

  /**
   * Gets the current pen color index
   * @return The index
   */
  public int getPenColor() {

    return penColor;
  }

  /**
   * Sets the current pen color
   * @param penColor The index of the color
   */
  public void setPenColor(int penColor) {
    MODEL_CONTROLLER.setPenColor(penColor);
    this.penColor = penColor;
  }

  /**
   * Gets the current turtle shape index
   * @return The index
   */
  public int getTurtleShape() {
    return turtleShape;
  }

  /**
   * Sets the current turtle shape
   * @param turtleShape The turtle shape
   */
  public void setTurtleShape(int turtleShape) {
    MODEL_CONTROLLER.setTurtleShape(turtleShape);
    this.turtleShape = turtleShape;
  }

  /**
   * Gets the current pen size
   * @return The pen size
   */
  public double getPenSize() {
    return penSize;
  }

  /**
   * Sets the current pen size
   * @param penSize The pen size
   */
  public void setPenSize(double penSize) {
    MODEL_CONTROLLER.setPenSize(penSize);
    this.penSize = penSize;
  }

  /**
   * Changes an index in the palette to a specific color
   * @param index The index in the palette to change
   * @param red RGB value for red
   * @param green RGB value for green
   * @param blue RGB value for blue
   */
  public void setPalette(int index, int red, int green, int blue){
    MODEL_CONTROLLER.setPalette(index, red, green, blue);
  }

}
