package slogo.model.parse.tokens;

import java.util.regex.Pattern;
import slogo.model.parse.CommandParser;

public abstract class Token {

  private String command;
  private String value;
  private static final Pattern COMMAND_REGEX = CommandParser.syntaxMap.get("Command");
  private static final Pattern VARIABLE_REGEX = CommandParser.syntaxMap.get("Variable");
  private static final Pattern CONSTANT_REGEX = CommandParser.syntaxMap.get("Constant");


  public Token(String command) {
    this.command = command;
    this.value = command;
  }

  public String getCommand() { return command; }

  public String getValue() { return  value; }

  public void setVariable(String val) { value = val; }

  protected boolean isCommand(String s) {
    return COMMAND_REGEX.matcher(s).matches();
  }

  protected boolean isVariable(String command) {
    return VARIABLE_REGEX.matcher(command).matches();
  }

  protected boolean isConstant(String command) {
    return CONSTANT_REGEX.matcher(command).matches();
  }

  public abstract int incrementParamCount(int blockSize, Token command);

}
