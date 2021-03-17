package slogo.model.parse.tokens;

import slogo.ErrorHandler;

public class ConstantList extends ListToken{

  public ConstantList(String command) {
    super(command);
  }

  @Override
  public int incrementParamCount(int blockSize, String command) {
    if(!isConstant(command)){ throw new ErrorHandler("WrongInputType"); }
    return blockSize++;
  }
}
