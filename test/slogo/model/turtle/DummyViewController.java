package slogo.model.turtle;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import javax.swing.text.html.ImageView;
import slogo.controller.BackEndExternalAPI;
import slogo.controller.FrontEndExternalAPI;
import slogo.model.commands.basic_commands.UserDefinedCommand;
import slogo.model.execution.Turtle;

public class DummyViewController implements FrontEndExternalAPI {

  @Override
  public void setBackGroundColor(int color) {

  }

  @Override
  public void setTurtleImage(Turtle turtle, ImageView image) {

  }

  @Override
  public String getLanguage() {
    return null;
  }

  @Override
  public Queue<String> getCommandHistory() {
    return null;
  }

  @Override
  public void displayError(String errorMessage) {

  }

  @Override
  public void setModelController(BackEndExternalAPI modelController) {

  }

  @Override
  public String getUserCommandInput() {
    return null;
  }

  @Override
  public void processUserCommandInput(String userCommandInput) {

  }

  @Override
  public void displayCommandStringOnTextArea(String command) {

  }

  @Override
  public Map<String, Double> getVariables() {
    return null;
  }

  @Override
  public void updateFrontEnd(Map<String, Double> variables,
      Map<String, UserDefinedCommand> userDefinedCommands) {

  }

  @Override
  public void setActiveTurtle(int turtleID) {

  }

  @Override
  public void setTurtlePosition(double xPosition, double yPosition) {

  }

  @Override
  public void setTurtleAngle(double angle) {

  }

  @Override
  public void setPenState(double penState) {

  }

  @Override
  public void setTurtleVisibility(double visibility) {

  }

  @Override
  public void clearScreen() {

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

  }

  @Override
  public void setLanguage(String language) {

  }

  @Override
  public void setActiveTurtles(List<Integer> iDs) {

  }
}
