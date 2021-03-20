package slogo.model.commands.basic_commands.command_types;

import slogo.model.commands.basic_commands.UserDefinedCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * This abstract class is designed to be used by any command that dictates the flow of information.
 * For this reason, any class that extends this class might need access to user defined
 * commands/variables
 */
public abstract class ControlStructureCommand extends Command {

  private final CommandInformationBundle INFORMATION_BUNDLE;

  /**
   * @param informationBundle The bundle that has the user defined variables/commands
   */
  public ControlStructureCommand(CommandInformationBundle informationBundle) {
    INFORMATION_BUNDLE = informationBundle;
  }

  /**
   * Gets a command name to a TreeNode that represents it
   *
   * @param name    The command name
   * @param command The TreeNode that represents it
   */
  protected void setCommand(String name, UserDefinedCommand command) {
    INFORMATION_BUNDLE.addCommand(name, command);
  }

  /**
   * Sets a variable name to a double
   *
   * @param name  The variable name
   * @param value The double value
   */
  protected void setVariable(String name, Double value) {
    INFORMATION_BUNDLE.addVariable(name, value);
  }

  /**
   * Sets a parameter name to a double
   *
   * @param name  The name of the parameter
   * @param value The value of the parameter
   */
  protected void setParameter(String name, Double value) {
    INFORMATION_BUNDLE.addParameter(name, value);
  }

  /**
   * Removes the last param map, used when a command with parameters is done executing
   */
  protected void removeParameterMap() {
    INFORMATION_BUNDLE.removeParameterMap();
  }

  /**
   * Removes the last param map, used when a command with parameters is done executing
   */
  protected void addParameterMap() {
    INFORMATION_BUNDLE.addParameterMap();
  }

  /**
   * Adds a layer of turtles, used for scope of tell/ask statements
   */
  protected void addTurtleLayer(){
    INFORMATION_BUNDLE.addActiveTurtleLayer();
  }

  /**
   * Removes a layer of turtles, used for scope of tell/ask statements
   */
  protected void removeTurtleLayer(){
    INFORMATION_BUNDLE.removeActiveTurtleLayer();
  }
  /**
   * Executes a block of commands. This is used for looping/conditionals
   *
   * @param node The node that holds all of the commands to execute
   * @return The double value that represents the executed command block
   */
  protected double executeBlock(TreeNode node) {
    return loadClass(INFORMATION_BUNDLE, node).execute();
  }


}
