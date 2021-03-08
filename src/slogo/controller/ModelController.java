package slogo.controller;

import java.util.*;
import javax.swing.text.html.ImageView;
import slogo.model.CommandParser;
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
        CommandParser commandParser = new CommandParser(commandsInOneLine, this);

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
     * @param error
     */
    public void handleInputError(String error){

    }

    /**
     * Passes the arraylist of values necessary to modify the turtle in the front end. This method
     * is intended to be called in the model when the turtle is updated
     *
     * @param parameters The List of parameters to pass to the view
     */
    public void passInputToFrontEnd(List<Double> parameters){
        //: TODO Call a method on the viewController and pass it this arraylist of parameters
        viewController.passInputFromBackendToFrontEnd(parameters);
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