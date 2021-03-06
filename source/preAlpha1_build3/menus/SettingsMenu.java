package com.minecraft.client.menus;

import java.awt.Color;
import java.awt.Graphics;

import com.minecraft.client.IO.CrashDumping;
import com.minecraft.client.main.Minecraft;
import com.minecraft.client.math.Methods;
import com.minecraft.client.misc.References;
import com.minecraft.client.resources.Tile;

public class SettingsMenu {
	private int x = 0, y = 60, ySpace = 25, widthtimes = 0, heighttimes = 0;
	
	public static int curselect = 0;
	
	private Color[] b = new Color[7];
	
	public String[] buttons = new String[7];
	
	public static int lastMenu = 0;
	
	private References r;
	private Methods m;
	
	public SettingsMenu() {
		r = Minecraft.r;
		m = new Methods();
		
		x = (r.PIXEL.width / 2) - (r.lrgButtonWidth / 2);
		
		widthtimes = (r.PIXEL.width / Tile.tileSize) + 1;
		heighttimes = (r.PIXEL.height / Tile.tileSize) + 1;
		
		if (r.fullscreen)
			buttons[0] = "Go Windowed Mode";
		else
			buttons[0] = "Go Fullscreen Mode";
		if (r.autoSave)
			buttons[1] = "Dissable Auto Save Feature";
		else
			buttons[1] = "Enable Auto Save Feature";
		if (r.playMusic)
			buttons[2] = "Dissable Music";
		else
			buttons[2] = "Enable Music";
		if (r.playSoundEF)
			buttons[3] = "Dissable Sound Effects";
		else
			buttons[3] = "Enable Sound Effects";
		buttons[4] = "Alter FPS Cap (" +  Math.ceil(r.FPScap) + ")";
		buttons[5] = "Delete A World";
		buttons[6] = "Back";
		
		b[0] = new Color(0, 0, 0, 100); b[1] = new Color(0, 0, 0, 100); b[2] = new Color(0, 0, 0, 100);
		b[3] = new Color(0, 0, 0, 100); b[4] = new Color(0, 0, 0, 100); b[5] = new Color(0, 0, 0, 100);
		b[6] = new Color(0, 0, 0, 100);
	}
	
	public void tick() {
		for (int i = 0; i < buttons.length; i++) {
			if (curselect == i) {
				b[i] = r.selected;
			} else {
				b[i] = r.unselected;
			}
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
		g.drawString("Settings", (r.PIXEL.width / 2) 
			- (m.getStringWidth("Settings", r.font1) / 2), 40);
		//buttons
		for (int i = 0; i < 7; i++) {
			//draw the lrgButton images
			g.drawImage(Tile.lrgButton, x, y + (ySpace * i), r.lrgButtonWidth, r.ButtonHeight, null);
			//draw the test and set the color
			g.setColor(b[i]);
			g.drawString(buttons[i], (r.PIXEL.width / 2)
				- (m.getStringWidth(buttons[i],  r.font1) / 2),
				(y + (r.ButtonHeight + 1) - (m.getStringHeight(buttons[i], r.font1) / 2)) + (ySpace * i));
		}
	}
}