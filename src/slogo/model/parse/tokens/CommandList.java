package slogo.model.parse.tokens;

import java.util.ArrayList;
import java.util.List;
import slogo.model.parse.CommandParser;

public class CommandList extends ListToken{

  public CommandList(String command) {
    super(command);

  }

  public void addParamToken(Token param) {
    if (isDefinedCommand(param.getValue()) ) {
      if (paramTokensExpected.isEmpty()) {
        params.add(param);
      }
      List<String> expected = new ArrayList<>(CommandParser.commandParam.get(param.getCommand()));
      if(!expected.isEmpty()) {
        paramTokensExpected.push(expected);
      }
    } else {
      while (!paramTokensExpected.isEmpty()) {
        paramTokensExpected.peek().remove(0);
        System.out.println("expected: "+paramTokensExpected);
        if(paramTokensExpected.peek().isEmpty()) {
          paramTokensExpected.pop();
        } else { break; }
      }
    }

  }





}
