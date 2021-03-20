package slogo.model.parse.tokens;

import java.util.List;

public class NumToken extends Token{

  protected boolean isComplete;
  protected List<Token> paramTokens;

  public NumToken(String command) {
    super(command);
    isComplete = false;
    if(isConstant(command)) { isComplete = true; }
  }

  private boolean getSatisfaction() { return isComplete; }

  @Override
  public int incrementParamCount(int blockSize, Token command) {
    return blockSize;
  }

  @Override
  public void addParamToken(Token param) {
    return;
  }

}
