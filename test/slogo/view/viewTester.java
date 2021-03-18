package slogo.view;

import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import slogo.Main;
import util.DukeApplicationTest;

public class viewTester extends DukeApplicationTest {
  private Main main;
  private TextArea codeInputBox;
  private Button runButton;
  private Button clearButton;
  private Button backgroundButton;
  private AnchorPane turtleDisplay;
  private Button penButton;

  @Override
  public void start(Stage stage) throws Exception {
    main = new Main();
    main.start(stage);

    codeInputBox = lookup("#textArea").query();
    runButton = lookup("#runButton").query();
    clearButton = lookup("#clearButton").query();
    backgroundButton = lookup("#BackgroundButton").query();
    turtleDisplay = lookup("#TurtleView").query();
    penButton = lookup("#PenButton").query();
  }

  @Test
  void showPreviousCommand() {
    String expected = "forward 100";

    writeTo(codeInputBox, expected);

    clickOn(runButton);
    clickOn(lookup("#previousCommandButton").query());

    String text = codeInputBox.getText();

    assertEquals(expected, text);
  }

  @Test
  void clearTextArea() {
    writeTo(codeInputBox, "cs");

    clickOn(clearButton);

    String expected = "";
    String text = codeInputBox.getText();

    assertEquals(expected, text);
  }

  @Test
  void changeBackgroundColor() {
    clickOn(backgroundButton);
    ColorPicker backgroundColorPicker = lookup("#BackgroundColorPicker").query();
    setValue(backgroundColorPicker, Color.BLUE);

    Paint fill = turtleDisplay.getBackground().getFills().get(0).getFill();
    String backgroundColor = fill.toString();
    assertEquals(Color.BLUE.toString(), backgroundColor);
  }

  @Test
  void changePenColor() {
    clickOn(penButton);
    ColorPicker penColorPicker = lookup("#PenColorPicker").query();
    setValue(penColorPicker, Color.DEEPPINK);

    writeTo(codeInputBox, "forward 100");
    clickOn(runButton);

    Line line = lookup("#Line").query();
    Paint fill = line.getStroke();
    assertEquals(Color.DEEPPINK.toString(), fill.toString());
  }

//  @Test
//  void uploadTurtle() {
//    clickOn(lookup("#TurtleButton").query());
//
//    String expected = "Turtle1.gif";
//    clickOn(lookup(".dialog-box").query());
//
//    ImageView turtle = lookup("#Turtle").query();
//    String turtleImage = turtle.getImage().toString();
//    assertEquals(expected, turtleImage);
//  }
}
