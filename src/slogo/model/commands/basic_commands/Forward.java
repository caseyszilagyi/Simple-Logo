package slogo.model.commands.basic_commands;

import slogo.model.commands.TurtleMovement;

/**
 * The forward command
 *
 * @author Casey Szilagyi
 */
public class Forward implements BasicCommand {

  private final int DISTANCE;

  /**
   * Makes an instance of the forward command
   *
   * @param args The distance forward that it will move
   */
  public Forward(int... args) {
    DISTANCE = args[0];
  }

  /**
   * Makes the TurtleMovement object that dictates how the turtle will move
   *
   * @return The TurtleMovement object
   */
  public TurtleMovement execute() {
    return new TurtleMovement(DISTANCE, 0);
  }
}
