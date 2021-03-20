package slogo.controller;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.text.html.ImageView;
import slogo.model.commands.basic_commands.UserDefinedCommand;
import slogo.model.execution.Turtle;
import slogo.view.ScreenCreator;

/**
 * @author Ji Yun Hyo
 */
public class ViewController implements FrontEndExternalAPI {

  BackEndExternalAPI modelController;
  ScreenCreator screenCreator;
  private String userCommandInputs;
  private final Deque<String> commandHistory;
  private final Map<String, String> userDefinedHistory;

  /**
   * Default constructor
   */
  public ViewController() {
    screenCreator = new ScreenCreator(this);
    commandHistory = new ArrayDeque<>();
    userDefinedHistory = new HashMap<>();
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
  public Deque<String> getCommandHistory() {
    // TODO implement here
    return commandHistory;
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
    commandHistory.offerFirst(userCommandInputs);
    screenCreator.updateCommandHistory(commandHistory);
    this.userCommandInputs = userCommandInputs;
    //print statement for debugging
    System.out.println(this.userCommandInputs);
    modelController.executeCommand(userCommandInputs);
  }

  @Override
  public void passInputFromBackendToFrontEnd(List<Double> parameters) {
    // TODO implement and decide which class will get sent these parameters
    for (Double go : parameters) {
      System.out.println(go);
    }
    screenCreator.moveTurtle(parameters);
  }

  @Override
  public void displayCommandStringOnTextArea(String command) {
    screenCreator.displayCommandStringOnTextArea(command);
  }

  @Override
  public Map<String, Double> getVariables() {
    return modelController.getVariables();
  }

  @Override
  public void updateFrontEnd(Map<String, Double> variables,
      Map<String, UserDefinedCommand> userDefinedCommands) {
    parseUserDefinedCommands(userDefinedCommands);
    screenCreator.updateVariablesAndUserDefinedCommands(variables, userDefinedHistory);
  }

  private void parseUserDefinedCommands(Map<String, UserDefinedCommand> userDefinedCommands) {
    for (Map.Entry<String, UserDefinedCommand> entry : userDefinedCommands.entrySet()) {
      System.out.println(entry.getKey());
      for (String command : commandHistory) {
        List<String> split = Arrays.asList(command.split(" "));
        if (split.size() > 1 && split.get(1).equals(entry.getKey()) && !userDefinedHistory
            .containsKey(command)) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(split.get(1));
          for (int i = 0; i < entry.getValue().getParamCount(); i++) {
            stringBuilder.append(" 50");
          }
          userDefinedHistory.put(commandHistory.getFirst(), stringBuilder.toString());
        }
      }

    }
  }


}