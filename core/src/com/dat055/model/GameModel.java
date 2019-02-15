package com.dat055.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
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

    public GameModel(){
        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        debugCam = new DebugCamera(new Vector2(Vector2.Zero));
        debugFont = new BitmapFont(Gdx.files.internal("fonts/Mincho.fnt"),
                Gdx.files.internal("fonts/Mincho.png"), false);
        debugFont.getData().setScale(0.4f, 0.4f);
    }

    /**
     *
     * @param fileName name of map (json)
     */
    public void createMap(String fileName) {
        GameMapFactory mapFactory = new GameMapFactory(fileName);
        map1 = mapFactory.getMap();
        map2 = mapFactory.getMap();
    }
    public GameMap getGameMap1() { return map1; }
    public GameMap getGameMap2() { return map2; }
    public Player getDebugCam() { return debugCam; }
    public BitmapFont getFont() { return debugFont; }
    public OrthographicCamera getCam() {return cam;}
}
