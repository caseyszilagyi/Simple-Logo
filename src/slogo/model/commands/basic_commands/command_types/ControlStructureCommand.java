package slogo.model.commands.basic_commands.command_types;

import java.util.List;
import java.util.Map;
import slogo.model.commands.basic_commands.BasicCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * This abstract class is designed to be used by any command that dictates the flow of information.
 * For this reason, any class that extends this class might need access to user defined
 * commands/variables
 */
public abstract class ControlStructureCommand extends Command {

  private final Map<String, Double> VARIABLE_MAP;
  private final Map<String, TreeNode> COMMAND_MAP;
  private final CommandInformationBundle INFORMATION_BUNDLE;

  /**
   * @param informationBundle The bundle that has the user defined variables/commands
   */
  public ControlStructureCommand(CommandInformationBundle informationBundle) {
    COMMAND_MAP = informationBundle.getCommandMap();
    VARIABLE_MAP = informationBundle.getVariableMap();
    INFORMATION_BUNDLE = informationBundle;
  }

  /**
   * Gets the TreeNode representing a certain variable
   *
   * @param name The variable name
   * @return The TreeNode
   */
  protected Double getVariable(String name) {
    return VARIABLE_MAP.get(name);
  }

  /**
   * Sets a variable name to a TreeNode
   *
   * @param name  The variable name
   * @param value The TreeNode
   */
  protected void setVariable(String name, Double value) {
    VARIABLE_MAP.put(name, value);
  }

  /**
   * Gets the TreeNode representing a certain user defined command
   *
   * @param name The variable name
   * @return The TreeNode
   */
  protected TreeNode getCommand(String name) {
    return COMMAND_MAP.get(name);
  }

  /**
   * Gets a command name to a TreeNode that represents it
   *
   * @param name    The command name
   * @param command The TreeNode that represents it
   */
  protected void setCommand(String name, TreeNode command) {
    COMMAND_MAP.put(name, command);
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
