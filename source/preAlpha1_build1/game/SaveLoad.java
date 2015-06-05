package com.minecraft.client.game;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import com.minecraft.client.IO.CrashDumping;
import com.minecraft.client.main.Minecraft;
import com.minecraft.client.misc.References;
import com.minecraft.client.resources.NewComputer;
import com.minecraft.client.resources.Tile;

public class SaveLoad {
	
	private static References r;
	private static String splitted;
	
	public SaveLoad() {
		r = Minecraft.r;
	}

	public static void getScreenShot(String name) {
		//bring the frame to the front of the screen
		File file = new File(NewComputer.savesDirectory + "\\" + name);
		//first things first, take that screenshot!
		try {
			BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			ImageIO.write(image, "png", new File(file + ".png"));
		} catch(Exception e) {
			CrashDumping.DumpCrash(e);
		}
	}
	
	@SuppressWarnings("static-access")
	public static void Save(String name) {
		int startTime = (int)(System.currentTimeMillis());
		int endTime = 0;
		File file = new File(NewComputer.savesDirectory + "\\" + name + ".dat");
		try {
			//now save the world info
			BufferedWriter pw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF8"));
			pw.write("~" + r.sx + ",");
			pw.newLine();
			pw.write("`" + r.sy + ";"); //player pos
			pw.newLine();
			pw.write("!" + (int)(r.direction) + ";"); //facing direction
			pw.newLine();
			pw.write(":");
			for (int x = 0; x < Minecraft.wr.block.length; x++) {
				for (int y = 0; y < Minecraft.wr.block[0].length; y++) {
					pw.write(x + "," + y + "," + Minecraft.wr.block[x][y].getID() + ";"); //world blocks
				}
			}
			pw.close();
			endTime = (int)(System.currentTimeMillis());
			System.out.println("Time it took: " + (endTime - startTime));
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "ERROR!\n" + ex, "System.ERROR(4) No Such World Exists", JOptionPane.NO_OPTION);
			CrashDumping.DumpCrash(ex);
		}
	}
	
	@SuppressWarnings({ "unused", "static-access" })
	public static void Load(String name) {
		int startTime = (int)(System.currentTimeMillis()), endTime = 0;
		File file = new File(NewComputer.savesDirectory + "\\" + name);
		try {
			//try and read from the world file
			BufferedReader br = new BufferedReader(new FileReader(file));
			String words;
			while ((words = br.readLine()) != null) {
				if        (words.startsWith("~")) { //x
					splitted = words.substring(1, words.length() - 1);
					r.sx = Double.parseDouble(splitted);
				} else if (words.startsWith("`")) { //y
					splitted = words.substring(1, words.length() - 1);
					r.sy = Double.parseDouble(splitted);
				} else if (words.startsWith("!")) { //facing
					splitted = words.substring(1, words.length() - 1);
					r.direction = Double.parseDouble(splitted);
				} else if (words.startsWith(":")) { //world blocks
					splitted = words.substring(1, words.length() - 1);
					String[] coords = splitted.split(";");
					for (int i = 0; i < coords.length; i++) {
						String[] end = coords[i].split(",");
						Minecraft.wr.block[Integer.parseInt(end[0])][Integer.parseInt(end[1])].setID(Integer.parseInt(end[2]));
						Minecraft.wr.block[Integer.parseInt(end[0])][Integer.parseInt(end[1])].id = getID(Integer.parseInt(end[2]));
					}
				}
			}
			br.close();
			//get world blocks
			//get date time
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "ERROR!\n" + ex, "System.ERROR(4) No Such World Exists", JOptionPane.NO_OPTION);
			CrashDumping.DumpCrash(ex);
		}
	}
	
	private static BufferedImage getID(int id) {
		if (id == 0)
			return Tile.blank;
		else if (id == 1)
			return Tile.dirt;
		else if (id == 2)
			return Tile.grass;
		else if (id == 3)
			return Tile.snowgrass;
		else if (id == 4)
			return Tile.stone;
		else if (id == 5) 
			return Tile.cobblestone;
		else if (id == 6)
			return Tile.bedrock;
		else if (id == 7)
			return Tile.log;
		else if (id == 8)
			return Tile.plank;
		else if (id == 9)
			return Tile.leaves;
		else 
			return Tile.empty;
	}
}