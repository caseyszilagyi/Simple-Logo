package slogo.model;

import java.util.*;

/**
 *
 */
public interface TurtleState {

  /**
   * Returns angle of turtle
   *
   * @return angle of turtle
   */
  public int getAngle();

  /**
   * Sets the angle of the turtle
   */
  public void setAngle(int angle);

  /**
   * Returns the x position of the turtle
   *
   * @return x position of turtle
   */
  public int getXPosition();

  /**
   * Sets the x position of the turtle
   */
  public void setXPosition(int xPosition);

  /**
   * Returns the y position of the turtle
   *
   * @return
   */
  public int getYPosition();

  /**
   * Sets the y position of the turtle
   */
  public void setYPosition(int yPosition);

  /**
   * Returns whether or not the turle is visible
   *
   * @return 1 if visible, 0 if not visible
   */
  public int isVisible();

}