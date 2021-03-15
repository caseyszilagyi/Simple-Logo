package slogo.view;

/**
 *
 */
public interface FrontEndInternalAPI {

  /**
   *
   */
  public void setBackgroundColor(String color);

  /**
   *
   */
  public void updateScreen();

  /**
   *
   */
  public void displayError(String errorMessage);

  void processUserCommandInput(String text);
}