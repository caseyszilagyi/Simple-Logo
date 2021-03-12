package slogo.model.commands.basic_commands.command_types;

import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;
import slogo.model.commands.basic_commands.BasicCommand;
import slogo.model.commands.basic_commands.UserDefinedCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * This abstract class is designed to be used by any command that dictates the flow of information.
 * For this reason, any class that extends this class might need access to user defined
 * commands/variables
 */
public abstract class ControlStructureCommand extends Command {

  private final Map<String, Double> VARIABLE_MAP;
  private final Map<String, UserDefinedCommand> COMMAND_MAP;
  private final List<Map<String, Double>> PARAMETER_MAP;
  private final CommandInformationBundle INFORMATION_BUNDLE;

  /**
   * @param informationBundle The bundle that has the user defined variables/commands
   */
  public ControlStructureCommand(CommandInformationBundle informationBundle) {
    COMMAND_MAP = informationBundle.getCommandMap();
    VARIABLE_MAP = informationBundle.getVariableMap();
    INFORMATION_BUNDLE = informationBundle;
    PARAMETER_MAP = informationBundle.getParameterMap();
  }

  /**
   * Gets the double value representing a certain variable
   *
   * @param name The variable name
   * @return The double value
   */
  protected double getVariable(String name) throws Exception {
    if (!VARIABLE_MAP.containsKey(name)) {
      throw new Exception("ParameterDNE");
    }
    return VARIABLE_MAP.get(name);
  }

  /**
   * Sets a variable name to a double
   *
   * @param name  The variable name
   * @param value The double value
   */
  protected void setVariable(String name, Double value) {
    VARIABLE_MAP.put(name, value);
  }

  /**
   * Gets the Double representing a parameter
   *
   * @param name The parameter name
   * @return The double value
   */
  protected double getParameter(String name) throws Exception {
    for (int i = PARAMETER_MAP.size() - 1; i >= 0; i--) {
      if (PARAMETER_MAP.get(i).containsKey(name)) {
        return PARAMETER_MAP.get(i).get(name);
      }
    }
    throw new Exception("ParameterDNE");
  }

  /**
   * Sets a parameter name to a double value
   *
   * @param name  The variable name
   * @param value The double value
   */
  protected void setParameter(String name, Double value) {
    PARAMETER_MAP.get(PARAMETER_MAP.size() - 1).put(name, value);
  }


  /**
   * Removes the last param map, used when a command with parameters is done executing
   */
  protected void removeParamMap() {
    PARAMETER_MAP.remove(PARAMETER_MAP.size()-1);
  }

  /**
   * Removes the last param map, used when a command with parameters is done executing
   */
  protected void addParamMap(Map map) {
    PARAMETER_MAP.add(map);
  }

  /**
   * Gets the TreeNode representing a certain user defined command
   *
   * @param name The variable name
   * @return The TreeNode
   */
  protected UserDefinedCommand getCommand(String name) {
    return COMMAND_MAP.get(name);
  }

  /**
   * Gets a command name to a TreeNode that represents it
   *
   * @param name    The command name
   * @param command The TreeNode that represents it
   */
  protected void setCommand(String name, UserDefinedCommand command) {
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
