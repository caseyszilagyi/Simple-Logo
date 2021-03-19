import slogo.model.execution.CommandInformationBundle;
import slogo.model.parse.CommandParser;
import slogo.model.parse.InputCleaner;
import slogo.model.parse.MakeTokens;

public class CommandExecuter {

  private CommandParser commandParser;
  private InputCleaner inputCleaner;
  private MakeTokens tokenMaker;
  private CommandInformationBundle bundle;

  public CommandExecuter() {

  }

}
