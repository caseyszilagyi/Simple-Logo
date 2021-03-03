/**
 User calls tan(90) in the command box

 This command is passed to the viewController which passes it to the modelController

 The modelController passes it to the parser

 The parser decides to construct the basicCommand Tan and pass it the argument 90

 The basic command is executed on the turtle, and an error is thrown

 This error is passed up to the view and an error message is displayed
 */