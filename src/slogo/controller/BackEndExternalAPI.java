package slogo.controller;

import java.util.*;
import javax.swing.text.html.ImageView;
import slogo.model.turtle.Turtle;

/**
 * BackEndExternal API methods the frontend can call
 */
public interface BackEndExternalAPI {

    /**
     * Return all command history to be displayed
     * @return List of Strings that represent the command histories
     */
    public List<String> getCommandHistory();

    /**
     * Allows user to remove a specific user-defined command
     */
    public void removeUserDefinedCommand();

    /**
     * Adds a new variable to
     */
    public void addVariable();

    /**
     * Removes a variable
     */
    public void removeVariable();

    /**
     * Returns a variable
     */
    public void getVariable();

    /**
     * Returns a data structure containing user defined commands
     * @return
     */
    public List<String> getUserDefinedCommands();

    /**
     * Adds a new user defined command to the
     * data structure containing all user defined commands
     */
    public void addUserDefinedCommands();

    /**
     * Passes in the String of commmands to be parsed
     * @param input
     */
    public void parseInput(String input);

    /**
     * Returns the image file of the turtle
     * @return ImageView of the image file
     */
    public ImageView getTurtleImage();

    /**
     * Returns some kind of data structure that the frontend can use to display result of
     * executeCommand
     * @return results of commands to be displlayed
     */
    public List<String> getCommandResult();

    /**
     * Returns a data structure containing all Turtle objects
     * Frontend can use this method along with getCommandResult to update all turtles
     * @return list of all Turtle objects
     */
    public List<Turtle> getAllTurtles();

}