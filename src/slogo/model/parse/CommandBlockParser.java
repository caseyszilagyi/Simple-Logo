package slogo.model.parse;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import slogo.ErrorHandler;
import slogo.model.parse.tokens.ListEndToken;
import slogo.model.parse.tokens.ListToken;
import slogo.model.parse.tokens.Token;

public class CommandBlockParser extends Parser{

  private static final String COMMAND_KEY = "CommandBlock_";

  private List<Token> tokens;
  private CommandParser commandParser;

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
    return currInd == userDefInd + blockSize + 2;
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
    for(int i=0; i< countNum; i++) {
      ret.add(NUM_TOKEN);
    }
    return ret;
  }

  @Override
  public List<String> parseResults() {
    return null;
  }
}
