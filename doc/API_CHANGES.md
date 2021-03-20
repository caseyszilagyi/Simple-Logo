## SLogo API Changes
### Team 01
### Names
- Casey Szilagyi (crs79)
- Jin Cho (jc695)
- Kathleen Chen (kc387)
- Ji Yun Hyo (jh160)

### Changes to the Initial APIs

#### Backend External

* Original
    - setup - launching the view
    - parse(string all_commands)  - parses through commands from view in a recognizable way for backend
    - update(parsed commands) - calls model methods with parsed commands
    - getTurtlePosition
    - makeRoutine
    - getPreviousCommands
    - getVariables
    - getLanguage

* Method changed: getTurtlePosition


* Why was the change made?
    - The front end shouldn't actually have to "get" anything. Instead, new information should be passed up from the backend
    - The back end internal API now just has many different methods to pass turtle information to the modelController
* Major or Minor (how much they affected your team mate's code)
    - Minor, becasue the front end was still getting passed the same information, just the back end is initiating it
* Better or Worse (and why)
    - Better, becasue the front end doesn't rely on the front end to get something, it can just exist and be passed information whenever
    - Back end can determine when something needs to be passed and call the proper method

* Method changed: getPreviousMethods

    * Why was the change made?
        - Realized that there was no good way to store user commands in the back end because by the time they are recognized, they are already in a tree format
    * Major or Minor (how much they affected your team mate's code)
        - Major, because this requiried the front end to implement a small parser to see if there are any user defined commands
    * Better or Worse (and why)
        - Overall better, because the back end doesn't have to worry about storing information that is only useful for the front end
        - However, this means that the front end needs a small parser which is not ideal

* Method changed: update

    * Why was the change made?
        - no longer passing up the command information but rather the new values for front end objects (ex. turtle pos and angle)
        - the backend needed this information about the turtle, rather than the original plan of not knowing anything and just passing up changes necessary, so we kept that info logged in the back end and send up updated info to the front end
        - design decision so that we dont have a long queue of commands the front end would apply to the turtle image on the GUI but rather update as we parse in the backend.
    * Major or Minor (how much they affected your team mate's code)
        - affected the information that the front end receives and how they use it, but this decision was made early on so no major changes had to be implemented.
        - affected internal backend code in how the turtle information had to be stored
    * Better or Worse (and why)
        - better. we are executing and updating as we go with the turtle information rather than having to parse in the backend and the front end


#### Backend Internal

- getCommand
- getType
- getTurtlePosition
- translateCommands

* Method changed: getCommand

    * Why was the change made?
        - Nothing needed to get the command, it simply needed to be loaded then executed
        - Now have an internal loadClass method and execute() method for each command
    * Major or Minor (how much they affected your team mate's code)
        - Minor, because parsing was done completely separately from the commands
        - Didn't impact the way commands were actually parsed through
    * Better or Worse (and why)
        - Better, because now there is no getter that is passing around a command object

* Method changed: translateCommands

    * Why was the change made?
        - there were more steps to cleaning the string and getting it in the form that the commandParser could just make a command tree (AST), so we broke the translateCommand method down to multiple steps and classes with different responsibilities
    * Major or Minor (how much they affected your team mate's code)
        - Minor. Didn't affect how the tree was made in the CommandParser class since the end result and beginning result was the same structure as before the changes were made
    * Better or Worse (and why)
        * Better. Splitting it up helped with single responsibility methods and classes. it also added the ability to detect if the commands were input correctly. splits up complicated specialized parsing such as for lists into separate methods/classes


#### Frontend External


- setBackGroundColor(String color);
- setTurtleImage(Turtle turtle, ImageView image);
- setLanguage(String language);
- displayCommandResult(List resultsOfCommandExecution);
- displayError(String errorMessage);

* Method changed: setTurtleImage(Turtle turtle, ImageView image);

    * Why was the change made?
        - The change was made because we changed the way we set the turtle image. The turtle image is set in the view component instead of being set by the model

    * Major or Minor (how much they affected your team mate's code)
        - Minor: no one even noticed this change

    * Better or Worse (and why)
        - neutral


* Method changed: setBackGroundColor(String color);

    * Why was the change made?
        - We changed the way we set the background color so that it would be using a ColorPicker in the view component. In this way, the background color is changed in view instead of in model.

    * Major or Minor (how much they affected your team mate's code)
        - Minor: didn't affect any one else's code

    * Better or Worse (and why)
        - pro: allows user to interact with program and choose a wide range of colors
        - con: for complete, there needs to be a command that changes the background
            - not exactly the same since the command needs to change it within a properties file so different than just choosing a color
        - Overall --> neutral just because it boiled down to a design decision that our team made that allowed the user to interact with the screen and choose a color


#### Frontend Internal

- makeButton
- updateScreen
- displayErrors

* Method changed: makeButton

    * Why was the change made?
        - we just decided to make the buttons in one class

    * Major or Minor (how much they affected your team mate's code)
        - Minor: didn't affect anything because we had good communication going

    * Better or Worse (and why)
        - Neutral: just a very natural part of developing a software


* Method changed: updateScreen

    * Why was the change made?
        - We stopped needing the updateScreen method because we changed the way the backend would relay information to the frontend

    * Major or Minor (how much they affected your team mate's code)
        - Minor: didn't break anyone's code

    * Better or Worse (and why)
        - Better: I feel like we came to this decision because doing so resulted in a better overall design for the entire project