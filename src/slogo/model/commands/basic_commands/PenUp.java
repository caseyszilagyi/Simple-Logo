package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.TurtleAlteringCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * The pen up command
 *
 * @author Casey Szilagyi
 */
public class PenUp extends TurtleAlteringCommand {

  /**
   * Makes an instance of the pen up command
   *
   * @param bundle Contains the turtle that will need to be altered for this command
   * @param nodes  This command has no children, so this will be null
   */
  public PenUp(CommandInformationBundle bundle, List<TreeNode> nodes) {
    super(bundle);
  }

  /**
   * Lifts the pen up
   *
   * @return 0, because the pen is not drawing
   */
  public double execute() {
    updateTurtle(turtle -> {
      changePenState(0);
    });
    return 0;
  }
}
