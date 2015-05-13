package com.minecraft.client.menus;

import java.awt.Color;
import java.awt.Graphics;

import com.minecraft.client.IO.CrashDumping;
import com.minecraft.client.main.Minecraft;
import com.minecraft.client.math.Methods;
import com.minecraft.client.misc.References;
import com.minecraft.client.resources.Tile;

public class MainMenu {
	
	private int x = 0, y = 100, ySpace = 25, widthtimes = 0, heighttimes = 0;
	public static int curselect = 0;
	
	public static Color[] b = new Color[5];
	
	String[] buttons = new String[5];
	
	private References r;
	private Methods m;

	public MainMenu() {
		r = Minecraft.r;
		m = new Methods();
		
		x = (r.PIXEL.width / 2) - (r.lrgButtonWidth / 2);
		
		widthtimes = (r.PIXEL.width / r.imgWidth) + 1;
		heighttimes = (r.PIXEL.height / r.imgHeight) + 1;
		
		buttons[0] = "New World";buttons[1] = "Resume World";buttons[2] = "Load World";
		buttons[3] = "Settings";buttons[4] = "Exit Game";
		
		b[0] = new Color(0, 0, 0, 100);b[1] = new Color(255, 100, 100);b[2] = new Color(0, 0, 0, 100);
		b[3] = new Color(0, 0, 0, 100);b[4] = new Color(0, 0, 0, 100);
	}
	
	public void tick() {
		if (r.played > 0) {
			if (curselect == 1) {
				b[1] = r.selected;
			} else {
				b[1] = r.unselected;
			}
		} else {
			if (curselect == 1) {
				b[1] = r.disabledSe;
			} else {
				b[1] = r.disabledUn;
			}
		}
		
		for (int i = 0; i < 5; i++) {
			if (curselect == i && i != 1) {
				b[i] = r.selected;
			} else if (curselect != i && i != 1) {
				b[i] = r.unselected;
			}
		}
	}
	
	public void render(Graphics g) {
		g.setFont(r.font3);
		g.setColor(r.emptyBG);
		try {
			for (int x = 0; x < widthtimes; x++) {
				for (int y = 0; y < heighttimes; y++) {
					g.drawImage(Tile.dirt, -4 + (r.imgWidth * x), -6 + (r.imgHeight * y), r.imgWidth, r.imgHeight, null);
				}
			}
		} catch (Exception e) {
			CrashDumping.DumpCrash(e);
			g.fillRect(0, 0, r.PIXEL.width, r.PIXEL.height);
		}
		g.setColor(Color.WHITE);
		//title
		g.drawImage(Tile.logo, (r.PIXEL.width / 2) - (Tile.logo.getWidth() / 2), 5, 279, 50, null);
		//version
		g.drawString(r.BUILD + " " + r.VERSION, 4, r.PIXEL.height - (m.getStringHeight(r.BUILD + " " + r.VERSION, r.font3) / 2));
		//copyright
		g.drawString("Copyright Bryce Hahn. Do not distribute!", (r.PIXEL.width - 6) - (m.getStringWidth("Copyright Bryce Hahn. Do not distribute!", r.font3)),
				r.PIXEL.height - (m.getStringHeight(r.BUILD + " " + r.VERSION, r.font3) / 2));
		//move frame
		g.setFont(r.font2);
		g.drawString("Press F12 to start/stop", 4, (r.PIXEL.height - 20) - (m.getStringHeight(r.BUILD + " " + r.VERSION, r.font3) / 2));
		g.drawString("moving screen", 20, (r.PIXEL.height - 12) - (m.getStringHeight(r.BUILD + " " + r.VERSION, r.font3) / 2));
		g.setFont(r.font1);
		//buttons
		for (int i = 0; i < 5; i++) {
			//draw the lrgButton images
			g.drawImage(Tile.lrgButton, x, y + (ySpace * i), r.lrgButtonWidth, r.ButtonHeight, null);
			//draw the text and set the color
			g.setColor(b[i]);
			g.drawString(buttons[i], (r.PIXEL.width / 2) 
				- (m.getStringWidth(buttons[i], r.font1) / 2),
				(y + (r.ButtonHeight + 1) - (m.getStringHeight(buttons[i], r.font1) / 2))  + (ySpace * i));
		}
	}
}