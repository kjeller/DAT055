package com.dat055.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.dat055.Model.Collision.CollisionHandler;
import com.dat055.Model.Entity.Player;
import com.dat055.Model.Map.GameMap;
import com.dat055.Model.Map.GameMapFactory;

public class GameModel extends Model {
    private GameMap map;
    private Player player1;
    private Player player2;
    private OrthographicCamera cam;
    private CollisionHandler handler1;

    public GameModel(){
        initialize();
    }

    @Override
    public void initialize() {
        player1 = new Player(new Vector2(10, 100), 80, 64, "red_penguin_64x80.png",
                "Towbie", 5, 5);
        player2= new Player(new Vector2(20, 100), 80, 64, "blue_penguin_64x80.png",
                "Kjello", 5, 5);
        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    /**
     * Creates a GameMap
     * @param fileName
     */
    public void createMap(String fileName, Integer tileSize) {
        GameMapFactory mapFactory = new GameMapFactory();
        map = mapFactory.getMap(fileName, tileSize);
    }

    public GameMap getGameMap() { return map; }
    public Player getPlayer1() { return player1; }
    public Player getPlayer2() { return player2; }
    public OrthographicCamera getCam() {return cam;}
    public CollisionHandler getHandler1() { return handler1; }

    public void setHandler1(CollisionHandler handler1) {this.handler1 = handler1; }
}
