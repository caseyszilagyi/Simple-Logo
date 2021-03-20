package slogo.model.parse;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import slogo.ErrorHandler;
import slogo.model.parse.tokens.ListEndToken;
import slogo.model.parse.tokens.ListToken;
import slogo.model.parse.tokens.Token;

/**
 * Condenses the translated String input as a List into a List of Token objects based on the Regex token. Also deals with different types of lists in String input.
 *
 * @author jincho
 */
public class MakeTokens {

  private final List<String> cleanedString;
  private List<Token> tokens;
  private static final String TOKEN_PACKAGE = MakeTokens.class.getPackageName() + ".tokens.";
  private static final String LANGUAGES_PACKAGE = "slogo.model.resources.languages.";
  private static final String COMMAND_PACKAGE = "slogo.model.resources.commands.";
  private static final String COMMAND_WITH_LISTS = "CommandBlocks";
  private static final String TOKENS_MAP = "TokenSyntax";
  private static final String COMMAND_KEY = "CommandBlock_";

  private CommandParser commandParser;
  private ResourceBundle listParams;
  private ResourceBundle tokenMap;
  private Map<String, Pattern> regexMap;
  private Deque<List<String>> tokenizeStack;

  /**
   * Constructs the MakeTokens object and necessary instance variables.
   *
   * @param cleanedString list of string commands recognizable by the back end
   * @param commandParser command parser for this specific user input
   */
  public MakeTokens(List<String> cleanedString, CommandParser commandParser) {
    this.commandParser = commandParser;
    this.cleanedString = cleanedString;
    listParams = ResourceBundle.getBundle(COMMAND_PACKAGE + COMMAND_WITH_LISTS);
    tokenMap = ResourceBundle.getBundle(LANGUAGES_PACKAGE + TOKENS_MAP);
    regexMap = new HashMap<>();
    addRegExPatterns("Syntax");
    tokens = new ArrayList<>();
    tokenizeStack = new ArrayDeque<>();
  }

  private void addRegExPatterns(String regEx) {
    ResourceBundle resources = ResourceBundle.getBundle(LANGUAGES_PACKAGE + regEx);
    for (String key : Collections.list(resources.getKeys())) {
      String regex = resources.getString(key);
      regexMap.put(key, Pattern.compile(regex, Pattern.CASE_INSENSITIVE));
    }
  }

  /**
   * Tokenizes the original list of tranlsated strings into a list of strings recognizable by the command parser to make a tree and be executed by the commands.
   *
   * @return List of Strings that match the command object classes needed for execution
   */
  public List<String> tokenString() {
    tokenize();
    commandBlockParams();
    return tokensToString();
  }

  private void tokenize() {
    String expected = null;
    boolean inList = false;
    for (String s : cleanedString) {
      Token toAdd;
      if (isListStart(s)) {
        try {
          toAdd = makeToken(tokenizeStack.peek().get(0));
        } catch (Exception e) {
          throw new ErrorHandler("WrongParamNum");
        }
        inList = true;
      } else { toAdd = makeToken(s); }
      if (listParams.containsKey(s)) {
        tokenizeStack.push(getListParams(s));
        expected = tokenizeStack.peek().get(0);
        tokens.add(toAdd);
        continue;
      }
      if (expected != null && inList) {
        expected = checkExpectedToken(toAdd, expected, inList);
      }
      tokens.add(toAdd);
    }
  }

  private Token makeToken(String command) {
    String type = tokenType(command);
    if(isList(command)) { type = command; }
//    System.out.println("token make type: "+type);
//    System.out.println("token make command: "+command);
    Token toRet;
    try {
      toRet = (Token) Class.forName(TOKEN_PACKAGE + type).getDeclaredConstructor(String.class).newInstance(command);
    } catch (Exception e) {
      throw new ErrorHandler("TokenCannotBeMade");
    }
    return toRet;
  }

  private String tokenType(String command) {
    String regexType = "";
    for (String key : regexMap.keySet()) {
      Pattern check = regexMap.get(key);
      if (check.matcher(command).matches()) {
        regexType = key;
        break;
      }
    }
    if (!regexType.equals("")) {
      return tokenMap.getString(regexType);
    }
    return command;
  }

  private List<String> getListParams(String command) {
    String[] splitList= listParams.getString(command).split(" ");
    List<String> splitAsList = Arrays.asList(splitList);
    return new ArrayList<>(splitAsList);
  }

  private String getClassName(Token token) {
    return token.getClass().getName().replace(TOKEN_PACKAGE, "");
  }

  private boolean isList(String s) {
    return s.contains("List");
  }

  private boolean isListStart(String s) { return regexMap.get("ListStart").matcher(s).matches(); }

  private boolean isListEnd(String s) {
    return s.equals("ListEndToken");
  }

  private String checkExpectedToken(Token toAdd, String expected, boolean inList) {
    if(!getClassName(toAdd).equals(expected) && !inList) {
      throw new ErrorHandler("WrongCommandArg");
    } else if (!isList(expected) || isListEnd(getClassName(toAdd))) {
      tokenizeStack.peek().remove(0);
      if (tokenizeStack.peek().isEmpty()) {
        tokenizeStack.pop();
        expected = null;
      } else {
        expected = tokenizeStack.peek().get(0);
      }
    }
    return expected;
  }

  private List<String> tokensToString() {
    List<String> ret = new ArrayList<>();
    for(Token t : tokens) {
      ret.add(t.getValue());
    }
    System.out.println(ret);
    return ret;
  }

  private void commandBlockParams() {
    Deque<Token> commandBlocks = new ArrayDeque<>();
    Deque<Integer> parameters = new ArrayDeque<>();
    int commandCount = 0;
    int blockSize = 0;
    for (int ind = 0; ind < tokens.size(); ind++) {
      Token curr = tokens.get(ind);
      if (curr instanceof ListEndToken) {
        tokens.remove(ind);
        ind--;
        Token popped = commandBlocks.pop();
        System.out.println("block size: " +blockSize+" for: " +popped.getValue()+" of type "+getClassName(popped));
        commandParser.addSingleParamCount(popped.getValue(), makeStringParam(blockSize));
        blockSize = parameters.pop();
        continue;
      }
      if(!commandBlocks.isEmpty()) {
        System.out.println("old blocksize: "+blockSize);
        blockSize = commandBlocks.peek().incrementParamCount(blockSize, curr);
        System.out.println("new blocksize: "+blockSize+" from "+curr.getCommand());
      }
      if (curr instanceof ListToken) {
        commandCount++;
        String commandKeyNum = COMMAND_KEY + Integer.toString(commandCount);
        curr.setVariable(commandKeyNum);
        commandBlocks.push(curr);
        parameters.push(blockSize);
        blockSize = 0;
      }
    }
    if (!commandBlocks.isEmpty()) {
      throw new ErrorHandler("WrongParamNum");
    }
  }

  private List<String> makeStringParam(int countNum) {
    List<String> ret = new ArrayList<>();
    for(int i=0; i< countNum; i++) {
      ret.add("NUM");
    }
    return ret;
  }

}
