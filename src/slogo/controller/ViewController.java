package slogo.controller;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
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
  private Deque<Double> commandsToBeExecuted;
  private Deque<String> typeToBeUpdated;

  private final Map<String, String> userDefinedHistory;
  private int NAME_OF_USER_DEFINED_COMMANDS = 1;
  private String EXAMPLE_PARAMETER = " 50";

  /**
   * Default constructor
   */
  public ViewController() {
    screenCreator = new ScreenCreator(this);
    commandHistory = new ArrayDeque<>();
    userDefinedHistory = new HashMap<>();
    commandsToBeExecuted = new ArrayDeque<>();
    typeToBeUpdated = new ArrayDeque<>();
  }

  /**
   *
   */
  public void setBackGroundColor(int index) {
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
    modelController.executeCommand(userCommandInputs);
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

  @Override
  public void setActiveTurtle(int turtleID) {
    System.out.println("Active turtles: " + turtleID);
  }

  @Override
  public void setTurtlePosition(double xPosition, double yPosition) {
    screenCreator.setTurtlePosition(xPosition,yPosition);
  }

  @Override
  public void setTurtleAngle(double angle) {
    screenCreator.updateCommandQueue("Angles", Collections.singletonList(angle));
  }

  @Override
  public void setPenState(double penState) {
    screenCreator.updateCommandQueue("Pen", Collections.singletonList(penState));
  }

  @Override
  public void setTurtleVisibility(double visibility) {
    screenCreator.updateCommandQueue("Visibility", Collections.singletonList(visibility));
  }

  @Override
  public void clearScreen() {
    screenCreator.clearScreen();
  }

  @Override
  public void setPenColor(int index) {

  }

  @Override
  public void setTurtleShape(int index) {

  }

  @Override
  public void setPalette(int index, int red, int green, int blue) {

  }

  @Override
  public void setPenSize(double penSize) {

  }

  @Override
  public void updateCommandQueue(String commandType, List<Double> commandValues) {
    //  screenCreator.updateCommandQueue(commandType, commandValues);
  }

  @Override
  public void setLanguage(String language) {
    screenCreator.updateLanguage(language);
  }


  private void parseUserDefinedCommands(Map<String, UserDefinedCommand> userDefinedCommands) {
    for (Map.Entry<String, UserDefinedCommand> entry : userDefinedCommands.entrySet()) {
      for (String command : commandHistory) {
        List<String> split = Arrays.asList(command.split(" "));
        if (split.size() > 1 && split.get(NAME_OF_USER_DEFINED_COMMANDS).equals(entry.getKey()) && !userDefinedHistory
                .containsKey(command)) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(split.get(NAME_OF_USER_DEFINED_COMMANDS));
          for (int i = 0; i < entry.getValue().getParamCount(); i++) {
            stringBuilder.append(EXAMPLE_PARAMETER);
          }
          userDefinedHistory.put(commandHistory.getFirst(), stringBuilder.toString());
        }
      }

    }
  }
}
