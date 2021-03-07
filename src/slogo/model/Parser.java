package slogo.model;

import java.util.*;

/**
 * 
 */
public interface Parser {

    /**
     * Translates the command
     */
    public List<String> translateCommand(List<String> commandsBeforeTranslation);

}