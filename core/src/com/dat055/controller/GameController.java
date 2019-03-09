package com.dat055.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.dat055.model.GameModel;
import com.dat055.model.entity.character.Player;
import com.dat055.model.map.GameMap;
import com.dat055.model.menu.Menu;
import com.dat055.model.menu.SettingsMenu;
import com.dat055.net.Server;
import com.dat055.view.GameView;

/**
 * The flow of the game and what is to
 * be rendered in {@link GameView} is determined here.
 * All of the non primitive objects are created by this
 * controller's {@link GameModel}.
 * Example: Moving camera, player and all of the other entities etc.
 * Methods like startGame() are used by a {@link MenuController} to start games.
 * @author Karl Strålman
 * @version 2019-02-25
 */
public class GameController extends Controller {
    /**
     * Mode that determines which map is currently active
     * and should be updated.
     */
    public enum Mode {FRONT, BACK}
    private Mode mode;

    //the mute boolean
    private boolean mute;

    // Objects that is created by GameModel
    private GameMap map1, map2;
    private Player currentPlayer, player1, player2;
    private OrthographicCamera cam;
    private Vector2 viewDistance;

    // Booleans that describes different game states
    private boolean isRotating, isPaused, isDebug,
            isMultiplayer, isRunning, isSingleMap;

    // Used to rotate a map
    private float rotationTimer;
    private float rotation;

    private float showMapNameTimer;

    private Server server;  // Used to communicate with a remote player

    public GameController() {
        super(new GameModel(), null);
        // view is created in startMap()
    }

    /**
     * Updates game - the flow of the game is determined here.
     * @param deltaTime time since last update
     */
    @Override
    public void update(float deltaTime) {
        if(!isPaused)
            updateCamera(deltaTime);        // Updates camera

        checkKeyboardInput();           // Handles keyboard input

        // Timer that is used to show name of map in gameview
        if(showMapNameTimer>=4) {
            ((GameView)view).setShowMapName(false);
        } else {
            showMapNameTimer += deltaTime;
        }

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
        if(!isRotating && !isPaused && !isMultiplayer) {
            if(mode == Mode.FRONT)
                map1.update(deltaTime);
            else
                map2.update(deltaTime);
        }

        // Multiplayer updates both maps
        if(isMultiplayer && !isSingleMap) {
            map1.update(deltaTime);
            map2.update(deltaTime);
        }

        // If there only is one map, do not check the other map
        if(!isSingleMap) {
            // Restart map if either of the maps are in that state (player is dead)
            if (map1.getRestart() || map2.getRestart())
                resetMap();
            if(map1.isFinished() && map2.isFinished())
                nextMap();
        } else {
            if(map1.getRestart())
                resetMap();
            // Change to next map if player1
            if(map1.isFinished()) {
                nextMap();
            }
        }

        // Change to next map if both players have reached their goals
        if(map1.isFinished() && map2.isFinished()) {
            nextMap();
        }

        /*
         * Multiplayer updates received from another player
         * and sent to that player.
         */
        if(isMultiplayer && server.isRunning()) {
            // Only send updates if player is in motion
            if(currentPlayer.getInMotion())
                server.sendPlayerUpdate(currentPlayer);

            // Update remote player's character
            if(currentPlayer == player1) {
                server.updatePlayer(player2);
            } else {
                server.updatePlayer(player1);
            }
        }
    }

