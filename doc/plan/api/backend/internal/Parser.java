package slogo.model;

import java.util.*;

/**
 * 
 */
public interface Parser {

    /**
     * Translates the command
     */
    public void translateCommand(List<String> commandsBeforeTranslation);

}