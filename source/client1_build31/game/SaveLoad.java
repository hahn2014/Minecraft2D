package com.minecraft.client.game;

import java.awt.Color;
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
import com.minecraft.client.IO.Logger;
import com.minecraft.client.IO.OptionPane;
import com.minecraft.client.main.Minecraft;
import com.minecraft.client.misc.References;
import com.minecraft.client.resources.NewComputer;
import com.minecraft.client.resources.Tile;

public class SaveLoad {
	
	private static References r;
	private static String splitted;
	
	@SuppressWarnings("static-access")
	public SaveLoad() {
		r = Minecraft.getMinecraft().r;
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
	
	public static void Save(String name) {
		int startTime = (int)(System.currentTimeMillis()), endTime = 0;
		File file = new File(NewComputer.savesDirectory + "\\" + name + ".dat");
		try {
			//now save the world info
			BufferedWriter pw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF8"));
			pw.write("'" + r.BUILDINITIALS + r.VERSION);
			pw.newLine();
			pw.write("~" + r.sx + ",");
			pw.newLine();
			pw.write("`" + r.sy + ";"); //player pos
			//pw.newLine();
			//pw.write("*" + Minecraft.getMinecraft().player.pos.x + "," + Minecraft.getMinecraft().player.pos.y + ";");
			pw.newLine();
			pw.write("!" + (int)(r.direction) + ";"); //facing direction
			pw.newLine();
			pw.write(":");
			for (int x = 0; x < Minecraft.level.block.length; x++) {
				for (int y = 0; y < Minecraft.level.block[0].length; y++) {
					pw.write(x + "," + y + "," + Minecraft.level.block[x][y].id + ";"); //world blocks
				}
			}
			pw.close();
			endTime = (int)(System.currentTimeMillis());
			System.out.println("Time it took to save world: " + (endTime - startTime));
			r.lastPlayedWorld = name;
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "ERROR!\n" + ex, "System.ERROR(4) No Such World Exists", JOptionPane.NO_OPTION);
			CrashDumping.DumpCrash(ex);
		}
	}
	
	@SuppressWarnings("unused")
	public static void Load(String name) {
		int startTime = (int)(System.currentTimeMillis()), endTime = 0;
		File file = new File(NewComputer.savesDirectory + "\\" + name);
		boolean continues = true;
		try {
			//try and read from the world file
			BufferedReader br = new BufferedReader(new FileReader(file));
			String words;
			while ((words = br.readLine()) != null) {
				if 		  (words.startsWith("'")) { //build and version
					continues = checkWorldVersion(words.substring(1, words.length()));
				} else if (words.startsWith("~")) { //sx
					splitted = words.substring(1, words.length() - 1);
					r.sx = Double.parseDouble(splitted);
				} else if (words.startsWith("`")) { //sy
					splitted = words.substring(1, words.length() - 1);
					r.sy = Double.parseDouble(splitted);
				} else if (words.startsWith("*")) { //player pos
					splitted = words.substring(1, words.length() - 1);
					double x = 0.0, y = 0.0;
					String[] tmp = splitted.split(",");
					x = Double.parseDouble(tmp[0]);
					y = Double.parseDouble(tmp[1]);
				//	Minecraft.getMinecraft().player.pos = new Point((int)x, (int)y);
				} else if (words.startsWith("!")) { //facing
					splitted = words.substring(1, words.length() - 1);
					r.direction = Double.parseDouble(splitted);
				} else if (words.startsWith(":")) { //world blocks
					splitted = words.substring(1, words.length() - 1);
					String[] coords = splitted.split(";");
					for (int i = 0; i < coords.length; i++) {
						String[] end = coords[i].split(",");
						//Minecraft.level.block[Integer.parseInt(end[0])][Integer.parseInt(end[1])].setID(Integer.parseInt(end[2]));
						//Minecraft.level.block[Integer.parseInt(end[0])][Integer.parseInt(end[1])].id = getID(Integer.parseInt(end[2]));
					}
				}
			}
			br.close();
			endTime = (int)(System.currentTimeMillis());
			if (continues) {
				r.MENU = 6;
				Logger.info("Time it took to load world: " + (endTime - startTime));
			} else {
				Logger.error("Your client is too updated to load world");
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "ERROR!\n" + ex, "System.ERROR(4) No Such World Exists", JOptionPane.NO_OPTION);
			CrashDumping.DumpCrash(ex);
		}
	}
	
	private static boolean checkWorldVersion(String loadversion) {
		int versionstart = 0;
		String version = ""; String build = "";
		for (int a = 0; a < loadversion.length(); a++) {
			if (Character.isDigit(loadversion.charAt(a))) {
				versionstart = a;
				break;
			}
		}
		build = loadversion.substring(0, versionstart);
		version = loadversion.substring(versionstart, loadversion.length());
		
		int[] numbers = getVersionToArray(version);
		
		if (getFullBuildName(build) != r.BUILD) {
			try {
				Minecraft.op = new OptionPane("Woah There!", "Looks like you're trying to load a world that was last played on a different build of the clients! " +
						"World build: " + getFullBuildName(build) + " - Client build: " + r.BUILD,  "OK", 200, 100,
						Tile.stone, 16, 16, 1.0f, Color.BLACK, Color.BLACK, Color.WHITE, true);
				Minecraft.op.updateVars(1);
				return false;
			} catch (Exception e) {}
		}
		if (numbers[0] != getVersionToArray(r.VERSION)[0] || numbers[1] != getVersionToArray(r.VERSION)[1] || numbers[2] != getVersionToArray(r.VERSION)[2]) {
			try {
				Minecraft.op = new OptionPane("Woah There!", "Looks like you're trying to load a world that was last played on a different version of the clients! " +
						"World version: " + version + " - Client version: " + r.VERSION,  "OK", 200, 60,
						Tile.stone, 16, 16, 1.0f, Color.BLACK, Color.BLACK, Color.WHITE, true);
				Minecraft.op.updateVars(1);
				return false;
			} catch (Exception e) {}
		}
		return true;
	}
	
	private static int[] getVersionToArray(String version) {
		String numbs[] = version.split("\\.");
		int[] numbers = new int[numbs.length];
		for (int i = 0; i < numbers.length; i++) {
			numbers[i] = Integer.parseInt(numbs[i].trim());
		}
		return numbers;
	}
	
	public static String getFullBuildName(String shortname) {
		if (shortname.equals("pA")) {
			return "Pre-Alpha BUILD";
		} else if (shortname.equals("A")) {
			return "Alpha BUILD";
		} else if (shortname.equals("pB")) {
			return "Pre-Beta BUILD";
		} else if (shortname.equals("B")) {
			return "Beta BUILD";
		} else if (shortname.equals("pR")) {
			return "Pre-Release BUILD";
		} else if (shortname.equals("R")) {
			return "Release BUILD";
		} else {
			return "unknown";
		}
	}
	
	public static boolean checkIfExists(String worldname) {
		return new File(NewComputer.savesDirectory + worldname + ".dat").exists();
	}
}