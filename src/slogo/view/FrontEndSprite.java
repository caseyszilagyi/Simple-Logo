package slogo.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

abstract class FrontEndSprite {
    private double xCoord;
    private double yCoord;
    private double penState;
    private ImageView turtleImageView;
    private double penThickness;

    public FrontEndSprite(double xCoord, double yCoord, ImageView turtleImage, double penState){
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.penState = penState;
        this.turtleImageView = turtleImage;
        penThickness = 1.0;
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

    public double getPenState(){
        return penState;
    }

    public void setPenState(double penState) {
        this.penState = penState;
    }

    public void setTurtleImageView(Image image){
        turtleImageView.setImage(image);
    }

    public ImageView getTurtle(){
        return turtleImageView;
    }

    public void setPenThickness(double penThickness){
        this.penThickness = penThickness;
    }

    public double getPenThickness(){
        return penThickness;
    }
}
