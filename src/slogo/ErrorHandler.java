package slogo;

public class ErrorHandler extends RuntimeException {

  /**
   * sets the message that the error corresponds to
   *
   * @param errorMessage The message corresponding to the error
   */
  public ErrorHandler(String errorMessage) {
    super(errorMessage);
  }

  /**
   * Allows the exception itself to be thrown and the message to be gotten from it
   * @param error The error
   */
  public ErrorHandler(Exception error){ super(error.getMessage()); }
}
