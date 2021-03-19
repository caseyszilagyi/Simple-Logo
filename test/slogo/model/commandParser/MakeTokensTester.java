package slogo.model.commandParser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.controller.ModelController;
import slogo.model.parse.CommandParser;
import slogo.model.parse.InputCleaner;
import slogo.model.parse.MakeTokens;

public class MakeTokensTester {

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
    List<String> actual = tokenMaker.tokenString();
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
    List<String> actual = tokenMaker.tokenString();
    List<String> expected = new ArrayList<>();
    expected.add("Sum");
    expected.add("50");
    expected.add("50");
    assertEquals(actual, expected);
  }

  /**
   * Test command with enclosed num params
   */
  @Test
  void testEnclosedNumParamTokenizer() {
    String userInput = "fd sum 50 50";
    MakeTokens tokenMaker = makeMakeTokens(userInput, "English");
    List<String> actual = tokenMaker.tokenString();
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
    List<String> actual = tokenMaker.tokenString();
    List<String> expected = new ArrayList<>();
    expected.add("Repeat");
    expected.add("4");
    expected.add("CommandBlock");
    expected.add("Forward");
    expected.add("50");
    assertEquals(actual, expected);
    assertEquals(commandParser.getParamCount("CommandBlock_1"), 1);

  }

  /**
   * Test command with nested brackets
   */
  @Test
  void testNestedBracketTokenizer() {
    String userInput = "to move [ :x ] [ repeat 4 [ fd :x ] ]";
    MakeTokens tokenMaker = makeMakeTokens(userInput, "English");
    List<String> actual = tokenMaker.tokenString();
    List<String> expected = new ArrayList<>();
    expected.add("MakeUserInstruction");
    expected.add("move");
    expected.add("CommandBlock");
    expected.add(":x");
    expected.add("CommandBlock");
    expected.add("Repeat");
    expected.add("4");
    expected.add("CommandBlock");
    expected.add("Forward");
    expected.add(":x");
    assertEquals(actual, expected);
    assertEquals(commandParser.getParamCount("CommandBlock_1"), 1);
    assertEquals(commandParser.getParamCount("CommandBlock_2"), 2);
    assertEquals(commandParser.getParamCount("CommandBlock_3"), 1);
  }

  /**
   * Test command with more nested brackets
   */
  @Test
  void testMoreNestedBracketTokenizer() {
    String userInput = "to move [ :x ] [ repeat 4 [ repeat 2 [ fd :x ] ] ]";
    MakeTokens tokenMaker = makeMakeTokens(userInput, "English");
    List<String> actual = tokenMaker.tokenString();
    List<String> expected = new ArrayList<>();
    expected.add("MakeUserInstruction");
    expected.add("move");
    expected.add("CommandBlock");
    expected.add(":x");
    expected.add("CommandBlock");
    expected.add("Repeat");
    expected.add("4");
    expected.add("CommandBlock");
    expected.add("Repeat");
    expected.add("2");
    expected.add("CommandBlock");
    expected.add("Forward");
    expected.add(":x");
    assertEquals(actual, expected);
    assertEquals(commandParser.getParamCount("CommandBlock_1"), 1);
    assertEquals(commandParser.getParamCount("CommandBlock_2"), 2);
    assertEquals(commandParser.getParamCount("CommandBlock_3"), 2);
    assertEquals(commandParser.getParamCount("CommandBlock_4"), 1);
  }

  /*

  Helper Methods

   */

  private MakeTokens makeMakeTokens(String input, String language) {
    ModelController modelController = new ModelController();
    commandParser = new CommandParser(input, language, modelController);
    System.out.println(CommandParser.syntaxMap.get("Command"));
    InputCleaner cleaner = new InputCleaner(input, language, modelController, commandParser);
    List<String> cleanedString = cleaner.cleanString();
    return new MakeTokens(cleanedString, commandParser);
  }

}
