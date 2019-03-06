package com.dat055.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dat055.model.menu.SettingsMenu;
import com.dat055.model.menu.*;
import com.dat055.view.MenuView;
import com.dat055.model.MenuModel;

public class MenuController extends Controller{
    private boolean visible;
    public boolean multiplayer; // A flag
    public boolean host;
    private boolean isCharOne;
    private boolean charOneBlocked = false;
    private boolean charTwoBlocked = false;
    public String currentMap;
    public String name;

    public MenuController() {
        super(new MenuModel(), new MenuView());
        visible = true;

        ((MenuModel)model).includeMenu("Main", new MainMenu(this));
        Gdx.input.setInputProcessor(((MenuModel)model).getStage());

        swapMenu("Main");
        ((MenuModel)model).includeMenu("Multiplayer", new MultiMenu(this));
        ((MenuModel)model).includeMenu("Pause", new PauseMenu(this));
        ((MenuModel)model).includeMenu("Select", new SelectMenu(this));
        ((MenuModel)model).includeMenu("Character", new CharacterMenu(this));
        ((MenuModel)model).includeMenu("Settings", new SettingsMenu(this));
        model.playMusic("title");
    }

    @Override
    public void update(float dt) {
        ((MenuModel)model).update();
    }

    @Override
    public void render() {
        if (visible) ((MenuView)view).draw(((MenuModel)model).getStage());
    }

    @Override
    public void resize(int width, int height) {
        ((MenuModel)model).resize(width, height);
    }

    public boolean startGame() {
        if (multiplayer) {
            System.out.println("Hosting the map: maps/" + currentMap + ".json as: "+ name);
            return ((GameController)ctrl).startMultiplayerMap("maps/" + currentMap + ".json", name);
        } else {
            System.out.println("Starting the map: maps/" + currentMap + ".json");
            return ((GameController)ctrl).startSingleplayerMap("maps/" + currentMap + ".json");
        }
    }

    public boolean joinGame(String ip) {
        return ((GameController)ctrl).joinMultiplayerMap(ip, name);
    }

    public void toggleVisibility() {
        if (visible)
            visible = false;
        else
            visible = true;
    }

    public boolean isVisible() {
        return visible;
    }

    public void clearStage() {
        ((MenuModel)model).getStage().clear();
        model.stopMusic();
    }

    public float getWidth()  { return ((MenuModel)model).getStage().getWidth();  }
    public float getHeight() { return ((MenuModel)model).getStage().getHeight(); }

    public boolean isCharOne() { return isCharOne; }
    public boolean isCharOneBlocked() { return charOneBlocked; }
    public boolean isCharTwoBlocked() { return charTwoBlocked; }

    public void setMultiplayer(boolean b) { multiplayer = b; }

    public void swapMenu(String menu) { ((MenuModel)model).swapMenu(menu); }

    public void togglePause() { ((GameController)ctrl).togglePause(); }
    /*
    public boolean startMultiplayer(String mapPath, String name) {
        return ((GameController)ctrl).startMultiplayerMap(mapPath, name);
    }

    public boolean joinMultiplayer(String ip, String name) {
        return ((GameController)ctrl).joinMultiplayerMap(ip, name);
    }
    */

    public void setController(GameController ctrl) {
        super.setController(ctrl);
    }
    public GameController getCtrl() { return (GameController)ctrl; }
}