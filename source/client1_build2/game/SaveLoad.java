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
import com.minecraft.client.IO.Logger;
import com.minecraft.client.main.Minecraft;
import com.minecraft.client.misc.References;
import com.minecraft.client.resources.NewComputer;

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
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "ERROR!\n" + ex, "System.ERROR(4) No Such World Exists", JOptionPane.NO_OPTION);
			CrashDumping.DumpCrash(ex);
		}
	}
	
	@SuppressWarnings("unused")
	public static void Load(String name) {
		int startTime = (int)(System.currentTimeMillis()), endTime = 0;
		File file = new File(NewComputer.savesDirectory + "\\" + name);
		try {
			//try and read from the world file
			BufferedReader br = new BufferedReader(new FileReader(file));
			String words;
			while ((words = br.readLine()) != null) {
				if        (words.startsWith("~")) { //sx
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
			r.MENU = 6;
			Logger.info("Time it took to load world: " + (endTime - startTime));
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "ERROR!\n" + ex, "System.ERROR(4) No Such World Exists", JOptionPane.NO_OPTION);
			CrashDumping.DumpCrash(ex);
		}
	}
}