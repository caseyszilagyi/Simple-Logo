package slogo.model.tokens;

import java.util.regex.Pattern;
import slogo.model.CommandParser;

public abstract class ListToken {

  private String command;
  private Pattern COMMAND_REGEX, VARIABLE_REGEX, CONSTANT_REGEX;

  public ListToken(String command) {
    this.command = command;
    COMMAND_REGEX = CommandParser.syntaxMap.get("Command");
    VARIABLE_REGEX = CommandParser.syntaxMap.get("Variable");
    CONSTANT_REGEX = CommandParser.syntaxMap.get("Constant");

  }

  public abstract int incrementParamCount(int blockSize, String command);

  protected boolean isCommand(String s) {
    return COMMAND_REGEX.matcher(s).matches();
  }

  protected boolean isVariable(String command) {
    return VARIABLE_REGEX.matcher(command).matches();
  }

  protected boolean isConstant(String command) {
    return VARIABLE_REGEX.matcher(command).matches();
  }

  public String getCommand() { return command; }

}
