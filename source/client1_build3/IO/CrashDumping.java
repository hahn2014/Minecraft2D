package com.minecraft.client.IO;

import java.awt.Color;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.minecraft.client.main.Minecraft;
import com.minecraft.client.resources.NewComputer;
import com.minecraft.client.resources.Tile;

public class CrashDumping {
	public static void DumpCrash(Exception exception) {
		try {
			
			DateFormat dateFormat = new SimpleDateFormat("MM_dd_yyyy_HH_mm_ss");
			Date date = new Date();
			
			PrintWriter writer = new PrintWriter(NewComputer.dumpDirectory + "\\" + dateFormat.format(date) + ".log", "UTF-8");
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			exception.printStackTrace(pw);
			writer.print(sw.toString());
			writer.close();
			
			try {
				Minecraft.op = new OptionPane("Woah There!", "Sorry! and unexpected error occured. " + exception + " A log file has been created as " + dateFormat.format(date) + ".log",
						"OK", 200, 60, Tile.stone, 16, 16, 1.0f, Color.BLACK, Color.BLACK, Color.WHITE, true);
				Minecraft.op.updateVars(1);
			} catch (Exception e) {
				CrashDumping.DumpCrash(e);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}