package com.minecraft.client.menus;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.minecraft.client.IO.CrashDumping;
import com.minecraft.client.main.Minecraft;
import com.minecraft.client.math.Methods;
import com.minecraft.client.misc.References;
import com.minecraft.client.resources.Tile;

public class PauseMenu {
	
	private int x = 0, y = 60, ySpace = 25, widthtimes = 0, heighttimes = 0;
	public static int curselect = 0;
	
	private Color[] b = new Color[4]; 
	private Color bg;
	
	public static String[] buttons = new String[4];
	
	private References r;
	private Methods m;

	public PauseMenu() {
		r = Minecraft.r;
		m = new Methods();
		
		x = (r.PIXEL.width / 2) - (r.lrgButtonWidth / 2);
		
		widthtimes = (r.PIXEL.width / Tile.tileSize) + 1;
		heighttimes = (r.PIXEL.height / Tile.tileSize) + 1;
		
		buttons[0] = "Resume Game";
		buttons[1] = "Settings";
		buttons[2] = "Save and Quit";
		buttons[3] = "Quit to dekstop and don't save";
		
		b[0] = new Color(0, 0, 0, 100); b[1] = new Color(0, 0, 0, 100);
		b[2] = new Color(0, 0, 0, 100);	b[3] = new Color(0, 0, 0, 100);
		
		bg = new Color(r.emptyBG.getRed(), r.emptyBG.getGreen(), r.emptyBG.getBlue(), 150);
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
		Graphics2D g2d = (Graphics2D)(g);
		g2d.setFont(r.font1);
		g2d.setColor(bg);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		try {
			for (int x = 0; x < widthtimes; x++) {
				for (int y = 0; y < heighttimes; y++) {
					g.drawImage(Tile.dirtbackground, -4 + (Tile.tileSize * x), -6 + (Tile.tileSize * y), null);
				}
			}
		}catch (Exception e) {
			CrashDumping.DumpCrash(e);
			g2d.fillRect(0, 0, r.PIXEL.width, r.PIXEL.height);
		}
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		//title
		g2d.setColor(Color.WHITE);
		g2d.drawString("Paused", (r.PIXEL.width / 2) 
			- (m.getStringWidth("Paused", r.font1) / 2), 40);
		//buttons
		for (int i = 0; i < buttons.length; i++) {
			//draw the lrgButton images
			g2d.drawImage(Tile.lrgButton, x, y + (ySpace * i), r.lrgButtonWidth, r.ButtonHeight, null);
			//draw the test and set the color
			g2d.setColor(b[i]);
			g2d.drawString(buttons[i], (r.PIXEL.width / 2)
				- (m.getStringWidth(buttons[i],  r.font1) / 2),
				(y + (r.ButtonHeight + 1) - (m.getStringHeight(buttons[i], r.font1) / 2)) + (ySpace * i));
		}
	}
}