package slogo.model.parse;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import slogo.ErrorHandler;
import slogo.model.SLogoCommandExecutor;
import slogo.model.parse.tokens.ListEndToken;
import slogo.model.parse.tokens.ListToken;
import slogo.model.parse.tokens.Token;

/**
 * Condenses the translated String input as a List into a List of Token objects based on the Regex token. Also deals with different types of lists in String input.
 *
 * @author jincho
 */
public class MakeTokens extends Parser{

  private final List<String> cleanedString;
  private List<Token> tokens;
  public static final String TOKEN_PACKAGE = MakeTokens.class.getPackageName() + ".tokens.";
  private static final String COMMAND_WITH_LISTS = "CommandBlocks";
  private static final String TOKENS_MAP = "TokenSyntax";

//  private CommandParser commandParser;
  private ResourceBundle listParams;
  private ResourceBundle tokenMap;
  private Deque<List<String>> tokenizeStack;

  /**
   * Constructs the MakeTokens object and necessary instance variables.
   *
   * @param cleanedString list of string commands recognizable by the back end
   */
  public MakeTokens(List<String> cleanedString) {
//    this.commandParser = commandParser;
    this.cleanedString = cleanedString;
    System.out.println(cleanedString);
    listParams = ResourceBundle.getBundle(SLogoCommandExecutor.COMMAND_PACKAGE + COMMAND_WITH_LISTS);
    tokenMap = ResourceBundle.getBundle(SLogoCommandExecutor.LANGUAGES_PACKAGE + TOKENS_MAP);
    tokens = new ArrayList<>();
    tokenizeStack = new ArrayDeque<>();
  }

  /**
   * Tokenizes the original list of tranlsated strings into a list of strings recognizable by the command parser to make a tree and be executed by the commands.
   *
   * @return List of Strings that match the command object classes needed for execution
   */
  public List<Token> tokenString() {
    tokenize();
//    commandBlockParams();
    return tokens;
  }

  /**
   *
   * @return
   */
  @Override
  public List<String> parseResults() {
    List<String> ret = new ArrayList<>();
    for(Token t : tokens) {
      ret.add(t.getValue());
    }
    return ret;
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
    System.out.println("token make type: "+type);
    System.out.println("token make command: "+command);
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
    for (String key : syntaxMap.keySet()) {
      Pattern check = syntaxMap.get(key);
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

  public String getClassName(Token token) {
    return token.getClass().getName().replace(TOKEN_PACKAGE, "");
  }

  private boolean isList(String s) {
    return s.contains("List");
  }

  private boolean isListStart(String s) { return match(s, syntaxMap.get("ListStart")); }

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



}
