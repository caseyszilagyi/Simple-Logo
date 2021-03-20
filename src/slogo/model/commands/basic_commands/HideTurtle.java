package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.TurtleAlteringCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * Hides the turtle
 *
 * @author Casey Szilagyi
 */
public class HideTurtle extends TurtleAlteringCommand {

  /**
   * Makes an instance of the HideTurtle command
   *
   * @param bundle Contains the turtle that will need to be altered for this command
   * @param nodes  This command has no children, so this will be null
   */
  public HideTurtle(CommandInformationBundle bundle, List<TreeNode> nodes) {
    super(bundle);
  }

  /**
   * Makes the turtle invisible
   *
   * @return 0, because the turtle is not visible
   */
  @Override
  public double execute() {
    updateTurtle(turtle -> {
      changeTurtleVisibility(0);
    });
    return 0;
  }
}
