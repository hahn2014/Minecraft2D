package com.minecraft.client.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import com.minecraft.client.misc.References;


public class MouseMotListener implements MouseMotionListener {
	
	References r = new References();
	
	@Override
	public void mouseDragged(MouseEvent e) {
		r.mouse.setLocation(e.getX(), e.getY());
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		r.mouse.setLocation(e.getX(), e.getY());
	}
}