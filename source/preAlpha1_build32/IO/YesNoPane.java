package com.minecraft.client.IO;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.regex.PatternSyntaxException;

import javax.swing.JOptionPane;

import com.minecraft.client.main.Minecraft;
import com.minecraft.client.math.Methods;
import com.minecraft.client.misc.References;
import com.minecraft.client.misc.Tribool;
import com.minecraft.client.misc.Tribool.VALUE;
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
public class YesNoPane {
	
	private int boxWidth, boxHeight, minwidth = 200, minheight = 80,
		imageWidth, imageHeight, minImageWidth = 8, minImageHeight = 8,
		buttonWidth = 100, buttonHeight = 20;
	public static int button = 0;
	private int[] img = {-1, -1};
	private String TITLE, QUESTION;
	private Color boxColor, titleColor, messageColor, buttonColor;
	private float opacity = 1.0f;
	private BufferedImage image;
	private boolean render = false;
	public Tribool ans;
	
	private Color unselectedColor;
	
	private String[] line = new String[10];
	
	private int x, y, xtimes, ytimes;
	
	Methods m;
	References r;

	
	/**
	 * Defualt constructor. Will take nothing in as peramaters and will set all initial values to defualt.
	 */
	public YesNoPane() {
		m = new Methods();
		r = Minecraft.getReferences();
		ans = new Tribool(VALUE.n);
		//set pane values to default
		boxWidth = minwidth;
		boxHeight = minheight;
		imageWidth = minImageWidth;
		imageHeight = minImageHeight;
		TITLE = "This is a question box title";
		QUESTION = "This is a question box question";
		boxColor = new Color(100, 80, 100);
		titleColor = Color.WHITE;
		messageColor = Color.WHITE;
		image = null;
		opacity = 1.0f;
		this.render = false;
		img[0] = -1; img[1] = -1;
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
	public YesNoPane(String title, String question, int boxWidth, int boxHeight, Color boxColor,
			float boxOpacity, Color titleColor, Color messageColor, Color buttonColor, boolean render) {
		m = new Methods();
		r = Minecraft.getReferences();
		ans = new Tribool(VALUE.n);
		if (boxWidth >= minwidth)
			this.boxWidth = boxWidth;
		else
			this.boxWidth = minwidth;
		if (boxHeight >= minheight)
			this.boxHeight = boxHeight;
		else 
			this.boxHeight = minheight;
		this.TITLE = title;
		this.QUESTION = question;
		this.boxColor = boxColor;
		this.opacity = boxOpacity;
		this.titleColor = titleColor;
		this.messageColor = messageColor;
		this.buttonColor = buttonColor;
		this.unselectedColor = setUnselectedColor(buttonColor);
		this.image = null;
		this.render = render;
		img[0] = -1; img[1] = -1;
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
	public YesNoPane(String title, String question, int boxWidth, int boxHeight, BufferedImage image, int imageWidth,
			int imageHeight, float imageOpacity, Color titleColor, Color messageColor, Color buttonColor, boolean render) throws Exception {
		m = new Methods();
		r = Minecraft.getReferences();
		ans = new Tribool(VALUE.n);
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
		this.QUESTION = question;
		this.titleColor = titleColor;
		this.messageColor = messageColor;
		this.buttonColor = buttonColor;
		this.unselectedColor = setUnselectedColor(buttonColor);
		this.image = image;
		this.opacity = imageOpacity;
		this.render = render;
		img[0] = -1; img[1] = -1;
		x = (r.PIXEL.width / 2) - (boxWidth / 2);
		y = (r.PIXEL.height / 2) - (boxHeight / 2);
		xtimes = (boxWidth / imageWidth) + 1;
		ytimes = (boxHeight / imageHeight) + 1;
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
	public YesNoPane(String title, String question, int boxWidth, int boxHeight, int[] image, int imageWidth,
			int imageHeight, float imageOpacity, Color titleColor, Color messageColor, Color buttonColor, boolean render) throws Exception {
		m = new Methods();
		r = Minecraft.getReferences();
		ans = new Tribool(VALUE.n);
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
		this.QUESTION = question;
		this.titleColor = titleColor;
		this.messageColor = messageColor;
		this.buttonColor = buttonColor;
		this.unselectedColor = setUnselectedColor(buttonColor);
		img = image;
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
			splitArray = QUESTION.split("\\s+");
		} catch (PatternSyntaxException e) {
		    CrashDumping.DumpCrash(e);
		}
		String cursentence = "";
		int curleng = 0;
		int curline = 0;
		for (int i = 0; i < splitArray.length; i++) {
			if (curleng + m.getStringWidth(splitArray[i], r.font2) <= boxWidth) {
				curleng += m.getStringWidth(splitArray[i], r.font2);
				cursentence += splitArray[i]; cursentence += " ";
			} else {
				line[curline] = cursentence;
				cursentence = "";
				curline++;
				curleng = 0;
				
				curleng += m.getStringWidth(splitArray[i], r.font2);
				cursentence += splitArray[i]; cursentence += " ";
			}
			if (!"".equals(cursentence)) {
			    line[curline] = cursentence;
			}
			
		}
	}
	
	public boolean getRender() {
		return render;
	}
	
	public void setRender(boolean render) {
		this.render = render;
	}
	
	public void setAnswer(String answer) {
		ans.setTribool(ans.valFromString(answer));;
	}
	
	public String getAnswer() {
		return ans.toString();
	}
	
	public Color setUnselectedColor(Color start) {
		return new Color(
			(start.getRed() 	- 80 >= 0) ? start.getRed() 	- 80 : 0,
			(start.getGreen() 	- 80 >= 0) ? start.getGreen() 	- 80 : 0,
			(start.getBlue() 	- 80 >= 0) ? start.getBlue() 	- 80 : 0,
			(start.getAlpha() 	- 100 >= 0) ? start.getAlpha() 	- 100 : 0);
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D)(g);
		g2d.setFont(r.font2);
		//draw out the menu graphics
		if (render) {
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
			//we need to render the popup dialog
			if (image == null && (img[0] == -1 && img[1] == -1)) {
				g2d.setColor(boxColor);
				g2d.fillRect(x, y, boxWidth, boxHeight);
			} else {
				for (int a = 0; a < xtimes; a++) {
					for (int b = 0; b < ytimes; b++) {
						if (img[0] == -1 && img[1] == -1) {
							g2d.drawImage(image, (x - 4) + (imageWidth * a), (y - 6) + (imageHeight * b), imageWidth, imageHeight, null);
						} else {
							g2d.drawImage(Tile.texture,(x - 4) + (imageWidth * a),(y - 6) + (imageHeight * b),
									((x - 4) + (imageWidth * a)) + imageWidth, ((y - 6) + (imageHeight * b)) + imageHeight,
									img[0] * Tile.tileSize, img[1] * Tile.tileSize, img[0] * Tile.tileSize + Tile.tileSize,
									img[1] * Tile.tileSize + Tile.tileSize, null);
						}
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
			g2d.drawImage(Tile.smlButton, x, (y + boxHeight) - (buttonHeight - 8), buttonWidth, buttonHeight, null);
			g2d.drawImage(Tile.smlButton, x + (boxWidth - buttonWidth) + 16, (y + boxHeight) - (buttonHeight - 8), buttonWidth, buttonHeight, null);
			if (button == 0)
				g2d.setColor(buttonColor);
			else
				g2d.setColor(unselectedColor);
			g2d.drawString("Yes", x + (buttonWidth / 2) - (m.getStringWidth("Yes", r.font2) / 2),
					(y + boxHeight) + 2); //yes btn
			if (button == 1)
				g2d.setColor(buttonColor);
			else
				g2d.setColor(unselectedColor);
			g2d.drawString("No" , x + (boxWidth + 18) - (buttonWidth / 2) - (m.getStringWidth("No", r.font2) / 2),
					(y + boxHeight) + 2); //no btn
		}
	}
}