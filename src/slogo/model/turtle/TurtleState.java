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
     * @param angle The angle
     */
    public void setAngle(double angle);

    /**
     * Returns the x position of the turtle
     * @return x position of turtle
     */
    public double getXPosition();

    /**
     * Sets the x position of the turtle
     * @param xPosition the X Position
     */
    public void setXPosition(double xPosition);

    /**
     * Returns the y position of the turtle
     * @return the Y Position
     */
    public double getYPosition();

    /**
     * Sets the y position of the turtle
     * @param yPosition the Y Position
     */
    public void setYPosition(double yPosition);

    /**
     * Returns whether or not the turtle is visible
     * @return 1 if visible, 0 if not visible
     */
    public int getVisibility();

    /**
     * Sets whether or not the turtle is visible
     * @param visibility 1 if visible, 0 if not visible
     */
    public void setVisibility(int visibility);

    /**
     * Returns whether or not the pen is currently drawing a line behind the turtle
     * @return 1 if yes, 0 if no
     */
    public int getPenState();


    /**
     * Sets whether or not the pen is currently drawing a line behind the turtle
     * @return 1 if yes, 0 if no
     */
    public void setPenState(int penState);



}