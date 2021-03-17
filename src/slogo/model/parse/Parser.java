package slogo.model.parse;

import java.util.List;

/**
 *
 */
public interface Parser {

  /**
   * Translates the command
   */
  public List<String> translateCommand(List<String> commandsBeforeTranslation);

}