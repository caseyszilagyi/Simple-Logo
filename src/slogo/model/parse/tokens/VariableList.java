package slogo.model.parse.tokens;


import java.util.ArrayList;
import java.util.List;
import slogo.model.parse.CommandParser;

public class VariableList extends ListToken {

  public VariableList(String command) {
    super(command);
  }

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
