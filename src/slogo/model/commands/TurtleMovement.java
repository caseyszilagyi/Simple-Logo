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

  /**
   * Still thinking about the way all of this will move around
   * Maybe have an actionable interface that has subclasses turtlemovement, penalterer, arithmetic, etc
   * that do different things?
   *
   * So we have 1 type of basic command that would make these objects, or multiple different abstract
   * types of basic commands? Then what do we do with these objects? Alter things in the backend
   * or pass information to the front end? Also how do things get altered? Pass these actionable objects
   * to the turtle? But what if something alters the pen? Do we just have some class that aggregates
   * the penstate and the turtle together, or should that all be in one class?
   *
   * Maybe have some executor class that has the turtle, the pen, and the ability to execute a command.
   * It takes this command and figures out what to do with it? Don't know how to do this without using instanceOf though
   *
   * Pass commands to a command executioner that can return an Integer. If the integer is null, then the command
   * has simply done its operation. If it is not null, then we get back an integer and can do whatever
   * we want with it
   *
   * Might need different handlers for different command types, and these handlers would kind of funciton
   * as the API in some sort of way? So have a turtle hadnler, arithmetic handler, conditional handler?
   *
   * Actually, now thinking might just have a turtlecommand and an othercommand. Othercommand returns some type of command
   * chaing object. Constants and variables would somehow
   * need to be integrated into this command chain object. This would give flexibility when executing commands, as all
   * of the Othercommands would simply return tthis same object type, whether it was an arithmetic
   *
   */

}
