# DESIGN Document for SLogo
## NAME(s)

- Casey Szilagyi (crs79)
- Jin Cho (jc695)
- Kathleen Chen (kc387)
- Ji Yun Hyo (jh160)

## Role(s)
- Casey Szilagyi
    - Backend
    - Creating all of the command objects and inheritance hierarchy
    - Turning the parsed tree into actionable calls to the front end and moving turtles in the back end
    - Storage of multiple turtles/user defined commands/variables in the backend
- Jin Cho
    - Backend
    - Cleaning and translating user input into back-end recognizable Strings
    - Condensing commands/constants/variables/lists into Token types and creating them with a Token Factory design pattern
    - Obtaining parameter information for proper AST construction for commands
    - Constructing n-ary AST in pre-order for execution
- Kathleen Chen
    - Primarly frontend (e.g. overall design of the UI, refactoring using Reflection, dealing with properties files)
    - Focusing on displaying/organizing the panes/text area/tabs on the screen
    - Helping with view controller
    - Working with Ji Yun to split work efficiently
- Ji Yun Hyo
    * Primarily frontend/controllers
    * Focusing on displaying of turtles, animation of turtles, passing information to and from backend, interactive buttons, making UML diagram and starting classes generated from the UML diagram, making tree in command parser
    * Working with Kathleen to discuss design decisions for frontend

## Design Goals
- Allow the user to easily add new types of commands while allowing each MVC component of the software be mostly independent so that altering any one of the components would not break the other two components
- Allow adequate encapsulation for data that are passed among the different components
- Adhere to TDD/dependency inversion principle/single-responsibility principle
- Write readable code that is both DRY and SHY
- Use API design to lay out the overall design of the software
- Use abstractions (e.g. interfaces, abstract classes) to minimize dependencies

## High-Level Design
- ScreenCreator class is the main class in the front end
    - This class creates each different pane that needs to be displayed on the screen
    - It has the main stage of the front end
    - Passes view controller/frontend api to the classes that need it
    - Serves as a communication point between classes in the front end when necessary
- SLogoCommandExecutor class is the main class in the back end
    - This class has a public method called executeCommand that is passed a command in string form and a language
    - This command is sent through the input cleaner, then it is parsed and turned into a TreeNode with strings as objects
        - Parsing is split into multiple steps
            - cleaning and translation (InputCleaner)
            - making tokens our of the commands and parameters (TokenParser)
            - counting and saving parameter counts for lsits (saved as CommandBlocks) for tree construction (CommandBlockParser)
            - constructing the tree (CommandParser)
    - The TreeNode has a null root, and so each child is loaded by the BasicCommandClassLoader by passing in the node, and the CommandInformationBundle
        - The CommandInformationBundle contains all of the turtle objects, user defined variables, and user defined commands that may need to be used for the commands
    - Each child has the execute() method called, which automatically executes the command and all of the children, making method calls in the controller automatically in the TurtleInformation class and DisplayInformation class when the front end needs to be updated
- ViewController and ModelController essentially act as one Controller
    - Perhaps, Main.java could have been named Controller.java to reflect its main purpose
    - The decision was made to divide Controller into two classes to clarify the roles each component would have to serve depending on whether the Controller was part of View or Model
    - Double dependencies between the two classes exist but are okay because the two essentically act as one class
- ViewController launches the main pane by constructing a ScreenCreator object
    - ScreenCreator subsequently creates all other panes by constructing their objects
    - most classes in the view component directly communicates with ViewController to notify that some information has to be sent to the backend and/or that some action in the view component at a different level of hierarchy has to be executed
- ModelController receives information from the frontend through ViewController and relays the information to the backend
    - each time the user takes an action that warrants information to be passed to the backend, ViewController calls some method in ModelController which subsequently calls relevant methods in the backend
- ViewController receives information from the backend through ModelController and relays the information to ScreenCreator so that ScreenCreator can relay the information to the respective Pane class to be displayed

## Assumptions or Simplifications
- The tell/askwith/ask command creates all turtles up to the highest number given. These new turtles are placed at the origin facing straight up
- A command such as ```fd set :x + :x 10``` will change the value of x for each turtle that it is acting on, becasue the forward command executes its own children
- all commands will condense down to a double and can be used as a parameter for any command that is expecting a constant
- Assuming that files uploaded for picture type will always be a .gif file
- Simplification --> example code is like uploading a file and reading it

