package slogo.controller;

import java.util.*;
import javax.swing.text.html.ImageView;
import slogo.model.CommandParser;
import slogo.model.InputCleaner;
import slogo.model.commands.BasicCommandClassLoader;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;
import slogo.model.turtle.Turtle;

/**
 * @author Ji Yun Hyo
 */
public class ModelController implements BackEndExternalAPI {

    FrontEndExternalAPI viewController;
    CommandInformationBundle commandInformationBundle;
    BasicCommandClassLoader basicCommandClassLoader;

    /**
     * Default constructor
     */
    public ModelController() {
        commandInformationBundle = new CommandInformationBundle(this);
        basicCommandClassLoader = new BasicCommandClassLoader();

    }

    /**
     *
     * @return
     */
    public List<String> getCommandHistory() {
        // TODO implement here
        // get the
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
     * parses through input and creates a tree. it then executes all the commands in that tree
     *
     * @param input String input
     */
    public void parseInput(String input) {
        // TODO implement here
        System.out.println("ModelController received the following string as input: \n" + input);
        CommandParser commandParser = new CommandParser(input, this);
        TreeNode inputRoot = commandParser.makeTree();

        //NEEDS TO BE REFACTORED TO MAKE SURE WE ADHERE TO DEPENDENCY INVERSION PRINCIPLE
        // inputRoot is null and the command starts from its child
//        CommandInformationBundle commandInformationBundle = new CommandInformationBundle(this);
//        BasicCommandClassLoader basicCommandClassLoader = new BasicCommandClassLoader();
        for(TreeNode child : inputRoot.getChildren()){
            System.out.println("Value of child of root: " + child.getValue());
            basicCommandClassLoader.makeCommand(commandInformationBundle,child).execute();
        }
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
    public void setViewController(FrontEndExternalAPI viewController) {
        this.viewController = viewController;
    }

}