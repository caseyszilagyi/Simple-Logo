package slogo.model.commands.basic_commands;

import java.util.ArrayList;
import java.util.List;
import slogo.model.commands.basic_commands.command_types.ControlStructureCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * The CommandBlock command is designed to hold an undefined amount of other commands. Can be used
 * to hold conditional blocks, loop blocks, or any time when multiple commands need to be grouped
 *
 * @author Casey Szilagyi
 */
public class CommandBlock extends ControlStructureCommand {

  private final List<BasicCommand> CHILDREN;


  /**
   * Makes an instance of the CommandBlock command
   *
   * @param informationBundle The information that could be needed to execute a command
   * @param nodes             All of the children to be executed
   */
  public CommandBlock(CommandInformationBundle informationBundle, List<TreeNode> nodes) {
    super(informationBundle);
    CHILDREN = new ArrayList<>();
    for (int i = 0; i < nodes.size(); i++) {
      CHILDREN.add(loadClass(informationBundle, nodes.get(i)));
    }
  }

  /**
   * Executes all of the children commands by looping through them. Returns whatever the last one
   * returns
   *
   * @return The final command value
   */
  public double execute() {
    double val = 0;
    for (int i = 0; i < CHILDREN.size(); i++) {
      val = CHILDREN.get(i).execute();
    }
    return val;
  }
}
