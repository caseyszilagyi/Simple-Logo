/**
 * User calls tan(90) in the command box
 * <p>
 * This command is passed to the viewController which passes it to the modelController
 * <p>
 * The modelController passes it to the parser
 * <p>
 * The parser decides to construct the basicCommand Tan and pass it the argument 90
 * <p>
 * The basic command is executed on the turtle, and an error is thrown
 * <p>
 * This error is passed up to the view and an error message is displayed
 */