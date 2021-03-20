package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.ControlStructureCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * This command makes and stores a user defined command for later use
 *
 * @author Casey Szilagyi
 */
public class MakeUserInstruction extends ControlStructureCommand {

  private final String NAME;
  private final UserDefinedCommand USER_COMMAND;

  /**
   * Makes an instance of the MakeUserInstruction command
   *
   * @param bundle   Contains the map of command names to commands
   * @param children Has the string of the command, the parameters the command takes, as well as the
   *                 block of commands to execute
   */

  public MakeUserInstruction(CommandInformationBundle bundle, List<TreeNode> children) {
    super(bundle);
    NAME = children.get(0).getCommand();
    USER_COMMAND = new UserDefinedCommand(bundle, children);
    setCommand(NAME, USER_COMMAND);
  }

  /**
   * Doesn't do anything because making the command is simply storing it
   *
   * @return 1, because the command was successfully stored
   */
  public double execute() {
    return 1;
  }

}
