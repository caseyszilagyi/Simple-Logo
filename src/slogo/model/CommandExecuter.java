package slogo.model;

/**
 * This interface is meant to be implemented by any class that takes in an input
 * and acts upon a certain set of objects.
 */
public interface CommandExecuter {

  /**
   * This method is passed a string and a command is executed as a result
   * @param command The raw string that is put in
   */
  public void executeCommand(String command, String language);
}
