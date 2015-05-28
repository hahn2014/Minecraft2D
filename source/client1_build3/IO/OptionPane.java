package com.minecraft.client.IO;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.regex.PatternSyntaxException;

import com.minecraft.client.main.Minecraft;
import com.minecraft.client.math.Methods;
import com.minecraft.client.misc.References;
import com.minecraft.client.resources.Tile;

/**
 * Option Pane renders a box in the middle of the screen, this class can render the box with either a color,
 * or a BufferedImage. This is a remake version of the standard JOptionPane method that comes with the java swing api.
 * This has the same idea, but allow for you much more options on the pane.
 * @author Bryce
 * 
 * @since 1.29
 * 
 * @see JOptionPane
 *
 */
public class OptionPane {
	
	private int boxWidth, boxHeight, minwidth = 200, minheight = 80,
		imageWidth, imageHeight, minImageWidth = 8, minImageHeight = 8,
		buttonWidth = 100, buttonHeight = 20;
	private String TITLE, MESSAGE, BUTTON;
	private Color boxColor, titleColor, messageColor, buttonColor;
	private float opacity = 1.0f;
	private BufferedImage image;
	private boolean render = false;
	
	private String[] line = new String[4];
	
	private int x, y, xtimes, ytimes;
	
	Methods m;
	References r;

	
	/**
	 * Defualt constructor. Will take nothing in as peramaters and will set all initial values to defualt.
	 */
	public OptionPane() {
		m = new Methods();
		r = Minecraft.getReferences();
		//set pane values to default
		boxWidth = minwidth;
		boxHeight = minheight;
		imageWidth = minImageWidth;
		imageHeight = minImageHeight;
		TITLE = "This is a popup box title";
		MESSAGE = "This is a popup box message";
		BUTTON = "This is a button";
		boxColor = new Color(100, 80, 100);
		titleColor = Color.WHITE;
		messageColor = Color.WHITE;
		setButtonColor(Color.LIGHT_GRAY);
		image = null;
		opacity = 1.0f;
		this.render = false;
		x = (r.PIXEL.width / 2) - (boxWidth / 2);
		y = (r.PIXEL.height / 2) - (boxHeight / 2);
	}
	
	/**
	 * Side constructor, will take in a title, message and a button string, the width and height of the 
	 * option pane, the background color to display, the opacity of the background color, title, message,
	 * and button text color, and whether to draw it or not.
	 * @param title The string title text.
	 * @param message The string message box text.
	 * @param button The string button text.
	 * @param boxWidth The int width of the message box. Default is 200
	 * @param boxHeight The int height of the message box. Default is 80
	 * @param boxColor The color of the background to the box.
	 * @param boxOpacity The float opacity to the backgroud. Default is 1.0f
	 * @param titleColor The color of the title text.
	 * @param messageColor The color of the message text.
	 * @param buttonColor The color of the button text.
	 * @param render boolean on whether the option pane should be rendered of not.
	 * 
	 * @since Pre-Alpha 1.29
	 * 
	 * @author Bryce
	 */
	public OptionPane(String title, String message, String button, int boxWidth, int boxHeight, Color boxColor,
			float boxOpacity, Color titleColor, Color messageColor, Color buttonColor, boolean render) {
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
		this.TITLE = title;
		this.MESSAGE = message;
		this.BUTTON = button;
		this.boxColor = boxColor;
		this.opacity = boxOpacity;
		this.titleColor = titleColor;
		this.messageColor = messageColor;
		this.buttonColor = buttonColor;
		this.image = null;
		this.render = render;
		x = (r.PIXEL.width / 2) - (boxWidth / 2);
		y = (r.PIXEL.height / 2) - (boxHeight / 2);
	}
	
