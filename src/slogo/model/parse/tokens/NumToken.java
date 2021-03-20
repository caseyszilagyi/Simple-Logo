package slogo.model.parse.tokens;

public class NumToken extends Token{

  public NumToken(String command) {
    super(command);
  }

  @Override
  public int incrementParamCount(int blockSize, Token command) {
    return blockSize;
  }
}
