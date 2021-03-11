package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * The ClearScreen command. Extends the Home command because they do the same thing, except that
 * this one removes all lines from the screen
 *
 * @author Casey Szilagyi
 */
public class ClearScreen extends Home {

  /**
   * Makes an instance of the ClearScreen command
   *
   * @param bundle Contains the turtle that will need to be altered for this command
   * @param nodes  This command has no children, so this will be null
   */
  public ClearScreen(CommandInformationBundle bundle, List<TreeNode> nodes) {
    super(bundle, nodes);
  }

  /**
   * Moves the turtle to 0, 0
   *
   * @return The distance that the turtle had to move to get there
   */
  public double execute() {
    double distance = super.execute();
    reset();
    return distance;
  }
}
