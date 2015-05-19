package com.minecraft.client.misc;

import java.io.IOException;

import com.minecraft.client.IO.CrashDumping;
import com.minecraft.client.main.Minecraft;
import com.minecraft.client.resources.NewComputer;

public class SettingsLoader {
	
	Minecraft mc = Minecraft.getMinecraft();
	@SuppressWarnings("static-access")
	References r = mc.r;

	IniFile ini;

	public SettingsLoader() {
		try {
			ini = new IniFile(NewComputer.settingsFile);
		} catch (IOException e) {
			CrashDumping.DumpCrash(e);
		}
	}
	
	@SuppressWarnings("static-access")
	public void loadSettings() {
		r.playMusic = ini.getBoolean("menu", "music");
		if (!r.playMusic)
			mc.soundengine.stopClip();
		r.playSoundEF = ini.getBoolean("menu", "soundef");
		r.autoSave = ini.getBoolean("menu", "autosave");
		mc.setFrameSize(ini.getBoolean("menu", "fullscreen"));
		r.fullscreen = ini.getBoolean("menu", "fullscreen");
		r.FPScap = ini.getDouble("menu", "fpscap");
		mc.FPS.alterFPSCap(ini.getDouble("menu", "fpscap"));
		mc.settings.buttons[4] = "Alter FPS Cap (" + Math.ceil(r.FPScap) + ")";
	}
	
	public void saveSettings() {
		//ini.setValue("music", r.playMusic);
		//ini.setValue("soundef", r.playSoundEF);
		//ini.setValue("autosave", r.autoSave);
		//ini.setValue("fullscreen", r.fullscreen);
		//ini.setValue("fpscap", r.FPScap);
	}
}