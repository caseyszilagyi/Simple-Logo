package slogo.model.parse.tokens;

public class CommandNum extends NumToken{

  public CommandNum(String command) {
    super(command);
  }

  @Override
  public int incrementParamCount(int blockSize, Token command) {
    return blockSize;
  }
}
