package slogo.model.commands.basic_commands;

public class Forward implements BasicCommand {

  private final int DISTANCE;

  public Forward(int... args){
    DISTANCE = args[0];
  }

  public void execute(int... args){

  }
}
