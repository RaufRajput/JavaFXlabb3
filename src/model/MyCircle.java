package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MyCircle extends MyShape {

    public MyCircle(double x, double y, Color c, double radius){
        super(x,y,c,radius,radius);
    }

    public void drawCircle(GraphicsContext gc){
    	gc.setFill(this.c);
        gc.fillOval(x, y, width, height);
    }
}
