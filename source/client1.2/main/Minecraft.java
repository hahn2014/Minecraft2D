package com.minecraft.client.main;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Toolkit;

import javax.swing.JFrame;

import com.minecraft.client.IO.CrashDumping;
import com.minecraft.client.IO.InputPane;
import com.minecraft.client.IO.Logger;
import com.minecraft.client.IO.OptionPane;
import com.minecraft.client.game.Player;
import com.minecraft.client.game.SaveLoad;
import com.minecraft.client.game.Sky;
import com.minecraft.client.game.WorldRender;
import com.minecraft.client.listeners.KeyInputListener;
import com.minecraft.client.listeners.MouseInpListener;
import com.minecraft.client.listeners.MouseMotListener;
import com.minecraft.client.listeners.MouseWhlListener;
import com.minecraft.client.math.FPS;
import com.minecraft.client.menus.DeleteWorldMenu;
import com.minecraft.client.menus.LoadWorldMenu;
import com.minecraft.client.menus.MainMenu;
import com.minecraft.client.menus.NewWorldMenu;
import com.minecraft.client.menus.PauseMenu;
import com.minecraft.client.menus.SettingsMenu;
import com.minecraft.client.misc.References;
import com.minecraft.client.misc.Splashes;
import com.minecraft.client.resources.NewComputer;
import com.minecraft.client.resources.SoundEngine;
import com.minecraft.client.resources.Tile;

public class Minecraft extends Applet implements Runnable {
	private static final long serialVersionUID = 6493530810487404301L;

	public static References 		r;
	
	public static JFrame 			frame = new JFrame();
	
	public Image					screen;
	
	private static Minecraft		game;
	
	public static Splashes			splashes;
	public static SoundEngine 		soundengine;
	public static Player			player;
	public static FPS				FPS;
	public static MainMenu			main;
	public static NewWorldMenu		newWorld;
	public static LoadWorldMenu		loadWorld;
	public static DeleteWorldMenu	deleteWorld;
	public static PauseMenu 		pauseMenu;
	public static WorldRender		wr;
	public static SettingsMenu		settings;
	public static NewComputer		nc;
	public static SaveLoad			sl;
	public static OptionPane 		op;
	public static InputPane			ip;
	public static Sky 				sky;

