package slogo.model.commandParserTester;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.model.CommandParserTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandParserTester {
    private CommandParserTest parser;

    /**
     * Sets up the commandParser
     */
    @BeforeEach
    void setUp() {
        parser = new CommandParserTest();
    }


    /**
     * Tests one parameters count
     */
    @Test
    void testOneCommand() {
        assertEquals(parser.getSymbol("Forward"), "1");
    }

    /**
     * Tests multiple parameters count
     */
    @Test
    void testMultCommand() {
        String userInput = "Forward Backward";
        List<String> input = Arrays.asList(userInput.split(" "));
        assertEquals(parser.commandParamCount(input), "1 1 ");
    }
}
