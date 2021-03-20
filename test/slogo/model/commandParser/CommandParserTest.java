package slogo.model.commandParser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.ErrorHandler;
import slogo.controller.BackEndExternalAPI;
import slogo.controller.ModelController;
import slogo.model.parse.CommandParser;
import slogo.model.tree.TreeNode;

public class CommandParserTest {

  private CommandParser parser;

  /**
   * Sets up the commandParser
   */
  @BeforeEach
  void setUp() {

  }


  /**
   * Tests one parameters count
   */
  @Test
  void testOneCommand() {
    CommandParser tester = makeParser("fd 50", "English");
    TreeNode root = tester.makeTree();
    List<String> results = new ArrayList<>();
    results.add(null);
    results.add("Forward");
    results.add("50");
    assertEquals(results, tester.preOrderResults);
  }

  /**
   * Tests one parameters count
   */
  @Test
  void testOneWrongCommand() {
    CommandParser tester = makeParser("fd 50 60", "English");
    TreeNode root = tester.makeTree();
    List<String> results = new ArrayList<>();
    results.add(null);
    results.add("Forward");
    results.add("50");
    assertEquals(results, tester.preOrderResults);
  }

  /**
   * Tests multiple parameters count
   */
  @Test
  void testMultCommand() {
    String userInput = "Forward 50 Backward 50 ";
    CommandParser tester = makeParser(userInput, "English");
    TreeNode root = tester.makeTree();
    List<String> results = new ArrayList<>();
    results.add(null);
    results.add("Forward");
    results.add("50");
    results.add("Backward");
    results.add("50");
    assertEquals(results, tester.preOrderResults);
  }

  /**
   * Tests multiple parameters count
   */
  @Test
  void testMultParamCommand() {
    String userInput = "Sum 50 50 ";
    CommandParser tester = makeParser(userInput, "English");
    TreeNode root = tester.makeTree();
    List<String> results = new ArrayList<>();
    results.add(null);
    results.add("Sum");
    results.add("50");
    results.add("50");
    assertEquals(results, tester.preOrderResults);
  }

  /**
   * Tests multiple parameters count
   */
  @Test
  void testBracketCommand() {
    String userInput = "To x [ :dist ] [ sum :dist 5 ] ";
    CommandParser tester = makeParser(userInput, "English");
    TreeNode root = tester.makeTree();
    List<String> results = new ArrayList<>();
    results.add(null);
    results.add("MakeUserInstruction");
    results.add("x");
    results.add("CommandBlock_1");
    results.add(":dist");
    results.add("CommandBlock_2");
    results.add("Sum");
    results.add(":dist");
    results.add("5");
    assertEquals(results, tester.preOrderResults);
  }

  /**
   * Tests one parameters count
   */
  @Test
  void testVariable() {
    CommandParser tester = makeParser(":size 50", "English");
    TreeNode root = tester.makeTree();
    List<String> results = new ArrayList<>();
    results.add(null);
    results.add(":size");
    results.add("50");
    assertEquals(results, tester.preOrderResults);
  }

  /**
   * Tests wrong param input
   */
  @Test
  void testWrongNumParam() {
    CommandParser tester = makeParser("to x y", "English");
    assertEquals(tester.makeTree(), new ErrorHandler("WrongParamNum"));
  }

  /**
   * Tests wrong param input. correct list types but command is missing parameter count
   */
  @Test
  void testWrongNumParamComplex() {
    CommandParser tester = makeParser("to x [ :y ] [ sum 50 ]", "English");
    assertEquals(tester.makeTree(), new ErrorHandler("WrongParamNum"));
  }

  /**
   * Tests wrong input type where brackets are misused
   */
  @Test
  void testWrongParamInput() {
    CommandParser tester = makeParser("sum [ fd 50 ]", "English");
    assertEquals(tester.makeTree(), new ErrorHandler("WrongParamNum"));
  }


  private CommandParser makeParser(String userInput, String language) {
    BackEndExternalAPI modelController = new ModelController();
    CommandParser commandParser = new CommandParser(userInput, language, modelController);
    return commandParser;
  }

  private TreeNode makeTree(String root, TreeNode... children) {
    return new TreeNode(root, root, Arrays.asList(children.clone()));
  }

}
