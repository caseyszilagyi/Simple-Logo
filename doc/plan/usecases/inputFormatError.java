// user input is recognized and triggers reading in of user command

// command is formatted as String in ViewController(?)

// String command is sent to CommandParser from ViewController

// command is parsed in CommandParser and added to queue

// In parsing, it recognizes an error in the order of the command (int before command string)

// send up error to controller to reflect in view
displayError();

