package slogo.model.commands.basic_commands;

import slogo.model.execution.CommandInformationBundle;

/**
 * This command makes and stores a variable for later use
 *
 * @author Casey Szilagyi
 */
public class MakeVariable implements BasicCommand {

  private final String NAME;
  private final BasicCommand VALUE;

  /**
   * Makes an instance of the Make command
   *
   * @param name     The name of the variables
   * @param value The variable value
   */
  public MakeVariable(String name, BasicCommand value) {
    NAME = name;
    VALUE = value;
  }

  /**
   * Adds the variable to the bundle that holds the variables, and returns the value of the
   * variable
   *
   * @param informationBundle The information bundle needed to store the variable in
   * @return The distance forward that it moved
   */
  public double execute(CommandInformationBundle informationBundle) {
    informationBundle.addVariable(NAME, VALUE);
    return VALUE.execute(informationBundle);
  }
}
