package slogo.controller;

import java.util.*;
import javax.swing.text.html.ImageView;
import slogo.model.turtle.Turtle;

/**
 * 
 */
public class ModelController implements BackEndExternalAPI {

    ViewController viewController;

    /**
     * Default constructor
     */
    public ModelController() {
    }

    /**
     *
     * @return
     */
    public List<String> getCommandHistory() {
        // TODO implement here
        return null;
    }

    /**
     * 
     */
    public void removeUserDefinedCommand() {
        // TODO implement here
    }

    /**
     * 
     */
    public void addVariable() {
        // TODO implement here
    }

    /**
     * 
     */
    public void removeVariable() {
        // TODO implement here
    }

    /**
     * 
     */
    public void getVariable() {
        // TODO implement here
    }

    /**
     *
     * @return
     */
    public List<String> getUserDefinedCommands() {
        // TODO implement here
        return null;
    }

    /**
     * 
     */
    public void addUserDefinedCommands() {
        // TODO implement here
    }

    /**
     * @param input String input
     */
    public void parseInput(String input) {
        // TODO implement here
        System.out.println("ModelController received the following string as input: \n" + input);

        String commandsInOneLine = convertInputIntoOneLineStringSeparatedBySingleSpace(input);
        System.out.println("Commands after manipulation: \n" + commandsInOneLine);
    }

    private String convertInputIntoOneLineStringSeparatedBySingleSpace(String input) {
        String commandsInOneLine = input.replaceAll("[\r\n]+", " ");
        commandsInOneLine = commandsInOneLine.trim().replaceAll(" +", " ");

        return commandsInOneLine;
    }

    private void manipulateStringToBeAllInOneLine(String input) {

    }

    /**
     *
     * @return
     */
    public ImageView getTurtleImage() {
        // TODO implement here
        return null;
    }

    /**
     *
     * @return
     */
    public List<String> getCommandResult() {
        // TODO implement here
        return null;
    }

    /**
     *
     * @return
     */
    public List<Turtle> getAllTurtles() {
        // TODO implement here
        return null;
    }

    @Override
    public void setViewController(ViewController viewController) {
        this.viewController = viewController;
    }

}