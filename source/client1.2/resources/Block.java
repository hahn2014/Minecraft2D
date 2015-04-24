package com.minecraft.client.resources;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.minecraft.client.main.Minecraft;
import com.minecraft.client.misc.References;

public class Block extends Rectangle{
	private static final long serialVersionUID = -473700971479061726L;
	public BufferedImage id;
	private int ID;
	private References r;

	public Block(Rectangle size, BufferedImage id, int ID) {
		setBounds(size);
		this.id = id;
		this.ID = ID;
		this.r = Minecraft.r;
	}
	
	public int getID() {
		return ID;
	}
	
	public void setID(int id) {
		ID = id;
	}
	
	public void render(Graphics g) {
		if (id != Tile.empty) {
			g.drawImage(id, x - (int) (r.sx), y - (int) (r.sy), x + width - (int) (r.sx), y + height - (int) (r.sy), null);
		}
	}
}