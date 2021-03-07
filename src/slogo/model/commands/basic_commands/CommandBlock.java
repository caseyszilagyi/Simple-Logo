package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * The CommandBlock command is designed to hold an undefined amount of other commands. Can be used
 * to hold conditional blocks, loop blocks, or any time when multiple commands need to be grouped
 *
 * @author Casey Szilagyi
 */
public class CommandBlock implements BasicCommand {

  private final List<TreeNode> CHILDREN;

  /**
   * Makes an instance of the CommandBlock command
   *
   * @param nodes All of the children to be executed
   */
  public CommandBlock(List<TreeNode> nodes) {
    CHILDREN = nodes;
  }

  /**
   * Executes all of the children commands by looping through them. Returns whatever the last one
   * returns
   *
   * @param informationBundle The bundle of all information that is needed to execute commands
   * @return The final command value
   */
  public double execute(CommandInformationBundle informationBundle) {
    double val = 0;
    for (int i = 0; i < CHILDREN.size(); i++) {
      val = informationBundle.loadClass(CHILDREN.get(i)).execute(informationBundle);
    }
    return val;
  }
}
