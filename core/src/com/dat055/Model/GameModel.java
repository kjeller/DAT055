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
    Player player;
    private OrthographicCamera cam;
    private CollisionHandler handler1;

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

        player = new Player(1, 80, 64, "red_penguin_64x80.png",
                "Towbie", 5, 5);
        handler1 = new CollisionHandler(player, map.getFrontTileMap());
    }

    @Override
    public void update(float deltaTime) {
        // Camera transition
        float lerp = 2f;
        Vector2 playerPosition = player.getPosition();
        Vector3 camPosition = cam.position;
        camPosition.x += (playerPosition.x - camPosition.x) * lerp * deltaTime;
        camPosition.y += (playerPosition.y - playerPosition.y) * lerp * deltaTime;
        cam.update();

        if(!isPaused) {
            //Todo: collisionhandler here
            handler1.checkCollision(player);


            // Player logic
            player.checkKeyboardInput();
            player.update();

            //TODO: Other entities here
        }
    }

    public GameMap getGameMap() { return map; }
    public Player getPlayer() { return player; }
    public OrthographicCamera getCam() {return cam;}
    public void setPause(Boolean bool) {isPaused = bool;}
}
