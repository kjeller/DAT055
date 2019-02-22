package com.dat055.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.ObjectMap;

public abstract class Model {
    protected ObjectMap<String, Music> musicBank;
    public Model() {}
    public void initialize(){

    }
    public abstract void initMusic();
    public abstract void playMusic(String ost);
    public void stopMusic() {
        for (Music entry : musicBank.values()) {
            entry.stop();
            entry.dispose();
        }
    }
    Music loadMusic(String file) { return Gdx.audio.newMusic(Gdx.files.internal("audio/ost/" + file));}
}
