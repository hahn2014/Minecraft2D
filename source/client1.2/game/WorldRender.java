package com.minecraft.client.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;

import com.minecraft.client.IO.CrashDumping;
import com.minecraft.client.main.Minecraft;
import com.minecraft.client.misc.References;
import com.minecraft.client.resources.Block;
import com.minecraft.client.resources.Blocks;
import com.minecraft.client.resources.Tile;

public class WorldRender {
	public static int 		percent = 0;
	private static int 		worldW = 100, worldH = 100;
	public static int 			worldInt = 100;
	public static Block[][] block = new Block[worldW][worldH];
	
	private References r;

	public WorldRender() {
		this.r = Minecraft.r;
		for (int x = 0; x < block.length; x++) {
			for (int y = 0; y < block[0].length; y++) {
				block[x][y] = new Block(new Rectangle(x, y, r.blocksize, r.blocksize), Tile.blank, 0);
				block[x][y].setID(0); //blank
			}
		}
	}
	
	public void generateLevel() {
		r.isGenerating = true;
		r.autoSave = false;
		// generate the mountains and the terrain randomly
		for (int y = 0; y < block.length; y++) {
			for (int x = 0; x < block[0].length; x++) {
//				block[x][y].id = Tile.stone;
//				block[x][y].setID(4);
//				block[x][y].setLocation(x, y);
				if (y > worldH / 2) {
					if (new Random().nextInt(worldInt) > 90) {
						try {
							if (block[x - 1][y - 1].id == Tile.dirt) {
								block[x][y].id = Tile.dirt;
								block[x][y].setID(1);// dirt
							}
						} catch (Exception ex) {
							CrashDumping.DumpCrash(ex);
						}
					}
					if (new Random().nextInt(worldInt) > 30) {
						try {
							if (block[x + 1][y - 1].id == Tile.dirt) {
								block[x][y].id = Tile.dirt;
								block[x][y].setID(1);// dirt
							}
						} catch (Exception ex) {
							CrashDumping.DumpCrash(ex);
						}
					}
					try {
						if (block[x][y - 1].id == Tile.dirt) {
							block[x][y].id = Tile.dirt;
							block[x][y].setID(1); //dirt
						}
					} catch (Exception ex) {
						ex.printStackTrace();
						CrashDumping.DumpCrash(ex);
					}
					if (new Random().nextInt(worldInt) < 50) {
						block[x][y].id = Tile.dirt;
						block[x][y].setID(1);//dirt
					}
				}
			}
		}
		 //generate trees!
		for (int y = 0; y < block.length; y++) {
			for (int x = 0; x < block[0].length; x++) {
				try {
					if (block[x][y + 1].id == Tile.dirt
							&& block[x][y].id == Tile.blank
							&& block[x + 1][y].id != Tile.log
							&& block[x - 1][y].id != Tile.log
							&& block[x - 1][y].id != Tile.dirt
							&& block[x + 1][y].id != Tile.dirt
							&& block[x + 1][y].id != Tile.grass
							&& block[x - 1][y].id != Tile.grass) {
						if (new Random().nextInt(worldInt) <= 10) {
							for (int i = 0; i < new Random().nextInt(9) + 6; i++) {
								block[x][y - i].id = Tile.log;
								block[x][y - i].setID(7); //log
								for (int j = 0; j < i + 2; j++) {
									if (j >= 4) {
										block[x + 1][y - j].id = Tile.leaves;
										block[x + 1][y - j].setID(9); //leaves
										block[x - 1][y - j].id = Tile.leaves;
										block[x - 1][y - j].setID(9); //leaves
									}
									if (block[x - 1][y].id == Tile.blank
											&& block[x - 1][y - 1].id == Tile.log) {
										block[x - 1][y].id = Tile.leaves;
										block[x - 1][y].setID(9); //leaves
									}
								}
							}
						}
					}
				} catch (Exception e) {
					CrashDumping.DumpCrash(e);
				}
			}
		}
		 //generate grass onto of all top dirt blocks
		for (int y = 0; y < block.length; y++) {
			for (int x = 0; x < block[0].length; x++) {
				if (block[x][y].id == Tile.dirt && block[x][y - 1].id == Tile.blank) {
					block[x][y].id = Tile.grass;
					block[x][y].setID(2); //grass
				}
			}
		}
		 //bedrock bottom
		for (int y = 0; y < block.length; y++) {
			for (int x = 0; x < block[0].length; x++) {
				if (x == 0 || y == 0 || x == block[0].length - 1 || y == block[0].length - 1) {
					block[x][y].id = Tile.bedrock;
					block[x][y].setID(6); //bedrock
				}
			}
		}
	}
	
