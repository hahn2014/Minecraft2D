package com.minecraft.client.resources;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import com.minecraft.client.IO.CrashDumping;

public class Tile {
	public static final int tileSize = 16;
	public static final int dropSize = 8;
	public static final int invCellSize = 25;
	public static final int invWidth = 9;
	public static final int invHeight = 3;
	public static final int invCellSpace = 4;
	public static final int invBorderSpace = 4;
	public static final int invItemBorder = 2;
	public static final int chestLength = 9;
	public static final int chestHeight = 5;
	public static final int invCraftLength = 2;
	public static final int invCraftHeight = 2;
	public static final int craftingLength = 3;
	public static final int craftingHeight = 3;
	
	/* IDs
	 * blank = 0 -- dirt = 1 -- grass = 2 -- snow grass = 3 -- stone = 4 -- cobblestone = 5 -- bedrock = 6 -- log = 7 -- plank = 8
	 * leaves = 9
	 */
	
	//misc menu images
	public static BufferedImage lrgButton;
	public static BufferedImage smlButton;
	public static BufferedImage textField;
	public static BufferedImage empty;
	public static BufferedImage logo;
	//blocks
	public static BufferedImage blank;
	public static BufferedImage dirt;
	public static BufferedImage grass;
	public static BufferedImage snowgrass;
	public static BufferedImage stone;
	public static BufferedImage cobblestone;
	public static BufferedImage bedrock;
	public static BufferedImage log;
	public static BufferedImage plank;
	public static BufferedImage leaves;
	
	//items
	
	
	NewComputer nc = new NewComputer();
	
	public Tile() {
		try {
			lrgButton		= ImageIO.read(Tile.class.getResourceAsStream("lrgButton.png"));
			smlButton		= ImageIO.read(Tile.class.getResourceAsStream("smlButton.png"));
			textField 		= ImageIO.read(Tile.class.getResourceAsStream("textField.png"));
			empty 			= ImageIO.read(Tile.class.getResourceAsStream("null.png"));
			logo			= ImageIO.read(Tile.class.getResourceAsStream("logo.png"));
			blank			= ImageIO.read(Tile.class.getResourceAsStream("blank.png"));
			dirt			= ImageIO.read(Tile.class.getResourceAsStream("dirt.png"));
			grass			= ImageIO.read(Tile.class.getResourceAsStream("grass.png"));
			snowgrass		= ImageIO.read(Tile.class.getResourceAsStream("grass_snow.png"));
			stone			= ImageIO.read(Tile.class.getResourceAsStream("stone.png"));
			cobblestone		= ImageIO.read(Tile.class.getResourceAsStream("cobblestone.png"));
			bedrock 		= ImageIO.read(Tile.class.getResourceAsStream("bedrock.png"));
			log				= ImageIO.read(Tile.class.getResourceAsStream("log.png"));
			leaves			= ImageIO.read(Tile.class.getResourceAsStream("leaves.png"));
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, ex + "\nWe could not load the resources...\nTry re-intralling the program", "Whoa There!", JOptionPane.ERROR_MESSAGE);
			CrashDumping.DumpCrash(ex);
			System.exit(0);
		}
	}
}