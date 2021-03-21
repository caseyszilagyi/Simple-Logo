package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.TurtleAlteringCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * Sets the position of the turtle to a specific X and Y position
 *
 * @author jincho
 */
public class SetPosition extends TurtleAlteringCommand {

  private final TreeNode NEW_X;
  private final TreeNode NEW_Y;

  /**
   * Makes instance of SetPosition command
   *
   * @param bundle Contains the turtle that will need to be altered for this command
   * @param nodes  The two children contain the X/Y values that the turtle will move to
   */
  public SetPosition(CommandInformationBundle bundle, List<TreeNode> nodes) {
    super(bundle);
    NEW_X = nodes.get(0);
    NEW_Y = nodes.get(1);
  }

  /**
   * Make the turtle change its location to the new specified location
   *
   * @return distance it moved from previous to new location
   */
  @Override
  public double execute() {
    return updateTurtle(turtle -> {
      return setTurtlePosition(executeNode(NEW_X), executeNode(NEW_Y));
    });
  }
}
