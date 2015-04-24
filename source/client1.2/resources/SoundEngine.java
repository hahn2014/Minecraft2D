package com.minecraft.client.resources;

import java.io.File;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import com.minecraft.client.IO.CrashDumping;
import com.minecraft.client.IO.Logger;
import com.minecraft.client.main.Minecraft;
import com.minecraft.client.misc.References;

public class SoundEngine {

    private AudioInputStream audioStream;
    private int cursong = 1;
    private Clip musicClip;
    private Clip effectClip;
    
    private References r;
    
    public SoundEngine() {
    	r = Minecraft.r;
    }
    
    public void startWithRandomSong() {
    	r.playMusic = true;
    	Random random = new Random();
    	random.setSeed(System.currentTimeMillis());
    	Logger.info("choosing random song to play on start up");
    	playSong("menu" + (random.nextInt(NewComputer.filedirs) + 1) + ".wav");
    }
    
    public void playSong(String file) {
    	Logger.info("Starting " + file);
    	r.playMusic = true;
        try {
			audioStream = AudioSystem.getAudioInputStream(new File(NewComputer.SoundsDirectory + "\\" + file));
			musicClip = AudioSystem.getClip();
			musicClip.open(audioStream);
			FloatControl gainControl = (FloatControl) musicClip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(gainControl.getMaximum()); // Reduce volume by 10 decibels.
			musicClip.start();
			while (musicClip.getMicrosecondLength() != musicClip.getMicrosecondPosition()) {}
			Logger.info("sound " + file + " is finished playing... Starting next sound.");
			playNextSong();
        } catch (Exception e) {
            e.printStackTrace();
            CrashDumping.DumpCrash(e);
            System.exit(1);
        }
    }
    
    public void playNextSong() {
    	Logger.info("menu" + cursong + ".wav has finished playing.");
    	if (cursong < NewComputer.filedirs) {
    		musicClip.close();
    		cursong++;
    		//end current song
    		playSong("menu" + cursong + ".wav");
    	} else {
    		cursong = 1;
    	}
    }
    
    public void playSoundEffect(String file) {
    	if (r.playSoundEF) {
	        try {
				audioStream = AudioSystem.getAudioInputStream(new File(NewComputer.SoundEffectDirectory + file));
				effectClip = AudioSystem.getClip();
				effectClip.open(audioStream);
				FloatControl gainControl = (FloatControl) effectClip.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(gainControl.getMaximum());
				effectClip.start();
	        } catch (Exception e) {
	            e.printStackTrace();
	            CrashDumping.DumpCrash(e);
	            System.exit(1);
	        }
    	}
    }
    
    public Clip getMusicClip() {
    	return musicClip;
    }
    
    public Clip getEffectClip() {
    	return effectClip;
    }
}