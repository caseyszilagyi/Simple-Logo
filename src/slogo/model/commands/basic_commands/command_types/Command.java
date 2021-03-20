package slogo.model.commands.basic_commands.command_types;

import slogo.model.commands.BasicCommandClassLoader;
import slogo.model.commands.basic_commands.BasicCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * This class has the ability to load a basic command, and is implemented by every command
 * in order to have the ability to load any other BasicCommand
 */
public abstract class Command implements BasicCommand {

  BasicCommandClassLoader CLASS_LOADER = new BasicCommandClassLoader();

  /**
   * This method loads the BasicCommand class represented by the TreeNode that is passed in. Is used
   * for loading the BasicCommands of the subtrees of any given BasicCommand
   *
   * @return The BasicCommand represented by the TreeNode
   */
  protected BasicCommand loadClass(CommandInformationBundle informationBundle, TreeNode node) {
    return CLASS_LOADER.makeCommand(informationBundle, node);
  }

}
