package slogo.model.commands.basic_commands;

import slogo.model.execution.CommandInformationBundle;

/**
 * This interface is designed to be used for all of the basic commands that the turtle can have
 * called on it
 *
 * @author Casey Szilagyi
 */
public interface BasicCommand {

  /**
   * The execute command is what is called in order to get the TurtleMovement object, which is used
   * to actually change the turtle's location/angle
   *
   * @return The TurtleMovement object
   */
  public int execute(CommandInformationBundle informationBundle);

}
