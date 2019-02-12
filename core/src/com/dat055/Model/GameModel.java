package com.dat055.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.dat055.Model.Entity.DebugCamera;
import com.dat055.Model.Entity.Player;
import com.dat055.Model.Map.GameMap;
import com.dat055.Model.Map.GameMapFactory;

public class GameModel extends Model {
    private GameMap map;
    private DebugCamera debugCam;
    private OrthographicCamera cam;

    public GameModel(){
        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        debugCam = new DebugCamera(new Vector2(Vector2.Zero));
    }

    /**
     *
     * @param fileName name of map (json)
     * @param tileSize size of one tile in tilemap
     */
    public void createMap(String fileName, int tileSize) {
        GameMapFactory mapFactory = new GameMapFactory();
        map = mapFactory.getMap(fileName, tileSize);
    }
    public GameMap getGameMap() { return map; }
    public Player getDebugCam() { return debugCam; }
    public OrthographicCamera getCam() {return cam;}
}
