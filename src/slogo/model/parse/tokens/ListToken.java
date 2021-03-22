package slogo.model.parse.tokens;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 *
 */
public abstract class ListToken extends Token {
  protected List<Token> params;
  protected Deque<List<String>> paramTokensExpected;

  /**
   *
   * @param command
   */
  public ListToken(String command) {
    super("CommandBlock");
    paramTokensExpected = new ArrayDeque<>();
    params = new ArrayList<>();
  }

  public int getListParamsCount() { return params.size(); }

  protected void reduceTokenParamStack(Deque<List<String>> stack) {
    while (!stack.isEmpty()) {
      stack.peek().remove(0);
      if(stack.peek().isEmpty()) {
        stack.pop();
      } else { return; }
    }
  }

  @Override
  public int incrementParamCount(int blockSize, Token command) {
    addParamToken(command);
    return params.size();
  }






}
