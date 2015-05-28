package com.minecraft.client.IO;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

import com.minecraft.client.main.Minecraft;
import com.minecraft.client.math.Methods;
import com.minecraft.client.misc.References;
import com.minecraft.client.resources.Tile;

/**
 * Input Pane renders a box in the middle of the screen with an input text field, this class can render the box
 * with either a color, or a BufferedImage. This is a remake of the standard JOptionPane method that comes with
 * the java swing api. This has the same idea, but allow for you much more options on the pane.
 * @author Bryce
 * 
 * @since 1.32
 * 
 * @see JOptionPane
 * 
 */
public class InputPane {
	private int boxWidth, boxHeight, minwidth = 200, minheight = 80, imageWidth,
		imageHeight, minImageWidth = 8, minImageHeight = 8, buttonWidth = 100,
		buttonHeight = 20, textFieldWidth = 100, textFieldHeight = 20;
	private String TITLE = "", MESSAGE = "", BUTTON = "", FIELD = "";
	private Color boxColor, titleColor, messageColor, fieldColor, buttonColor;
	private float opacity = 1.0f;
	private BufferedImage image;
	private boolean render = false;
	
	private int x, y, xtimes, ytimes;
	
	Methods m;
	References r;
	
	/**
	 * Defualt constructor. Will take nothing in as peramaters and will set all initial values to defualt.
	 */
	public InputPane() {
		m = new Methods();
		r = Minecraft.getReferences();
		//set the pane values to default
		boxWidth = minwidth;
		boxHeight = minheight;
		imageWidth = minImageWidth;
		imageHeight = minImageHeight;
		TITLE = "This is a poput box title";
		MESSAGE = "This is a popup box message";
		FIELD = "This is a popup box field";
		BUTTON = "This is a button";
		boxColor = new Color(100, 80, 100);
		titleColor = Color.WHITE;
		messageColor = Color.WHITE;
		fieldColor = Color.BLUE;
		buttonColor = Color.LIGHT_GRAY;
		image = null;
		opacity = 1.0f;
		render = false;
		x = (r.PIXEL.width / 2) - (boxWidth / 2);
		y = (r.PIXEL.height / 2) - (boxHeight / 2);
	}
	
	/**
	 * 
	 * @param title
	 * @param message
	 * @param button
	 * @param boxWidth
	 * @param boxHeight
	 * @param fieldWidth
	 * @param fieldHeight
	 * @param boxColor
	 * @param boxOpacity
	 * @param titleColor
	 * @param messageColor
	 * @param fieldColor
	 * @param buttonColor
	 *  
	 * @since Pre-Alpha 1.29
	 * 
	 * @author Bryce
	 */
	public InputPane(String title, String message, String button, int boxWidth, int boxHeight, int fieldWidth, int fieldHeight, Color boxColor,
			float boxOpacity, Color titleColor, Color messageColor, Color fieldColor, Color buttonColor, boolean render) {
		m = new Methods();
		r = Minecraft.getReferences();
		if (boxWidth >= minwidth)
			this.boxWidth = boxWidth;
		else
			this.boxWidth = minwidth;
		if (boxHeight >= minheight)
			this.boxHeight = boxHeight;
		else 
			this.boxHeight = minheight;
		this.textFieldWidth = fieldWidth;
		this.textFieldHeight = fieldHeight;
		this.TITLE = title;
		this.MESSAGE = message;
		this.BUTTON = button;
		this.FIELD = "";
		this.boxColor = boxColor;
		this.opacity = boxOpacity;
		this.titleColor = titleColor;
		this.messageColor = messageColor;
		this.fieldColor = fieldColor;
		this.buttonColor = buttonColor;
		this.image = null;
		this.render = render;
		x = (r.PIXEL.width / 2) - (boxWidth / 2);
		y = (r.PIXEL.height / 2) - (boxHeight / 2);
	}
	
	/**
	 * 
	 * @param title
	 * @param message
	 * @param button
	 * @param boxWidth
	 * @param boxHeight
	 * @param fieldWidth
	 * @param fieldHeight
	 * @param image
	 * @param imageWidth
	 * @param imageHeight
	 * @param imageOpacity
	 * @param titleColor
	 * @param messageColor
	 * @param fieldColor
	 * @param buttonColor
	 * @param render
	 * 
	 * @throws Exception
	 * 
	 * @since Pre-Alpha 1.29
	 * 
	 * @author Bryce
	 */
	public InputPane(String title, String message, String button, int boxWidth, int boxHeight, int fieldWidth, int fieldHeight, BufferedImage image, int imageWidth,
			int imageHeight, float imageOpacity, Color titleColor, Color messageColor, Color fieldColor, Color buttonColor, boolean render) throws Exception {
		m = new Methods();
		r = Minecraft.getReferences();
		if (boxWidth >= minwidth)
			this.boxWidth = boxWidth;
		else
			this.boxWidth = minwidth;
		if (boxHeight >= minheight)
			this.boxHeight = boxHeight;
		else 
			this.boxHeight = minheight;
		
		if (imageWidth >= minImageWidth)
			this.imageWidth = imageWidth;
		else
			this.imageWidth = minImageWidth;
		if (imageHeight >= minImageHeight)
			this.imageHeight = imageHeight;
		else 
			this.imageHeight = minImageHeight;
		this.textFieldHeight = fieldHeight;
		this.textFieldWidth = fieldWidth;
		this.TITLE = title;
		this.MESSAGE = message;
		this.BUTTON = button;
		this.titleColor = titleColor;
		this.messageColor = messageColor;
		this.fieldColor = fieldColor;
		this.buttonColor = buttonColor;
		this.image = image;
		this.opacity = imageOpacity;
		this.render = render;
		x = (r.PIXEL.width / 2) - (boxWidth / 2);
		y = (r.PIXEL.height / 2) - (boxHeight / 2);
		xtimes = (boxWidth / imageWidth) + 1;
		ytimes = (boxHeight / imageHeight) + 1;
	}
	
