SLogo Design
====

This project implements a development environment that helps users write programs to draw using a turtle.

Names:
- Casey Szilagyi (crs79)
- Jin Cho (jc695)
- Kathleen Chen (kc387)
- Ji Yun Hyo (jh160)

### Timeline

Start Date: 3/4/2021

Finish Date: 3/22/2021

Hours Spent: 300


### Primary Roles
* Casey Szilagyi
    * Primarily backend
    * Focused on command implementation and communication with the controller in order to have turtles moving
        * Turned the tree into actionable command calls

* Kathleen Chen
    * Primarly frontend (e.g. overall design of the UI, refactoring using Reflection, dealing with properties files)
    * Focusing on displaying/organizing the panes/text area/tabs on the screen
    * Helping with view controller
    * Working with Ji Yun to split work efficiently

* Jin Cho
    * Primarily backend
    * Focusing on parsing and initial communication with the controller
        * making sure that input was correct/valid during parsing
    * constructing the tree

* Jiyun Hyo
    * Primarily frontend/controllers
    * Focusing on displaying of turtles, animation of turtles, passing information to and from backend, interactive buttons, making UML diagram and starting classes generated from the UML diagram, making tree in command parser
    * Working with Kathleen to discuss design decisions for frontend

### Resources Used
- https://docs.oracle.com/en/
    - especially for javafx features and implementations
- https://www.geeksforgeeks.org
    - also for javafx features and implementation and example code snippets to reference
- lab_advanced
    - example referenced for using reflection for button creation (how to use properties files to make reflective method calls)
- https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html
    - For lambda expressions

- https://www.tutorialspoint.com/design_pattern/factory_pattern.htm
    - for factory design pattern

### Running the Program

**Main class:** src.slogo.Main to run the SLogo GUI

**Data files needed:**
- language properties files
    - for commands and errors - model
    - buttons, example code, history display tabs, user command input pane buttons - view
- properties files for command definitions:
    1. command name as key, type of parameters it takes as key (NUM v LIST)
    2. commands that take lists define the type of list it takes (CommandList, ConstantList, VariableList) and the order in which they're taken
- icons for the buttons (turtle, pen, background, new window)
- icon for turtle
- properties files for reflection for button making

**Features implemented:**
- Backend:
    - all basic commands and additional commands from complete implementation
        - cannot do the infinite parameters defined by "()"
    - recursive user-defined commands work
    - error handling of incorrect user input
        - number of parameters and parameter types
    - error handling of improper commands input (invalid commands that don't exist or can't be translated given the language properties files)
    - commands can be applied to multiple turtles
        - proper error handling with invalid commands or variables defined, especially with user-defined
        - non-existent turtles are also made in the backend and reflected in the frontend
    - different languages recognized
    - keep track of user defined commands and variables and can use them in the same command definition or in future inputs

- Frontend:
    - display user defined command and variable history
    - display user input history
    - change display aspects (background and pen colors, turtle image) with buttons and color pickers
    - easy access commands to populate the input
        - history of commands
        - command examples
        - drop down of command names
        - user defined commands
    - buttons that can be pressed to do specific preset commands
        - forward
        - backward
        - left
        - right
        - penup/pendown
        - clearscreen
        - home
    - help button that shows all possible commands and brings up information alert about these commands
    - slider that changes speed of animation
    - combobox that changes the language that the program is run in as well as the button names (into that language)
    - turtle movement is animated
    - code examples change languages when the language is changed as well

### Notes/Assumptions

Assumptions or Simplifications:
- parsing:
    - lines with comments end with a new line so that we could detect the end of the comments. therefore, if the user ends with a comment, they must have an additional like after (\r or \n) for proper removal of the comment

- commands:
    - user defined command names cannot have numbers in them so that the Command regex can recognize it as a command
    -

Interesting data files:
- src/slogo/view/resources/references
    - files provided that are displayed when information button is asked for
- src/slogo/view/resources/UpdateNextReflectionAction
    - used for reflection when updating the next command
    - holds the method names for each key

Known Bugs:
- parsing:
    - we have a null root for the AST, so the top level of commands are the children of this null root. parsing doesn't recognize the case where the user inputs an improper hanging constant (ex. fd 50 60). This just sets a child as 60. However, it properly executes in that the hanging value is ignored. The issue we faced with this was that there are cases in which it is valid for this null root node to have a constant as a child (ex. make :x 10 which defines a variable). Because it is dealt with in the execution, we decided not to display it as an error but rather just ignore in execution.
- command execution:
    - There are certain commands that will execute too many times on turtles. For example, tell [ 1 2 3 ] fd fd 15 will execute 6 times on each turtle. This is due to the fact that we were unable to figure out exactly how to deal with scenarios where movement commands are children with movement commands. The value of the fd 15 can't just be evaluated and passed up, becuase 15 could be replaced with the xcor query and therefore that command would need to be different for each turtle. However, if we loop through all forward commands in the same way, it results in too many executions. To our knowledge, this bug should really only happen if there is some type of movement command as a child of another movement command when multiple turtles are active.
    - There is a bug with passing parameters to user defined commands, most likely with when the passed parameters are also variable values. This bug was not tested thoroughly
    - Nested loops can freeze up the display if complex enough
- view:
    - when Urdu is selected, javafx does not like to display Urdu characters properly so the font looks very strange when selected; however all commands still work just fine in urdu

Extra credit: animation for multiple turtles, slider to control animation speed, language change for commands also changes the language for the UI

### Impressions
- in parsing, one confusing aspect was trying to understand what might be considered a valid command intuitively and how some would work. for example, dotimes fd 50 [ fd 50 ] repeats fd 50 50 times, but how does the fd 50 play into the turtle? This caused initial confusion in terms of if we have to specify the type of command that could be placed in certain parameters (in this case if only mathematic operations could be in the place of the number of timed you repeat). In the end, we were told to just accept any command since they all reduce to a double, but it was intuitively hard to understand that in the beginning for movement commands being in place of what makes most sense as just a constant

- in the view component, understanding how the user commands/variables/history needed to be displayed was initially confusing. the use of color palettes to change the background was also a little confucing since the instructions weren't quite clear about what value should the user put for changing the background/pen color using the color palettes.
    - the most difficult thing was probably refactoring view since JavaFX does not lend itself to refactoring as well as backend (can't really use abstractions as easily)
    - also keeping different panes seperate and independent is super difficult when they sometimes need information from each other so that was something that was a little bit harder to fix

- it was very difficult than initially thought to implement the animations.
    - order of execution for multiple turtles was hard
    - keeping track of which commands needed to be executed by which turtle was harder than I initially thought

- Overall it was an interesting project with a lot of cool components. It was a little difficult to understand exactly how local/global variables/turtle commands were supposed to work, so I think that could have been a little bit clearer.