    /**
     * Updates the camera to follow player and to be in bounds.
     * @param deltaTime time since last update
     */
    private void updateCamera(float deltaTime) {
        // Camera transition to player
        float lerp = 2f;
        Vector2 playerPosition = currentPlayer.getPosition();
        Vector3 camPosition = cam.position;

        camPosition.x += Math.round((playerPosition.x - camPosition.x) * lerp * deltaTime);
        camPosition.y += Math.round((playerPosition.y - camPosition.y) * lerp * deltaTime);

        // Forces camera inside bounds no zoom.
        if(camPosition.x + viewDistance.x > map1.getWidthPixels())
            cam.position.x = (int)(map1.getWidthPixels() - viewDistance.x);

        if(camPosition.x - viewDistance.x < 0)
            cam.position.x = (int) viewDistance.x;

        if(camPosition.y + viewDistance.y > map1.getHeightPixels())
            cam.position.y = (int)(map1.getHeightPixels() - viewDistance.y);
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
            if(!isMultiplayer && !isSingleMap)
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
     * Calls {@link GameModel} tied to this controller
     * to create a game map for a specific map.
     * @param fileName of a json file in assets/maps/
     */
    private boolean startMap(String fileName) {
        // if there is no more map after current
        if(fileName.equals("None.")) {
            model.stopMusic();
            isRunning = false;
            ((MenuController)ctrl).swapMenu("Finish");
            ((MenuController)ctrl).playMusic();
            return true;
        }
        isSingleMap = false;

        ((GameModel)model).createMap(fileName); // Tell model to create map

        // Get maps
        map1 = ((GameModel)model).getGameMap1();
        map2 = ((GameModel)model).getGameMap2();

        if(map2 == null)
            isSingleMap = true;

        cam = ((GameModel)model).getCam();
        player1 = map1.getPlayer();
        if(!isSingleMap)
            player2 = map2.getPlayer();

        // Start running
        isRunning = true;
        isRotating = false;
        isPaused = false;
        isDebug = false;

        // Default value for rotationTimer
        rotationTimer = 180f;

        // Default value for map name timer
        showMapNameTimer = 0;

        // Create a new view
        view = new GameView((GameModel)model);

        // Sets current player based on mode
        whoIsOnTop(mode);

        // Set camera position to current player to avoid panning to player at start
        Vector2 camStartPos = currentPlayer.getPosition().cpy();
        viewDistance = ((GameModel)model).getCamViewDistance();
        cam.position.set(new Vector3(camStartPos.x, camStartPos.y, 0));

        // Set a server for a player to send updates
        if(isMultiplayer)
            currentPlayer.setServer(server);

        // Set view properties

        ((GameView)view).setSingleMap(isSingleMap);
        ((GameView)view).setShowMapName(true);

        playMusic();
        return true;
    }

    /**
     * Mutes the music
     * @param foo Boolean to mute the music
     */
    public void setMute(boolean foo){mute = foo;}

    /**
     * Starts and stops the music
     */
    private void playMusic(){
        if (!mute) {
            model.playMusic("map_01");
            return;
        } model.stopMusic();
    }

    /**
     * Creates next map and starts it.
     */
    private void nextMap() { startMap(((GameModel)model).getNextMap()); }

    /**
     * Re-creates the current map and starts it.
     */
    private void resetMap() { startMap(((GameModel)model).getCurrentMap()); }

    /**
     * Starts a singleplayer map where player toggles between
     * playable characters. Mainmenu will call this to start map.
     * @param fileName name of map that will be created with startMap().
     */
    boolean startSingleplayerMap(String fileName) {
        isMultiplayer = false;
        mode = Mode.FRONT;
        return startMap(fileName);
    }

    /**
     * Creates a server and starts waiting for other peer to connect.
     * Starts a multiplayer map where each player is assigned a
     * playable character. Switching between characters not enabled.
     * {@link MenuController} will call this to start map. Host then needs to wait for
     * another player to join server.
     * @param fileName name of map that will be created with startMap()
     */
    boolean startMultiplayerMap(String fileName, String name) {
        server = new Server(name, 1337);
        if(!server.startServer(fileName))
            return false;

        // Wait for server to start
        while(true) {
            if(!server.isRunning()) {
                try { Thread.sleep(0);
                } catch (InterruptedException ignored) {}
            } else { break; }
        }

        //Host decides this from menu
        mode = Mode.FRONT;
        isMultiplayer = true;
        startMap(fileName);
        return true;
    }

    /**
     * Joins server and creates own server to communicate with other server.
     * Then starts a multiplayer map where each player is assigned a
     * playable character. Switching between characters not enabled.
     * @param addr IP of other server
     */
    boolean joinMultiplayerMap(String addr, String name) {
        server = new Server(name, 1337);
        if(!server.startServerAndClient(addr))
            return false;

        // Waits for server to start
        while(true) {
            if(!server.isRunning()) {
                try {
                    Thread.sleep(0);
                } catch (InterruptedException ignored) {}
            } else { break; }
        }
        mode = Mode.BACK;
        isMultiplayer = true;
        startMap(server.getChosenMap());

        return true;
    }

    // === Toggle methods and helper methods ==

    /**
     * Closes current game session and multiplayer session
     * if running.
     */
        void closeGame() {
        if(isMultiplayer)
            server.close();
    }

    /**
     * Decides who is the top player based on {@link Mode}.
     * Sets currentPlayer to player on active plane.
     * @param mode FRONT or BACK
     */
    private void whoIsOnTop(Mode mode) {
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
    /**
     * Toggles current player to other player.
     * Only used in singeplayer mode. What player
     * that will be toggles is determined by current {@link Mode}
     */
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
    /**
     * Toggles pause and tells {@link MenuController} to swap to the pause menu
     */
    void togglePause() {
        isPaused = !isPaused;
        if(isPaused) ((MenuController)ctrl).swapMenu("Pause");
        else ((MenuController)ctrl).clearStage();
    }

    /**
     * Toggles debug mode where every entity's rectangle is rendered and
     * properties are written to screen.
     */
    private void toggleDebug() {
        isDebug = !isDebug;
        ((GameView) view).setDebug(isDebug);
    }

    /**
     * @return current map properties
     */
    public String toString() {
        return String.format("-currentPlayer: %s \n-GameMap1: %s \n-GameMap2: %s", currentPlayer, map1, map2);
    }

    /**
     * Sets {@link MenuController} for this controller.
     * @param ctrl sets menucontroller for this controller
     */
    public void setController(MenuController ctrl) { super.setController(ctrl); }

    /**
     * @return true if game is running
     */
    public boolean isRunning() { return isRunning;}
}