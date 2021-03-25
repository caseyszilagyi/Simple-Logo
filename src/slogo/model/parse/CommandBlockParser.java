package slogo.model.parse;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import slogo.ErrorHandler;
import slogo.model.parse.tokens.ListEndToken;
import slogo.model.parse.tokens.ListToken;
import slogo.model.parse.tokens.Token;

/**
 * parser that deals with lists and counting and saving the number of parameters each list has in the map that is used to create the AST
 * in the CommandParser class. This creates a unique identifier for each list that allows for reference when it is reached. It also replaces the
 * use of list tokens as identifiers of lists all to command blocks, which is a basic command type we created in terms of recognizable execution
 * by the basic command classes.
 *
 * @author jincho
 */
public class CommandBlockParser extends Parser {
  private static final String COMMAND_KEY = "CommandBlock_";
  private static final int USER_DEF_VARLIST_INDEX = 2;

  private List<Token> tokens;
  private CommandParser commandParser;

  /**
   * constructs this parser and also makes a method call to make the command blocks and get their parameter counts. 
   * @param tokens
   * @param commandParser
   */
  public CommandBlockParser(List<Token> tokens, CommandParser commandParser) {
    this.tokens = tokens;
    this.commandParser = commandParser;
    commandBlockParams();
  }

  private void commandBlockParams() {
    Deque<Token> commandBlocks = new ArrayDeque<>();
    Deque<Integer> parameters = new ArrayDeque<>();
    int userDefInd = -1;
    int commandCount = 0;
    int blockSize = 0;
    boolean inUserDefCommand = false;
    for (int ind = 0; ind < tokens.size(); ind++) {
      Token curr = tokens.get(ind);
      if (isUserDefCommand(curr)) {
        inUserDefCommand = true;
        userDefInd = ind;
      }
      if (curr instanceof ListEndToken) {
        tokens.remove(ind);
        ind--;
        if(inUserDefCommand && isEndVarList(ind, userDefInd, blockSize)) {
          completedUserDefVarList(blockSize, userDefInd);
          inUserDefCommand = false;
        }
        blockSize = completeListParamCount(commandBlocks, parameters, blockSize);
        continue;
      }
      if(!commandBlocks.isEmpty()) {
        blockSize = commandBlocks.peek().incrementParamCount(blockSize, curr);
      }
      if (curr instanceof ListToken) {
        commandCount++;
        blockSize = startListParamCount(curr, commandBlocks, parameters, blockSize, commandCount);
      }
    }
    if (!commandBlocks.isEmpty()) { throw new ErrorHandler("WrongParamNum"); }
  }

  private boolean isUserDefCommand(Token token) {
    return token.getCommand().equals("MakeUserInstruction");
  }

  private boolean isEndVarList(int currInd, int userDefInd, int blockSize) {
    return currInd == userDefInd + blockSize + USER_DEF_VARLIST_INDEX;
  }

  private void completedUserDefVarList(int blockSize, int userDefInd) {
    commandParser.addSingleParamCount(tokens.get(userDefInd+1).getValue(), makeStringParam(blockSize));
  }

  private int completeListParamCount(Deque<Token> commandBlocks, Deque<Integer> parameters, int blockSize) {
    Token popped = commandBlocks.pop();
    commandParser.addSingleParamCount(popped.getValue(), makeStringParam(blockSize));
    return parameters.pop();
  }

  private int startListParamCount(Token curr, Deque<Token> commandBlocks, Deque<Integer> parameters, int blockSize, int commandCount) {
    String commandKeyNum = COMMAND_KEY + Integer.toString(commandCount);
    curr.setValue(commandKeyNum);
    commandBlocks.push(curr);
    parameters.push(blockSize);
    return 0;
  }

  private List<String> makeStringParam(int countNum) {
    List<String> ret = new ArrayList<>();
    for (int i = 0; i < countNum; i++) {
      ret.add(NUM_TOKEN);
    }
    return ret;
  }

  /**
   *
   * @return
   */
  @Override
  public List<String> parseResults() {
    return null;
  }
}
