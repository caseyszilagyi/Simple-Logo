package slogo.model.commands.basic_commands;

import java.util.ArrayList;
import java.util.List;
import slogo.model.commands.basic_commands.command_types.MultipleTurtleCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * Defines a condition, and only turtles that meet that condition have the commands
 * executed
 *
 * @author Casey Szilagyi
 */
public class AskWith extends MultipleTurtleCommand {

  private final List<Integer> IDS = new ArrayList<>();
  private final TreeNode CONDITION;
  private final TreeNode COMMANDS;

  /**
   * Gets the commands and turtles to act on
   * @param informationBundle The bundle that contains all turtles
   * @param children 3 children, one of which has a command block with turtle IDS, one of which has the condition, and
   *                 the other which has the commands to run
   */
  public AskWith(CommandInformationBundle informationBundle, List<TreeNode> children){
    super(informationBundle);
    CONDITION = children.get(0);
    COMMANDS = children.get(1);
  }

  /**
   * Finds the turtles that meet the condition and executes the commands on them
   *
   * @return The ID of the last turtle in the list
   */
  @Override
  public double execute(){
    determineActiveTurtles(CONDITION);
    double result = executeNode(COMMANDS);
    removeActiveTurtleLayer();
    return result;
  }
}
