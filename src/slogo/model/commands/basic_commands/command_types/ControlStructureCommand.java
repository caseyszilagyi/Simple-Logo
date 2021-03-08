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
public abstract class ControlStructureCommand implements BasicCommand {

  Map<String,TreeNode> VARIABLE_MAP;
  Map<String, TreeNode> COMMAND_MAP;

  /**
   * @param informationBundle The bundle that has the user defined variables/commands
   * @param children          The TreeNodes that are used to execute this command
   */
  public ControlStructureCommand(CommandInformationBundle informationBundle,
      List<TreeNode> children) {
    COMMAND_MAP = informationBundle.getCommandMap();
    VARIABLE_MAP = informationBundle.getVariableMap();
  }

  /**
   * Gets the TreeNode representing a certain variable
   * @param name The variable name
   * @return The TreeNode
   */
  public TreeNode getVariable(String name){
    return VARIABLE_MAP.get(name);
  }

  /**
   * Sets a variable name to a TreeNode
   * @param name The variable name
   * @param value The TreeNode
   */
  public void setVariable(String name, TreeNode value){
    VARIABLE_MAP.put(name, value);
  }

  /**
   * Gets the TreeNode representing a certain user defined command
   * @param name The variable name
   * @return The TreeNode
   */
  public TreeNode getCommand(String name){
    return COMMAND_MAP.get(name);
  }

  /**
   * Gets a command name to a TreeNode that represents it
   * @param name The command name
   * @param command The TreeNode that represents it
   */
  public void setCommand(String name, TreeNode command){
    COMMAND_MAP.put(name, command);
  }




}
