package slogo.model.parse.tokens;

import slogo.ErrorHandler;

public class ConstantList extends ListToken{

  public ConstantList(String command) {
    super(command);
  }

  @Override
  public int incrementParamCount(int blockSize, Token command) {
    if(!isConstant(command.getCommand())){ throw new ErrorHandler("WrongInputType"); }
    return blockSize+1;
  }
  @Override
  public void addParamToken(Token param) {
    return;
  }
}
