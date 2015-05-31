package com.minecraft.client.resources;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import com.minecraft.client.IO.CrashDumping;

public class Tile {
	public static final int tileSize 		= 16;
	public static final int dropSize 		= 8;
	public static final int invCellSize 	= 25;
	public static final int invWidth 		= 9;
	public static final int invHeight 		= 3;
	public static final int invCellSpace 	= 4;
	public static final int invBorderSpace 	= 4;
	public static final int invItemBorder 	= 2;
	public static final int chestLength 	= 9;
	public static final int chestHeight 	= 5;
	public static final int invCraftLength 	= 2;
	public static final int invCraftHeight 	= 2;
	public static final int craftingLength 	= 3;
	public static final int craftingHeight 	= 3;
	
	// blocks
	public static final int[] blank 		= {-1, -1};
	public static final int[] dirt 			= {1, 0};
	public static final int[] grass 		= {2, 0};
	public static final int[] snowgrass 	= {3, 0};
	public static final int[] sand 			= {2, 1};
	public static final int[] plank 		= {14, 1};
	public static final int[] wood 			= {15, 1};
	public static final int[] stone 		= {0, 0};
	public static final int[] cobbleStone	= {0, 1};
	public static final int[] brick 		= {7, 0};
	public static final int[] leaves 		= {13, 1};
	public static final int[] glass 		= {5, 0};
	public static final int[] stoneSlabs 	= {4, 1};
	public static final int[] bedrock 		= {1, 1};
	// special stuff
	public static final int[] craft 		= {3, 3};
	public static final int[] chest 		= {5, 3};
	public static final int[] chestL 		= {6, 3};
	public static final int[] stick 		= {0, 6};
	public static final int[] lader 		= {3, 5};
	//animations
	public static int[] player = {10, 13};
	
	//misc menu images
	public static BufferedImage lrgButton;
	public static BufferedImage smlButton;
	public static BufferedImage textField;
	public static BufferedImage empty;
	public static BufferedImage logo;
	public static BufferedImage dirtbackground;
	
	public static BufferedImage texture;
	
	//items
	
	
	NewComputer nc = new NewComputer();
	
	public Tile() {
		try {
			texture			= ImageIO.read(Tile.class.getResourceAsStream("texture_pack.png"));
			dirtbackground 	= ImageIO.read(Tile.class.getResourceAsStream("dirtbackground.png"));
			lrgButton		= ImageIO.read(Tile.class.getResourceAsStream("lrgButton.png"));
			smlButton		= ImageIO.read(Tile.class.getResourceAsStream("smlButton.png"));
			textField 		= ImageIO.read(Tile.class.getResourceAsStream("textField.png"));
			empty 			= ImageIO.read(Tile.class.getResourceAsStream("null.png"));
			logo			= ImageIO.read(Tile.class.getResourceAsStream("logo.png"));
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, ex + "\nWe could not load the resources...\nTry re-intralling the program",
					"Whoa There!", JOptionPane.ERROR_MESSAGE);
			CrashDumping.DumpCrash(ex);
			System.exit(0);
		}
	}
}