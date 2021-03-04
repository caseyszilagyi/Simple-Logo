package slogo.model.commands;

/**
 * Whenever a certain command is run, it makes a TurtleMovement object that represents the distance
 * forward as well as the rotation angle
 * @author CaseySzilagyi
 */
public class TurtleMovement {

  public final int DISTANCE;
  public final double ROTATION_ANGLE;

  /**
   * Makes an instance of the TurtleMovement object with the given distance traveled and angle rotation
   * @param distance The distance traveled
   * @param angle The rotation angle
   */
  public TurtleMovement(int distance, double angle){
    DISTANCE = distance;
    ROTATION_ANGLE = angle;
  }

  /**
   * The distance that the turtle moves
   * @return The distance
   */
  public int getDistance(){
    return DISTANCE;
  }

  /**
   * The angle that the turtle rotates
   * @return The angle
   */
  public double getAngle(){
    return ROTATION_ANGLE;
  }

}
