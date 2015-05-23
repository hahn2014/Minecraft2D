package com.minecraft.client.game;

import java.awt.Color;
import java.awt.Graphics;

import com.minecraft.client.main.Minecraft;
import com.minecraft.client.misc.References;

public class Sky {
	public static int day = 0, night = 1, time = day;
	
	public static int r1 = 0, g1 = 255, b1 = 255; // 0, 255, 255
	public static int r2 = 15, g2 = 15, b2 = 80; // day
	public static int r = r1, g = g1, b = b1; // day
	public static int dayFrame = 0, dayTime = 6000;
	public static int changeFrame = 0, changeTime = 4;
	
	private References ref;
	
	public Sky() {
		ref = Minecraft.r;
		if (time == day) {
			r = r1;
			g = g1;
			b = b1;
		} else if (time == night) {
			r = r2;
			g = g2;
			b = b2;
		}
	}
	
	public void tick() {
		if (dayFrame >= dayTime) {
			if (time == day) {
				time = night;
			} else if (time == night) {
				time = day;
			}
			dayFrame = 0;
		} else {
			dayFrame += 1;
		}
		if (changeFrame >= changeTime) {
			if (time == day) {
				if (r < r1) {
					r += 1;
				}
				if (g < g1) {
					g += 1;
				}
				if (b < b1) {
					b += 1;
				}
			} else if (time == night) {
				if (r > r2) {
					r -= 1;
				}
				if (g > g2) {
					g -= 1;
				}
				if (b > b2) {
					b -= 1;
				}
			}
			changeFrame = 0;
		} else {
			changeFrame += 1;
		}
	}
	public void render(Graphics gr) {
		gr.setColor(new Color(r, g, b));
		gr.fillRect(0, 0, ref.PIXEL.width, ref.PIXEL.height);
	}
}