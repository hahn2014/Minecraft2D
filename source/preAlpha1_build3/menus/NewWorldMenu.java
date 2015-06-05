package com.minecraft.client.menus;

import java.awt.Color;
import java.awt.Graphics;

import com.minecraft.client.IO.CrashDumping;
import com.minecraft.client.IO.Logger;
import com.minecraft.client.IO.OptionPane;
import com.minecraft.client.main.Minecraft;
import com.minecraft.client.math.Methods;
import com.minecraft.client.misc.References;
import com.minecraft.client.resources.Tile;

public class NewWorldMenu {
	
	private int x = 0, y = 60, ySpace = 35, widthtimes = 0, heighttimes = 0;

	public static int curselect = 1;

	
	private Color textField = new Color(255, 255, 255);
	private Color b1 = new Color(0, 0, 0, 100);
	private Color b2 = new Color(0, 0, 0, 100);
	private Color constant = new Color(255, 255, 255);
	private Color cursor = new Color(255, 255, 255);
	
	private int R1 = 255, G1 = 255, B1 = 255;
	
	public static String finalName = "";
	private String button1 = "Create New World";
	private String button2 = "Cancel";
	
	private static References r;
	private Methods m;
	
	public NewWorldMenu() {
		r = Minecraft.r;
		m = new Methods();
		
		x = (r.PIXEL.width / 2) - (r.lrgButtonWidth / 2);

		widthtimes = (r.PIXEL.width / Tile.tileSize) + 1;
		heighttimes = (r.PIXEL.height / Tile.tileSize) + 1;
	}
	
	public void tick() {
		if (curselect == 1) { //on text field
			r.isTyping = true;
			textField = new Color(255, 255, 255, 255);
			b1 = new Color(0, 0, 0, 100);
			b2 = new Color(0, 0, 0, 100);
			//cleanse the text to remove any unwanted symbols and such
			String a = "";
			for (int i = 0; i < finalName.length(); i++) {
				if (Character.isAlphabetic(finalName.charAt(i)) || Character.isDigit(finalName.charAt(i)) || finalName.charAt(i) == ' ') {
					a += finalName.charAt(i);
				}
			}
			finalName = a;
		} else if (curselect == 2) { //create new world lrgButton
			r.isTyping = false;
			textField = new Color(100, 100, 100, 200);
			b1 = new Color(255, 255, 255);
			b2 = new Color(0, 0, 0, 100);
		} else if (curselect == 3) { //cancel lrgButton
			r.isTyping = false;
			textField = new Color(100, 100, 100, 200);
			b1 = new Color(0, 0, 0, 100);
			b2 = new Color(255, 255, 255);
		}
		
		if (r.isTyping) {
			if (R1 > 255) {
				R1--;
			} else if (R1 <= 255) {
				R1 = 255;
			}
			if (G1 > 255) {
				G1--;
			} else if (G1 <= 255) {
				G1 = 255;
			}
			if (B1 > 255) {
				B1--;
			} else if (B1 <= 255) {
				B1 = 255;
			}
			cursor = new Color(R1, G1, B1);
		} else {
			cursor = new Color(0, 0, 0);
		}
	}
	
	public void render(Graphics g) {
		g.setFont(r.font1);
		g.setColor(r.emptyBG);
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
		
		//title
		g.setColor(Color.WHITE);
		g.drawString("Create A New World", (r.PIXEL.width / 2) 
			- (m.getStringWidth("Create A New World", r.font1) / 2), 40);
		//text field
		g.setColor(textField);
		g.drawImage(Tile.textField, x, y, r.lrgButtonWidth, r.ButtonHeight, null);
		g.drawString(finalName,  x + 2,  y + 14);
		//draw the cursor
		g.setColor(cursor);
		g.drawString("|", x + (m.getStringWidth(finalName, r.font1) + 4), y + 13);
		g.setColor(constant);
		g.drawString("Creating in: " + finalName + ".dat", x, y + 30);
		g.drawString(finalName.length() + "/" + r.textFieldMaxLength + " letters", x, y + 45);
		//create new world lrgButton
		g.setColor(b1);
		g.drawImage(Tile.lrgButton, x, y + (ySpace * 2), r.lrgButtonWidth, r.ButtonHeight, null);
		g.drawString(button1, (r.PIXEL.width / 2)
			- (m.getStringWidth(button1, r.font1) / 2), y + (ySpace * 2) + 14);
		//cancel lrgButton
		g.setColor(b2);
		g.drawImage(Tile.lrgButton, x, y + (ySpace * 3), r.lrgButtonWidth, r.ButtonHeight, null);
		g.drawString(button2, (r.PIXEL.width / 2) - (m.getStringWidth(button2, r.font1) / 2),  y + (ySpace * 3) + 14);
	}
	
	@SuppressWarnings("static-access")
	public void createWorld() {
		if (finalName.length() >= 1) {
			r.isGenerating = true;
			r.hasStarted = true;
			r.MENU = 6;
			curselect = 1;
			for (int a = 0; a < Minecraft.level.block.length; a++) {
				for (int b = 0; b < Minecraft.level.block[0].length; b++) {
					Minecraft.level.block[a][b].id = Tile.blank;
				}
			}
			Minecraft.level.generateLevel();
			r.played++;
			Minecraft.sl.Save(finalName);
			r.loaded = false;
			Minecraft.frame.setTitle(r.NAME + " " + r.BUILD + " " + r.VERSION + " Playing on " + finalName + ".dat");
			r.curWorld = finalName;
			Logger.info(r.NAME + " " + r.BUILD + " " + r.VERSION + " Playing on " + finalName + ".dat");
		} else {
			try {
				Minecraft.op = new OptionPane("Woah There!", "You are trying to create a new world without a name! Please enter a valid name then try again.",
						"OK", 200, 80, Tile.stone, 16, 16, 1.0f, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.WHITE, true);
				Minecraft.op.updateVars(1);
			} catch(Exception e) {
				CrashDumping.DumpCrash(e);
			}
		}
	}
}