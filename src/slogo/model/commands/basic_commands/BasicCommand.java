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
   * The execute command is what is called in order to alter the state of the turtle object
   *
   * @return An integer representing the result of the command
   */
  public double execute(CommandInformationBundle informationBundle);

}
