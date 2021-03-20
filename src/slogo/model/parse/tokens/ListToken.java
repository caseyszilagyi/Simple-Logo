package slogo.model.parse.tokens;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.regex.Pattern;
import slogo.model.parse.CommandParser;

public abstract class ListToken extends Token{

  private int paramCount;
  protected List<Token> params;
  protected Deque<List<String>> paramTokensExpected;


  public ListToken(String command) {
    super("CommandBlock");
    paramCount = 0;
    paramTokensExpected = new ArrayDeque<>();
    params = new ArrayList<>();
  }

  @Override
  public int incrementParamCount(int blockSize, Token command) {
    addParamToken(command);
    return params.size();
  }






}
