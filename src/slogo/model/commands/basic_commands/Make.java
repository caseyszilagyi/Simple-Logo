package slogo.model.commands.basic_commands;

import slogo.model.execution.CommandInformationBundle;

/**
 * This command makes and stores a variable for later use
 *
 * @author Casey Szilagyi
 */
public class Make implements BasicCommand {

  private final String NAME;
  private final BasicCommand VALUE;

  /**
   * Makes an instance of the Make command
   *
   * @param name     The name of the variables
   * @param commands The variable value
   */
  public Make(String name, BasicCommand... commands) {
    NAME = name;
    VALUE = commands[0];
  }

  /**
   * Adds the variable to the bundle that holds the variables, and returns the value of the
   * variable
   *
   * @param informationBundle The information bundle needed to store the variable in
   * @return The distance forward that it moved
   */
  public int execute(CommandInformationBundle informationBundle) {
    int value = VALUE.execute(informationBundle);
    informationBundle.addVariable(NAME, value);
    return value;
  }
}