## Changes from the Plan
- Backend did end up knowing information about where the turtle was, this information was not contained just in the front end
    - Found that this was necessary to perform calculations
    - Didn't end up passing "pen movement information" to the controller from the back end. Instead, made specific method calls on the controller that could tell the front end what part of a turtle was moving

- In properties file, rather than specifying the number of parameters a command takes in, it defines the type of each parameter, for example, fd takes a NUM parameter while a MakeUserInstruction takes in a NUM LIST LIST (command names are NUM because they are a constant/reduce to a double)
    - This was necessary to make sure the user input was formatted properly in terms of the type of parameters and the order of them
    - Still gave access to the number of parameters (list of strings representing expected parameters --> size = num parameters)
    - added the further specification of list types in a separate properties file since commands would expect lists of different purposes (defining variables, defining commands, defining constants)
- Set up of the view changed (classes and how it would function) from our crc cards
    - decided to split most classes based on which pane different functionalities would be located in
        - allowed us to split the jobs of different panes better and make sure one class focuses mainly on one thing
- the original plan did not include doing the animation
    - new methods/classes had to be introduced to ensure animation is done correctly while adhering to encapsulation


## How to Add New Features
- Adding a new command requires making the class for that command and extending the proper superclass
    - Use these superclass methods to implement the desired behavior. This may require using lambdas to pass information to the superclass for turtle movement commands
    - Add information to the front end properties files. The command needs to be added to the language properties files so it is recognized in all lanugages. Additionally, the CommandsParam and CommandsBlock properties files need to be updated so that the parser knows what types of arguments to give it

- Adding infinite parameter commands
    - An infinite parameter command can be seen as a command followed by a ConstantList. As an example, ( sum 10 20 30 40 ) can be seen as sum [ 10 20 30 40 ] in terms of how the number of parameters for sum are counted.
        - in cleaning the string in InputCleaner so that the backend can recognize a ListStart within the group for where the parameters start, it must insert a "[" after the command. since we know that a grouping is only for 1 command, it is safe to assume that the [ would be placed 2 indices in the list after the (
          ```( sum 10 20 30 40 ) --> ( sum [ 10 20 30 40  )```

        - in CommandBlocks properties file, add "(" as a key and the value would be ConstantList, as a group should create a constant list token so that it is recognized in TokensParser as a "command" that expects a list in the parameters. need to also add GroupStart Token class so it can be made with reflection in tokenizing the list
            - this will properly deal with creating a ConstantList Token for the parameters of the group command
    - CommandBlock Parser commandBlockParams needs another stack to keep track of the commands that are in groups (recognized by the GroupStart token) and counting parameters would be as normal
        - saving the parameters however is different from a normal commandblock in that we don't want to create a new commandblock but rather work with the token of the command in this group. therefore, we need to have a conditional that detects for the end of a group which then calls a method that deals with the following:
            - TreeNode objects will have the command instance variable still match the basic command that it is associated with and is connected to a BasicCommand class, but the value instance variable will be unique to the specific command with infinite parameters (similar to the CommandBlock how the command is CommandBlock, but the value is unique CommandBlock_# that maps to that block's specific number of parameters) and setValue of the token will be called on this as it is for command blocks
            - adding to the CommandParser commandParams map will be for the command of the group and the size of the ConstantList as parameters

    - In tree construction, as the parsing takes care of setting the number of parameters for this command with infinite parameters, it will create a tree as normal
    - The command objects need to be altered to take indefinite number of arguments and use all these arguments. For example, the sum command would need to be changed to loop through each child node, not just assume that there are 2
- Adding uploading code --> simply add a button to the usercommandpane and parse through the text/slogo file and return the string to the backend to be parsed
    - Already have to parse to display help button
    - Use that method to read through a file and then ask the viewController the process the outputed String
- Displaying information about the turtle
    - all information is now stored in TurtleDisplayPane.java inside private Map<Integer, FrontEndSprite> allTurtleInformation. Checkout the FrontEndTurtle class to see which information is accessible. Pass the information to be displayed to ViewController which would send that information to ScreenCreator. Have the ScreenCreator class display the information or pass the information to the relevant classes to be displayed depending on where the information is to be displayed