	/**
	 * Side constructor, will take in a title, message and a button string, the width and height of the 
	 * option pane, the background image to display, the opacity of the background image, title, message,
	 * and button text color, and whether to draw it or not.
	 * 
	 * @param title The string title text.
	 * @param message The string message box text.
	 * @param button The string button text.
	 * @param boxWidth The int width of the message box. Default is 200
	 * @param boxHeight The int height of the message box. Default is 80
	 * @param image The bufferedimage of the background. Defaykt is empty
	 * @param imageWidth The int width the image will be rendered. (will render full box width, not stretched)
	 * @param imageHeight The int height the image will be rendered. (will render the full box height, not stretched)
	 * @param imageOpacity The float opacity the image will render at.
	 * @param titleColor The color of the title text.
	 * @param messageColor The color of the message text.
	 * @param buttonColor The color of the button text.
	 * @param render boolean on whether the option pane should be rendered of not.
	 * 
	 * @throws Exception
	 * 
	 * @since Pre-Alpha 1.29
	 * 
	 * @author Bryce Hahn
	 */
	public OptionPane(String title, String message, String button, int boxWidth, int boxHeight, BufferedImage image, int imageWidth,
			int imageHeight, float imageOpacity, Color titleColor, Color messageColor, Color buttonColor, boolean render) throws Exception {
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
		this.TITLE = title;
		this.MESSAGE = message;
		this.BUTTON = button;
		this.titleColor = titleColor;
		this.messageColor = messageColor;
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
		//we need to split the message to fit the box
		String[] splitArray = null;
		try {
			splitArray = MESSAGE.split("\\s+");
		} catch (PatternSyntaxException e) {
		    CrashDumping.DumpCrash(e);
		}
		String cursentence = "";
		int curleng = 0;
		int curline = 0;
		for (int i = 0; i < splitArray.length; i++) {
			if (curleng + m.getStringWidth(splitArray[i], r.font1) <= boxWidth) {
				curleng += m.getStringWidth(splitArray[i], r.font1);
				cursentence += splitArray[i]; cursentence += " ";
			} else {
				line[curline] = cursentence;
				cursentence = "";
				curline++;
				curleng = 0;
				
				curleng += m.getStringWidth(splitArray[i], r.font1);
				cursentence += splitArray[i]; cursentence += " ";
			}
			if (!"".equals(cursentence)) {
			    line[curline] = cursentence;
			}
			
		}
	}
	
	public Dimension getSize() {
		return new Dimension(boxWidth, boxHeight);
	}
	
	public void setSize(int boxWidth, int boxHeight) {
		if (boxWidth >= minwidth)
			this.boxWidth = boxWidth;
		else
			this.boxWidth = minwidth;
		if (boxHeight >= minheight)
			this.boxHeight = boxHeight;
		else 
			this.boxHeight = minheight;
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
	
	public String getButton() {
		return BUTTON;
	}
	
	public void setButton(String button) {
		this.BUTTON = button;
	}
	
	public Color getBoxColor() {
		return boxColor;
	}
	
	public void setBoxColor(Color boxColor) {
		this.boxColor = boxColor;
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

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D)(g);
		g2d.setFont(r.font2);
		//draw out the menu graphics
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
			if (line[0] != null)
				g2d.drawString(line[0], x + 2, y + 24);
			if (line[1] != null)
				g2d.drawString(line[1], x + 2, y + 38);
			if (line[2] != null)
				g2d.drawString(line[2], x + 2, y + 52);
			if (line[3] != null)
				g2d.drawString(line[3], x + 2, y + 66);
			//now render the ok button
			g2d.setColor(buttonColor);
			g2d.drawImage(Tile.smlButton, (r.PIXEL.width / 2) - (buttonWidth / 2), (y + boxHeight) - (buttonHeight - 8), buttonWidth, buttonHeight, null);
			g2d.drawString(BUTTON, (r.PIXEL.width / 2) - (m.getStringWidth(BUTTON, r.font2) / 2) - 3, (y + boxHeight) + 2);
		}
	}
}