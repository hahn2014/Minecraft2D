package com.minecraft.client.game;

import java.awt.Graphics;
import java.awt.Point;
import java.util.concurrent.Executors;

import com.minecraft.client.main.Minecraft;
import com.minecraft.client.math.DoubleRectangle;
import com.minecraft.client.misc.References;
import com.minecraft.client.resources.Tile;

public class Player extends DoubleRectangle {
	private References r;
	private Level level;
	//moving
	public double movingSpeed = 0.6;
	private boolean canMove = false;
	//jumping
	private double jumpingSpeed = 0.5;
	private int maxJumpHeight = Tile.tileSize * 2 + 10, jumpCount = 0;
	private boolean jumping = false;

	public Player(int width, int height) {
		r = Minecraft.getReferences();
		level = Minecraft.getLevel();
		setBounds((r.PIXEL.width / 2) - (width / 2),
			(r.PIXEL.height / 2)- (height / 2), width, height);
	}
	
	public void takeScreenshot() {
		//this will take a screenshot and save it as the level image
	}
	
	public void tick() {
		//fall checking
		if (!jumping && !isCollidingWithBlock(new Point((int)x + 3, (int)(y + height)),
				new Point((int)(x + width - 3), (int)(y + height)))) {
			//acceleration
			try {
				if (r.fallingSpeed < r.maxFallSpeed) {
					Executors.newSingleThreadExecutor().execute(new Runnable() {
						@Override
						public void run() {
							try {
								Thread.sleep(100);
								r.fallingSpeed += r.fallingAcceleration;
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					});
				}
			} catch (Exception e) {}
			//make it fall
			y += r.fallingSpeed;
			r.sy += r.fallingSpeed;
		} else { //not falling
			r.fallingSpeed = 1.3;
			//jump checking
			if (r.isJumping) {
				jumping = true;
			}
		}
		//move checking
		if (r.isMoving) {
			if (r.direction == movingSpeed) { // right side collision checking
				canMove = isCollidingWithBlock(new Point((int)(x + width - 2), (int)y),
						new Point((int)(x + width - 1), (int)(y + (height - 2))));
			} else if (r.direction == -movingSpeed) { //left side collision checking
				canMove = isCollidingWithBlock(new Point((int)x - 2, (int)y),
						new Point((int)x - 1, (int)(y + (height - 2))));
			}
			
			if (!canMove) {
				x += r.direction;
				r.sx += r.direction;
			}
		}
		//jump checking
		if (jumping) {
			if (!isCollidingWithBlock(new Point((int)x + 3, (int)y),
					new Point((int)(x + width - 2), (int)y))) {
				if (jumpCount >= maxJumpHeight) {
					jumping = false;
					jumpCount = 0;
				} else {
					y -= jumpingSpeed;
					r.sy -= jumpingSpeed;
					jumpCount++;
				}
			} else {
				jumping = false;
				jumpCount = 0;
			}
		}
	}
	
	public boolean isCollidingWithBlock(Point p1, Point p2) {
		for (int x = (int)(this.x / Tile.tileSize); x < (int)(this.x / Tile.tileSize + 3); x++) {
			for (int y = (int)(this.y / Tile.tileSize); y < (int)(this.y / Tile.tileSize + 3); y++) {
				if (x >= 0 && y >= 0 && x < Minecraft.getLevel().block.length
						&& y < Minecraft.getLevel().block[0].length) {
					if (level.block[x][y].id != Tile.blank) {
						if (level.block[x][y].contains(p1) || level.block[x][y].contains(p2)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public void render(Graphics g) {
		g.drawImage(Tile.texture, (int)(x - r.sx), (int)(y - r.sy), (int)(x + width - r.sx),
				(int)(y + height - r.sy), Tile.player[0] * Tile.tileSize,
				Tile.player[1] * Tile.tileSize, Tile.player[0] * Tile.tileSize
				+ (int)width, Tile.player[1] * Tile.tileSize + (int)height, null);
	}
}