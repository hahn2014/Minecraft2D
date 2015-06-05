package com.minecraft.client.math;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.minecraft.client.misc.References;
public class FPS {
	private static long lastTime = System.nanoTime();
	private static double unprocessed = 0;
	public  double FPSCap = 60;
	private double nsPerTick = 1000000000.0 / FPSCap;
	private static double frames = 0;
	private static long lastTimer = System.currentTimeMillis();
	private static double finalFrames;
	private static Font font = new Font("Arial", Font.PLAIN, 12);
	
	private References r = new References();
	
	public void alterFPSCap(double newcap) {
		this.FPSCap = newcap;
		nsPerTick = 1000000000.0 / FPSCap;
	}
	
	public void getFPS() {
		long now = System.nanoTime();
		unprocessed += (now - lastTime) / nsPerTick;
		lastTime = now;
		boolean shouldRender = false;
		while (unprocessed > 1) {
			unprocessed -= 1;
			shouldRender = true;
		}
		if (shouldRender) {
			frames++;
		}
		if (System.currentTimeMillis() - lastTimer > 1000) {
			lastTimer += 1000;
			r.fps = finalFrames;
			finalFrames = frames;
			frames = 0;
		}
	}
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.setFont(font);
		g.drawString("fps: " + finalFrames, 2, 21);
	}
}