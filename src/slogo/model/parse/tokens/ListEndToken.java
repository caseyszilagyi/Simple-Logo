package slogo.model.parse.tokens;

public class ListEndToken extends Token{

  public ListEndToken(String command) {
    super(command);
  }

  @Override
  public int incrementParamCount(int blockSize, Token command) {
    return blockSize;
  }

  @Override
  public void addParamToken(Token param) {
    return;
  }
}
