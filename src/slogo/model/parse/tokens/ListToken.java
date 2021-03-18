package slogo.model.parse.tokens;

import java.util.regex.Pattern;
import slogo.model.parse.CommandParser;

public abstract class ListToken extends Token{

  private int paramCount;


  public ListToken(String command) {
    super("CommandBlock");
    paramCount = 0;
  }






}