	public Minecraft() {
		r = new References();
		op = new OptionPane();
		ip = new InputPane();
		sky = new Sky();
		soundengine = new SoundEngine();
		splashes = new Splashes();
		frame.dispose();
		frame.setUndecorated(true);
		frame.pack();
		frame.setSize(r.SIZE);
		frame.setPreferredSize(r.SIZE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setEnabled(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		setFrameSize(r.fullscreen);
		frame.setTitle(r.NAME + " " + r.BUILD + " " + r.VERSION);
		setPreferredSize(r.SIZE);
		addKeyListener(new KeyInputListener());
		addMouseListener(new MouseInpListener());
		addMouseMotionListener(new MouseMotListener());
		addMouseWheelListener(new MouseWhlListener());
	}
	
	public void start() {
		//start the game loop
		r.isRunning = true;
		r.MENU = 0;
		new Thread(this).start();
		requestFocus();
		//start sound
		//set frame to visible
		frame.setVisible(true);
		
		//temporarily dissabling sound cause i like to jam to my jams while coding
	
		//soundengine.startWithRandomSong();
		
		Logger.info("Finished Settings Things Up");
	}
	
	public void stop() {
		r.isRunning = false;
		System.exit(0);
	}

	public void tick() {
		switch (r.MENU) {
			case 0:
				main.tick(); 		//Main Menu
				splashes.tick();    //tick for splashing
				break;
			case 1:
				newWorld.tick(); 	//New World Menu
				break;
			case 2:
				settings.tick(); 	//Settings Menu
				break;
			case 3:
				deleteWorld.tick(); //Delete World Menu
				break;
			case 4:
				loadWorld.tick(); 	//Load World Menu
				break;
			case 5:
				pauseMenu.tick();	//Pause Menu
				break;
			case 6:					//in game
				wr.tick((int)(r.sx), (int)(r.sy), (int)(r.PIXEL.width / Tile.tileSize) + 1, (int) (r.PIXEL.height / Tile.tileSize) + 1);
				player.tick();
				sky.tick();
				//if (console.typing || console.dispMessage) {
				//    console.tick();
				//}
				
				break;
		}
		r.mosX = MouseInfo.getPointerInfo().getLocation().x;
		r.mosY = MouseInfo.getPointerInfo().getLocation().y;
	}
	
	public void render() {
		if (r.dragging != true) {
			Graphics g = screen.getGraphics();
			switch (r.MENU) {
				case 0:
					main.render(g); 		//Main Menu
					splashes.render(g);		//render splashes
					break;
				case 1:
					newWorld.render(g); 	//New World Menu
					break;
				case 2:
					settings.render(g);		//Settings Menu
					break;
				case 3:
					deleteWorld.render(g);	//Delete World Menu
					break;
				case 4:
					loadWorld.render(g);	//Load World Menu
					break;
				case 5:
					pauseMenu.render((Graphics2D)(g)); 	//Pause Menu
					break;
				case 6:
					if (r.hasStarted) {
						g.setFont(r.font1);
						g.setColor(r.color1);
						g.fillRect(0, 0, r.PIXEL.width, r.PIXEL.height);
						sky.render(g);
						wr.render(g, (int)(r.sx), (int)(r.sy), (int)(r.PIXEL.width / Tile.tileSize), (int)(r.PIXEL.height / Tile.tileSize));
						player.render(g);
						if (r.inventoryOpen) {
							//inventory.render(g);
						} else if (r.chestOpen) {
							//chest.render(g);
						} else if (r.craftingOpen) {
							//crafting.render(g);
						}
						//console.render(g);
						if (r.settingsOpen) {
							//pause.render(g);
						}
						g.setFont(r.font2);
						g.setColor(r.color2);
						g.drawString(r.BUILD + " " + r.VERSION, 311, 8);
					}
					break;
			}
			FPS.render(g);
			if (op.getRender())
				op.render(g);
			if (ip.getRender())
				ip.render(g);
			g = getGraphics();
			g.drawImage(screen, 0, 0, r.SIZE.width, r.SIZE.height, null);
			g.dispose();
		}
	}
	
	@Override
	public void run() {
		screen = createVolatileImage(r.PIXEL.width, r.PIXEL.height);
		while(r.isRunning) {
			tick();
			render();
			FPS.getFPS();
			try {
				Thread.sleep(5);
			} catch(Exception ex) {
				ex.printStackTrace();
				op = new OptionPane("Woah There!", ex + "\nSorry! and unexpected error occured. The program will now exit",
						"OK", 200, 60, new Color(100, 80, 100), 1.0f,Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.WHITE, true);
				op.updateVars(2);
				CrashDumping.DumpCrash(ex);
				System.exit(0);
			}
		}
	}
	
	public static void setFrameSize(boolean fullScreen) {
		if (fullScreen) {
			//set it to fullscreen (no duhhh)
			r.SIZE = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
			frame.setSize(r.SIZE);
			frame.getContentPane().setPreferredSize(r.SIZE);
			frame.setLocationRelativeTo(null);
		} else {
			//set it back to original size
			r.SIZE = new Dimension(1200, 700);
			frame.setSize(r.SIZE);
			frame.getContentPane().setPreferredSize(r.SIZE);
			frame.setLocationRelativeTo(null);
		}
	}
	
	public static void main(String[] args) {
		
		for (String arg : args) {
			if (arg.equals("-d")) {
				Logger.debug = true;
				Logger.debug("Debug true");
			}
		}
		
		game = new Minecraft();
		frame.add(game);
		nc = new NewComputer();
		player = new Player(Tile.tileSize, Tile.tileSize * 2); //1 block wide, 2 high
		FPS = new FPS();
		FPS.alterFPSCap(60.0);
		//init game objects
		new Tile(); //loading images
		
		//loadWorld = new LoadWorldMenu();
		settings = new SettingsMenu();
		//delete = new DeleteMenu();
		main = new MainMenu();
		newWorld = new NewWorldMenu();
		loadWorld = new LoadWorldMenu();
		deleteWorld = new DeleteWorldMenu();
		pauseMenu = new PauseMenu();
		wr = new WorldRender();
		sl = new SaveLoad();
		//start game
		game.start();
	}
	
	public static Minecraft getMinecraft() {
		return game;
	}
}