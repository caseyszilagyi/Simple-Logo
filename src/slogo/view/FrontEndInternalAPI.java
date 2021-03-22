package slogo.view;

/**
 *
 */
public interface FrontEndInternalAPI {

  /**
   *
   */
  public void setBackground(String color);

  void getLanguage();

  void setActiveTurtle();

  /**
   *
   */
  public void displayError(String errorMessage);

  void processUserCommandInput(String text);
}