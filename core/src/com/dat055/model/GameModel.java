package com.dat055.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ObjectMap;
import com.dat055.model.map.GameMap;
import com.dat055.model.map.GameMapFactory;

/**
 * This class is responsible for loading game assets
 * and creating 1-2 gamemaps.
 * @author Karl Strålman
 * @version 2019-02-22
 */
public class GameModel extends Model {
    private GameMap map1;
    private GameMap map2;
    private OrthographicCamera cam;
    private BitmapFont debugFont;
    private String currentMap;
    private String nextMap;

    public GameModel(){
        debugFont = new BitmapFont(Gdx.files.internal("fonts/Mincho.fnt"),
                Gdx.files.internal("fonts/Mincho.png"), false);
        debugFont.getData().setScale(0.4f, 0.4f);
        musicBank = new ObjectMap<String, Music>();
        initMusic();
    }

    @Override
    public void initMusic() {
        musicBank.put("map_01", loadMusic("map_01.mp3"));
    }

    @Override
    public void playMusic(String ost) {
        Music music = musicBank.get(ost);
        if (ost.equals("map_01"))
            music.setVolume(0.3f);
        music.play();
    }

    /**
     * Creates map1 (and map2) if given a filepath to a map
     * @param fileName name of map (json)
     */
    public void createMap(String fileName) {
        GameMapFactory mapFactory = new GameMapFactory(fileName);
        currentMap = fileName;
        map1 = mapFactory.getMap();
        map2 = mapFactory.getMap();
        nextMap = mapFactory.getNextMap();
        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }
    public GameMap getGameMap1() { return map1; }
    public GameMap getGameMap2() { return map2; }
    public BitmapFont getFont() { return debugFont; }
    public OrthographicCamera getCam() {return cam;}
    public String getNextMap() { return nextMap; }

    public String getCurrentMap() {
        return currentMap;
    }
}
