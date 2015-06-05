package com.minecraft.client.misc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

public class References {
	
	public final int 				moveFromBorder = 0;
	public int 						played = 0;
	public int 						mosX = MouseInfo.getPointerInfo().getLocation().x;
	public int						mosY = MouseInfo.getPointerInfo().getLocation().y;
	public int						MENU = 0; //0 - main menu, 1 - new, 2 - settings, 3 - delete, 4 - load, 5 - pause, 6 - in game
	public int						worldsCount = 0;
	public int						imgWidth = 32;
	public int						imgHeight = 32;
	public int						lrgButtonWidth = 200;
	public int						smlButtonWidth = 100;
	public int						ButtonHeight = 20;
	public final int				blocksize = 32;
	public final int				maxMobs = 6;
	public final int				textFieldMaxLength = 20;
	
	
	public final String 			BUILD = "Pre-Alpha BUILD";
	public final String				VERSION = "0.1.2.9";
	public final String				NAME = "Minecraft 2D";
	public String					curWorld = "";
	public String					splash = "";
	

	public final int 				pixelSize = 3;
	public double					sx = moveFromBorder;
	public double					sy = moveFromBorder;
	public double					direction = 0;
	public double					fps;
	public double					FPScap = 60;
	public double					fallingSpeed = 1.3;
	public double					maxFallSpeed = 2;
	public double					fallingAcceleration = 0.0025;
	
	
	public Dimension				SIZE = new Dimension(1280, 720);
	public final Dimension			PIXEL = new Dimension(SIZE.width / (int)(pixelSize), SIZE.height / (int)(pixelSize));
	
	
	public Point 					mouse = new Point(0, 0);
	public Point					moveFramePoint = new Point(0, 0);
	public Point					framePos = new Point((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2) - (int)(SIZE.getWidth() / 2),
									(int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2) - (int)(SIZE.getHeight() / 2));
	
	public boolean					development = false;
	public boolean 					loaded = false;
	public boolean 					isRunning = false;
	public boolean					isMoving = false;
	public boolean 					isJumping = false;
	public boolean					isTyping = false;
	public boolean					hasStarted = false;
	public boolean					playMusic = false;
	public boolean					playSoundEF = true;
	public boolean					autoSave = true;
	public boolean					wasPlaying = false;
	public boolean 					fullscreen = false;
	public boolean 					isGenerating = false;
	public boolean					isMouseLeft = false;
	public boolean					isMouseRight = false;
	public boolean					chestOpen = false;
	public boolean					inventoryOpen = false;
	public boolean					craftingOpen = false;
	public boolean					settingsOpen = false;
	public boolean					moveFrame = false;
	public boolean 					dragging = false;
	
	
	public final Font				font1 = new Font(Font.DIALOG, Font.PLAIN, 12);
	public final Font				font2 = new Font(Font.DIALOG, Font.PLAIN, 9);
	public final Font 				font3 = new Font(Font.SERIF, Font.PLAIN, 12);
	
	
	public final Color				color1 = new Color(255, 255, 255);
	public final Color				color2 = new Color(100, 100, 100);
	public final Color 				emptyBG = new Color(100, 80, 100, 170);
	public final Color 				unselected = new Color(0, 0, 0, 100);
	public final Color 				selected = new Color(255, 255, 255);
	public final Color 				disabledUn = new Color(185, 20, 20, 160);
	public final Color 				disabledSe = new Color(255, 20, 20);
	
	private static AffineTransform 	affinetransform = new AffineTransform(); 

	public FontRenderContext 		frc = new FontRenderContext(affinetransform, true, true);
}	