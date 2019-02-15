package com.dat055.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.dat055.Model.Entity.Player;
import com.dat055.Model.GameModel;
import com.dat055.Model.Map.GameMap;
import com.dat055.View.GameView;

import java.rmi.MarshalledObject;


public class GameController extends Controller {
    public enum Mode {FRONT, BACK}
    private Mode mode;

    private GameMap map1;
    private GameMap map2;
    private Player currentPlayer;
    private Player player1;
    private Player player2;
    private OrthographicCamera cam;

    private boolean isRotating;
    private boolean isPaused;
    private boolean isDebug;
    private boolean isMultiplayer;

    private float rotationTimer = 180f;
    private float rotation = 0;

    public GameController(GameModel model, GameView view) {
        super(model, view);
    }

    @Override
    public void update(float deltaTime) {
        if(!isPaused)
            updateCamera(deltaTime);    // Updates camera
        checkKeyboardInput();           // Handles keyboard input

        // Tile rotation map transition
        if(isRotating && !isPaused)
            rotationTimer+= 2f;
        if(rotationTimer >= 180f) {
            isRotating = false;
            rotationTimer = 180f;
        }
        rotation = mode == Mode.FRONT ? 180f + rotationTimer : rotationTimer; // Sets rotation for planes
        ((GameView)view).setRotation(rotation);

        // Time break
        if(!isRotating && !isPaused) {
            if(mode == Mode.FRONT)
                map1.update(deltaTime);
            else
                map2.update(deltaTime);
        }
    }

    /**
     * Updates the camera
     * @param deltaTime
     */
    private void updateCamera(float deltaTime) {
        float effectiveViewportWidth = cam.viewportWidth * cam.zoom;
        float effectiveViewportHeight = cam.viewportHeight * cam.zoom;

        // Camera transition to player
        float lerp = 2f;
        Vector2 playerPosition = currentPlayer.getPosition();
        Vector3 camPosition = cam.position;
        camPosition.x += Math.round((playerPosition.x - camPosition.x) * lerp * deltaTime);
        camPosition.y += Math.round((playerPosition.y - camPosition.y) * lerp * deltaTime);

        if(camPosition.x >= effectiveViewportWidth)
            cam.position.x = effectiveViewportWidth;

        if(camPosition.y >= effectiveViewportHeight)
            cam.position.y = effectiveViewportHeight;
        cam.update();
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch); // Render view
        ((GameView)view).setDebugString(String.format(
                "mode: %s\nrot: %.1f\nrot.timer: %s\nisRotating: %s\nisPaused: %s",
                mode, rotation, rotationTimer, isRotating, isPaused));
    }

    /**
     * Handles keyboard input for current player.
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
        ((GameModel)model).createMap(fileName);

        map1 = ((GameModel)model).getGameMap1();
        map2 = ((GameModel)model).getGameMap2();
        cam = ((GameModel)model).getCam();
        player1 = map1.getPlayer();
        player2 = map2.getPlayer();

        // Set default values
        isPaused = false;
        isRotating = false;
        isDebug = false;
        mode = Mode.FRONT;

        whosOnTop(mode);
    }

    /**
     * Decides who is the top player based on mode
     * @param mode FRONT or BACK
     */
    private void whosOnTop(Mode mode) {
        if(mode == Mode.FRONT) {
            ((GameView)view).setRotation(360f);
            currentPlayer = player1;
        } else {
            ((GameView)view).setRotation(0);
            currentPlayer = player2;
        }
    }

    private void toggleCurrentPlayer() {
        if(mode == Mode.FRONT) {
            currentPlayer = player2;
            mode = Mode.BACK;
        }
        else {
            currentPlayer = player1;
            mode = Mode.FRONT;
        }
        ((GameView)view).setMode(mode);

        rotationTimer = 0; // Resets timer
        isRotating = true; // Will start adding to rotation timer in update
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
        }
        ((GameView) view).setDebug(isDebug);
    }

    public String toString() {
        return String.format("-currentPlayer: %s \n-GameMap1: %s \n-GameMap2: %s", currentPlayer, map1, map2);
    }
}
