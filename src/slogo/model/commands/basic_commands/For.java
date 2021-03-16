package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.ControlStructureCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * Allows the user to specify an increment, start, and end. The command will increment the given
 * variable by the specified increment amount each time the loop runs until it is finished
 *
 * @author Casey Szilagyi
 */
public class For extends ControlStructureCommand {

  private final String VARIABLE;
  private final double START;
  private final double END;
  private final double INCREMENT;
  private final TreeNode COMMAND_BLOCK;

  /**
   * Makes an instance of the For loop
   *
   * @param bundle   The pieces of information, such as variables and user defined commands, that
   *                 may be needed to execute the the command
   * @param children Has 2 nodes. One has the variable name, start, end, and increment, and the
   *                 other has the block of commands
   */
  public For(CommandInformationBundle bundle, List<TreeNode> children) {
    super(bundle);
    VARIABLE = children.get(0).getChildren().get(0).getValue();
    START = executeBlock(children.get(0).getChildren().get(1));
    END = executeBlock(children.get(0).getChildren().get(2));
    INCREMENT = executeBlock(children.get(0).getChildren().get(3));
    COMMAND_BLOCK = children.get(1);
  }

  /**
   * Repeats the loop, incrementing by the specified amount, until over the limit. The variable is
   * updated with each loop
   *
   * @return The value of the final command executed
   */
  public double execute() {
    double val = 0;
    addParameterMap();
    for (double i = START; i <= END; i += INCREMENT) {
      setParameter(VARIABLE, i);
      val = executeBlock(COMMAND_BLOCK);
    }
    removeParameterMap();
    return val;
  }
}
