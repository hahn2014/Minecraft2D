package com.minecraft.client.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Timer;
import java.util.TimerTask;

import com.minecraft.client.IO.CrashDumping;
import com.minecraft.client.main.Minecraft;
import com.minecraft.client.math.DoubleRectangle;
import com.minecraft.client.menus.LoadWorldMenu;
import com.minecraft.client.menus.NewWorldMenu;
import com.minecraft.client.misc.References;
import com.minecraft.client.resources.Tile;

public class Player extends DoubleRectangle {
	public double 		fallingSpeed = 1.3;
	public double 		movingSpeed = 1;
	public int 			jumpingHeight = 20;
	public int 			jumpingCount = 0;
	public double 		curHealth = 20.0;
	public final double	finalHealth = 20.0;
	public int 			curHunger = 40;
	public final int 	fianlHunger = 40;
	public double 		jumpingSpeed = 2;
	public int 			animation = 0;
	public int 			animationFrame = 0;
	public int			animationTime = 40;
	public Point 		pos;
	private boolean 	isJumping;
	public static Timer timer;
	
	private static References r;
	
	public Player(int width, int height) {
		r = Minecraft.r;
		setBounds((r.PIXEL.width / 2), (r.PIXEL.height / 2) - (height / 2), width, height);
		r.direction = movingSpeed;
		timer = new Timer();
		pos = new Point();
		normalFallSpeed();
	}
	
	public void normalFallSpeed() {
		timer.schedule(new TimerTask() {
			public void run() {
				fallingSpeed = 1.3;
			}
		}, 8000);
	}
	
	public void recoverHealth() {
		if (curHealth < finalHealth) {
			timer.schedule(new TimerTask() {
				public void run() {
					if (curHealth < finalHealth) {
						try {
							curHealth = curHealth + 0.5;
							Thread.sleep(300);
						} catch (Exception e) {
						}
					}
				}
			}, 500);
		}
	}
	
	public static void autoSave() {
		if (r.autoSave && r.hasStarted) {
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					try {
						int seconds = 60, multiplyer = 100;
						@SuppressWarnings("unused")
						long starttime = System.currentTimeMillis(), endtime;
						//show message in console
						//console.showMessage("AutoSaving in progress...", 10000);
						if (r.loaded) { //in game
							String tmp = LoadWorldMenu.lastWorld;
							tmp = tmp.replace(".dat", "");
							SaveLoad.getScreenShot(tmp);
							//SaveLoad.Save(LoadWorldMenu.lastWorld);
						} else { //creating new world
							SaveLoad.getScreenShot(NewWorldMenu.finalName);
							//SaveLoad.Save(NewWorldMenu.finalname);
						}
						endtime = System.currentTimeMillis();
						//console.showMessage("AutoSave completed in " + (endtime - starttime) + " seconds", 10000);
						Thread.sleep(seconds * multiplyer); //wait one minute
					} catch (Exception e) {
						e.printStackTrace();
						CrashDumping.DumpCrash(e);
					}
				}
			}, 60000);
		}
	}
	
	public void tick() {
		pos.setLocation(x, y);
		if (curHealth < finalHealth) {
			recoverHealth();
		}
		if (r.autoSave && r.hasStarted) {
			autoSave();
		}
		if (!r.isJumping && !isCollindingWithBlock(new Point((int) (x + 2), (int) (y + height - 2)), new Point((int) (x + width), (int) (y + height)))) {
			if (!r.isGenerating) {
				y += fallingSpeed;
				r.sy += fallingSpeed;
			} else {
				y += fallingSpeed * 7;
				r.sy += fallingSpeed * 7;
			}
		} else {
			if (r.isJumping) {
				this.isJumping = true;
			}
			r.isGenerating = false;
			r.autoSave = true;
			WorldRender.percent = 0;
		}
		if (r.isMoving && !r.inventoryOpen && !r.settingsOpen && !r.chestOpen && !r.craftingOpen /** && !command.typing**/) {
			boolean canMove = false;
			if (r.direction == movingSpeed) {
				canMove = isCollindingWithBlock(new Point((int) (x + (width) - 1), (int) (y)), new Point((int) (x + width) - 1, (int) (y + (height - 2))));
			} else if (r.direction == -movingSpeed) {
				canMove = isCollindingWithBlock(new Point((int) (x - 2), (int) (y)), new Point((int) (x - 2), (int) (y + (height - 2))));
			}
			if (animationFrame >= animationTime) {
				if (animation > 1) {
					animation = 1;
				} else {
					animation += 1;
				}
				animationFrame = 0;
			} else {
				animationFrame += 1;
			}
			if (!canMove) {
				x += r.direction;
				r.sx += r.direction;
			}
		} else {
			animation = 0;
		}
		if (isJumping) {
			if (!isCollindingWithBlock(new Point((int) (x + 2), (int) (y - 1)), new Point((int) (x + width - 2), (int) (y)))) {
				if (jumpingCount >= jumpingHeight) {
					isJumping = false;
					jumpingCount = 0;
				} else {
					y -= jumpingSpeed;
					r.sy -= jumpingSpeed;
					jumpingCount += 1;
				}
			} else {
				isJumping = false;
				jumpingCount = 0;
			}
		}
	}
	
	@SuppressWarnings("static-access")
	public boolean isCollindingWithBlock(Point pt1, Point pt2) {
		for (int x = (int) (this.x / Tile.tileSize); x < (int) (this.x / Tile.tileSize + 2); x++) {
			for (int y = (int) (this.y / Tile.tileSize); y < (int) (this.y / Tile.tileSize + 1); y++) {
				if (x >= 0 && y >= 0 && x < WorldRender.block.length && y < WorldRender.block[0].length) {
					if (WorldRender.block[x][y].id != Tile.blank) {
						if (WorldRender.block[x][y].contains(pt1) || WorldRender.block[x][y].contains(pt2)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public void render(Graphics g) {
		// draw the health on the screen
		g.setColor(Color.BLACK);
		g.drawString("Health: " + (int) (curHealth), 1, 10);
		if (r.direction == movingSpeed) {
			g.drawRect((int)r.sx, (int)r.sy, (int)((x + width) - r.sx), (int)((y + height) - r.sy));
		} else if (r.direction == -movingSpeed) {
			g.drawRect((int)r.sx, (int)r.sy, (int)((x + width) - r.sx), (int)((y + height) - r.sy));
		}
	}
}