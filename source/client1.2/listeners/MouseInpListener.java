package com.minecraft.client.listeners;

import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

import com.minecraft.client.main.Minecraft;
import com.minecraft.client.misc.References;

public class MouseInpListener implements MouseInputListener{
	Minecraft m = Minecraft.getMinecraft();
	@SuppressWarnings("static-access")
	References r = m.r;
	
	@Override
	public void mouseClicked(MouseEvent event) {
		
	}

	@Override
	public void mouseEntered(MouseEvent event) {
		
	}

	@Override
	public void mouseExited(MouseEvent event) {
		
	}

	@Override
	public void mousePressed(MouseEvent event) {
		r.moveFramePoint = new Point(event.getX(), event.getY());
		r.moveFrame = true;
		r.dragging = true;
		//Logger.debug(r.moveFramePoint.getX() + ", " + r.moveFramePoint.getY());
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		r.moveFrame = false;
		r.dragging = false;
	}
	public void mouseDragged(MouseEvent event) {}
	public void mouseMoved(MouseEvent event) {}
}