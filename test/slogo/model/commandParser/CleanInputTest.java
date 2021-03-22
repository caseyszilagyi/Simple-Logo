package slogo.model.commandParser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.ErrorHandler;
import slogo.controller.ModelController;
import slogo.model.parse.CommandParser;
import slogo.model.parse.InputCleaner;

public class CleanInputTest {

  /**
   * Sets up the commandParser
   */
  @BeforeEach
  void setUp() {

  }

  /**
   * Test specific command translation
   */
  @Test
  void testXCor() {
    String userInput = "xcor";
    InputCleaner cleaner = makeInputCleaner(userInput, "English");
    List<String> expected = new ArrayList<>();
    expected.add("XCoordinate");
    assertEquals(cleaner.parseResults(), expected);
  }

  /**
   * Test language translation
   */
  @Test
  void testLanguage() {
    String userInput = "# comment\nqianjin 50 ht 50 chongfu 5 [ qj 50 ]";
    InputCleaner cleaner = makeInputCleaner(userInput, "Chinese");
    List<String> expected = new ArrayList<>();
    expected.add("Forward");
    expected.add("50");
    expected.add("Backward");
    expected.add("50");
    expected.add("Repeat");
    expected.add("5");
    expected.add("[");
    expected.add("Forward");
    expected.add("50");
    expected.add("]");
    assertEquals(cleaner.parseResults(), expected);
  }

  /**
   * Test invalid language translation
   */
  @Test
  void testErrorLanguage() {
    String userInput = "# comment\nforward 50 ht 50 chongfu 5 [ qj 50 ]";
    InputCleaner cleaner = makeInputCleaner(userInput, "Chinese");
    assertEquals(cleaner.parseResults(), new ErrorHandler("InvalidCommandName"));
  }

  /**
   * Test all steps (remove commands and group blocks
   */
  @Test
  void testCleaningAll() {
    String userInput = "if :size < 5 \n#comment\n[ forward :size back :size ]";
    InputCleaner cleaner = makeInputCleaner(userInput, "English");
    List<String> expected = new ArrayList<>();
    expected.add("If");
    expected.add(":size");
    expected.add("<");
    expected.add("5");
    expected.add("[");
    expected.add("Forward");
    expected.add(":size");
    expected.add("Backward");
    expected.add(":size");
    expected.add("]");
    assertEquals(cleaner.parseResults(), expected);
  }

  /**
   * Test all steps (remove commands and group blocks
   */
  @Test
  void testMultCommandBlocks() {
    String userInput = "if :size < 5 \n#comment\n[ forward :size back :size ] repeat 4 [ forward 5 ]";
    InputCleaner cleaner = makeInputCleaner(userInput, "English");
    List<String> expected = new ArrayList<>();
    expected.add("If");
    expected.add(":size");
    expected.add("<");
    expected.add("5");
    expected.add("[");
    expected.add("Forward");
    expected.add(":size");
    expected.add("Backward");
    expected.add(":size");
    expected.add("]");
    expected.add("Repeat");
    expected.add("4");
    expected.add("[");
    expected.add("Forward");
    expected.add("5");
    expected.add("]");
    assertEquals(cleaner.parseResults(), expected);
 }

  /**
   * Test two wrapped brackets
   */
  @Test
  void testTwoWrappedBrackets() {
    String userInput = "repeat 2 [ repeat 3 [ fd 100 ] ]";
    InputCleaner cleaner = makeInputCleaner(userInput, "English");
    List<String> expected = new ArrayList<>();
    expected.add("Repeat");
    expected.add("2");
    expected.add("[");
    expected.add("Repeat");
    expected.add("3");
    expected.add("[");
    expected.add("Forward");
    expected.add("100");
    expected.add("]");
    expected.add("]");
    assertEquals(cleaner.parseResults(), expected);
 }


  /**
   * Tests the Repeat command repeat 2 [ repeat 3 [ fd 100 ] ]
   */
  @Test
  void testUserDefCommands() {
    String input = "to Movement [ :distance ] [ fd :distance ]";
    InputCleaner cleaner = makeInputCleaner(input, "English");
    List<String> expected = new ArrayList<>();
    expected.add("MakeUserInstruction");
    expected.add("Movement");
    expected.add("[");
    expected.add(":distance");
    expected.add("]");
    expected.add("[");
    expected.add("Forward");
    expected.add(":distance");
    expected.add("]");
    assertEquals(expected, cleaner.parseResults());
  }



  private InputCleaner makeInputCleaner(String userInput, String language) {
    ModelController modelController = new ModelController();
    InputCleaner cleaner = new InputCleaner(userInput, language, modelController);
    return cleaner;
  }


}

