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
        parser = new CommandParser(userInput, modelController);
    }


    /**
     * Tests one parameters count
     */
    @Test
    void testOneCommand() {
        assertEquals(parser.getParamCount("Forward"), 1);
    }

    /**
     * Tests multiple parameters count
     */
    @Test
    void testMultCommand() {
        String userInput = "Forward Backward";
        List<String> input = Arrays.asList(userInput.split(" "));
        for (String s : input) {
            assertEquals(parser.getParamCount(s), 1);
        }
    }

    /**
     * Tests one parameters count
     */
    @Test
    void testTranslation() {
        String userInput = "fd 50 forward 10";
        List<String> input = Arrays.asList(userInput.split(" "));
        List<String> expected = new ArrayList<>();
        expected.add("Forward");
        expected.add("50");
        expected.add("Forward");
        expected.add("10");
        assertEquals(parser.translateCommand(input), expected);
    }


}
