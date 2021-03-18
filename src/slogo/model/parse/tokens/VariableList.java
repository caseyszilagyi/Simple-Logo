package slogo.model.parse.tokens;


import java.util.regex.Pattern;

public class VariableList extends ListToken {

  private Pattern VARIABLE_REGEX;

  public VariableList(String command) {
    super(command);
  }

  @Override
  public int incrementParamCount(int blockSize, Token command) {
    return blockSize++;
  }
}
