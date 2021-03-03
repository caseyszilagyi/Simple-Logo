package slogo.model;

/**
 * This turtle class is the backend representation of the turtle so that commands can be
 * called on the turtle to move/rotate it
 */
public class Turtle implements TurtleState {

    private double angle;
    private int xPosition;
    private int yPosition;
    /**
     * Default constructor
     */
    public Turtle() {
        xPosition = 0;
        yPosition = 0;
        angle = 0;
    }

    /**
     * Gets the angle that the turtle is facing
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
     * @return The X position of the turtle
     */
    public int getXPosition() {
        return xPosition;
    }

    /**
     * Gets the Y position of the turtle
     * @return The Y position of the turtle
     */
    public int getYPosition() {
        return yPosition;
    }

    /**
     *
     * @return
     */
    public int isVisible() {
        return 0;
    }

}