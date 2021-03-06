package slogo.model.commands.basic_commands;

import slogo.model.execution.CommandInformationBundle;

/**
 * Command to get the XCoordinate of the turtle
 *
 * @author Casey Szilagyi
 */
public class XCoordinate implements BasicCommand {

  /**
   * Makes an instance of the XCoordinate Command
   *
   * @param commands The basic commands. This will be null since it is a query
   */
  public XCoordinate(BasicCommand... commands) {
  }

  /**
   * Gets the turtle's XCoordinate
   *
   * @param informationBundle The bundle that has the turtle object and variables
   * @return The XCoordinate
   */
  public double execute(CommandInformationBundle informationBundle) {
    return informationBundle.getTurtle().getXPosition();
  }
}
