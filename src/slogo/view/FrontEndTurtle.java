package slogo.view;


import javafx.scene.image.ImageView;

/**
 * @author Ji Yun Hyo
 */
public class FrontEndTurtle extends FrontEndSprite {


  /**
   * Constructor for FrontEndTurtle
   * @param xCoord x coordinate of the turtle
   * @param yCoord y coordinate of the turtle
   * @param turtleImage image of the turtle
   * @param penState state of the pen
   */
  public FrontEndTurtle(double xCoord, double yCoord, ImageView turtleImage, double penState) {
    super(xCoord, yCoord, turtleImage, penState);
  }

}
