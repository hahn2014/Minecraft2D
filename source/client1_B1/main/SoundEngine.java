package com.minecraft.client.main;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.util.WaveData;

import com.minecraft.client.resources.Tile;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;

import static org.lwjgl.openal.AL10.*;

public class SoundEngine {

    public static void main(String[] args) throws FileNotFoundException {
        try {
            AL.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            AL.destroy();
            System.exit(1);
        }
        int buffer = alGenBuffers();
        WaveData data = WaveData.create(new BufferedInputStream(Tile.class.getResourceAsStream("menu1.wav")));
        alBufferData(buffer, data.format, data.data, data.samplerate);
        data.dispose();
        int source = alGenSources();
        alSourcei(source, AL_BUFFER, buffer);
        alSourcePlay(source);
        alDeleteBuffers(buffer);
        AL.destroy();
        System.exit(0);
    }
}