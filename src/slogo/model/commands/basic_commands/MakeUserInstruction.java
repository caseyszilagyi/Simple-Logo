package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.ControlStructureCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

public class MakeUserInstruction extends ControlStructureCommand {

  private final String NAME;
  private final BasicCommand COMMANDS;
  private final BasicCommand VARIABLES;
  /**
   * @param informationBundle The bundle that has the user defined variables/commands
   */
  public MakeUserInstruction(CommandInformationBundle informationBundle, List<TreeNode> nodes) {
    super(informationBundle);
    NAME = nodes.get(0).getValue();
    VARIABLES = loadClass(informationBundle, nodes.get(1));
//    this is the CommandBundle that has the defined commands for this instruction
    COMMANDS = loadClass(informationBundle, nodes.get(2));
  }

  @Override
  public double execute() {
    //first execute the variable CommandBock
    // VALUES.execute();
    // then save the method
//    setCommand(NAME, COMMANDS);
//    double ret = COMMANDS.execute()
    return 0;
  }
}
