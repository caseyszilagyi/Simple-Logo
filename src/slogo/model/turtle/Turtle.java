package slogo.model.turtle;

/**
 * This turtle class is the backend representation of the turtle so that commands can be called on
 * the turtle to move/rotate it
 */
public class Turtle implements TurtleState {

  private double angle;
  private int xPosition;
  private int yPosition;
  private int isVisible;

  /**
   * Default constructor
   */
  public Turtle() {
    xPosition = 0;
    yPosition = 0;
    angle = 0;
    isVisible = 1;
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
  }

  /**
   * Sets the X position of the turtle
   */
  public void setXPosition(int newXPosition) {
    xPosition = newXPosition;
  }

  /**
   * Sets the Y position of the turtle
   */
  public void setYPosition(int newYPosition) {
    yPosition = newYPosition;
  }

  /**
   * Gets the X position of the turtle
   *
   * @return The X position of the turtle
   */
  public int getXPosition() {
    return xPosition;
  }

  /**
   * Gets the Y position of the turtle
   *
   * @return The Y position of the turtle
   */
  public int getYPosition() {
    return yPosition;
  }

  /**
   * Gets whether or not the turtle is visible
   *
   * @return The turtle's visibility
   */
  public int getVisibility() {
    return isVisible;
  }

  /**
   * Sets whether or not the turtle is visible
   *
   * @param visibility The visibility
   */
  public void setVisibility(int visibility) {
    isVisible = visibility;
  }

}