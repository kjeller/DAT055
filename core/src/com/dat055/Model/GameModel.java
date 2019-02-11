package com.dat055.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.dat055.Model.Entity.DebugCamera;
import com.dat055.Model.Entity.Entity;
import com.dat055.Model.Entity.Player;
import com.dat055.Model.Map.GameMap;
import com.dat055.Model.Map.GameMapFactory;

import java.util.ArrayList;

public class GameModel extends Model {
    public enum Mode {FRONT, BACK}
    private GameMap map;
    private Mode mode;
    private Player player1;
    private Player player2;
    private DebugCamera debugCam;
    private OrthographicCamera cam;
    public ArrayList<Entity> entities;

    public GameModel(){
        mode = Mode.BACK;
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
        entities = map.getEntities();
        player1 = map.getPlayer1();
        player2 = map.getPlayer2();
    }
    public void setMode(Mode mode) {this.mode=mode;}

    public GameModel.Mode getMode() {return this.mode;}
    public GameMap getGameMap() { return map; }
    public Player getPlayer1() { return player1; }
    public Player getPlayer2() { return player2; }
    public Player getDebugCam() { return debugCam; }
    public OrthographicCamera getCam() {return cam;}
}
