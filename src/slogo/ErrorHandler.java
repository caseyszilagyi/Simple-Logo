package slogo;

public class ErrorHandler extends RuntimeException{

  /**
   * sets the message that the error corresponds to
   * @param errorMessage
   */
  public ErrorHandler(String errorMessage) { super(errorMessage); }
}
