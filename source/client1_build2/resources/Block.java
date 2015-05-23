package com.minecraft.client.resources;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.minecraft.client.main.Minecraft;
import com.minecraft.client.misc.References;

public class Block extends Rectangle{
	private static final long serialVersionUID = -473700971479061726L;
	//public BufferedImage id;
	public int[] id;
	
	private References r;
	
	public Block(Rectangle size, int[] id) {
		r = Minecraft.getReferences();
		setBounds(size);
		this.id = id;
	}
	
	@SuppressWarnings("static-access")
	public void render(Graphics g) {
		if (id != Tile.blank) {
			if (!Minecraft.getMinecraft().r.development) {
				g.setColor(Color.RED);
				g.fillRect(x - (int)r.sx, y - (int)r.sy, x + width, y + height);
				g.setColor(Color.BLUE);
				g.drawRect(x - (int)r.sx, y - (int)r.sy, x + width, y + height);
			} else {
				g.drawImage(Tile.texture, x - (int)r.sx, y - (int)r.sy, x + width - (int)r.sx,
						y + height - (int)r.sy, id[0] * Tile.tileSize, id[1] * Tile.tileSize,
						id[0] * Tile.tileSize + Tile.tileSize, id[1] * Tile.tileSize + Tile.tileSize, null);
			}
		}
	}
}