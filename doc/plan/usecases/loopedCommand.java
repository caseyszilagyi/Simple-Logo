/**
 * DOTIMES is called with the number 5 and the commands forward(limit) and rt(limit)
 * <p>
 * This call is passed from the view all the way to the backend parser
 * <p>
 * The parser recognizes that dotimes is a looping command and that the commands inside need to be
 * called more than once
 * <p>
 * The parser builds a command object filled with basic commands as well as the respective variable
 * <p>
 * this command object is executed on the turtle as many times as specified
 * <p>
 * The change of the turtle is reflected in the front end through the turtle listener in the
 * controller
 */