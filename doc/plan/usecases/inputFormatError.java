// user input is recognized and triggers reading in of user command

// command is formatted as String in ViewController(?)

// String command 'fd 50' is sent to CommandParser from ViewController

// command is parsed in CommandParser and added to queue

// queue is used to construct a Command object

// Command object constructs respective BasicCommand for forward

// Command object executes forward BasicCommand with ForwardBasic.execute()

// execution calculation is put onto queue of integers that represent change in turtle/pen

// queue is passed to ModelController/ViewController

// Controller applies forward movement change to turtle and pen's current location (pen if pen is down)

// sends information to View to be reflected in turtle and pen (if pen is down)

// Change in location is reflected in the View

