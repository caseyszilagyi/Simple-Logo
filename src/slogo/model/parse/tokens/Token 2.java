package slogo.model.parse.tokens;

import java.util.regex.Pattern;
import slogo.model.parse.CommandParser;
import slogo.model.parse.Parser;

public abstract class Token {

  private String command;
  private String value;
  private static final Pattern CONSTANT_REGEX = Parser.syntaxMap.get("Constant");


  public Token(String command) {
    this.command = command;
    this.value = command;
  }

  public String getCommand() {
    return command;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String val) {
    value = val;
  }

  protected boolean isDefinedCommand(String s) {
    return CommandParser.commandParam.containsKey(s);
  }

  protected boolean isConstant(String command) {
    return CONSTANT_REGEX.matcher(command).matches();
  }

  public abstract int incrementParamCount(int blockSize, Token command);

  public abstract void addParamToken(Token param);

}
