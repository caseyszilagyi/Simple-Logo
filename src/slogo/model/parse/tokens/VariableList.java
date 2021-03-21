package slogo.model.parse.tokens;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import slogo.model.parse.CommandParser;

public class VariableList extends ListToken {

  private Pattern VARIABLE_REGEX;

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
      while (!paramTokensExpected.isEmpty()) {
        paramTokensExpected.peek().remove(0);
        if(paramTokensExpected.peek().isEmpty()) {
          paramTokensExpected.pop();
        } else { break; }
      }
    }

    return;
  }
}
