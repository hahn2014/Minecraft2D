package com.minecraft.client.resources;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import javax.swing.JOptionPane;

import com.minecraft.client.IO.CrashDumping;
import com.minecraft.client.IO.Logger;
import com.minecraft.client.main.Minecraft;

public class NewComputer {
	public static String appdata = System.getenv("APPDATA");
	public static int songs = 5;
	public static int effects = 1;
	//set up the initial folders
	public final static String SoundsDirectory 			= new String(appdata 			+ "\\.MINECRAFT2D\\Sounds");
	public final static String savesDirectory 			= new String(appdata 			+ "\\.MINECRAFT2D\\saves");
	public final static String dumpDirectory			= new String(appdata 			+ "\\.MINECRAFT2D\\Dumps");
	public final static String SoundEffectDirectory		= new String(SoundsDirectory 	+ "\\Effecfts");
	public final static String settingsFile				= new String(appdata 			+ "\\.MINECRAFT2D\\settings.ini");
	public final String gameDirectory 					= new String(appdata 			+ "\\.MINECRAFT2D");
	private boolean shown = false;
	
	public void checkForMissingFiles() {
		//we need to check if the files exist
		if (!checkIfDirExists(SoundsDirectory)) {
			Logger.debug("Sound directory was not found... we need to download everything");
			new File(SoundsDirectory).mkdir();
		}
		if (!checkIfDirExists(SoundEffectDirectory)) {
			Logger.debug("Sound Effect directory was not found... we need to download everything");
			new File(SoundEffectDirectory).mkdir();
		}
		if (!checkIfDirExists(dumpDirectory)) {
			Logger.debug("Dump directory was not found... we need to create it");
			new File(dumpDirectory).mkdir();
		}
		//download any missing songs
		for (int i = 1; i < songs; i++) {
			if (!checkIfFileExists(SoundsDirectory + "\\menu" + i + ".wav")) {
				if (!shown) {
					JOptionPane.showConfirmDialog(null, "We noticed some game files are missing...\nPlease wait while we download them for you.", "Please Wait", JOptionPane.OK_OPTION);
					shown = true;
				}
				Logger.debug("could not find song menu" + i + ".wav");
				downloadFile("menu" + i + ".wav");
			}
		}
		//download any missing effects
		for (int i = 1; i < effects; i++) {
			if (!checkIfFileExists(SoundEffectDirectory + "\\effect" + i + ".wav")) {
				if (!shown) {
					JOptionPane.showConfirmDialog(null, "We noticed some game files are missing...\nPlease wait while we download them for you.", "Please Wait", JOptionPane.OK_OPTION);
					shown = true;
				}
				Logger.debug("could not find sound effect effect" + i + ".wav");
				downloadFile("Effecfts/effect" + i + ".wav");
			}
		}
		//check if settings file is missing
		if (!new File(settingsFile).exists()) {
			Logger.debug("Settings file is missing. We will recreate it");
			try {
				new File(settingsFile).createNewFile();
				Minecraft.getReferences().needIni = true;
			} catch (IOException e) {
				CrashDumping.DumpCrash(e);
			}
		}
	}
	
	private boolean checkIfDirExists(String dir) {
		File f = new File(dir);
		if (f.exists() && f.isDirectory() && !f.isFile()) {
			return true;
		}
		return false;
	}
	
	private boolean checkIfFileExists(String file) {
		File f = new File(file);
		if (f.exists() && !f.isDirectory() && f.isFile()) {
			return true;
		}
		return false;
	}
	
	private void downloadFile(String filename) {
		OutputStream out;
		BufferedInputStream in;
		try {
			out = new BufferedOutputStream(new FileOutputStream(SoundsDirectory + "\\" + filename));
			in = new BufferedInputStream(new URL("https://github.com/hahn2014/Minecraft2D/raw/master/Recources/sounds/" + filename).openStream());
		    byte data[] = new byte[1024];
		    int count;
	        Logger.debug("downloading file to " + SoundsDirectory + "\\" + filename);
		    while((count = in.read(data, 0, 1024)) != -1) {
		        out.write(data, 0, count);
		    }
			out.close();
			in.close();
		} catch (FileNotFoundException e) {
			CrashDumping.DumpCrash(e);
		} catch (IOException e) {
			CrashDumping.DumpCrash(e);
		}
	}
}