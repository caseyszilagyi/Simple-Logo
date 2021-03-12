package slogo.model.commands.basic_commands;

import com.sun.source.tree.Tree;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import slogo.model.commands.basic_commands.command_types.ControlStructureCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * This command makes and stores a user defined command for later use
 *
 * @author Casey Szilagyi
 */
public class UserDefinedCommand extends ControlStructureCommand {

  private final String NAME;
  private final List<TreeNode> PARAMETERS;
  private final TreeNode COMMAND_BLOCK;

  private Map<String, Double> paramMap = new HashMap<>();

  /**
   * Is created by the MakeUserInstruction command and stored in a map, can be executed
   * if it is passed parameters
   *
   * @param bundle   Contains the map of command names to commands
   * @param children Has the string of the command, the parameters the command takes, as well as the
   *                 block of commands to execute
   */
  public UserDefinedCommand(CommandInformationBundle bundle, List<TreeNode> children) {
    super(bundle);
    NAME = children.get(0).getValue();
    PARAMETERS = children.get(1).getChildren();
    COMMAND_BLOCK = children.get(2);
  }

  /**
   * Gets the parameter count for this user defined command. Used in tree parsing to know
   * how many arguments to expect
   * @return The number of parameters to expect
   */
  public int getParamCount(){
    return PARAMETERS.size();
  }

  /**
   * Executes the suer defined command with the established map
   *
   * @return The variable value
   */
  public double execute() {
    addParamMap(paramMap);
    double result = executeBlock(COMMAND_BLOCK);
    removeParamMap();
    return result;
  }


  /**
   * Stores the parameter values in a map
   * @param parameterValues The given set of parameter values
   */
  public void passParams(List<TreeNode> parameterValues){
    paramMap = new HashMap<>();
    for(int i = 0; i<PARAMETERS.size(); i++){
      paramMap.put(PARAMETERS.get(i).getCommand(), executeBlock(parameterValues.get(i)));
    }
  }
}
