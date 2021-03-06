package slogo.controller;

import java.util.*;
import javax.swing.text.html.ImageView;
import slogo.model.turtle.Turtle;
import slogo.view.ScreenCreator;

/**
 * 
 */
public class ViewController implements FrontEndExternalAPI {
    ModelController modelController;
    /**
     * Default constructor
     */
    public ViewController() {
        ScreenCreator screenCreator = new ScreenCreator();
        modelController = new ModelController();
    }

    /**
     * 
     */
    public void setBackGroundColor(String color) {
        // TODO implement here
    }

    /**
     * 
     */
    public void setTurtleImage(Turtle turtle, ImageView image) {
        // TODO implement here
    }

    /**
     * 
     */
    public void setLanguage(String language) {
        // TODO implement here
    }

    /**
     * 
     */
    public void displayCommandResult(List<String> resultsOfCommandExecution) {
        // TODO implement here
    }

    /**
     * 
     */
    public void displayError(String errorMessage) {
        // TODO implement here
    }

}