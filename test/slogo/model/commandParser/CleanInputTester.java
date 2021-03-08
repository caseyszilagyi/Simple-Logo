package slogo.model.commandParser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.EnumSource.Mode;
import slogo.controller.ModelController;
import slogo.model.CommandParser;
import slogo.model.InputCleaner;

public class CleanInputTester {

  /**
   * Sets up the commandParser
   */
  @BeforeEach
  void setUp() {

  }


  /**
   * Tests translating english to simple commands recognizable by backend
   */

  void testTranslation() {
    String userInput = "fd 50 forward 10";
    InputCleaner cleaner = makeInputCleaner(userInput);
    String expected = "Forward 50 Forward 10 ";
//    assertEquals(cleaner.translateCommand(userInput), expected);
  }

  /**
   * Tests translating english to simple commands recognizable by backend
   */

  void testNonExistentCommand() {
    String userInput = "hello";
    List<String> input = Arrays.asList(userInput.split(" "));
    List<String> expected = new ArrayList<>();
//    assertEquals(cleaner.translateCommand(userInput), expected);
  }

  /**
   * Tests removing comments
   */

  void testRemovingCommentsBeginning() {
    String userInput = "# fd 50 \n";
    String expected = " ";
//    assertEquals(cleaner.removeComments(), expected);
  }

  /**
   * Tests removing comments
   */
  void testRemovingCommentsMultiple() {
    String userInput = "# fd 50 \n# fd 50 \nfd 50";
    String expected = "  fd 50";
//    assertEquals(cleaner.removeComments(), expected);
  }

  /**
   * Tests refactoring command blocks (private method but makde public to test alone)
   */

  void testCommandBlocks() {
    String userInput = "if :size < 5 [ forward :size back :size stop ]";
    InputCleaner cleaner = makeInputCleaner(userInput);
    List<String> input = Arrays.asList(userInput.split(" "));
    List<String> expected = new ArrayList<>();
    expected.add("if");
    expected.add(":size");
    expected.add("<");
    expected.add("5");
    expected.add("CommandBlock");
    expected.add("forward");
    expected.add(":size");
    expected.add("back");
    expected.add(":size");
    expected.add("stop");
//    assertEquals(cleaner.findCommandBlocks(input), expected);
  }

  /**
   * Test all steps (remove commands and group blocks
   */
  @Test
  void testCleaningAll() {
    String userInput = "if :size < 5 \n#comment\n[ forward :size back :size stop ]";
    InputCleaner cleaner = makeInputCleaner(userInput);
    List<String> expected = new ArrayList<>();
    expected.add("If");
    expected.add(":size");
    expected.add("<");
    expected.add("5");
    expected.add("CommandBlock_1");
    expected.add("Forward");
    expected.add(":size");
    expected.add("Backward");
    expected.add(":size");
    expected.add("stop");
    assertEquals(cleaner.cleanString(), expected);
    assertEquals(cleaner.commandParser.getParamCount("CommandBlock_1"), 3);
  }

  /**
   * Test all steps (remove commands and group blocks
   */
  @Test
  void testMultCommandBlocks() {
    String userInput = "if :size < 5 \n#comment\n[ forward :size back :size stop ] repeat 4 [ forward 5 ]";
    InputCleaner cleaner = makeInputCleaner(userInput);
    List<String> expected = new ArrayList<>();
    expected.add("If");
    expected.add(":size");
    expected.add("<");
    expected.add("5");
    expected.add("CommandBlock_1");
    expected.add("Forward");
    expected.add(":size");
    expected.add("Backward");
    expected.add(":size");
    expected.add("stop");
    expected.add("Repeat");
    expected.add("4");
    expected.add("CommandBlock_2");
    expected.add("Forward");
    expected.add("5");
    assertEquals(cleaner.cleanString(), expected);
    assertEquals(cleaner.commandParser.getParamCount("CommandBlock_1"), 3);
    assertEquals(cleaner.commandParser.getParamCount("CommandBlock_2"), 1);

  }

  private InputCleaner makeInputCleaner(String userInput){
    ModelController modelController = new ModelController();
    CommandParser commandParser = new CommandParser(userInput, modelController);
    InputCleaner cleaner = new InputCleaner(userInput, modelController, commandParser);
    return cleaner;
  }




}

