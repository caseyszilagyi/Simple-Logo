// user input is recognized and triggers reading in of user command

// command is formatted as String in ViewController

// String command is sent to CommandParser from ViewController

// command is parsed in CommandParser and added to queue
    //in the commandParser, it needs to recognize when it's own "method" or series of instructions is called recursively
    //save this recusrion in some form (possibly a separate command object type for recursion)

// The parser builds a command object filled with basic commands as well as the respective variable
    // maybe need a separate command class that is recursive and not just limited to basic

// this command object is executed and change values are calculated

// changes are calculated in the controller and reflects changes in view step by step (separate recursion into its moves)
