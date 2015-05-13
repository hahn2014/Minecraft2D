package com.minecraft.client.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import com.minecraft.client.main.Minecraft;
import com.minecraft.client.misc.References;


public class MouseMotListener implements MouseMotionListener {
	
	Minecraft m = Minecraft.getMinecraft();
	@SuppressWarnings("static-access")
	References r = m.r;
	
	@SuppressWarnings("static-access")
	@Override
	public void mouseDragged(MouseEvent e) {
		r.mouse.setLocation(e.getX(), e.getY());
		System.out.println(r.moveFrame);
		if (r.moveFrame == true) {
			m.frame.setLocation((int)(e.getXOnScreen() - r.moveFramePoint.getX()), (int)(e.getYOnScreen() - r.moveFramePoint.getY()));
		}
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		r.mouse.setLocation(e.getX(), e.getY());
	}
}