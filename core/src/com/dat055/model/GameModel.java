package com.dat055.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;
import com.dat055.model.map.GameMap;
import com.dat055.model.map.GameMapFactory;

/**
 * This class is responsible for loading game assets
 * and calling {@link GameMapFactory} to create {@link GameMap}.
 * @author Karl Strålman
 * @version 2019-02-22
 */
public class GameModel extends Model {
    private GameMap map1;
    private GameMap map2;
    private OrthographicCamera cam;
    private Vector2 camViewDistance;
    private BitmapFont debugFont;
    private BitmapFont mapFont;

    private String currentMap;
    private String nextMap;
    private String mapName;

    /**
     * Default constructor
     */
    public GameModel(){
        debugFont = new BitmapFont(Gdx.files.internal("fonts/Mincho.fnt"),
                Gdx.files.internal("fonts/Mincho.png"), false);
        mapFont = new BitmapFont(Gdx.files.internal("fonts/Mincho.fnt"),
                Gdx.files.internal("fonts/Mincho.png"), false);
        debugFont.getData().setScale(0.4f, 0.4f);
        mapFont.getData().setScale(2f, 2f);
        musicBank = new ObjectMap<String, Music>();
        initMusic();
    }

    /**
     * Loads music to the music bank.
     */
    @Override
    public void initMusic() {
        musicBank.put("map", loadMusic("map.mp3"));
    }

    /**
     * Plays music from the music bank.
     * @param ost music that will be played.
     */
    @Override
    public void playMusic(String ost) {
       Music music = musicBank.get(ost);
       if (ost.equals("map"))
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
        mapName = mapFactory.getMapName();
        int width = 1500;
        int height = 1100;
        cam = new OrthographicCamera(width, height);
        camViewDistance = new Vector2((float)width/2, (float)height/2);
    }

    /**
     * @return map 1
     */
    public GameMap getGameMap1() { return map1; }

    /**
     * @return map 2
     */
    public GameMap getGameMap2() { return map2; }

    /**
     * @return font used to draw debug text
     */
    public BitmapFont getDebugFont() { return debugFont; }

    /**
     * @return font used to draw map name at the beginning of map
     */
    public BitmapFont getMapFont() { return mapFont; }

    /**
     * @return camera.
     */
    public OrthographicCamera getCam() {return cam;}

    /**
     * @return gets camera bounds
     */
    public Vector2 getCamViewDistance() { return camViewDistance; }

    /**
     * @return filepath to next map.
     */
    public String getNextMap() { return nextMap; }

    /**
     * @return filepath to current map.
     */
    public String getCurrentMap() { return currentMap; }

    /**
     * @return name of current map
     */
    public String getMapName() { return mapName; }
}
