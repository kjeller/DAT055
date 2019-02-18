package com.dat055.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.dat055.model.entity.Player;
import com.dat055.model.GameModel;
import com.dat055.model.map.GameMap;
import com.dat055.net.Multiplayer;
import com.dat055.net.PeerNetwork;
import com.dat055.view.GameView;


public class GameController extends Controller {
    public enum Mode {FRONT, BACK}
    private Mode mode;

    private GameMap map1, map2;
    private Player currentPlayer, player1, player2;
    private OrthographicCamera cam;

    private boolean isRotating, isPaused, isDebug, isMultiplayer;

    private boolean isRunning = false;

    private float rotationTimer = 180f;
    private float rotation = 0;


    private PeerNetwork server;

    public GameController(GameModel model, GameView view) {
        super(model, view);
    }

    @Override
    public void update(float deltaTime) {
        if(map1 != null || map2 != null) {
            if(!isPaused)
                updateCamera(deltaTime);    // Updates camera
            checkKeyboardInput();           // Handles keyboard input

            // tile rotation map transition
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

            if(isMultiplayer) {
                server.sendPlayerUpdate(currentPlayer);
            }
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
    private void startMap(String fileName) {
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
        isRunning = true;

        whosOnTop(mode);
    }

    /**
     * Starts a singleplayer map where player toggles between
     * playable characters. Mainmenu will call this to start map.
     * @param fileName name of map that will be created with startMap()
     */
    public void startSingleplayerMap(String fileName) {
        startMap(fileName);

        isMultiplayer = false;
        mode = Mode.FRONT;

        whosOnTop(mode);
    }

    /**
     * Starts a multiplayer map where each player is assigned a
     * playable character. Switching between characters not enabled.
     * Mainmenu will call this to start map. Host then needs to wait for
     * another player to join server.
     * @param fileName name of map that will be created with startMap()
     */
    public void startMultiplayerMap(String fileName) {
        startMap(fileName);

        isMultiplayer = true;
        //Todo: start server here maybe waiting for a player to join
        server = new PeerNetwork(1337);

        // Wait for peer to join
        while(server.getStatus()) { }

        System.out.println("Noice");

        //Host decides this from menu
        mode = Mode.FRONT;
        whosOnTop(mode);
    }

    /**
     * Joins server and creates own server to communicate with other server
     * @param addr IP of other server
     */
    public void joinMultiplayerMap(String addr) {
        server = new PeerNetwork(1337, addr);

        // Wait for peer to accept join request
        while(server.getStatus()) {

        }
        // get map filename
        isMultiplayer = true;
        mode = Mode.BACK;
        whosOnTop(mode);
        startMap("maps/map_0.json");
        // TODO: Implement get map

        //start sending a shit ton of positions for currentPlayer
    }

    /**
     * Decides who is the top player based on mode
     * Sets currentPlayer to player on active plane
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
        ((GameView)view).setMode(mode);
    }

    private void toggleCurrentPlayer() {
        if(!isMultiplayer) {
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
    }
    public void togglePause() {
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

    public boolean isPaused() { return  isPaused;}
    public boolean isRunning() { return isRunning;}
}
