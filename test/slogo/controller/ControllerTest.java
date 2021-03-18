package slogo.controller;

import org.junit.jupiter.api.BeforeEach;
import slogo.model.CommandParser;

public class ControllerTest {

  private CommandParser parser;

  /**
   * Sets up the commandParser
   */
  @BeforeEach
  void setUp() {
    BackEndExternalAPI modelController = new ModelController();
    String userInput = "fd";
//        parser = new CommandParser(userInput, modelController);
  }

}
