package slogo.model.parse.tokens;

public class CommandList extends ListToken{



  public CommandList(String command) {
    super(command);
  }

  @Override
  public int incrementParamCount(int blockSize, String command) {
    if(isCommand(command)) { return blockSize + 1; }
    return blockSize;
  }



}
