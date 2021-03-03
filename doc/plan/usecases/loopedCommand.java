/**

 DOTIMES is called with the number 5 and the commands forward(limit) and rt(limit)

 This call is passed from the view all the way to the backend parser

 The parser recognizes that dotimes is a looping command and that the commands inside need to be called
 more than once

 The parser builds a command object filled with basic commands as well as the respective variable

 this command object is executed on the turtle as many times as specified

 The change of the turtle is reflected in the front end through the turtle listener in the controller

 */