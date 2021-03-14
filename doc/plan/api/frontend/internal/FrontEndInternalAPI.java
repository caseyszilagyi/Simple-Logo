package slogo.view;

import java.util.*;

/**
 * FrontEndInternal API methods that stays within frontend and gets called within frontend
 */
public interface FrontEndInternalAPI {

  /**
   * Sets background of the GUI
   */
  public void setBackgroundColor(String color);

  /**
   * updates the screen for the movemnt of the turtle
   */
  public void updateScreen();

  /**
   * displays specific error thrown by controller on teh screen
   */
  public void displayError(String errorMessage);

}