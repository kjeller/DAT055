package com.dat055.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Base class for MenuModel and GameModel.
 * Is responsible for both models to have music.
 * @author Tobias Campbell
 * @version 22-02-2019
 */
public abstract class Model {
    ObjectMap<String, Music> musicBank;
    Model() { musicBank = new ObjectMap<String, Music>(); }
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
