
# API Lab Discussion
# Cell Society API Discussion

## Names and NetIDs
- Casey Szilagyi (crs79)
- Jin Cho (jc695)
- Kathleen Chen (kc387)
- Ji Yun Hyo (jh160)


### Cell Society Team 11 Front End API Motivation/Analogies

#### External
- User only has buttons to interact with.
- Any user should only be able to use these buttons in oroder to do anything, no need for them to have access to any other implementation details


#### Internal
- need to create methods for each button action event that is added internally
- adding a different type of grid that extends the abstract class to fit the desired qualities (math for the grid setup)


### Cell Society Team 11 Front End API Classes/Methods

#### External
- ScreenControl constructor
- createGrid - Making the grid
- public void updateGrid - Making step in the simulation
- public void clearGrid - Removes everything from the grid. Used in the controller so it is public, but gives the user a lot of access to remove everything
- public Scene getScene - Gets the scene


#### Internal


Class names:
ButtonBuilder
ErrorMessages
GridBuilder
HexagonGrid
RectangleGrid
ScreenControl - builds the screen
TriangleGrid



```
package cellsociety.view
public class SimulationScreen {
public SimulationScreen(Stage stage, SimulationEngine engine)
public void setDescription(String description)
public void update(State[][] states, String model)
public void checkWindowSizeChanged()
}

package cellsociety.view
public class GridGraphics {
public GridGraphics()
public Node getNode()
public void update(State[][] states, String model)
public void reset()
public void resizeGrid(double size)
}

package cellsociety.view
public class SidePanel {
public SidePanel()
public Node getNode()
public void addNodesToPane(Node... nodes)
public void setDescription(String description)
public String removeDescription()
}
```