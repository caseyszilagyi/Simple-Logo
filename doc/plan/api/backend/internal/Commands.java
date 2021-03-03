package slogo.model.commands;

import java.util.*;

/**
 * 
 */
public interface Commands {

    /**
     * Executes the set of commands
     */
    public void executeCommand(List<String> commands);

    /**
     * Returns a data structure containing all necessary parameters to execute the command
     */
    public List<String> getParameters();

}