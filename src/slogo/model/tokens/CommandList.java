package slogo.model.tokens;

import java.util.regex.Pattern;
import slogo.model.CommandParser;

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
