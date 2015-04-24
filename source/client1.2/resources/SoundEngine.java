package com.minecraft.client.resources;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import com.minecraft.client.main.Minecraft;
import com.minecraft.client.misc.References;

public class SoundEngine {

	private References r;
    private final int BUFFER_SIZE = 128000;
    private AudioInputStream audioStream;
    private AudioFormat audioFormat;
    private SourceDataLine sourceLine;
    private int cursong = 1;
    
    public SoundEngine() {
    	r = Minecraft.r;
    }
    
    /**
     * @param filename the name of the file that is going to be played
     */
    public void playSound(String file) {
    	
        try {
            //audioStream = AudioSystem.getAudioInputStream(getClass().getResource(file)); //this allows a file from in the jar.. makes the jar huge though..
        	audioStream = AudioSystem.getAudioInputStream(new File(NewComputer.SoundsDirectory + "\\" + file));
        } catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }

        audioFormat = audioStream.getFormat();

        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        try {
            sourceLine = (SourceDataLine) AudioSystem.getLine(info);
            sourceLine.open(audioFormat);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        sourceLine.start();
        System.out.println("Now playing menu" + cursong + ".wav");

        int nBytesRead = 0;
        byte[] abData = new byte[BUFFER_SIZE];
        while (nBytesRead != -1) {
        	if (r.playMusic) {
	            try {
	                nBytesRead = audioStream.read(abData, 0, abData.length);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	            if (nBytesRead >= 0) {
	                @SuppressWarnings("unused")
	                int nBytesWritten = sourceLine.write(abData, 0, nBytesRead);
	            }
        	}
        }

        sourceLine.drain();
        sourceLine.close();
        System.out.println("Done playing the sound, switching to next sound...");
        playNext();
    }
    
    public void playNext() {
    	if (cursong < 3) {
    		cursong++;
    		//end current song
    		playSound("menu" + cursong + ".wav");
    	} else {
    		cursong = 1;
    	}
    }
}