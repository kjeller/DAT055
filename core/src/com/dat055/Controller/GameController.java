package com.dat055.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.dat055.Model.Collision.CollisionHandler;
import com.dat055.Model.Entity.Entity;
import com.dat055.Model.Entity.Player;
import com.dat055.Model.GameModel;
import com.dat055.Model.Map.GameMap;
import com.dat055.View.GameView;

import java.util.ArrayList;

public class GameController extends Controller {
    public enum Mode {FRONT, BACK}

    private Mode mode;
    private Player currentPlayer;
    private Player player1;
    private Player player2;
    private ArrayList<Entity> entitiesFront;
    private ArrayList<Entity> entitiesBack;
    private Camera cam;
    private CollisionHandler handler1;
    private CollisionHandler handler2;

    private boolean isRotating;
    private boolean isPaused;
    private boolean isDebug;
    private boolean isBackActive;

    private float rotationTimer = 0;

    public GameController(GameModel model, GameView view) {
        super(model, view);
        mode = Mode.FRONT;
    }

    @Override
    public void update(float deltaTime) {
        checkKeyboardInput(); // Handles keyboard input

        // Tile rotation map transition
        if(isRotating) {
            rotationTimer+= 2f;
        }

        if(rotationTimer >= 180f) {
            isRotating = false;
            rotationTimer = 180f;
        }
        ((GameView)view).setRotationTimer(rotationTimer);

        // Camera transition
        float lerp = 2f;
        Vector2 playerPosition = currentPlayer.getPosition();
        Vector3 camPosition = cam.position;
        camPosition.x += (playerPosition.x - camPosition.x) * lerp * deltaTime;
        camPosition.y += (playerPosition.y - camPosition.y) * lerp * deltaTime;
        cam.update();

        if(!isPaused && !isRotating) {
            // Updates entities position, health etc. depending on mode
            if(!isBackActive) {
                for(Entity entity : entitiesFront) {
                    entity.update();
                }
            }
            else {
                for(Entity entity : entitiesBack) {
                    entity.update();
                }
            }
            handler1.checkCollision(player1);
            if (player1.getHook() != null)
                handler1.checkCollision(player1.getHook());

            handler2.checkCollision(player2);
            if (player2.getHook() != null)
                handler2.checkCollision(player2.getHook());

            if(isDebug)
                ((GameModel)model).getDebugCam().update();
        }
    }

    /**
     * Handles keyboard input for a specific player.
     */
    private void checkKeyboardInput() {
        // Player input movements
        if(!isRotating) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                currentPlayer.move(-1);
                currentPlayer.setLookingDirection(new Vector2(-1, currentPlayer.getDirection().y));
                currentPlayer.setMoving(true);
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                currentPlayer.move(1);
                currentPlayer.setLookingDirection(new Vector2(1, currentPlayer.getDirection().y));
                currentPlayer.setMoving(true);
            } else {
                currentPlayer.setMoving(false);
                currentPlayer.move(0);
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.CONTROL_LEFT))
                currentPlayer.attack();

            if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
                currentPlayer.jump();

            if(Gdx.input.isKeyJustPressed(Input.Keys.T))
                toggleCurrentPlayer();
        }

        // Toggles pause menu
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            togglePause();

        // Enter debug mode
        if(Gdx.input.isKeyJustPressed((Input.Keys.B)))
            toggleDebug();

        // Easier switching between players in debug mode
        if(isDebug) {
            if(Gdx.input.isKeyJustPressed((Input.Keys.NUM_1)))
                currentPlayer = player1;

            if(Gdx.input.isKeyJustPressed((Input.Keys.NUM_2)))
                currentPlayer = player2;
        }
    }

    /**
     * Calls gamemodel to create a game map for a specific map.
     * @param fileName of a json file in assets/maps/
     */
    public void startMap(String fileName) {
        ((GameModel)model).createMap(fileName, 64);

        GameMap map = ((GameModel)model).getGameMap();
        handler1 = new CollisionHandler(map.getFrontTileMap());
        handler2 = new CollisionHandler(map.getBackTileMap());
        player1 = map.getPlayer1();
        player2 = map.getPlayer2();
        entitiesFront = map.getEntitiesFront();
        entitiesBack = map.getEntitiesBack();

        isPaused = false;
        isBackActive = true;
        isRotating = false;
        isDebug = false;

        currentPlayer = player2;
        cam = ((GameModel)model).getCam();
    }

    private void toggleCurrentPlayer() {
        if(!isBackActive) {
            isBackActive = true;
            currentPlayer = player2;
        }
        else {
            isBackActive = false;
            currentPlayer = player1;
        }

        rotationTimer = 0; // Resets timer
        isRotating = true; // Will start adding to rotation timer in update
        ((GameView)view).setBackActive(isBackActive); // Notfies view to draw correctly

        if(isDebug)
            currentPlayer = ((GameModel)model).getDebugCam();
    }
    private void togglePause() {
        if(isPaused)
            isPaused = false;
        else
            isPaused = true;
    }
    private void toggleDebug() {
        if(isDebug)
            isDebug = false;
        else {
            isDebug = true;
            currentPlayer = ((GameModel)model).getDebugCam();
        }
        ((GameView) view).setDebug(isDebug);
    }
}
