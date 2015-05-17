package com.minecraft.client.listeners;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import com.minecraft.client.IO.CrashDumping;
import com.minecraft.client.IO.InputPane;
import com.minecraft.client.IO.Logger;
import com.minecraft.client.IO.OptionPane;
import com.minecraft.client.game.SaveLoad;
import com.minecraft.client.main.Minecraft;
import com.minecraft.client.menus.DeleteWorldMenu;
import com.minecraft.client.menus.LoadWorldMenu;
import com.minecraft.client.menus.MainMenu;
import com.minecraft.client.menus.NewWorldMenu;
import com.minecraft.client.menus.PauseMenu;
import com.minecraft.client.menus.SettingsMenu;
import com.minecraft.client.misc.References;
import com.minecraft.client.resources.Splashes;
import com.minecraft.client.resources.Tile;

public class KeyInputListener implements KeyListener{
	@SuppressWarnings("static-access")
	private References r = Minecraft.getMinecraft().r;
	
	double ans;
	@SuppressWarnings("static-access")
	@Override
	public void keyPressed(KeyEvent event) {
		if (r.isTyping) {
			if (NewWorldMenu.finalName.length() <= r.textFieldMaxLength - 1) {
				int key = event.getKeyCode();
				char c = (char)(key);
				if (Character.isAlphabetic(c) || Character.isDigit(c) || c == ' ') {
					NewWorldMenu.finalName += Character.toString(c);
				}
			}
		} else if (Minecraft.ip.getRender()) {
			char key = event.getKeyChar();
			if ((Character.isDigit(key) || key == '.' || key == '-') && Minecraft.ip.getField().length() < 5) {
				int dots = 0, negative = 0;
				for (int i = 0; i < Minecraft.ip.getField().length(); i++) {
					if (Minecraft.ip.getField().charAt(i) == '.') {
						dots++;
					}
					if (Minecraft.ip.getField().charAt(i) == '-') {
						negative++;
					}
				}
				if (key == '.' && dots == 0) {
					Minecraft.ip.addToField(Character.toString(key));
				} else if (key == '-' && negative == 0) {
					Minecraft.ip.addToField(Character.toString(key));
				} else if (key != '.' && key != '-') {
					Minecraft.ip.addToField(Character.toString(key));
				}
			}
		}
		int key = event.getKeyCode();
		switch (key) {
			case KeyEvent.VK_D:
				r.isMoving = true;
				r.direction = Minecraft.getMinecraft().player.movingSpeed;
				break;
			case KeyEvent.VK_A:
				r.isMoving = true;
				r.direction = -Minecraft.getMinecraft().player.movingSpeed;
				break;
			case KeyEvent.VK_UP:
				switch (r.MENU) {
					case 0: //main menu
						if (MainMenu.curselect > 0) {
							MainMenu.curselect--;
						} else {
							MainMenu.curselect = 4;
						}
						break;
					case 1: //new world menu
						if (NewWorldMenu.curselect > 1) {
							NewWorldMenu.curselect--;
						} else {
							NewWorldMenu.curselect = 3;
						}
						break;
					case 2: //settings menu
						if (SettingsMenu.curselect > 0) {
							SettingsMenu.curselect--;
						} else {
							SettingsMenu.curselect = 6;
						}
						break;
					case 3: //delete world menu
						if (DeleteWorldMenu.curselect > 0) {
							DeleteWorldMenu.curselect--;
						} else {
							DeleteWorldMenu.curselect = -1;
						}
						DeleteWorldMenu.getWorldSelected();
						break;
					case 4: //load world menu
						if (LoadWorldMenu.curselect > 0) {
							LoadWorldMenu.curselect--;
						} else {
							LoadWorldMenu.curselect = -1;
						}
						if (r.worldsCount > 0) {
							LoadWorldMenu.getWorldSelected();
						}
						break;
					case 5: //pause menu
						if (PauseMenu.curselect > 0) {
							PauseMenu.curselect--;
						} else {
							PauseMenu.curselect = PauseMenu.buttons.length - 1;
						}
						break;
				}
				break;
			case KeyEvent.VK_DOWN:
				switch (r.MENU) {
					case 0: //main menu
						if (MainMenu.curselect < 4) {
							MainMenu.curselect++;
						} else {
							MainMenu.curselect = 0;
						}
						break;
					case 1: //new world menu
						if (NewWorldMenu.curselect == 3) {
							NewWorldMenu.curselect = 1;
						} else if (NewWorldMenu.curselect == 2) {
							NewWorldMenu.curselect = 3;
						} else if (NewWorldMenu.curselect == 1) {
							NewWorldMenu.curselect = 2;
						}
						break;
					case 2: //settings menu
						if (SettingsMenu.curselect < 6) {
							SettingsMenu.curselect++;
						} else {
							SettingsMenu.curselect = 0;
						}
						break;
					case 3: //delete world menu
						if (DeleteWorldMenu.curselect < DeleteWorldMenu.worlds.size() - 1) {
							DeleteWorldMenu.curselect++;
						}
						DeleteWorldMenu.getWorldSelected();
						break;
					case 4: //load world menu
						if (LoadWorldMenu.curselect < LoadWorldMenu.worlds.size() - 1 && LoadWorldMenu.curselect != -2) {
							LoadWorldMenu.curselect++;
						} else if (LoadWorldMenu.curselect == LoadWorldMenu.worlds.size()) {
							LoadWorldMenu.curselect = -1;
						} else if (LoadWorldMenu.curselect == -2) {
							LoadWorldMenu.curselect = 0;
						}
						LoadWorldMenu.getWorldSelected();
						break;
					case 5: //pause menu
						if (PauseMenu.curselect < PauseMenu.buttons.length - 1) {
							PauseMenu.curselect++;
						} else {
							PauseMenu.curselect = 0;
						}
						break;
				}
				break;
			case KeyEvent.VK_RIGHT:
				switch (r.MENU) {
					case 4: //load menu
						if (LoadWorldMenu.curselect != -1 && LoadWorldMenu.curselect != -2) {
							LoadWorldMenu.curselect = -1;
							LoadWorldMenu.scrollbar = 0;
						} else if (LoadWorldMenu.curselect == -1) {
							LoadWorldMenu.curselect = -2;
						} else if (LoadWorldMenu.curselect == -2) {
							LoadWorldMenu.curselect = -1;
						}
						break;
					case 3: //delete menu
						DeleteWorldMenu.curselect = -1;
						DeleteWorldMenu.scrollbar = 0;
						break;
				}
				break;
			case KeyEvent.VK_LEFT:
				switch (r.MENU) {
					case 4: //load menu
						if (LoadWorldMenu.curselect != -1 && LoadWorldMenu.curselect != -2) {
							LoadWorldMenu.curselect = -1;
							LoadWorldMenu.scrollbar = 0;
						} else if (LoadWorldMenu.curselect == -1) {
							LoadWorldMenu.curselect = -2;
						} else if (LoadWorldMenu.curselect == -2) {
							LoadWorldMenu.curselect = -1;
						}
						break;
					case 3: //delete menu
						DeleteWorldMenu.curselect = -1;
						DeleteWorldMenu.scrollbar = 0;
						break;
				}
				break;
			case KeyEvent.VK_ENTER:
				if (!Minecraft.op.getRender() && !Minecraft.ip.getRender()) {
					switch (r.MENU) {
					case 0: //main menu
						switch (MainMenu.curselect) {
							case 0: //new world
								r.MENU = 1;
								MainMenu.curselect = 0;
								break;
							case 1: //resume last world
								if (r.played > 0) {
									//resume game
									MainMenu.curselect = 0;
								} else {
									try {
										Minecraft.op = new OptionPane("Woah There!", "You need to have played a world in order to resume from the last played world! Please create a world first.",
												"OK", 200, 80, Tile.stone, 16, 16, 1.0f, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.WHITE, true);
										Minecraft.op.updateVars(1);
									} catch(Exception e) {
										CrashDumping.DumpCrash(e);
									}
								}
								break;
							case 2: //load world
								if (r.worldsCount > 0) {
									MainMenu.curselect = 0;
									r.MENU = 4;
									//reset list of worlds
									LoadWorldMenu.listWorlds();
									LoadWorldMenu.getWorldSelected();
								} else {
									try {
										Minecraft.op = new OptionPane("Woah There!", "You need to have at least one world created in order to load from a world. Please create a world first.",
												"OK", 200, 80, Tile.stone, 16, 16, 1.0f,Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.WHITE, true);
										Minecraft.op.updateVars(1);
									} catch (Exception e) {
										CrashDumping.DumpCrash(e);
									}
								}
								break;
							case 3: //settings
								r.MENU = 2;
								MainMenu.curselect = 0;
								break;
							case 4: //exit
								r.isRunning = false;
								System.exit(0);
								break;
						}
						break;
					case 1: //new world menu
						if (NewWorldMenu.curselect == 2) {
							//create new world
							Minecraft.newWorld.createWorld();
						} else if (NewWorldMenu.curselect == 3) {
							//go back to menu
							r.MENU = 0;
							NewWorldMenu.finalName = "";
						}
						NewWorldMenu.curselect = 1;
						break;
					case 2: //settings menu
						switch (SettingsMenu.curselect) {
							case 0:
								if (r.fullscreen) {
									Minecraft.settings.buttons[0] = "Go Fullscreen Mode";
									Minecraft.setFrameSize(false);
								} else {
									Minecraft.settings.buttons[0] = "Go Windowed Mode";
									Minecraft.setFrameSize(true);
								}
								r.fullscreen = !r.fullscreen;
								break;
							case 1:
								Logger.debug("Auto Saving is now " + !r.autoSave);
								if (r.autoSave) {
									Minecraft.settings.buttons[1] = "Enable Auto Save Feature";
								} else {
									Minecraft.settings.buttons[1] = "Dissable Auto Save Feature";
								}
								r.autoSave = !r.autoSave;
								break;
							case 2:
								Logger.debug("Music Playing is now " + !r.playMusic);
								if (r.playMusic) {
									Minecraft.settings.buttons[2] = "Enable Music";
								} else {
									Minecraft.settings.buttons[2] = "Dissable Music";
								}
								r.playMusic = !r.playMusic;
								if (r.playMusic == true && Minecraft.soundengine.getMusicClip() != null) {
									Minecraft.soundengine.getMusicClip().start();
								} else if (r.playMusic == false) {
									if (Minecraft.soundengine.getMusicClip() != null) {
										Minecraft.soundengine.getMusicClip().stop();
									}
								}
								if (Minecraft.soundengine.getMusicClip() == null && r.playMusic == true) {
									Minecraft.soundengine.startWithRandomSong();
								}
								break;
							case 3:
								Logger.debug("Sound Effect Playing is now " + !r.playSoundEF);
								if (r.playSoundEF) {
									Minecraft.settings.buttons[3] = "Enable Sound Effects";
								} else {
									Minecraft.settings.buttons[3] = "Dissable Sound Effects";
								}
								r.playSoundEF = !r.playSoundEF;
								if (r.playSoundEF && Minecraft.soundengine.getEffectClip() != null) {
									Minecraft.soundengine.getEffectClip().start();
								} else {
									if (Minecraft.soundengine.getEffectClip() != null) {
										Minecraft.soundengine.getEffectClip().stop();
									}
								}
								break;
							case 4:
								try {
									Minecraft.ip = new InputPane("Alter FPS CAP", "Enter a number from 24-10000 OR -1 for no cap", "DONE", 200, 80, 100, 20, Tile.stone, 16, 16, 1.0f,
											Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.WHITE, Color.WHITE, true);
									Minecraft.ip.updateVars(1);
								} catch (Exception e) {
									CrashDumping.DumpCrash(e);
								}
								break;
							case 5:
								if (r.worldsCount > 0) {
									//delete world menu
									MainMenu.curselect = 0;
									r.MENU = 3;
									DeleteWorldMenu.cameFrom = 2;
								} else {
									try {
										Minecraft.op = new OptionPane("Woah There!", "In order to open the delete menu, you must have at least one world created to delete. Please create a new world first.",
												"OK", 200, 80, Tile.stone, 16, 16, 1.0f,Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.WHITE, true);
										Minecraft.op.updateVars(1);
									} catch (Exception e) {
										CrashDumping.DumpCrash(e);
									}
								}
								break;
							case 6:
								r.MENU = SettingsMenu.lastMenu;
								SettingsMenu.curselect = 0;
								Minecraft.settingsloader.saveSettings();
								break;
						}
						break;
					case 3: //delete world menu
						for (int i = 0; i < DeleteWorldMenu.worlds.size(); i++) {
							if (DeleteWorldMenu.curselect == -1) {
								r.MENU = DeleteWorldMenu.cameFrom;
								if (DeleteWorldMenu.cameFrom == 4) {
									LoadWorldMenu.listWorlds();
									LoadWorldMenu.getWorldSelected();
								}
							} else if (DeleteWorldMenu.curselect == i) { //on a selected world, we need to delete it now
								Logger.debug("now deleting " + DeleteWorldMenu.worlds.get(i) + ".dat");
								//delete the files
								try {
									//delete .dat world file
									Path path = FileSystems.getDefault().getPath(Minecraft.nc.savesDirectory + "\\", DeleteWorldMenu.worlds.get(i) + ".dat");
									Files.deleteIfExists(path);
									//delete .png world image
									path = FileSystems.getDefault().getPath(Minecraft.nc.savesDirectory + "\\", DeleteWorldMenu.worlds.get(i) + ".png");
									Files.deleteIfExists(path);
								} catch(Exception e) {e.printStackTrace();}
								//refresh list of worlds
								DeleteWorldMenu.listWorlds();
								LoadWorldMenu.listWorlds();
								DeleteWorldMenu.getWorldSelected();
								LoadWorldMenu.getWorldSelected();
								DeleteWorldMenu.curselect = -1;
							}
						}
						break;
					case 4: //load world menu
						for (int i = 0; i < LoadWorldMenu.worlds.size(); i++) {
							if (LoadWorldMenu.curselect == -1) {
								r.MENU = 0;
							} else if (LoadWorldMenu.curselect == -2) {
								r.MENU = 3;
								DeleteWorldMenu.cameFrom = 4;
							} else if (LoadWorldMenu.curselect == i) { //on a selected world, we need to load it now
								r.MENU = 6;
								Logger.debug("now loading " + LoadWorldMenu.worlds.get(i) + ".dat");
								//load the world
								SaveLoad.Load(LoadWorldMenu.worlds.get(i) + ".dat");
								r.curWorld = LoadWorldMenu.worlds.get(i);
								Minecraft.frame.setTitle(r.NAME + " " + r.BUILD + " " + r.VERSION + " Playing on " + LoadWorldMenu.worlds.get(i));
								LoadWorldMenu.curselect = -1;
								LoadWorldMenu.lastWorld = LoadWorldMenu.worlds.get(i);
								r.loaded = true;
								r.hasStarted = true;
							}
						}
						break;
					case 5: //pause menu
						switch (PauseMenu.curselect) {
						case 0: //resume
							r.MENU = 6;
							break;
						case 1: //settings
							r.MENU = 2;
							SettingsMenu.lastMenu = 5;
							break;
						case 2: //main menu & save
							//save code
							SaveLoad.Save(r.curWorld);
							r.MENU = 0;
							break;
						case 3: //quit
							r.isRunning = false;
							System.exit(0);
							break;
						}
						break;
					}
				} else if (Minecraft.op.getRender() && !Minecraft.ip.getRender()) {
					Minecraft.op.setRender(false);
				} else if (!Minecraft.op.getRender() && Minecraft.ip.getRender()) {
					Minecraft.ip.setRender(false);
					if (Minecraft.ip.getField() != null && Minecraft.ip.getField() != "") {
						ans = Double.parseDouble(Minecraft.ip.getField());
						
						if (ans >= 24 && ans  <= 10000) {
							r.FPScap = ans;
							Minecraft.FPS.alterFPSCap(ans);
							Minecraft.settings.buttons[4] = "Alter FPS Cap (" + (int)(r.FPScap) + ")";
						} else if (ans == -1) {
							r.FPScap = 999999.9;
							Minecraft.FPS.alterFPSCap(999999.9);
							Minecraft.settings.buttons[4] = "Alter FPS Cap (INFINITE)";
						} else {
							try {
								Minecraft.op = new OptionPane("Woah There!", "You entered " + ans + " and that is not a valid number to be entered\nPlease try a different number.",
										"OK", 200, 80, Tile.stone, 16, 16, 1.0f,Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.WHITE, true);
								Minecraft.op.updateVars(1);
							} catch (Exception e) {
								CrashDumping.DumpCrash(e);
							}
						}
					}
				}
				Minecraft.soundengine.playSoundEffect("effect1.wav");
				break;
			case KeyEvent.VK_BACK_SPACE:
				if (r.MENU == 1 && r.isTyping) { //new world menu
					if (NewWorldMenu.finalName.length() - 1 >= 0) {
						NewWorldMenu.finalName = NewWorldMenu.finalName.substring(0, NewWorldMenu.finalName.length() - 1);
					}
				} else if (Minecraft.ip.getRender() && Minecraft.ip.getField().length() >= 1) {
					Minecraft.ip.setField(Minecraft.ip.getField().substring(0, Minecraft.ip.getField().length() - 1));
				}
				break;
			case KeyEvent.VK_ESCAPE:
				if (r.MENU == 6) {
					r.MENU = 5;
				} else if (r.MENU == 5) {
					r.MENU = 6;
				}
				break;
			case KeyEvent.VK_R:
				if (r.MENU == 0) {
					Splashes.getRandSplash();
				}
				break;
			case KeyEvent.VK_F10:
				Minecraft.setFrameSize(!r.fullscreen);
				r.fullscreen = !r.fullscreen;
				Logger.debug("Fullscreen [" + r.fullscreen + "]");
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {
		int key = event.getKeyCode();
		switch (key) {
		case KeyEvent.VK_D:
			if (r.direction == Minecraft.player.movingSpeed) {
				r.isMoving = false;
			}
			break;
		case KeyEvent.VK_A:
			if (r.direction == -Minecraft.player.movingSpeed) {
				r.isMoving = false;
			}
			break;
		case KeyEvent.VK_SPACE:
			r.isJumping = false;
			break;
		}
	}
	public void keyTyped(KeyEvent event) {}
}