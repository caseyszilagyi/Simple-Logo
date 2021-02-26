# API Lab Discussion 
# SLogo API Design

## Names and NetIDs
- Casey Szilagyi (crs79)
- Jin Cho (jc695)
- Kathleen Chen (kc387)
- Ji Yun Hyo (jh160)

### Planning Questions

* What behaviors (methods) should the turtle have?
    - move up, down, left, right
    - turn direction
    - ability to store some type of repeated routine
        - determine frequency of repetition
    - Go home ability (go back to starting location)

* When does parsing need to take place and what does it need to start properly?
    - Front end takes a string
    - Controller parses through the string and determines the proper back-end method calls
    - Each back end method call gives info back to the controller
    - Validity:
        - Needs to have the same number of open/closed brackets (else it is not a valid command)

* What is the result of parsing and who receives it?
    - parsing results go into method calls from model/backend which returns information on what the updates are and sends it through the controller back to the view

* When are errors detected and how are they reported?
    - invalid string or movement parameter input recognized in controller parsing
    - Seems like most errors should be recognized in the controller. The backend should be able to assume good input and most errors should be caught in the controller

* What do commands know, when do they know it, and how do they get it?
    - They know the direction of movement and the distance. They only know it when that exact command is called. The command is given this information by the controller after parsing.

* How is the GUI updated after a command has completed execution?
    - model completes the execution of the command and passes up the update information to the Controller and view

* What behaviors does the result of a command need to have to be used by the front end?
    - Direction and distance of movement


### APIs

#### Backend External API
The goal of the backend external API would be to give any new user the ability to make the turtle do whatever they want. Ideally there are methods available to make their job as simple as possible. So if they want to make the turtle perform a specific action, they will have less to implement themselves.
- setup - launching the view
- parse(string all_commands)  - parses through commands from view in a recognizable way for backend
- update(parsed commands) - calls model methods with parsed commands
- getTurtlePosition
- makeRoutine
- getPreviousCommands
- getVariables
- getLanguage

#### Frontend External API
The frontend external API is pretty much whatever commands need to get communicated to the back end. The back end needs to be able to get certain information from the front end, so the API should be able to accommodate that.
- getDirection
- getDestination
- setColor
- setDirection
- getLanguage


#### Backend Internal API
- getCommand
- getType
- getTurtlePosition
- translateCommands


#### Frontend Internal API
The frontend internal API should make it easy for any person to add new features to the front end. This means that new functionality with the turtle that the user is able to use should be able to be added in some kind of way
- makeButton
- updateScreen
- displayErrors


### Design

#### Backend Design CRCs


#### Frontend Design CRCs



#### Use Cases

* The user types 'fd 50' in the command window, sees the turtle move in the display window leaving a trail, and has the command added to the environment's history.

* The user types '50 fd' in the command window and sees an error message that the command was not formatted correctly.

* The user types 'pu fd 50 pd fd 50' in the command window and sees the turtle move twice (once without a trail and once with a trail).

* The user changes the color of the environment's background.