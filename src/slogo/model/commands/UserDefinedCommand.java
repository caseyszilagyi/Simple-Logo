package slogo.model.commands;

import java.util.*;

/**
 * 
 */
public class UserDefinedCommand implements UserDefinedCommands {

    /**
     * Default constructor
     */
    public UserDefinedCommand() {
    }

    /**
     *
     * @return
     */
    public List<String> getVariables() {
        // TODO implement here
        return null;
    }

    /**
     * Executes the set of commands
     *
     * @param commands
     */
    @Override
    public void executeCommand(List<String> commands) {

    }

    /**
     * Returns a data structure containing all necessary parameters to execute the command
     */
    @Override
    public List<String> getParameters() {
        return null;
    }
}