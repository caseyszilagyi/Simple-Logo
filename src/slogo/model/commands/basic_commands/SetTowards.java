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

  private final double PREV_ANGLE;
  private final double X_HEADING;
  private final double Y_HEADING;
  private final double CURR_X;
  private final double CURR_Y;


  /**
   * Makes the BasicCommand and saves the Turtle
   *
   * @param informationBundle The bundle of information that contains the turtle
   * @param children
   */
  public SetTowards(CommandInformationBundle informationBundle, List<TreeNode> children) {
    super(informationBundle);
    PREV_ANGLE = informationBundle.getTurtle().getAngle();
    CURR_X = informationBundle.getTurtle().getXPosition();
    CURR_Y = informationBundle.getTurtle().getXPosition();
    X_HEADING = loadClass(informationBundle, children.get(0)).execute();
    Y_HEADING = loadClass(informationBundle, children.get(1)).execute();
  }

  // Calculates the new angle that the turtle should be facing
  private double newAngle() {
    double x_diff = Math.toRadians(X_HEADING - CURR_X);
    double y_diff = Math.toRadians(Y_HEADING - CURR_Y);
    return Math.toDegrees(Math.atan(x_diff / y_diff));
  }

  // Calculates the change in angle to return
  private double angleChange() {
    return Math.abs(newAngle() - PREV_ANGLE);
  }

  /**
   * changes angle which turtle is facing to the angle matching the x and y given
   *
   * @return
   */
  @Override
  public double execute() {
    updateTurtle(turtle -> {
      setAngle(newAngle());
    });
    return angleChange();
  }
}
