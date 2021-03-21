package slogo.view;

public class Coordinates {

    private double xCoord;
    private double yCoord;

    public Coordinates(double xCoord, double yCoord){
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public void setxCoord(double xCoord) {
        this.xCoord = xCoord;
    }

    public void setyCoord(double yCoord) {
        this.yCoord = yCoord;
    }

    public double getxCoord(){
        return xCoord;
    }

    public double getyCoord(){
        return yCoord;
    }
}
