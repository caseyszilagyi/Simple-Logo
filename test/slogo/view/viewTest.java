package slogo.view;

import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import slogo.Main;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

public class viewTest extends DukeApplicationTest {
  private Main main;
  private TextArea codeInputBox;
  private Button runButton;
  private Button clearButton;
  private Button backgroundButton;
  private AnchorPane turtleDisplay;
  private Button penButton;
  private ScreenCreator screenCreator;

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
  void ShowPreviousCommand() {
    String expected = "forward 100";

    writeTo(codeInputBox, expected);

    clickOn(runButton);
    clickOn(lookup("#previousCommandButton").query());

    String text = codeInputBox.getText();

    assertEquals(expected, text);
  }

  @Test
  void ClearTextArea() {
    writeTo(codeInputBox, "cs");

    clickOn(clearButton);

    String expected = "";
    String text = codeInputBox.getText();

    assertEquals(expected, text);
  }

  @Test
  void ChangeBackgroundColor() {
    clickOn(backgroundButton);
    ColorPicker backgroundColorPicker = lookup("#BackgroundColorPicker").query();
    setValue(backgroundColorPicker, Color.valueOf("0xd3d3d3ff"));

    Paint fill = turtleDisplay.getBackground().getFills().get(0).getFill();
    String backgroundColor = fill.toString();
    assertEquals(Color.valueOf("0xd3d3d3ff").toString(), backgroundColor);
  }

  @Test
  void ChangePenColor() {
    clickOn(penButton);
    ColorPicker penColorPicker = lookup("#PenColorPicker").query();
    setValue(penColorPicker, Color.DEEPPINK);

    writeTo(codeInputBox, "forward 100");
    clickOn(runButton);

    Line line = lookup("#Line").query();
    Paint fill = line.getStroke();
    assertEquals(Color.DEEPPINK.toString(), fill.toString());
  }

  @Test
  void TestForwardButton() {
    ImageView turtle = lookup("#Turtle").query();
    double original = turtle.getY();

    Button forwardButton = lookup("#ForwardButton").query();
    clickOn(forwardButton);

    double current = turtle.getY();

    assertTrue(original > current);
  }

  @Test
  void TestBackwardButton() {
    ImageView turtle = lookup("#Turtle").query();
    double original = turtle.getY();

    Button backwardButton = lookup("#BackwardButton").query();
    clickOn(backwardButton);

    double current = turtle.getY();

    assertTrue(original < current);
  }

  @Test
  void TestRightButton() {
    Button rightButton = lookup("#RightButton").query();
    clickOn(rightButton);

    double correct = 45.0;
    ImageView turtle = lookup("#Turtle").query();
    double current = turtle.getRotate();

    assertEquals(correct, current);
  }

  @Test
  void TestLeftButton() {
    Button leftButton = lookup("#LeftButton").query();
    clickOn(leftButton);

    double correct = -45.0;
    ImageView turtle = lookup("#Turtle").query();
    double current = turtle.getRotate();

    assertEquals(correct, current);
  }

  @Test
  void TestHomeButton() {
    ImageView turtle = lookup("#Turtle").query();
    double original = turtle.getY();

    Button homeButton = lookup("#HomeButton").query();
    clickOn(homeButton);

    double current = turtle.getY();

    assertEquals(original, current);
  }

  @Test
  void TestLanguageComboBox() {
    ComboBox lang = lookup("#LanguageComboBox").query();
    select(lang, "Italiano");
    System.out.println("selected");

    codeInputBox = lookup("#textArea").query();
    runButton = lookup("#runButton").query();
    clearButton = lookup("#clearButton").query();

    assertEquals("Eseguire il Programma", runButton.getText());
  }

//  @Test
//  void testPenUpButton() {
//    Button penUpButton = lookup("#PenUpButton").query();
//    clickOn(penUpButton);
//
//    writeTo(codeInputBox, "forward 10");
//
//    assertTrue(lookup("#Line").query() == null);
//  }

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