	public void updateVars(int method) {
		if (method == 1) {
			x = (r.PIXEL.width / 2) - (boxWidth / 2);
			y = (r.PIXEL.height / 2) - (boxHeight / 2);
			xtimes = (boxWidth / imageWidth) + 1;
			ytimes = (boxHeight / imageHeight) + 1;
		} else if (method == 2) {
			x = (r.PIXEL.width / 2) - (boxWidth / 2);
			y = (r.PIXEL.height / 2) - (boxHeight / 2);
		}
	}

	public int getBoxWidth() {
		return boxWidth;
	}

	public void setBoxWidth(int boxWidth) {
		this.boxWidth = boxWidth;
	}

	public int getBoxHeight() {
		return boxHeight;
	}

	public void setBoxHeight(int boxHeight) {
		this.boxHeight = boxHeight;
	}

	public int getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	public int getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}

	public String getTitle() {
		return TITLE;
	}

	public void setTitle(String title) {
		TITLE = title;
	}

	public String getMessage() {
		return MESSAGE;
	}

	public void setMessage(String message) {
		MESSAGE = message;
	}
	
	public String getField() {
		return FIELD;
	}

	public void addToField(String field) {
		FIELD += field;
	}
	
	public void setField(String field) {
		FIELD = field;
	}

	public String getButton() {
		return BUTTON;
	}

	public void setButton(String button) {
		BUTTON = button;
	}

	public Color getBoxColor() {
		return boxColor;
	}

	public void setBoxColor(Color boxColor) {
		this.boxColor = boxColor;
	}

	public Color getTitleColor() {
		return titleColor;
	}

	public void setTitleColor(Color titleColor) {
		this.titleColor = titleColor;
	}

	public Color getMessageColor() {
		return messageColor;
	}

	public void setMessageColor(Color messageColor) {
		this.messageColor = messageColor;
	}

	public Color getFieldColor() {
		return fieldColor;
	}

	public void setFieldColor(Color fieldColor) {
		this.fieldColor = fieldColor;
	}

	public Color getButtonColor() {
		return buttonColor;
	}

	public void setButtonColor(Color buttonColor) {
		this.buttonColor = buttonColor;
	}

	public float getOpacity() {
		return opacity;
	}

	public void setOpacity(float opacity) {
		this.opacity = opacity;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public boolean getRender() {
		return render;
	}

	public void setRender(boolean render) {
		this.render = render;
	}
	
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D)(g);
		g2d.setFont(r.font2);
		if (render) {
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
			//we need to render the popup dialog
			if (image == null) {
				g2d.setColor(boxColor);
				g2d.fillRect(x, y, boxWidth, boxHeight);
			} else {
				for (int a = 0; a < xtimes; a++) {
					for (int b = 0; b < ytimes; b++) {
						g2d.drawImage(image, (x - 4) + (imageWidth * a), (y - 6) + (imageHeight * b), imageWidth, imageHeight, null);
					}
				}
			}
			//now render the title
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
			g2d.setColor(titleColor);
			g2d.drawString(TITLE, x + 2, y + 10);
			//now render the message
			g2d.setColor(messageColor);
			g2d.drawString(MESSAGE, x + 2, y + 24);
			//now render the field
			g2d.setColor(fieldColor);
			g2d.drawImage(Tile.textField, (r.PIXEL.width / 2) - (textFieldWidth / 2), y + 40, textFieldWidth, textFieldHeight, null);
			g2d.drawString(getField(), (r.PIXEL.width / 2) - (textFieldWidth / 2), y + 53);
			//now render the ok button
			g2d.setColor(buttonColor);
			g2d.drawImage(Tile.smlButton, (r.PIXEL.width / 2) - (buttonWidth / 2), (y + boxHeight) - (buttonHeight - 8), buttonWidth, buttonHeight, null);
			g2d.drawString(BUTTON, (r.PIXEL.width / 2) - (m.getStringWidth(BUTTON, r.font2) / 2) - 3, (y + boxHeight) + 2);
		}
	}
}