package slogo.model.commandParser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.controller.ModelController;
import slogo.model.parse.CommandBlockParser;
import slogo.model.parse.CommandParser;
import slogo.model.parse.InputCleaner;
import slogo.model.parse.MakeTokens;
import slogo.model.parse.tokens.Token;

public class TokensAndCommandBlockTest {

  private CommandParser commandParser;

  /**
   * Sets up the commandParser
   */
  @BeforeEach
  void setUp() {

  }

  /**
   * Test command without params
   */
  @Test
  void testSimpleTokenizer() {
    String userInput = "xcor";
    MakeTokens tokenMaker = makeMakeTokens(userInput, "English");
    List<Token> tokens = tokenMaker.tokenString();
    CommandBlockParser commandBlockParser = new CommandBlockParser(tokens, commandParser);
    List<String> actual = tokensToString(tokens);
    List<String> expected = new ArrayList<>();
    expected.add("XCoordinate");
    assertEquals(actual, expected);
  }

  /**
   * Test command with num params
   */
  @Test
  void testNumParamTokenizer() {
    String userInput = "sum 50 50";
    MakeTokens tokenMaker = makeMakeTokens(userInput, "English");
    List<Token> tokens = tokenMaker.tokenString();
    CommandBlockParser commandBlockParser = new CommandBlockParser(tokens, commandParser);
    List<String> actual = tokensToString(tokens);
    List<String> expected = new ArrayList<>();
    expected.add("Sum");
    expected.add("50");
    expected.add("50");
    assertEquals(actual, expected);
  }

  /**
   * Test command with constant list
   */
  @Test
  void testConstantList() {
    String userInput = "tell [ 1 2 3 4 5 ]";
    MakeTokens tokenMaker = makeMakeTokens(userInput, "English");
    List<Token> tokens = tokenMaker.tokenString();
    CommandBlockParser commandBlockParser = new CommandBlockParser(tokens, commandParser);
    List<String> actual = tokensToString(tokens);
    List<String> expected = new ArrayList<>();
    expected.add("Tell");
    expected.add("CommandBlock_1");
    expected.add("1");
    expected.add("2");
    expected.add("3");
    expected.add("4");
    expected.add("5");
    assertEquals(actual, expected);
    assertEquals(5, commandParser.commandParam.get("CommandBlock_1").size());
  }

  /**
   * Test command with user defined commands in the commands
   * to slice [ ]
   * [
   *   rt 30
   *   fd 50
   * ]
   */
  @Test
  void testUserDefInList() {
    String userInput = "to slice [ ]\n"
        + "[\n"
        + "  rt 30\n"
        + "  fd 50\n"
        + "]";
    MakeTokens tokenMaker = makeMakeTokens(userInput, "English");
    List<Token> tokens = tokenMaker.tokenString();
    CommandBlockParser commandBlockParser = new CommandBlockParser(tokens, commandParser);
    List<String> actual = tokensToString(tokens);
    List<String> expected = new ArrayList<>();
    expected.add("MakeUserInstruction");
    expected.add("slice");
    expected.add("CommandBlock_1");
    expected.add("CommandBlock_2");
    expected.add("Right");
    expected.add("30");
    expected.add("Forward");
    expected.add("50");
    assertEquals(actual, expected);
    assertEquals(0, commandParser.commandParam.get("slice").size());
    assertEquals(0, commandParser.commandParam.get("CommandBlock_1").size());
    assertEquals(2, commandParser.commandParam.get("CommandBlock_2").size());
  }

