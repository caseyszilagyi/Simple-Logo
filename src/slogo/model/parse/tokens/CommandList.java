package slogo.model.parse.tokens;

public class CommandList extends ListToken{



  public CommandList(String command) {
    super(command);
  }

  @Override
  public int incrementParamCount(int blockSize, Token command) {
    if(isBasicCommand(command.getValue())) { return blockSize + 1; }
    return blockSize;
  }





}