	public void building(int camX, int camY, int renderWidth, int renderHeight) {
		if (r.isMouseLeft || r.isMouseRight) {
			// delete and place blocks
			for (int x = (camX / Tile.tileSize); x < (camX / Tile.tileSize) + renderWidth; x++) {
				for (int y = (camY / Tile.tileSize); y < (camY / Tile.tileSize) + renderHeight; y++) {
					if (x >= 0 && y >= 0 && x < worldW && y < worldH) {
						if (block[x][y].contains(new Point((r.mouse.x) / (int) (r.pixelSize) + (int) (r.sx), (r.mouse.y) / (int) (r.pixelSize) + (int) (r.sy)))) {
							if (r.isMouseLeft && !r.chestOpen && !r.inventoryOpen && !r.craftingOpen && block[x][y].id != Tile.bedrock && block[x][y].id != Tile.blank) {
								// delete blocks
								block[x][y].id = Tile.blank;
								block[x][y].setID(0); //blank
							} else if (r.isMouseRight) {
								block[x][y].id = Tile.grass;
								block[x][y].setID(2); //grass
							}
							if (r.isMouseRight && block[x][y].id != Tile.blank && !r.inventoryOpen && !r.chestOpen && !(r.MENU == 5) && !r.craftingOpen) {
//								if (block[x][y].id == Tile.chest) {
//									r.chestOpen = true;
//								}
//								if (block[x][y].id == Tile.crafting) {
//									r.craftingOpen = true;
//								}
							}
							if (r.isMouseRight && block[x][y].id == Tile.blank && (block[x - 1][y].id != Tile.blank) && (block[x + 1][y].id != Tile.blank) && !r.inventoryOpen && !r.chestOpen && !(r.MENU == 5) && !r.craftingOpen) {
//								if (block[x][y].id == Tile.chest) {
//									Chest.isOpen = true;
//								}
							}
							break;
						}
					}
				}
			}
		}
	}
	
	public void tick(int camX, int camY, int renderWidth, int renderHeight) {
		building(camX, camY, renderWidth, renderHeight);
	}
	
	public void render(Graphics g, int camX, int camY, int renderWidth, int renderHeight) {
		for (int x = (camX / Tile.tileSize); x < (camX / Tile.tileSize) + renderWidth; x++) {
			for (int y = (camY / Tile.tileSize); y < (camY / Tile.tileSize) + renderHeight; y++) {
				if (x >= 0 && y >= 0 && x < worldW && y < worldH) {
					block[x][y].render(g);
					//this next if statement checks if all the inventory and menus are closed and draws the box around block mouse is over
					if (block[x][y].getID() != Blocks.BLANK && block[x][y].getID() != Blocks.BEDROCK) {
						if (block[x][y].contains(new Point((r.mouse.x) / (int) (r.pixelSize) + (int) (r.sx), (r.mouse.y) / (int) (r.pixelSize) + (int) (r.sy)))) {
							g.setColor(new Color(100, 100, 100));
							g.drawRect(block[x][y].x - camX, block[x][y].y - camY, block[x][y].width - 1, block[x][y].height - 1);
						}
					}
				}
			}
		}
	}
}