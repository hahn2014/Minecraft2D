package com.minecraft.client.misc;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import com.minecraft.client.main.Minecraft;
import com.minecraft.client.math.Methods;

public class Splashes {

	private static ArrayList<String> splashes;

	private static Random random;
	
	private boolean goingDown = false;
	private static Font pump = new Font(Font.SERIF, Font.PLAIN, 13);
	private static double fontSize = 13;
	
	private static References r;
	private static Methods m;
	
	public Splashes() {
		r = Minecraft.r;
		m = new Methods();
		random = new Random();
		random.setSeed(System.currentTimeMillis());
		splashes = new ArrayList<>();
		loadSplashes();
		getRandSplash();
	}

	public static void getRandSplash() {
		r.splash = splashes.get(random.nextInt(splashes.size()));
	}
	
	private void loadSplashes() {
		
		URL url = null;
		try {
			url = new URL("https://raw.githubusercontent.com/hahn2014/Minecraft2D/master/Recources/splash.txt");
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		
        String line;
        BufferedReader in;
		try {
			in = new BufferedReader(new InputStreamReader(url.openStream()));
    	    while ((line = in.readLine()) != null) {
    	    	if (line.endsWith("!")) {
        	        splashes.add(line);
    	    	} else {
    	    		System.out.println("Found this weird text sentence: " + line);
    	    	}
    	    }
    	    in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void tick() {
		if (!goingDown) {
			if (fontSize < 18) {
				fontSize += 0.07;
			} else {
				goingDown = true;
			}
		}
		if (goingDown) {
			if (fontSize > 13) {
				fontSize -= 0.07;
			} else {
				goingDown = false;
			}
		}
		pump = new Font(Font.SERIF, Font.BOLD, (int)(fontSize));
	}
	
	public void render(Graphics g) {
		//splash at angle!
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setColor(Color.YELLOW);
		g2d.setFont(pump);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
        g2d.drawString(r.splash, (r.PIXEL.width / 2) - (m.getStringWidth(r.splash, pump) / 2), 70);
        g2d.dispose();
	}
}