package slogo.controller;

import java.util.*;
import javax.swing.text.html.ImageView;
import slogo.model.turtle.Turtle;
import slogo.view.FrontEndInternalAPI;
import slogo.view.ScreenCreator;

/**
 * @author Ji Yun Hyo
 */
public class ViewController implements FrontEndExternalAPI {
    BackEndExternalAPI modelController;
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
    public String getLanguage() {
        // TODO implement here
        return screenCreator.getLanguage();
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
    public void setModelController(BackEndExternalAPI modelController) {
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

    @Override
    public void passInputFromBackendToFrontEnd(List<Double> parameters) {
        // TODO implement and decide which class will get sent these parameters
        for (Double go : parameters) {
            System.out.println(go);
        }
        screenCreator.moveTurtle(parameters);
    }

}