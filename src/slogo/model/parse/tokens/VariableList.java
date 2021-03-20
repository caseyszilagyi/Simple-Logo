package slogo.model.parse.tokens;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.regex.Pattern;
import slogo.model.parse.CommandParser;

public class VariableList extends ListToken {

  private Pattern VARIABLE_REGEX;
  private Deque<Token> params;
  private Deque<List<String>> paramTokensExpected;

  public VariableList(String command) {
    super(command);
    params = new ArrayDeque<>();
    paramTokensExpected = new ArrayDeque<>();
  }

  @Override
  public int incrementParamCount(int blockSize, Token command) {
    addParamToken(command);
    return params.size();
  }

  @Override
  public void addParamToken(Token param) {
    List<String> expected = new ArrayList<>();
    if (paramTokensExpected.isEmpty()) {
      params.add(param);
    }
    if (isBasicCommand(param.getValue())) {
      expected = new ArrayList<>(CommandParser.parameters.get(param.getCommand()));
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
