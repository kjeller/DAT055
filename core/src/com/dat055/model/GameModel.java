package com.dat055.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.dat055.model.collision.CollisionHandler;
import com.dat055.model.entity.Player;
import com.dat055.model.map.GameMap;
import com.dat055.model.map.GameMapFactory;

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
        player1 = new Player(1, 80, 64, "red_penguin_64x80.png",
                "Towbie", 5, 5);
        player2= new Player(1, 80, 64, "blue_penguin_64x80.png",
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
