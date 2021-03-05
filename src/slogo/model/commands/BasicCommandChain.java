package slogo.model.commands;

import java.util.ArrayList;
import java.util.List;
import slogo.model.commands.basic_commands.BasicCommand;

/**
 * The basic command chain holds an ArrayList of basic commands, as well as the number that
 * represents how many times the command chain should be completed
 */
public class BasicCommandChain {

  private int repetitions;
  private List<BasicCommand> COMMAND_CHAIN = new ArrayList<>();

  /**
   * Creates an instance of the commandChain with the given number of repetitions, and that acts on
   * the given commandChain
   *
   * @param userRepetitions The number of times the commandChain will be repeated
   * @param commandChain    The chain of BasicCommands that will be repeated
   */
  public BasicCommandChain(int userRepetitions, List commandChain) {
    COMMAND_CHAIN = commandChain;
    repetitions = userRepetitions;
  }

}
