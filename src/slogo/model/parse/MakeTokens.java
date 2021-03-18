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
import slogo.model.parse.tokens.CommandList;
import slogo.model.parse.tokens.ListEndToken;
import slogo.model.parse.tokens.ListToken;
import slogo.model.parse.tokens.Token;

public class MakeTokens {

  private final List<String> cleanedString;
  private List<Token> tokens;
  private static final String TOKEN_PACKAGE = MakeTokens.class.getPackageName() + ".tokens.";
  private static final String LANGUAGES_PACKAGE = "slogo.model.resources.languages.";
  private static final String COMMAND_PACKAGE = "slogo.model.resources.commands.";
  private static final String COMMAND_WITH_LISTS = "CommandBlocks";
  private static final String TOKENS_MAP = "TokenSyntax";

  private CommandParser commandParser;
  private ResourceBundle listParams;
  private ResourceBundle tokenMap;
  private Map<String, Pattern> regexMap;

  public MakeTokens(List<String> cleanedString, CommandParser commandParser) {
    this.commandParser = commandParser;
    this.cleanedString = cleanedString;
    listParams = ResourceBundle.getBundle(COMMAND_PACKAGE + COMMAND_WITH_LISTS);
    tokenMap = ResourceBundle.getBundle(LANGUAGES_PACKAGE + TOKENS_MAP);
    regexMap = new HashMap<>();
    addRegExPatterns("Syntax");
    tokens = new ArrayList<>();
  }

  private void addRegExPatterns(String regEx) {
    ResourceBundle resources = ResourceBundle.getBundle(LANGUAGES_PACKAGE + regEx);
    for (String key : Collections.list(resources.getKeys())) {
      String regex = resources.getString(key);
      regexMap.put(key, Pattern.compile(regex, Pattern.CASE_INSENSITIVE));
    }
  }

  public void tokenize() {
    Deque<List<String>> listCommandParams = new ArrayDeque<>();
    String expected = null; //next expected if list command
    boolean inList = false;
    for (String s : cleanedString) {
      System.out.println(s);
      Token toAdd;
      if (regexMap.get("ListStart").matcher(s).matches()) {
        System.out.println("Start of list type: "+listCommandParams.peek().get(0));
        toAdd = makeToken(listCommandParams.peek().get(0));
        inList = true;
      } else { toAdd = makeToken(s); }
      if (listParams.containsKey(s)) {
        listCommandParams.push(new ArrayList<>(Arrays.asList(listParams.getString(s).split(" "))));
        expected = listCommandParams.peek().get(0);
        tokens.add(toAdd);
        continue;
      }
      if (expected != null) {
        System.out.println("expected: "+expected);
        if(!getClassName(toAdd).equals(expected) && !inList) {
          System.out.println("Error. Class name is: " + getClassName(toAdd));
          throw new ErrorHandler("WrongCommandArg");
        } else if (!expected.contains("List") || getClassName(toAdd).equals("ListEndToken")){
          listCommandParams.peek().remove(0);
          if (listCommandParams.peek().isEmpty()) {
            listCommandParams.pop();
            expected = null;
          } else { expected = listCommandParams.peek().get(0); }
        }
      }
      tokens.add(toAdd);
    }
  }

  private Token makeToken(String command) {
    String type = tokenType(command);
    if(command.contains("List")) { type = command; }
    System.out.println(type);
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

  private String getClassName(Token token) {
    return token.getClass().getName().replace(TOKEN_PACKAGE, "");
  }

  public List<String> tokensToString() {
    List<String> ret = new ArrayList<>();
    for(Token t : tokens) {
      ret.add(t.getCommand());
    }
    System.out.println(ret);
    return ret;
  }

  private void commandBlockParams() {
    Deque<Token> commandBlocks = new ArrayDeque<>();
    Deque<Integer> parameters = new ArrayDeque<>();
    String commandKey = "CommandBlock_";
    String currBlock = "";
    int commandCount = 0;
    int blockSize = 0;
    for (int ind = 0; ind < tokens.size(); ind++) {
      String commandKeyNum = "";
      Token curr = tokens.get(ind);
      if(!commandBlocks.isEmpty()) {
        blockSize = commandBlocks.peek().incrementParamCount(blockSize, curr);
      }
      if (curr instanceof ListToken) {
        commandCount++;
        commandKeyNum = commandKey + Integer.toString(commandCount);
        curr.setVariable(commandKeyNum);
        commandBlocks.push(curr);
        parameters.push(blockSize);
        blockSize = 0;
      }
      if (curr instanceof ListEndToken) {
        tokens.remove(ind);
        ind--;
        Token popped = commandBlocks.pop();
        commandParser.addSingleParamCount(popped.getValue(), makeStringParam(blockSize));
        blockSize = parameters.pop();
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
