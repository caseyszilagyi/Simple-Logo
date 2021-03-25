package slogo.model.parse.tokens;


import java.util.ArrayList;
import java.util.List;
import slogo.model.parse.CommandParser;

/**
 * object used to identify the start of a list of variables and to store information about its parameters. this is used when defining a list of
 * variables that will be used as parameters for a user defined instruction, but can be expanded to other uses as more commnands may be specified. it
 * is also used when defining values of variables in a list, so there are some added complications, as the other lists had, with regards to counting
 * parameters that is addressed in the addParamToken method.
 *
 * @author jincho
 */
public class VariableList extends ListToken {

  /**
   * constructs this token object using VariableList as the command as it doens't specifically count as a command but rather a place holder
   * to indicate the start of a list and specifying the type
   * @param command indication of it being a VariableList token as no command is associated
   */
  public VariableList(String command) {
    super(command);
  }

  /**
   * sees if the token will be a direct parameter of this variable list or is a subparameter in that it is a parameter of a command already in this
   * list and will not be a direct chld node of this list in the AST. This essentially checks for when there is a command in the list,
   * it stores the info on the expected parameters and uses that to determine if the new param Token is a child of one of those commands
   * stored, or if there are no commands stored that are still expecting parameters, it is just a direct parameter.
   *
   * @param param the command that is being added the the list of parameter tokens for this token, whether that be direct or a sub param.
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
    return;
  }
}
