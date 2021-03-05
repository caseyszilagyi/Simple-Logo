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
     * Sets the x position of the turtle
     * @param xPosition the X Position
     */
    public void setXPosition(double xPosition);

    /**
     * Sets the y position of the turtle
     */
    public void setYPosition(double yPosition);

    /**
     * Returns the x position of the turtle
     * @return x position of turtle
     */
    public double getXPosition();

    /**
     * Returns the y position of the turtle
     * @return
     */
    public double getYPosition();

    /**
     * Returns whether or not the turle is visible
     * @return 1 if visible, 0 if not visible
     */
    public int getVisibility();

    /**
     * Returns whether or not the turle is visible
     * @return 1 if visible, 0 if not visible
     */
    public int getPenState();

    /**
     * Sets whether or not the turle is visible
     * @return 1 if visible, 0 if not visible
     */
    public void setVisibility(int visibility);

    /**
     * Sets whether or not the turle is visible
     * @return 1 if visible, 0 if not visible
     */
    public void setPenState(int penState);



}