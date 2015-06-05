package com.minecraft.client.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.minecraft.client.main.Minecraft;
import com.minecraft.client.resources.Block;
import com.minecraft.client.resources.Tile;

public class Level {
	
	
	public Block[][] block = new Block[20][80];

	public Level() {
		for (int x = 0; x < block.length; x++) {
			for (int y = 0; y < block[0].length; y++) {
				block[x][y] = new Block(new Rectangle(x * Tile.tileSize,
					y * Tile.tileSize, Tile.tileSize, Tile.tileSize), Tile.blank);
			}
		}
	}
	
	@SuppressWarnings("static-access")
	public void generateLevel() {
		for (int x = 0; x < block.length; x++) {
			for (int y = 0; y < block[0].length; y++) {
				if (x == 0 || y == 0 || x == block.length - 1 || y == block[0].length - 1) {
					block[x][y].id = Tile.bedrock;
				}
			}
		}
		//all generation has been completed, take picture
		Minecraft.getMinecraft().player.takeScreenshot();
	}
	
	public void tick() {
		
	}

	public void render(Graphics g) {
		g.setColor(Color.RED);
		for (int x = 0; x < block.length; x++) {
			for (int y = 0; y < block[0].length; y++) {
				block[x][y].render(g);
			}
		}
	}
}