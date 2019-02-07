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
    private static GameModel instance = null;
    // Determines what character the player is controlling (single player)
    public enum Mode {
        Front, Back
    }

    Boolean isPaused;

    private GameMap map;
    Player currentPlayer;
    Player player1;
    Player player2;
    private OrthographicCamera cam;
    private CollisionHandler handler1;
    private CollisionHandler handler2;

    private GameModel() {
        isPaused = false;
    }

    public static synchronized GameModel getInstance() {
        if(instance == null)
            instance = new GameModel();

        return instance;
    }

    /**
     * Creates a GameMap
     * @param fileName
     */
    public void createMap(String fileName, Integer tileSize) {
        GameMapFactory mapFactory = new GameMapFactory();
        map = mapFactory.getMap(fileName, tileSize);
        Mode mode = Mode.Front;
        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        player1 = new Player(1, 80, 64, "red_penguin_64x80.png",
                "Towbie", 5, 5);
        player2= new Player(1, 80, 64, "blue_penguin_64x80.png",
                "Kjello", 5, 5);
        handler1 = new CollisionHandler(player1, map.getFrontTileMap());
        handler2 = new CollisionHandler(player2, map.getBackTileMap());

        currentPlayer = player1;
    }

    @Override
    public void update(float deltaTime) {
        // Camera transition
        float lerp = 2f;
        Vector2 playerPosition = currentPlayer.getPosition();
        Vector3 camPosition = cam.position;
        camPosition.x += (playerPosition.x - camPosition.x) * lerp * deltaTime;
        camPosition.y += (playerPosition.y - camPosition.y) * lerp * deltaTime;
        cam.update();

        if(!isPaused) {
            //Todo: collisionhandler here
            handler1.checkCollision(player1);
            handler1.checkCollision(player2);

            player1.update(); // Updates player position, health etc.
            player2.update();

            //TODO: Other entities here
        }
    }

    public GameMap getGameMap() { return map; }
    public Player getCurrentPlayer() { return currentPlayer; }
    public Player getPlayer1() { return player1; }
    public Player getPlayer2() { return player2; }
    public OrthographicCamera getCam() {return cam;}
    public void setPause(Boolean bool) {isPaused = bool;}
    public void setCurrentPlayer(Player player) { currentPlayer = player; }
    public void toggleCurrentPlayer() {
        if(currentPlayer == player1)
            currentPlayer = player2;
        else
            currentPlayer = player1;
    }
}
