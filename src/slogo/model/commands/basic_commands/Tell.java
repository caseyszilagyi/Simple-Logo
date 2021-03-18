package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.MultipleTurtleCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * This is the tell command. It defines the set of turtles that the commands act on
 */
public class Tell extends MultipleTurtleCommand {

  public Tell(CommandInformationBundle informationBundle, List<TreeNode> children){
    super(informationBundle);

  }



  public double execute(){
    return 0;
  }
}
