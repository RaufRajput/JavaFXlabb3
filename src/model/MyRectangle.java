package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MyRectangle extends MyShape {

    public MyRectangle(double x, double y, Color c, double width, double height){
        super(x,y,c,width,height);
    }

    public void drawRect(GraphicsContext gc){
    	gc.setFill(c);
        gc.fillRect(x,y,width,height);
    }
}
