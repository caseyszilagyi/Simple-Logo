package slogo.model.parse.tokens;

import java.util.ArrayList;
import java.util.List;
import slogo.ErrorHandler;
import slogo.model.parse.CommandParser;

public class ConstantList extends ListToken{


  public ConstantList(String command) {
    super(command);
  }

  @Override
  public void addParamToken(Token param) {
    if (paramTokensExpected.isEmpty()) {
      params.add(param);
    }
    if (isBasicCommand(param.getValue())) {
      List<String> expected = new ArrayList<>(CommandParser.parameters.get(param.getCommand()));
      paramTokensExpected.push(expected);
    } else {
      while (!paramTokensExpected.isEmpty()) {
        paramTokensExpected.peek().remove(0);
        if(paramTokensExpected.peek().isEmpty()) {
          paramTokensExpected.pop();
        } else { break; }
      }
    }
  }
}
