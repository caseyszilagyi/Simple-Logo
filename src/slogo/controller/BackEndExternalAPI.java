package slogo.controller;

import java.util.*;

/**
 * 
 */
public interface BackEndExternalAPI {

    /**
     * 
     */
    public void getCommandHistory();

    /**
     * 
     */
    public void removeUserDefinedCommand();

    /**
     * 
     */
    public void addVariable();

    /**
     * 
     */
    public void removeVariable();

    /**
     * 
     */
    public void getVariable();

    /**
     * 
     */
    public void getUserDefinedCommands();

    /**
     * 
     */
    public void addUserDefinedCommands();

    /**
     * @param String input
     */
    public void parseInput(String input);

    /**
     * 
     */
    public void getTurtleImage();

    /**
     * 
     */
    public void getCommandResult();

    /**
     * 
     */
    public void getAllTurtles();

}