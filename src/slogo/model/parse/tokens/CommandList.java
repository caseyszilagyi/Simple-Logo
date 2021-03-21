package slogo.model.parse.tokens;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import slogo.model.parse.CommandParser;

public class CommandList extends ListToken{

  public CommandList(String command) {
    super(command);

  }

  public void addParamToken(Token param) {
    if (isCommand(param.getValue()) ) {
      if (paramTokensExpected.isEmpty()) {
        params.add(param);
      }
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
