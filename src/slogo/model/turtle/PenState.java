package slogo.model.turtle;

import java.util.*;

/**
 * 
 */
public interface PenState {

    /**
     * Returns 1 if pen is down and returns 0 if pen is up
     * @return 1 if pendown, 0 if penup
     */
    public int isPenUp();

    /**
     * Returns the color of the pen
     * @return color of pen
     */
    public String getPenColor();

    /**
     * Sets the color of the pen
     */
    public void setPenColor(String color);

}