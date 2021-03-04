package slogo.model.turtle;

import java.util.*;

/**
 * 
 */
public interface TurtleState {

    /**
     * Returns angle of turtle
     * @return angle of turtle
     */
    public double getAngle();

    /**
     * Sets the angle of the turtle
     */
    public void setAngle(double angle);

    /**
     * Sets the x position of the turtle
     */
    public void setXPosition(int xPosition);

    /**
     * Sets the y position of the turtle
     */
    public void setYPosition(int yPosition);

    /**
     * Returns the x position of the turtle
     * @return x position of turtle
     */
    public int getXPosition();

    /**
     * Returns the y position of the turtle
     * @return
     */
    public int getYPosition();

    /**
     * Returns whether or not the turle is visible
     * @return 1 if visible, 0 if not visible
     */
    public int getVisibility();

}