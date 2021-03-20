package slogo.model.parse.tokens;

public class ConstantNum extends NumToken{

  public ConstantNum(String command) {
    super(command);
  }

  @Override
  public int incrementParamCount(int blockSize, Token command) {
    return blockSize;
  }
}
