package com.dat055.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.ObjectMap;

public abstract class Model {
    ObjectMap<String, Music> musicBank;
    Model() {}
    public abstract void initMusic();
    public abstract void playMusic(String ost);

    /**
     * Plays a music and disposes the song to preserve memory.
     */
    public void stopMusic() {
        for (Music entry : musicBank.values()) {
            entry.stop();
            entry.dispose();
        }
    }

    /**
     * Gets a certain music track.
     * @param file file name of music to be loaded
     * @return the music object containing the music from assets.
     */
    Music loadMusic(String file) { return Gdx.audio.newMusic(Gdx.files.internal("audio/ost/" + file));}
}
