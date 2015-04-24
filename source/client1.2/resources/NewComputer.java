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

public class NewComputer {
	private static String appdata = System.getenv("APPDATA");
	private int filedirs = 6;
	//set up the initial folders
	public final static String SoundsDirectory 			= new String(appdata + "\\.MINECRAFT2D\\Sounds");
	public final static String savesDirectory 		= new String(appdata + "\\.MINECRAFT2D\\saves");
	public final String gameDirectory 				= new String(appdata + "\\.MINECRAFT2D");
	private boolean shown = false;
	
	public NewComputer() {
		//we need to check if the files exist
		if (!checkIfDirExists(SoundsDirectory)) {
			System.out.println("Sound directory was not found... we need to download everything");
			new File(SoundsDirectory).mkdir();
		}
		for (int i = 1; i < filedirs; i++) {
			System.out.println("checking if menu" + i + ".wav exists");
			if (!checkIfFileExists(SoundsDirectory + "\\menu" + i + ".wav")) {
				if (!shown) {
					JOptionPane.showConfirmDialog(null, "We noticed some game files are missing...\nPlease wait while we download them for you.", "Please Wait", JOptionPane.OK_OPTION);
					shown = true;
				}
				System.out.println("could not find menu" + i + ".wav");
				downloadFile("menu" + i + ".wav");
			} else {
				System.out.println("menu" + i + ".wav was found, skipping to next file");
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
	        System.out.println("downloading file to " + SoundsDirectory + "\\" + filename);
		    while((count = in.read(data, 0, 1024)) != -1) {
		        out.write(data, 0, count);
		    }
			out.close();
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}