package slogo.model.turtle;

/**
 * This turtle class is the backend representation of the turtle so that commands can be called on
 * the turtle to move/rotate it
 */
public class Turtle implements TurtleState {

  private double angle;
  private double xPosition;
  private double yPosition;
  private int isVisible;
  private int penState;

  /**
   * Default constructor
   */
  public Turtle() {
    xPosition = 0;
    yPosition = 0;
    angle = 0;
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
  }

  /**
   * Sets the X position of the turtle
   */
  public void setXPosition(double newXPosition) {
    xPosition = newXPosition;
  }

  /**
   * Sets the Y position of the turtle
   */
  public void setYPosition(double newYPosition) {
    yPosition = newYPosition;
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

  /**
   * Sets the pen state
   * @param userPenState The pen state
   */
   public void setPenState(int userPenState){
    penState = userPenState;
   }

  /**
   * Gets the current pen state
   * @return The pen state
   */
  public int getPenState(){ return penState; };




}