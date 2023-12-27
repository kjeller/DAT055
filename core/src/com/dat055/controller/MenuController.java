package com.dat055.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.*;
import com.dat055.model.menu.*;
import com.dat055.view.MenuView;
import com.dat055.model.MenuModel;

/**
 * This is where the menu system is controlled, in order to do this, it employs some flags.
 * The draw method that calls {@link MenuView}.
 * The method that are used to update the {@link MenuModel} are located here.
 * It also handles the communication with {@link GameController}. It, in turn, uses the method swap()
 * to bring up the pause menu in-game.
 * @author Erik Börne
 * @version 2019-03-06
 */
public class MenuController extends Controller{
    public boolean multiplayer;
    public boolean host;
    private boolean isCharOne;
    private boolean charOneBlocked = false;
    private boolean charTwoBlocked = false;
    private boolean mute;
    public String currentMap;
    public String name;

    /**
     * The default constructor for {@link MenuController}, which initializes the controller and includes the
     * {@link Menu}s. This is done here instead of in the Menu Model since it needs the controller.
     */
    public MenuController() {
        super(new MenuModel(), new MenuView());
        mute = false;

        ((MenuModel)model).includeMenu("Main", new MainMenu(this));
        InputMultiplexer multiInput = new InputMultiplexer();
        multiInput.addProcessor(((MenuModel)model).getStage());
        multiInput.addProcessor(new InputAdapter());
        Gdx.input.setInputProcessor(multiInput);

        swapMenu("Main");
        ((MenuModel)model).includeMenu("Multiplayer", new MultiMenu(this));
        ((MenuModel)model).includeMenu("Pause", new PauseMenu(this));
        ((MenuModel)model).includeMenu("Select", new SelectMenu(this));
        ((MenuModel)model).includeMenu("Character", new CharacterMenu(this));
        ((MenuModel)model).includeMenu("Settings", new AlternativeSettingsMenu(this));
        ((MenuModel)model).includeMenu("Credits", new CreditsMenu(this));
        ((MenuModel)model).includeMenu("Finish", new FinishMenu(this));
        playMusic();
    }

    /**
     * This method updates the {@link MenuModel}.
     * @param dt The time elapsed since last update.
     */
    @Override
    public void update(float dt) {
        ((MenuModel)model).update();
    }

    /**
     * Calls the draw method located in {@link MenuView}.
     */
    public void render() { ((MenuView)view).draw(((MenuModel)model).getStage()); }

    /**
     * Resizes the {@link Stage} in {@link MenuModel}.
     */
    public void resize(int width, int height) {
        ((MenuModel)model).resize(width, height);
        update(0);
    }

    /**
     * Calls the startGame method from the {@link GameController} with the selected map name. It also keeps track of
     * if the session is multiplayer or singleplayer.
     * @return A boolean that reports if the method is successful or not.
     */
    public boolean startGame() {
        if (multiplayer) {
            System.out.println("Hosting the map: maps/" + currentMap + ".json as: "+ name);
            return ((GameController)ctrl).startMultiplayerMap("maps/" + currentMap + ".json", name);
        } else {
            System.out.println("Starting the map: maps/" + currentMap + ".json");
            return ((GameController)ctrl).startSingleplayerMap("maps/" + currentMap + ".json");
        }
    }

    /**
     * Calls the joinGame method from the {@link GameController} with the selected ip.
     * @param ip the IP to join.
     * @return A boolean that reports if the method is successful or not.
     */
    public boolean joinGame(String ip) {
        return ((GameController)ctrl).joinMultiplayerMap(ip, name);
    }

    /**
     * This method clears the {@link Stage}.
     */
    public void clearStage() {
        ((MenuModel)model).getStage().clear();
        model.stopMusic();
    }

    /**
     * A method used to get the {@link Stage}'s width.
     * @return The width of the stage.
     */
    public float getWidth()  { return ((MenuModel)model).getStage().getWidth();  }

    /**
     * A method used to get the {@link Stage}'s height.
     * @return The height of the stage.
     */
    public float getHeight() { return ((MenuModel)model).getStage().getHeight(); }

    /**
     * Checks if the current client is character one (for use in {@link CharacterMenu}).
     * @return True, if client is character one, otherwise false.
     */
    public boolean isCharOne() { return isCharOne; }

    /**
     * Checks if the current co-op partner is character one (for use in {@link CharacterMenu}).
     * @return True, if partner is character one, otherwise false.
     */

    public boolean isCharOneBlocked() { return charOneBlocked; }

    /**
     * Checks if the current co-op partner is character two (for use in {@link CharacterMenu}).
     * @return True, if partner is character two, otherwise false.
     */
    public boolean isCharTwoBlocked() { return charTwoBlocked; }

    /**
     * Swaps the {@link Menu} to the specified key.
     * @param menu The key to the menu.
     */
    public void swapMenu(String menu) { ((MenuModel)model).swapMenu(menu); }

    /**
     * Swaps to {@link MainMenu} and stops playing music.
     */
    public void swapToMain() {
        swapMenu("Main");
        getCtrl().getModel().stopMusic();
        getModel().playMusic("title");
    }

    /**
     * Closes the game
     */
    public void closeGame() { ((GameController)ctrl).closeGame();}

    /**
     * Mutes the music.
     * @param state Boolean to mute the music.
     */
    public void setMute(boolean state) {mute = state;}

    /**
     * A method to play the music.
     */
    public void playMusic(){
        if(!mute)
            model.playMusic("title");
        else
            model.stopMusic();
    }

    /**
     * Toggles the pause method located in {@link GameController}.
     */
    public void togglePause() { ((GameController)ctrl).togglePause(); }

    /**
     * Gets the {@link GameController} is in use.
     * @param ctrl The that controls the game session (start, pause, join etc.).
     */
    public void setController(GameController ctrl) {
        super.setController(ctrl);
    }

    /**
     * This method is allows the {@link GameController} to be reached from the menus.
     * @return The current {@link GameController}.
     */
    public GameController getCtrl() { return (GameController)ctrl; }
}