  /**
   * Test command with user defined commands and reuse of variables
   * set :distance 50
   * set :angle 10
   *
   * to square [ ]
   * [
   *   repeat 4
   *   [
   *     fd :distance
   *     rt 90
   *   ]
   * ]
   *
   * to tunnel [ ]
   * [
   *   repeat 10
   *   [
   *     square
   *     set :distance sum :distance 10
   *   ]
   * ]
   *
   * to warp [ ]
   * [
   *   repeat 10
   *   [
   *     square
   *     rt :angle
   *     set :distance sum :distance 10
   *   ]
   * ]
   *
   * cs
   * tunnel
   */
  @Test
  void testVarRedefList() {
    String userInput = "set :distance 50\n"
        + "set :angle 10\n"
        + "\n"
        + "to square [ ]\n"
        + "[\n"
        + "  repeat 4\n"
        + "  [\n"
        + "    fd :distance\n"
        + "    rt 90\n"
        + "  ]\n"
        + "]\n"
        + "\n"
        + "to tunnel [ ]\n"
        + "[\n"
        + "  repeat 10\n"
        + "  [\n"
        + "    square\n"
        + "    set :distance sum :distance 10\n"
        + "  ]\n"
        + "]\n"
        + "\n"
        + "to warp [ ]\n"
        + "[\n"
        + "  repeat 10\n"
        + "  [\n"
        + "    square\n"
        + "    rt :angle\n"
        + "    set :distance sum :distance 10\n"
        + "  ]\n"
        + "]\n"
        + "\n"
        + "\n"
        + "cs\n"
        + "tunnel";
    MakeTokens tokenMaker = makeMakeTokens(userInput, "English");
    List<Token> tokens = tokenMaker.tokenString();
    CommandBlockParser commandBlockParser = new CommandBlockParser(tokens, commandParser);
    List<String> actual = tokensToString(tokens);
    assertEquals(0, commandParser.commandParam.get("square").size());
    assertEquals(0, commandParser.commandParam.get("tunnel").size());
    assertEquals(0, commandParser.commandParam.get("warp").size());
    assertEquals(0, commandParser.commandParam.get("CommandBlock_1").size());
    assertEquals(1, commandParser.commandParam.get("CommandBlock_2").size());
    assertEquals(2, commandParser.commandParam.get("CommandBlock_3").size());
    assertEquals(0, commandParser.commandParam.get("CommandBlock_4").size());
    assertEquals(1, commandParser.commandParam.get("CommandBlock_5").size());
    assertEquals(2, commandParser.commandParam.get("CommandBlock_6").size());
    assertEquals(0, commandParser.commandParam.get("CommandBlock_7").size());
    assertEquals(1, commandParser.commandParam.get("CommandBlock_8").size());
    assertEquals(3, commandParser.commandParam.get("CommandBlock_9").size());


  }

  /**
   * Test command with constant list that contains non constants
   */
  @Test
  void testNonConstantList() {
    String userInput = "tell [ :t sum 50 50 ]";
    MakeTokens tokenMaker = makeMakeTokens(userInput, "English");
    List<Token> tokens = tokenMaker.tokenString();
    CommandBlockParser commandBlockParser = new CommandBlockParser(tokens, commandParser);
    List<String> actual = tokensToString(tokens);
    List<String> expected = new ArrayList<>();
    expected.add("Tell");
    expected.add("CommandBlock_1");
    expected.add(":t");
    expected.add("Sum");
    expected.add("50");
    expected.add("50");
    assertEquals(actual, expected);
    assertEquals(2, commandParser.commandParam.get("CommandBlock_1").size());
  }

  /**
   * Test command with enclosed num params
   */
  @Test
  void testEnclosedNumParamTokenizer() {
    String userInput = "fd sum 50 50";
    MakeTokens tokenMaker = makeMakeTokens(userInput, "English");
    List<Token> tokens = tokenMaker.tokenString();
    CommandBlockParser commandBlockParser = new CommandBlockParser(tokens, commandParser);
    List<String> actual = tokensToString(tokens);
    List<String> expected = new ArrayList<>();
    expected.add("Forward");
    expected.add("Sum");
    expected.add("50");
    expected.add("50");
    assertEquals(actual, expected);
  }

  /**
   * Test command with brackets
   */
  @Test
  void testBracketTokenizer() {
    String userInput = "repeat 4 [ fd 50 ]";
    MakeTokens tokenMaker = makeMakeTokens(userInput, "English");
    List<Token> tokens = tokenMaker.tokenString();
    CommandBlockParser commandBlockParser = new CommandBlockParser(tokens, commandParser);
    List<String> actual = tokensToString(tokens);
    List<String> expected = new ArrayList<>();
    expected.add("Repeat");
    expected.add("4");
    expected.add("CommandBlock_1");
    expected.add("Forward");
    expected.add("50");
    assertEquals(actual, expected);
    assertEquals(commandParser.commandParam.get("CommandBlock_1").size(), 1);
  }

  /**
   * Test command with brackets without constant before bracket
   */
  @Test
  void testBracketWithCommandTokenizer() {
    String userInput = "repeat sum 5 5 [ fd 50 ]";
    MakeTokens tokenMaker = makeMakeTokens(userInput, "English");
    List<Token> tokens = tokenMaker.tokenString();
    CommandBlockParser commandBlockParser = new CommandBlockParser(tokens, commandParser);
    List<String> actual = tokensToString(tokens);
    List<String> expected = new ArrayList<>();
    expected.add("Repeat");
    expected.add("Sum");
    expected.add("5");
    expected.add("5");
    expected.add("CommandBlock_1");
    expected.add("Forward");
    expected.add("50");
    assertEquals(actual, expected);
    assertEquals(commandParser.commandParam.get("CommandBlock_1").size(), 1);
  }

