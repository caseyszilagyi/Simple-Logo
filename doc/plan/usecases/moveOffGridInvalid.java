// user input is recognized and triggers reading in of user command

// command is formatted as String in ViewController

// String command is sent to CommandParser from ViewController

// command is parsed in CommandParser and added to queue

// The parser builds a command object filled with basic commands as well as the respective variable

// this command object is executed and change values are calculated

// once the changes are being applied to the turtle in controller, it realizes that it will go off the grid and returns error message
displayError();