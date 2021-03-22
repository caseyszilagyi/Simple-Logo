package slogo.view;

import java.util.List;
import java.util.Map;
import slogo.controller.BackEndExternalAPI;
import slogo.controller.FrontEndExternalAPI;
import slogo.model.commands.basic_commands.UserDefinedCommand;

public class DummyModelController implements BackEndExternalAPI {

  @Override
  public Map<String, Double> getVariables() {
    return null;
  }

  @Override
  public Map<String, UserDefinedCommand> getUserDefinedCommands() {
    return null;
  }

  @Override
  public void executeCommand(String input) {

  }

  @Override
  public String getLanguage() {
    return null;
  }

  @Override
  public void setViewController(FrontEndExternalAPI viewController) {

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
  public void setActiveTurtle(int turtleID) {

  }

  @Override
  public void setActiveTurtles(List<Integer> IDs) {

  }

  @Override
  public void setBackgroundColor(int index) {

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
}
