package model;

import javafx.scene.paint.Color;

public class MyShape {

	double x;
	double y;
	Color c;
	double width;
	double height;

	public MyShape(double x, double y, Color c, double width, double height) {
		this.x = x;
		this.y = y;
		this.c = c;
		this.width = width;
		this.height = height;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {

		this.y = y;
	}

	public Color getC() {

		return c;
	}

	public void setC(Color c) {
		this.c = c;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	@Override
	public String toString() {
		return "MyShape{" + "x=" + x + ", y=" + y + ", c=" + c + ", width=" + width + ", height=" + height + '}';
	}
}
