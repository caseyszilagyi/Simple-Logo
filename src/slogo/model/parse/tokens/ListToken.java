package slogo.model.parse.tokens;

import java.util.regex.Pattern;
import slogo.model.parse.CommandParser;

public abstract class ListToken extends Token{



  public ListToken(String command) {
    super(command);


  }

  public abstract int incrementParamCount(int blockSize, String command);





}
