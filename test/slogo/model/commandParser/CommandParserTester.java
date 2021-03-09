package slogo.model.commandParser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.EnumSource.Mode;
import slogo.controller.ModelController;
import slogo.model.CommandParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import slogo.model.InputCleaner;
import slogo.model.tree.TreeNode;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandParserTester {
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
        CommandParser tester = makeParser("fd 50");
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
        CommandParser tester = makeParser(userInput);
        List<String> results = new ArrayList<>();
        results.add(null);
        results.add("Forward");
        results.add("50");
        results.add("Backward");
        results.add("50");
        assertEquals(results, tester.preOrderResults);
    }

    /**
     * Tests one parameters count
     */
    @Test
    void testVariable() {
        CommandParser tester = makeParser(":size 50");
        List<String> results = new ArrayList<>();
        results.add(null);
        results.add(":size");
        results.add("40");
        assertEquals(results, tester.preOrderResults);
    }


    private CommandParser makeParser(String userInput){
        ModelController modelController = new ModelController();
        CommandParser commandParser = new CommandParser(userInput, modelController);
        return commandParser;
    }

    private TreeNode makeTree(String root, TreeNode... children){
        return new TreeNode(root, root,  Arrays.asList(children.clone()));
    }

}
