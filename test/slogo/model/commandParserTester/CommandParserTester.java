package slogo.model.commandParserTester;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.controller.ModelController;
import slogo.model.CommandParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandParserTester {
    private CommandParser parser;

    /**
     * Sets up the commandParser
     */
    @BeforeEach
    void setUp() {
        ModelController modelController = new ModelController();
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
     * Tests translating english to simple commands recognizable by backend
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

    /**
     * Tests translating english to simple commands recognizable by backend
     */
    @Test
    void testNonExistentCommand() {
        String userInput = "hello";
        List<String> input = Arrays.asList(userInput.split(" "));
        List<String> expected = new ArrayList<>();
        assertEquals(parser.translateCommand(input), expected);
    }

    /**
     * Tests translating mult lang simple commands recognizable by backend
     * also makes sure that special characters like + and [ work
     */
    @Test
    void testLanguageTranslation() {
        String userInput = "ava 50 se + 1 2 [ fd 10 ]";
        List<String> input = Arrays.asList(userInput.split(" "));
        List<String> expected = new ArrayList<>();
        expected.add("Forward");
        expected.add("50");
        expected.add("If");
        expected.add("Sum");
        expected.add("1");
        expected.add("2");
        expected.add("[");
        expected.add("Forward");
        expected.add("10");
        expected.add("]");
        assertEquals(parser.translateCommand(input), expected);
    }




}
