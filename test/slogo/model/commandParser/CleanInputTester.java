package slogo.model.commandParser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.controller.ModelController;
import slogo.model.CommandParser;
import slogo.model.InputCleaner;

public class CleanInputTester {

  private InputCleaner cleaner;

  /**
   * Sets up the commandParser
   */
  @BeforeEach
  void setUp() {
    ModelController modelController = new ModelController();

    String userInput = "fd 50 forward 10";
    CommandParser commandParser = new CommandParser(userInput, modelController);
    cleaner = new InputCleaner(userInput, modelController, commandParser);
  }


  /**
   * Tests translating english to simple commands recognizable by backend
   */
  @Test
  void testTranslation() {
    String userInput = "fd 50 forward 10";
    String expected = "Forward 50 Forward 10 ";
    assertEquals(cleaner.translateCommand(userInput), expected);
  }

  /**
   * Tests translating english to simple commands recognizable by backend
   */
  @Test
  void testNonExistentCommand() {
    String userInput = "hello";
    List<String> input = Arrays.asList(userInput.split(" "));
    List<String> expected = new ArrayList<>();
    assertEquals(cleaner.translateCommand(userInput), expected);
  }

}

