package com.minecraft.client.math;

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

public class Methods {
	/**
	 * This method will take in a String and will measure how long in pixels the string is
	 * then will return the length as an integer. This is useful for drawing text and
	 * positioning it without using hard coded numbers
	 * @param text A String of a word, or a sentence that you wish to find the length of
	 * @param font The font of the string used to allow the method to calculate the size
	 * @return the length of the given String as an integer.
	 */
	public int getStringWidth(String text, Font font) {
		AffineTransform affinetransform = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
		return (int)(font.getStringBounds(text, frc).getWidth());
	}
	
	/**
	 * This method will take in a String and will measure how tall in pixels the string is
	 * then will return the height as an integer. This is useful for drawing text and
	 * positioning it without using hard coded numbers
	 * @param text A String of a word, or a sentence that you wish to find the length of
	 * @param font The font of the string used to allow the method to calculate the size
	 * @return the height of the given String as an integer.
	 */
	public int getStringHeight(String text, Font font) {
		AffineTransform affinetransform = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
		return (int)(font.getStringBounds(text, frc).getHeight());
	}
}