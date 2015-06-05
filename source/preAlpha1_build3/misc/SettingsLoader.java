package com.minecraft.client.misc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.ini4j.Ini;
import org.ini4j.Profile.Section;
import org.ini4j.Wini;

import com.minecraft.client.main.Minecraft;
import com.minecraft.client.resources.NewComputer;
import com.minecraft.client.IO.*;

public class SettingsLoader {
	
	Minecraft mc = Minecraft.getMinecraft();
	@SuppressWarnings("static-access")
	References r = mc.getReferences();

	@SuppressWarnings("static-access")
	public void loadSettings(String file) {
		if (!r.needIni) {
			Ini ini = new Ini();
			try {
				ini.load(new FileReader(new File(file)));
				//if we could load ini  continue getting data
				Section section = ini.get("menu");
				r.playMusic = Boolean.parseBoolean(section.get("sound"));
				r.playSoundEF = Boolean.parseBoolean(section.get("soundef"));
				r.autoSave = Boolean.parseBoolean(section.get("autosave"));
				r.fullscreen = Boolean.parseBoolean(section.get("fullscreen"));
				mc.setFrameSize(r.fullscreen);
				r.FPScap = Double.parseDouble(section.get("fpscap"));
				mc.FPS.alterFPSCap(r.FPScap);
				r.lastPlayedWorld = section.get("lpw");
				//stop any music that might be playing if music is disabled
				if (!r.playMusic)
					mc.soundengine.stopClip();
				updateSettingsButtons();
				showLoadDebug();
			} catch (Exception e) {
				e.printStackTrace();
				Logger.error("Failed to load settings... Will try loading with default settings");
				loadWithDefault();
			}
		} else {
			createIni();
			loadSettings(NewComputer.settingsFile);
		}
	}
	
	public void saveSettings(String file) {
		if (!r.needIni) {
			try {
				Wini ini = new Wini(new File(file));
				ini.put("menu", "sound", 		r.playMusic);
				ini.put("menu", "soundef", 		r.playSoundEF);
				ini.put("menu", "autosave", 	r.autoSave);
				ini.put("menu", "fullscreen", 	r.fullscreen);
				ini.put("menu", "fpscap", 		r.FPScap);
				ini.put("menu", "lpw", 			r.lastPlayedWorld);
				ini.store();
				showSaveDebug();
			} catch (Exception e) {
				e.printStackTrace();
				Logger.error("Failed to save settings...");
			}
		} else {
			createIni();
			saveSettings(NewComputer.settingsFile);
		}
	}
	
	private void showLoadDebug() {
		Logger.debug("----Loaded Settings----");
		Logger.debug("Music 		[" + r.playMusic + "]");
		Logger.debug("Sound Effects 	[" + r.playSoundEF + "]");
		Logger.debug("Auto Save		[" + r.autoSave + "]");
		Logger.debug("Full Screen		[" + r.fullscreen + "]");
		Logger.debug("FPS Cap 		[" + r.FPScap + "]");
		Logger.debug("last Played World     [" + r.lastPlayedWorld + "]");
		Logger.debug("-----------------------");
	}
	
	private void showSaveDebug() {
		Logger.debug("----Saved Settings----");
		Logger.debug("Music 		[" + r.playMusic + "]");
		Logger.debug("Sound Effects 	[" + r.playSoundEF + "]");
		Logger.debug("Auto Save		[" + r.autoSave + "]");
		Logger.debug("Full Screen		[" + r.fullscreen + "]");
		Logger.debug("FPS Cap 		[" + r.FPScap + "]");
		Logger.debug("last Played World     [" + r.lastPlayedWorld + "]");
		Logger.debug("-----------------------");
	}
	
	public void createIni() {
		BufferedWriter writer;
		File file = new File(NewComputer.settingsFile);
		try {
			writer = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
			writer.write("[menu]"); writer.newLine();
			writer.write("sound=true"); writer.newLine();
			writer.write("soundef=false"); writer.newLine();
			writer.write("autosave=true"); writer.newLine();
			writer.write("fullscreen=false"); writer.newLine();
			writer.write("fpscap=59.97"); writer.newLine();
			writer.write("lpw=null"); writer.newLine();
			writer.close();
			Logger.debug("Ini file has been created");
			r.needIni = false;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * If all else fails, the program will resort to default
	 * settings to avoid any conflicting crashes
	 */
	private void loadWithDefault() {
		r.playMusic = true; r.playSoundEF = false;
		r.autoSave = true; r.fullscreen = false;
		r.FPScap = 59.97; r.lastPlayedWorld = null;
		showLoadDebug();
	}
	
	private void updateSettingsButtons() {
		if (r.fullscreen)
			Minecraft.settings.buttons[0] = "Go Windowed Mode";
		else
			Minecraft.settings.buttons[0] = "Go Fullscreen Mode";
		if (r.autoSave)
			Minecraft.settings.buttons[1] = "Dissable Auto Save Feature";
		else
			Minecraft.settings.buttons[1] = "Enable Auto Save Feature";
		if (r.playMusic)
			Minecraft.settings.buttons[2] = "Dissable Music";
		else
			Minecraft.settings.buttons[2] = "Enable Music";
		if (r.playSoundEF)
			Minecraft.settings.buttons[3] = "Dissable Sound Effects";
		else
			Minecraft.settings.buttons[3] = "Enable Sound Effects";
		if (r.FPScap == 999999.9)
			Minecraft.settings.buttons[4] = "Alter FPS Cap (INFINITE)";
		else
			Minecraft.settings.buttons[4] = "Alter FPS Cap (" + Math.ceil(r.FPScap) + ")";
	}
}