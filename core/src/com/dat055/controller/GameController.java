package com.dat055.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.dat055.model.GameModel;
import com.dat055.model.entity.Player;
import com.dat055.model.map.GameMap;
import com.dat055.net.PeerNetwork;
import com.dat055.view.GameView;


public class GameController extends Controller {
    public enum Mode {FRONT, BACK}
    private Mode mode;

    private GameMap map1, map2;
    private Player currentPlayer, player1, player2;
    private OrthographicCamera cam;

    private boolean isRotating, isPaused, isDebug, isMultiplayer, isRunning;

    private float rotationTimer;
    private float rotation;

    private PeerNetwork net;

    public GameController() {
        super(new GameModel(), null);
        // view is created in startMap()
    }

    @Override
    public void update(float deltaTime) {
        if(!isPaused)
            updateCamera(deltaTime);        // Updates camera
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

        if(isMultiplayer && net.isConnected()) {
            net.sendPlayerUpdate(currentPlayer);

            if(currentPlayer == player1) {
                net.updatePlayer(player2);
            } else {
                net.updatePlayer(player1);
            }
        }
    }

    /**
     * Updates the camera
     * @param deltaTime
     */
    private void updateCamera(float deltaTime) {
        float effectiveViewportWidth = cam.viewportWidth * cam.zoom ;
        float effectiveViewportHeight = cam.viewportHeight * cam.zoom;

        // Camera transition to player
        float lerp = 2f;
        Vector2 playerPosition = currentPlayer.getPosition();
        Vector3 camPosition = cam.position;
        camPosition.x += Math.round((playerPosition.x - camPosition.x) * lerp * deltaTime);
        camPosition.y += Math.round((playerPosition.y - camPosition.y) * lerp * deltaTime);

        if(camPosition.x  >= ((map1.getWidth() - 10) *  60) ) {
            cam.position.x = (map1.getWidth() -10) *  60 ;
        }
        if(camPosition.x - 66*10 <= 0)
            cam.position.x = 66*10;

        if(camPosition.y >= (map1.getWidth() -10) *  60 )
            cam.position.y = (map1.getWidth() -10) *  60 ;
        cam.update();
    }

    @Override
    public void render(PolygonSpriteBatch batch) {
        super.render(batch); // Render view
        ((GameView)view).setDebugString(String.format(
                "mode: %s\nrot: %.1f\nrot.timer: %s\nisRotating: %s\nisPaused: %s\nisDebug: %s\nisMultiplayer: %s",
                mode, rotation, rotationTimer, isRotating, isPaused, isDebug, isMultiplayer));
    }

    /**
     * Handles keyboard input for current player.
     */
    private void checkKeyboardInput() {
        // Player input movements
        if(!isRotating && !isPaused) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                currentPlayer.move(-1);
                currentPlayer.setLookingDirectionX(-1);
                currentPlayer.setMoving(true);
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                currentPlayer.move(1);
                currentPlayer.setLookingDirectionX(1);
                currentPlayer.setMoving(true);
            } else {
                currentPlayer.setMoving(false);
                currentPlayer.move(0);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                currentPlayer.setLookingDirectionY(1);
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                currentPlayer.setLookingDirectionY(-1);
            } else {
                currentPlayer.setLookingDirectionY(0);
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.CONTROL_LEFT))
                currentPlayer.attack();
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
                currentPlayer.jump();
            if(!isMultiplayer)
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
    private boolean startMap(String fileName) {
        ((GameModel)model).createMap(fileName);

        map1 = ((GameModel)model).getGameMap1();
        map2 = ((GameModel)model).getGameMap2();
        cam = ((GameModel)model).getCam();
        player1 = map1.getPlayer();
        player2 = map2.getPlayer();


        // Start running
        isRunning = true;
        isRotating = false;
        isPaused = false;
        isDebug = false;

        view = new GameView((GameModel)model);

        rotationTimer = 180f;

        // Sets current player based on mode
        whosOnTop(mode);

        // Set camera position to current player to avoid panning to player at start
        Vector2 camStartPos = currentPlayer.getPosition().cpy();
        cam.position.set(new Vector3(camStartPos.x, camStartPos.y, 0));
        model.playMusic("map_01");
        return true; //TODO: Fix a return false which indicates if map created successfully or not
    }

    /**
     * Starts a singleplayer map where player toggles between
     * playable characters. Mainmenu will call this to start map.
     * @param fileName name of map that will be created with startMap()
     */
    public boolean startSingleplayerMap(String fileName) {
        isMultiplayer = false;
        mode = Mode.FRONT;
        return startMap(fileName);
    }

    /**
     * Starts a multiplayer map where each player is assigned a
     * playable character. Switching between characters not enabled.
     * Mainmenu will call this to start map. Host then needs to wait for
     * another player to join net.
     * @param fileName name of map that will be created with startMap()
     */
    public boolean startMultiplayerMap(String fileName, String name) {
        net = new PeerNetwork(name, 1337, fileName);

        System.out.println("Map created");

        //Host decides this from menu
        mode = Mode.FRONT;
        isMultiplayer = true;
        startMap(fileName);
        return true;
    }

    /**
     * Joins net and creates own net to communicate with other net
     * @param addr IP of other net
     */
    public boolean joinMultiplayerMap(String addr, String name) {
        net = new PeerNetwork(name, addr, 1337);

        mode = Mode.BACK; //TODO: This will be set from message from other peer
        while(!net.isReady()); // wait for map to be set
        System.out.println("Map choosen from server: " + net.getChoosenMap());
        startMap(net.getChoosenMap());
        // TODO: Implement get map
        isMultiplayer = true;

        return true;
    }

    // === Toggle methods and helper methods ==

    /**
     * Decides who is the top player based on mode
     * Sets currentPlayer to player on active plane
     * @param mode FRONT or BACK
     */
    private void whosOnTop(Mode mode) {
        if(mode == Mode.FRONT) {
            rotation = 360f;
            currentPlayer = player1;
        } else {
            rotation = 0;
            currentPlayer = player2;
        }
        ((GameView)view).setRotation(rotation);
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
        isPaused = !isPaused;
        if(isPaused) {
            ((MenuController)ctrl).swapMenu("Pause");
        } else {
            ((MenuController)ctrl).clearStage();
        }
        //((MenuController)ctrl).setVisible(isPaused);
    }
    private void toggleDebug() {
        isDebug = !isDebug;
        ((GameView) view).setDebug(isDebug);
    }

    public String toString() {
        return String.format("-currentPlayer: %s \n-GameMap1: %s \n-GameMap2: %s", currentPlayer, map1, map2);
    }
    public void setController(MenuController ctrl) { super.setController(ctrl); }
    public boolean isPaused() { return  isPaused;}
    public boolean isRunning() { return isRunning;}
}