package slogo.model;

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