package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.TurtleAlteringCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * changes the direction turtle faces based on x, y coord it should face
 *
 * @author jincho
 */
public class SetTowards extends TurtleAlteringCommand {

  private final TreeNode X_HEADING;
  private final TreeNode Y_HEADING;


  /**
   * Makes the BasicCommand and saves the Turtle
   *
   * @param informationBundle The bundle of information that contains the turtle
   * @param children The x and y position that the turtle will be facing
   */
  public SetTowards(CommandInformationBundle informationBundle, List<TreeNode> children) {
    super(informationBundle);
    X_HEADING = children.get(0);
    Y_HEADING = children.get(1);
  }

  /**
   * changes angle which turtle is facing to the angle matching the x and y given
   *
   * @return
   */
  @Override
  public double execute() {
    return updateTurtle(turtle -> {
      double xDifference = executeNode(X_HEADING) - getXCoordinate();
      double yDifference = executeNode(Y_HEADING)- getYCoordinate();
      if(xDifference>0) {
        return setAngle(Math.toDegrees(Math.atan(yDifference / xDifference)));
      }
      else{
        return setAngle(Math.toDegrees(Math.atan(yDifference / xDifference)) + 180);
      }
    });
  }
}
