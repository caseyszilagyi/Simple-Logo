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
    ScreenCreator screenCreator;
    private String userCommandInputs;

    /**
     * Default constructor
     */
    public ViewController() {
        screenCreator = new ScreenCreator(this);
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

    @Override
    public void setModelController(ModelController modelController) {
        this.modelController = modelController;
    }

    @Override
    public String getUserCommandInput() {
        return userCommandInputs;
    }

    @Override
    public void processUserCommandInput(String userCommandInputs) {
        this.userCommandInputs = userCommandInputs;
        //print statement for debugging
        System.out.println(this.userCommandInputs);
        modelController.parseInput(userCommandInputs);
    }

}