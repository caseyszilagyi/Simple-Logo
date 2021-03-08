package slogo.model.commands.basic_commands.command_types;

import java.util.List;
import slogo.model.commands.basic_commands.BasicCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * This abstract class is meant to be implemented by any math/logic command that doesn't need any
 * other information to function
 *
 * @author Casey Szilagyi
 */
public abstract class MathAndLogicCommand implements BasicCommand {

  /**
   * Constructs an instance of this class type
   *
   * @param informationBundle None of this bundle is used for any command that deals with
   *                          math/logic
   * @param children          The TreeNodes that are used to execute the command
   */
  public MathAndLogicCommand(CommandInformationBundle informationBundle, List<TreeNode> children) {
  }


}
