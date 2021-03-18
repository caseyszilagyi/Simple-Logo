package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.TurtleAlteringCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * The home command
 *
 * @author Casey Szilagyi
 */
public class Home extends TurtleAlteringCommand {

  private final double PREV_X;
  private final double PREV_Y;

  /**
   * Makes an instance of the Home command
   *
   * @param bundle Contains the turtle that will need to be altered for this command
   * @param nodes  This command has no children, so this will be null
   */
  public Home(CommandInformationBundle bundle, List<TreeNode> nodes) {

    super(bundle);
    PREV_X = getXCoordinate();
    PREV_Y = getYCoordinate();
  }

  // Gets the distance that the turtle traveled
  private double getDistance(){
    return Math.sqrt(Math.pow(PREV_X, 2) + Math.pow(PREV_Y, 2));
  }

  /**
   * Moves the turtle to 0, 0
   *
   * @return The distance that the turtle had to move to get there
   */
  public double execute() {
    updateTurtle(turtle -> {
      setTurtleX(0);
      setTurtleY(0);
      setAngle(90);
    });
    return getDistance();
  }
}
