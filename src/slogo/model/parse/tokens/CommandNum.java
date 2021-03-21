package slogo.model.parse.tokens;

import java.util.List;
import slogo.model.parse.CommandParser;

public class CommandNum extends NumToken{

  private List<String> expectedParamsLeft;

  public CommandNum(String command) {
    super(command);
    expectedParamsLeft = CommandParser.commandParam.get(command);
  }

  @Override
  public void addParamToken(Token param) {

  }
}
