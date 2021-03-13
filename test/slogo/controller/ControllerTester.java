package slogo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.model.CommandParser;

public class ControllerTester {
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
