package com.minecraft.client.misc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
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
				mc.settings.buttons[4] = "Alter FPS Cap (" + Math.ceil(r.FPScap) + ")";
				//stop any music that might be playing if music is disabled
				if (!r.playMusic)
					mc.soundengine.stopClip();
	
				showLoadDebug();
			} catch (Exception e) {
				e.printStackTrace();
				Logger.error("Failed to load settings...");
			}
		} else {
			createIni();
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
		Logger.debug("-----------------------");
	}
	
	private void showSaveDebug() {
		Logger.debug("----Saved Settings----");
		Logger.debug("Music 		[" + r.playMusic + "]");
		Logger.debug("Sound Effects 	[" + r.playSoundEF + "]");
		Logger.debug("Auto Save		[" + r.autoSave + "]");
		Logger.debug("Full Screen		[" + r.fullscreen + "]");
		Logger.debug("FPS Cap 		[" + r.FPScap + "]");
		Logger.debug("-----------------------");
	}
	
	public void createIni() {
		PrintWriter writer;
		try {
			writer = new PrintWriter(NewComputer.settingsFile, "UTF-8");
			writer.println("[menu]");
			writer.println("sound=true");
			writer.println("soundef=false");
			writer.println("autosave=true");
			writer.println("fullscreen=false");
			writer.println("fpscap=59.97");
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}