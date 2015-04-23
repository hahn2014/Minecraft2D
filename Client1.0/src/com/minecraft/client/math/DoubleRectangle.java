package com.minecraft.client.math;

public class DoubleRectangle {
	public static double x, y, width, height;
	
	public DoubleRectangle() {
		setBounds(0, 0, 0, 0);
	}
	
	public DoubleRectangle(double x, double y, double width, double height) {
		
	}
	
	public static void setBounds(double X, double Y, double Width, double Height) {
		x = X;
		y = Y;
		width = Width;
		height = Height;
	}
}