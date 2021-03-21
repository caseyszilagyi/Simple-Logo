package slogo.model.commands.basic_commands;

import java.util.List;
import slogo.model.commands.basic_commands.command_types.DisplayAlteringCommand;
import slogo.model.execution.CommandInformationBundle;
import slogo.model.tree.TreeNode;

/**
 * Changes the color represented in the palette in the front end
 *
 * @author Casey Szilagyi
 */
public class SetPalette extends DisplayAlteringCommand {

  private final double INDEX;
  private final double RED;
  private final double GREEN;
  private final double BLUE;

  /**
   * Makes an instance of the set palette command
   *
   * @param bundle Contains the model controller that the command is sent through
   * @param nodes  4 children, which is the index of the palette that is being modified and
   *               the 3 RGB values
   */
  public SetPalette(CommandInformationBundle bundle, List<TreeNode> nodes) {
    super(bundle);
    INDEX = loadClass(bundle, nodes.get(0)).execute();
    RED = loadClass(bundle, nodes.get(1)).execute();
    GREEN = loadClass(bundle, nodes.get(2)).execute();
    BLUE = loadClass(bundle, nodes.get(3)).execute();
  }

  /**
   * Sets the index in the palette to the corresponding RGB value
   *
   * @return The index of the altered palette color
   */
  @Override
  public double execute() {
    setPalette((int) INDEX, (int) RED, (int) GREEN, (int) BLUE);
    return INDEX;
  }
}
