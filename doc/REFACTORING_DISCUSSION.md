## Refactoring Lab Discussion
### Team 01
### Names
- Casey Szilagyi (crs79)
- Jin Cho (jc695)
- Kathleen Chen (kc387)
- Ji Yun Hyo (jh160)

### Issues in Current Code
- Long methods (especially in view and parser)
- Duplicate code for making buttons/combo boxes
- Complex methods in parsing process are not very readable

#### Method or Class
- CommandInformationBundle

* Design issue
    - Currently the parser takes this in because it needs the user defined variables/parameters
        - However, it also has turtle information that the parser doesn't need
        - This entire bundle is instantiated in the modelController, giving it too much access to things that it doesn't need
    - Has a lot of methods with different functionalities, like managing turtles, display information, and user defined variables

#### Method or Class
- ViewPane.java (on the Master branch)

* Design issue
    - We have to refactor this class because there are duplicated code as well as long methods (the class in general is also way to long and does multiple things). However, we've already started extensively thinking about the possible design options and have pushed some of the changes to our personal branches. So we are on the right track.

#### Method or Class
- MakeToken.java
- Design Issue
    - Complex methods are hard to read/follow
        - one of the main issues this class deals with is the issue that different commands have different types of lists in terms of the information they hold (variable list, command list, turtle index list), and the way each of them are constructed to be properly recognized by the AST we are making in the CommandParser needs to act differently for each type
            - preventing hard coding: had properties files to differentiate the cases of these and have class definitions for each type to split up how the same method is applied to these list constructions but with different logic behind them
        - issue is that, because there are still conditions that must be met and the use of a stack under certain conditions as not everything we are dealing with has lists, the code can get complex and therefore hard to read. however, there are limited ways to go around these conditionals with the use of a stack as we have it now, so we must extract methods so that it is more readable

### Refactoring Plan

* What are the code's biggest issues?
    - ViewPane.java has over 350 lines of code
        - violates Single Responsibility Principle
        - violates Dependency Inversion Principle
        - Not as DRY or SHY as it could be
    - CommandInformationBundle has many responsibilities
        - Manging turtles, display info, and user defined info
        - Makes it difficult to understand
        - Not very SHY when it is getting passed to the parser
    - MakeToken has long-ish complicated/hard to read methods
        - methods can be broken down more than current state
        - contains conditionals that could be refactored

* Which issues are easy to fix and which are hard?
    - Refactoring ViewPane
        - Breaking up ViewPane by creating multiple classes, each of which performs one specific task, will fix SRP
        - Creating interfaces and having the higher-level classes depend on the interfaces instead of concrete classes will fix DIP
        - Refactoring methods and/or using Reflection plus properties files would fix DRY/SHY
    - Refactoring MakeToken
        - just need to extract some checks into separate methods and have method calls (more single responsibility and readable, smaller methods)

### Refactoring Work

* Issue chosen: Fix and Alternatives
    - ViewPane.java could potentially be divided up into multiple classes, each of which would implement an interface. We have also thought about using Reflection and properties files to minimize duplication when creating buttons and combo boxes.


* Issue chosen: Fix and Alternatives
    * CommandInformationBundle/Parser/ClassLoader/InputCleaner are all in modelController, so there is a lot of dependencies on the way the back end is implemented. The bundle is also too large and has too many responsibilities
        - Plan is to have an execution class in the backend that holds the inputCleaner, parser, commandInformationBundle, and classloader and coordinates what happens when a command is sent in
        - This way, only the necessary user defined information can be passed to the parser/inputcleaner
        - This allows the CommandInformationBundle to be split into multiple components
        - Reduces the dependency of the modelController on the specific implementation of the back end. Now, the modelController can just have a single instance of the backend "executioner" interface

* Issue chosen: Fix and Alternatives
    * MakeToken class methods tokenize and commandBlockParams could be broken down further to have method calls to shorter single responsibility methods to also increase readability of the methods