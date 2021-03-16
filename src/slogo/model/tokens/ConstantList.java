package slogo.model.tokens;

public class ConstantList extends ListToken{

  public ConstantList(String command) {
    super(command);
  }

  @Override
  public int incrementParamCount(int blockSize, String command) {
    return blockSize++;
  }
}
