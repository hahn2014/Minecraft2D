package com.minecraft.client.menus;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.minecraft.client.IO.CrashDumping;
import com.minecraft.client.game.SaveLoad;
import com.minecraft.client.main.Minecraft;
import com.minecraft.client.math.Methods;
import com.minecraft.client.misc.References;
import com.minecraft.client.resources.NewComputer;
import com.minecraft.client.resources.Tile;

public class LoadWorldMenu {
	
	private int x = 0, y = 60, ySpace = 60, widthtimes = 0, heighttimes = 0;
	private static int scrollbarstart = 50, scrollbarheight = 64, multiplier;
	
	public static int curselect = -1, scrollbar = scrollbarstart;
	
	public static String lastWorld = "";
	
	public static ArrayList<String> worlds = new ArrayList<String>();
	
	private Color b1 = new Color(255, 255, 255), b2 = new Color(0, 0, 0, 100);
	
	private static BufferedImage[] img;
	
	private static File[] listOfFiles;
	
	private static String[] builds;
	
	private static int[] worldSelected = new int[worlds.size()]; //for worldSelected -> -1 = cancel smlButton, 1 = top, 2 = middle, 3 = bottom, 4 = only 2 worlds
	
	public static References r;
	Methods m;
	

	public LoadWorldMenu() {
		r 	= Minecraft.r;
		m 	= new Methods();
		
		x = (r.PIXEL.width / 2) - (r.lrgButtonWidth / 2);
		
		widthtimes = (r.PIXEL.width / Tile.tileSize) + 1;
		heighttimes = (r.PIXEL.height / Tile.tileSize) + 1;
		listWorlds();
		if (r.worldsCount > 0) {
			getWorldSelected();
		}
	}
	
	public void tick() {
		for (int i = 0; i < worlds.size(); i++) {
			if (curselect == -1) {
				b1 = new Color(255, 255, 255);
				b2 = new Color(0, 0, 0, 100);
			} else if (curselect == -2) {
				b1 = new Color(0, 0, 0, 100);
				b2 = new Color(255, 255, 255);
			} else {
				b1 = new Color(0, 0, 0, 100);
				b2 = new Color(0, 0, 0, 100);
			}
		}
	}
	
	private static void iniArray() {
		worlds = new ArrayList<String>(listOfFiles.length);
		builds = new String[listOfFiles.length];
	}
	
	public static void getWorldSelected() {
		for (int i = 0; i < worlds.size(); i++) {
			if (i == 0) {
				worldSelected[i] = 1;
			} else if (i > 0 && i < worlds.size() - 1) {
				worldSelected[i] = 2;
			} else if (i == worlds.size() - 1) {
				worldSelected[i] = 3;
			}
		}
		multiplier = scrollbarheight / r.worldsCount;
		if (curselect != -1) {
			scrollbar = multiplier * curselect;
		} else {
			scrollbar = 0;
		}
	}
	
	@SuppressWarnings("static-access")
	public static void listWorlds() {
		//dir path here
		String path = Minecraft.nc.savesDirectory;
		File folder = new File(path);
		listOfFiles = folder.listFiles();
		if (listOfFiles.length > 0) {
			r.worldsCount = listOfFiles.length;
			iniArray();
			getWorlds();
		} else {
			r.worldsCount = 0; //no files to load, return to menu
		}
	}
	
