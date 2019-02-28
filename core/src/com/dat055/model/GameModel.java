package com.dat055.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;
import com.dat055.model.entity.DebugCamera;
import com.dat055.model.entity.Player;
import com.dat055.model.map.GameMap;
import com.dat055.model.map.GameMapFactory;

public class GameModel extends Model {
    private GameMap map1;
    private GameMap map2;
    private DebugCamera debugCam;
    private OrthographicCamera cam;
    private BitmapFont debugFont;
    private String currentMap;

    public GameModel(){
        debugFont = new BitmapFont(Gdx.files.internal("fonts/Mincho.fnt"),
                Gdx.files.internal("fonts/Mincho.png"), false);
        debugFont.getData().setScale(0.4f, 0.4f);
        musicBank = new ObjectMap();
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
     *
     * @param fileName name of map (json)
     */
    public void createMap(String fileName) {
        System.out.println("Gamemodel: " + fileName);
        GameMapFactory mapFactory = new GameMapFactory(fileName);
        currentMap = fileName;
        map1 = mapFactory.getMap();
        map2 = mapFactory.getMap();
        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        debugCam = new DebugCamera(new Vector2(Vector2.Zero));
    }
    public GameMap getGameMap1() { return map1; }
    public GameMap getGameMap2() { return map2; }
    public Player getDebugCam() { return debugCam; }
    public BitmapFont getFont() { return debugFont; }
    public OrthographicCamera getCam() {return cam;}

    public String getCurrentMap() {
        return currentMap;
    }
}