  /**
   * Test command with brackets without constant before bracket and extra command (to be detected in command parser)
   */
  @Test
  void testBracketWithExtraCommandTokenizer() {
    String userInput = "repeat sum 5 5 sum 5 5 [ fd 50 ]";
    MakeTokens tokenMaker = makeMakeTokens(userInput, "English");
    List<Token> tokens = tokenMaker.tokenString();
    CommandBlockParser commandBlockParser = new CommandBlockParser(tokens, commandParser);
    List<String> actual = tokensToString(tokens);
    List<String> expected = new ArrayList<>();
    expected.add("Repeat");
    expected.add("Sum");
    expected.add("5");
    expected.add("5");
    expected.add("Sum");
    expected.add("5");
    expected.add("5");
    expected.add("CommandBlock_1");
    expected.add("Forward");
    expected.add("50");
    assertEquals(actual, expected);
    assertEquals(commandParser.commandParam.get("CommandBlock_1").size(), 1);
  }

  /**
   * Test command with nested brackets
   */
  @Test
  void testNestedBracketTokenizer() {
    String userInput = "to move [ :x ] [ repeat 4 [ fd :x ] ]";
    MakeTokens tokenMaker = makeMakeTokens(userInput, "English");
    List<Token> tokens = tokenMaker.tokenString();
    CommandBlockParser commandBlockParser = new CommandBlockParser(tokens, commandParser);
    List<String> actual = tokensToString(tokens);
    List<String> expected = new ArrayList<>();
    expected.add("MakeUserInstruction");
    expected.add("move");
    expected.add("CommandBlock_1");
    expected.add(":x");
    expected.add("CommandBlock_2");
    expected.add("Repeat");
    expected.add("4");
    expected.add("CommandBlock_3");
    expected.add("Forward");
    expected.add(":x");
    assertEquals(actual, expected);
    assertEquals(commandParser.commandParam.get("CommandBlock_1").size(), 1);
    assertEquals(commandParser.commandParam.get("CommandBlock_2").size(), 1);
    assertEquals(commandParser.commandParam.get("CommandBlock_3").size(), 1);
  }

  /**
   * Test command with more nested brackets
   */
  @Test
  void testMoreNestedBracketTokenizer() {
    String userInput = "to move [ :x ] [ repeat 4 [ repeat 2 [ fd :x ] ] ]";
    MakeTokens tokenMaker = makeMakeTokens(userInput, "English");
    List<Token> tokens = tokenMaker.tokenString();
    CommandBlockParser commandBlockParser = new CommandBlockParser(tokens, commandParser);
    List<String> actual = tokensToString(tokens);
    List<String> expected = new ArrayList<>();
    expected.add("MakeUserInstruction");
    expected.add("move");
    expected.add("CommandBlock_1");
    expected.add(":x");
    expected.add("CommandBlock_2");
    expected.add("Repeat");
    expected.add("4");
    expected.add("CommandBlock_3");
    expected.add("Repeat");
    expected.add("2");
    expected.add("CommandBlock_4");
    expected.add("Forward");
    expected.add(":x");
    assertEquals(actual, expected);
    assertEquals(commandParser.commandParam.get("CommandBlock_1").size(), 1);
    assertEquals(commandParser.commandParam.get("CommandBlock_2").size(), 1);
    assertEquals(commandParser.commandParam.get("CommandBlock_3").size(), 1);
    assertEquals(commandParser.commandParam.get("CommandBlock_4").size(), 1);
  }

  /**
   * Test command with more nested brackets
   * Test where user uses variable instead of constant
   * to moveRep5 [ :rep ] [ repeat :rep [ fd 5 ] ]
   */
  @Test
  void testVariableConstantTokenizer() {
    String userInput = "to moveRep [ :rep ] [ repeat :rep [ fd 5 ] ]";
    MakeTokens tokenMaker = makeMakeTokens(userInput, "English");
    List<Token> tokens = tokenMaker.tokenString();
    CommandBlockParser commandBlockParser = new CommandBlockParser(tokens, commandParser);
    List<String> actual = tokensToString(tokens);
    List<String> expected = new ArrayList<>();
    expected.add("MakeUserInstruction");
    expected.add("moveRep");
    expected.add("CommandBlock_1");
    expected.add(":rep");
    expected.add("CommandBlock_2");
    expected.add("Repeat");
    expected.add(":rep");
    expected.add("CommandBlock_3");
    expected.add("Forward");
    expected.add("5");
    assertEquals(actual, expected);
    assertEquals(commandParser.commandParam.get("CommandBlock_1").size(), 1);
    assertEquals(commandParser.commandParam.get("CommandBlock_2").size(), 1);
    assertEquals(commandParser.commandParam.get("CommandBlock_3").size(), 1);
  }


  /*

  Helper Methods

   */

  private MakeTokens makeMakeTokens(String input, String language) {
    ModelController modelController = new ModelController();
    commandParser = new CommandParser(input, language, modelController);
    InputCleaner cleaner = new InputCleaner(input, language, modelController);
    List<String> cleanedString = cleaner.parseResults();
    return new MakeTokens(cleanedString);
  }

  private List<String> tokensToString(List<Token> tokens) {
    List<String> ret = new ArrayList<>();
    for(Token t : tokens) {
      ret.add(t.getValue());
    }
    return ret;
  }





}