	@SuppressWarnings("static-access")
	private static void getWorlds() {
		String files;
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				files = listOfFiles[i].getName();
				if (files.endsWith(".dat")) { //make sure it is the appropriate file extension
					String world = files;
					world = world.replace(".dat", "");
					worlds.add(world);
					builds[i] = getBuild(world);
				}
			}
		}
		if (worlds.size() > 0) {
			img = new BufferedImage[worlds.size()];
			worldSelected = new int[worlds.size()];
		} else {
			img = new BufferedImage[0];
			worldSelected = new int[0];
		}
		for (int i = 0; i < worlds.size(); i++) {
			String tmp = worlds.get(i);
			tmp = tmp.replace(".dat", "");
			try {
				img[i] = ImageIO.read(new File(Minecraft.nc.savesDirectory + "\\" + tmp + ".png"));
			} catch(Exception e) {
				img[i] = Tile.empty;
			}
		}
		r.loaded = true;
	}
	
	private static String getBuild(String filename) {
		String build = "";
		File file = new File(NewComputer.savesDirectory + "\\" + filename + ".dat");
		try {
			//try and read from the world file
			BufferedReader br = new BufferedReader(new FileReader(file));
			String words;
			while ((words = br.readLine()) != null) {
				if (words.startsWith("'")) {
					words = words.substring(1, words.length());
					int versionstart = 0;
					for (int a = 0; a < words.length(); a++) {
						if (Character.isDigit(words.charAt(a))) {
							versionstart = a;
							break;
						}
					}
					build = SaveLoad.getFullBuildName(words.substring(0, versionstart));
					build += " " + words.substring(versionstart, words.length());
				}
			}
			br.close();
		} catch (Exception e) {}
		return build;
	}
	
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D)(g);
		//draw out the menu graphics
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		g2d.setColor(r.emptyBG);
		//draw the background
		try {
			for (int x = 0; x < widthtimes; x++) {
				for (int y = 0; y < heighttimes; y++) {
					g.drawImage(Tile.dirtbackground, -4 + (Tile.tileSize * x), -6 + (Tile.tileSize * y), null);
				}
			}
		}catch (Exception e) {
			CrashDumping.DumpCrash(e);
			g.fillRect(0, 0, r.PIXEL.width, r.PIXEL.height);
		}
		//draw the title
		g2d.setColor(new Color(40, 40, 40, 200));
		g2d.fillRect(40, 48, 310, 52);
		g2d.setColor(new Color(20, 20, 20, 210));
		g2d.fillRect(40, 100, 310, 60);
		g2d.setColor(new Color(40, 40, 40, 200));
		g2d.fillRect(40, 160, 310, 53);
		g2d.setColor(Color.WHITE);
		g2d.drawString("Load A World", (r.PIXEL.width / 2) - (m.getStringWidth("Load A World", r.font1) / 2), 12);
		//back smlButton
		g2d.setColor(b1);
		g2d.drawImage(Tile.smlButton, 40, 20, r.smlButtonWidth, r.ButtonHeight, null);
		g2d.drawString("Back", (40 + (r.smlButtonWidth / 2)) - (m.getStringWidth("Back", r.font1) / 2), (y + 4) - (ySpace / 2));
		//delete smlButton
		g2d.setColor(b2);
		g2d.drawImage(Tile.smlButton, 250, 20, r.smlButtonWidth, r.ButtonHeight, null);
		g2d.drawString("Delete World", (250 + (r.smlButtonWidth / 2)) - (m.getStringWidth("Delete World", r.font1) / 2), (y + 4) - (ySpace / 2));
		
		for (int i = 0; i < worlds.size(); i++) {
			if (curselect > -1 && curselect == i) {
				if (worldSelected[i] == 1) {
					g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
					g2d.setColor(Color.WHITE);
					g2d.drawString(worlds.get(i),     x - 59, y);
					g2d.drawImage(img[i], x + 180, y - 10, 40, 40, null);
					g2d.drawString(builds[i], x - 59, y + 32);
					
					if (worlds.size() >= 2) {
						g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
						g2d.setColor(Color.LIGHT_GRAY);
						g2d.drawString(worlds.get(i + 1), x - 59, y + ySpace);
						g2d.drawImage(img[i + 1], x + 180, y + ySpace - 10, 40, 40, null);
						g2d.drawString(builds[i + 1], x - 59, y + ySpace + 32);
					}
					if (worlds.size() >= 3) {
						g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
						g2d.setColor(Color.LIGHT_GRAY);
						g2d.drawString(worlds.get(i + 2), x - 59, y + (ySpace * 2));
						g2d.drawImage(img[i + 2], x + 180, y + (ySpace * 2) - 10, 40, 40, null);
						g2d.drawString(builds[i + 2], x - 59, y + (ySpace * 2) + 30);
					}
				} else if (worldSelected[i] == 2) {

					g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
					g2d.setColor(Color.LIGHT_GRAY);
					g2d.drawString(worlds.get(i - 1), x - 59, y);
					g2d.drawImage(img[i - 1], x + 180, y - 10, 40, 40, null);
					g2d.drawString(builds[i - 1], x - 59, y + 32);

					g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
					g2d.setColor(Color.WHITE);
					g2d.drawString(worlds.get(i),     x - 59, y + ySpace);
					g2d.drawImage(img[i], x + 180, y + ySpace - 10, 40, 40, null);
					g2d.drawString(builds[i], x - 59, y + ySpace + 32);

					g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
					g2d.setColor(Color.LIGHT_GRAY);
					g2d.drawString(worlds.get(i + 1), x - 59, y + (ySpace * 2));
					g2d.drawImage(img[i + 1], x + 180, y + (ySpace * 2) - 10, 40, 40, null);
					g2d.drawString(builds[i + 1], x - 59, y + (ySpace * 2) + 30);
				} else if (worldSelected[i] == 3) {
					if (i - 2 >= 0) {
						g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
						g2d.setColor(Color.LIGHT_GRAY);
						g2d.drawString(worlds.get(i - 2), x - 59, y);
						g2d.drawImage(img[i - 2], x + 180, y - 10, 40, 40, null);
						g2d.drawString(builds[i - 2], x - 59, y + 32);
					}
					if (i - 1 >= 0) {
						g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
						g2d.setColor(Color.LIGHT_GRAY);
						if (worlds.size() >= 3) {
							g2d.drawString(worlds.get(i - 1), x - 59, y + ySpace);
							g2d.drawImage(img[i - 1], x + 180, y + ySpace - 10, 40, 40, null);
							g2d.drawString(builds[i - 1], x - 59, y + ySpace + 32);
						} else {
							g2d.drawString(worlds.get(i - 1), x - 59, y);
							g2d.drawImage(img[i - 1], x + 180, y - 10, 40, 40, null);
							g2d.drawString(builds[i - 1], x - 59, y + 32);
						}
					}
					g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
					g2d.setColor(Color.WHITE);
					if (worlds.size() >= 3) {
						g2d.drawString(worlds.get(i),     x - 59, y + (ySpace * 2));
						g2d.drawImage(img[i], x + 180, y + (ySpace * 2) - 10, 40, 40, null);
						g2d.drawString(builds[i], x - 59, y + (ySpace * 2) + 30);
					} else if (worlds.size() >= 2) {
						g2d.drawString(worlds.get(i),     x - 59, y + ySpace);
						g2d.drawImage(img[i], x + 180, y + ySpace - 10, 40, 40, null);
						g2d.drawString(builds[i], x - 59, y + (ySpace) + 32);
					} else if (worlds.size() >= 1) {
						g2d.drawString(worlds.get(i),     x - 59, y);
						g2d.drawImage(img[i], x + 180, y - 10, 40, 40, null);
						g2d.drawString(builds[i], x - 59, y + 32);
					}
				}
			} else if (curselect == -1 || curselect == -2) {
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.25f));
				g2d.setColor(Color.LIGHT_GRAY);
				
				g2d.drawString(worlds.get(0),     x - 59, y);
				g2d.drawImage(img[0], x + 180, y - 10, 40, 40, null);
				g2d.drawString(builds[0], x - 59, y + 32);
				
				if (worlds.size() >= 2) {
					g2d.drawString(worlds.get(1), x - 59, y + ySpace);
					g2d.drawImage(img[1], x + 180, y + ySpace - 10, 40, 40, null);
					g2d.drawString(builds[1], x - 59, y + ySpace + 32);
				}
				if (worlds.size() >= 3) {
					g2d.drawString(worlds.get(2), x - 59, y + (ySpace * 2));
					g2d.drawImage(img[2], x + 180, y + (ySpace * 2) - 10, 40, 40, null);
					g2d.drawString(builds[2], x - 59, y + (ySpace * 2) + 30);
				}
			}
		}
		//draw the scroll bar
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		g2d.setColor(new Color(0, 0, 0));
		g2d.fillRect(342, scrollbarstart + scrollbar, 8, 110);
	}
}