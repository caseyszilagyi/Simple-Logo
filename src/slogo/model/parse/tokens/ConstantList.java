package slogo.model.parse.tokens;

import java.util.ArrayList;
import java.util.List;
import slogo.model.parse.CommandParser;

/**
 * object to indicate the start of a list of constants or commands that reduces to list of constants. in current implementation, it is used
 * mainly when indicating indices of turtles as this is the only current case that deals with a list of constants, but if more commands
 * were to be added that required a list of constants, it woudl still continue to work. The construction of these is deon through
 * reflection in the TokenFactory with reflection with the use of properties files.
 *
 * @author jincho
 */
public class ConstantList extends ListToken{

  /**
   *
   * @param command
   */
  public ConstantList(String command) {
    super(command);
  }

  /**
   *  sees if the token will be a direct parameter of this constant list or is a subparameter in that it is a parameter of a command already in this
   *  list and will not be a direct chld node of this list in the AST. This essentially checks for when there is a command in the list,
   *  it stores the info on the expected parameters and uses that to determine if the new param Token is a child of one of those commands
   *  stored, or if there are no commands stored that are still expecting parameters, it is just a direct parameter.
   *
   * @param param the token you are checking for if it is a direct child or a parameter of a command within the list (not direct)
   */
  @Override
  public void addParamToken(Token param) {
    if (paramTokensExpected.isEmpty()) {
      params.add(param);
    }
    if (isDefinedCommand(param.getValue())) {
      List<String> expected = new ArrayList<>(CommandParser.commandParam.get(param.getCommand()));
      paramTokensExpected.push(expected);
    } else {
      reduceTokenParamStack(paramTokensExpected);
    }
  }
}
