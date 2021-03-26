package slogo.model.parse;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.ResourceBundle;
import slogo.ErrorHandler;
import slogo.model.SLogoCommandExecutor;
import slogo.model.parse.tokens.ListEndToken;
import slogo.model.parse.tokens.Token;
import slogo.model.parse.tokens.TokenFactory;

/**
 * Condenses the translated String input as a List into a List of Token objects based on the Regex token.
 * Also deals with different types of lists in String input. More details are in the TokenFactory javadoc in terms of
 * explaining what a Token is and its purpose. This class just organizes and uses this class to make it ready for
 * counting paameters and extracting desired information about commands.
 *
 * @author jincho
 */
public class TokensParser extends Parser {
  private final List<String> cleanedString;
  private List<Token> tokens;
  public static final String TOKEN_PACKAGE = TokensParser.class.getPackageName() + ".tokens.";
  private static final String COMMAND_WITH_LISTS = "CommandBlocks";

  private ResourceBundle listParams;
  private Deque<List<String>> tokenizeStack;
  private TokenFactory tokenFactory;

  /**
   * Constructs the TokensParser object and necessary instance variables.
   *
   * @param cleanedString list of string commands recognizable by the back end
   */
  public TokensParser(List<String> cleanedString) {
    this.cleanedString = cleanedString;
    tokenFactory = new TokenFactory();
    listParams = ResourceBundle.getBundle(SLogoCommandExecutor.COMMAND_PACKAGE + COMMAND_WITH_LISTS);
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
    return tokens;
  }

  /**
   * creates a list of strings that just contains the actual command/constant/variable value associated with the token
   *
   * @return this list of strings that specifies the command/value type
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
          toAdd = tokenFactory.makeToken(tokenizeStack.peek().get(0));
        } catch (Exception e) {
          throw new ErrorHandler("WrongParamNum");
        }
        inList = true;
      } else { toAdd = tokenFactory.makeToken(s); }
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

  private boolean isListStart(String s) { return match(s, syntaxMap.get("ListStart")); }

  private String checkExpectedToken(Token toAdd, String expected, boolean inList) {
    if(!getClassName(toAdd).equals(expected) && !inList) {
      throw new ErrorHandler("WrongParamNum");
    } else if (!isList(expected) || toAdd instanceof ListEndToken) {
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
