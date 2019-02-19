package com.dat055.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dat055.model.menu.*;
import com.dat055.view.MenuView;
import com.dat055.model.MenuModel;

public class MenuController extends Controller{
    private boolean visible;
    private boolean multiplayer;
    public String currentMap;

    public MenuController() {
        super(new MenuModel(), new MenuView());
        visible = true;

        ((MenuModel)model).includeMenu("Main", new MainMenu(this));
        Gdx.input.setInputProcessor(((MenuModel)model).getStage());

        swapMenu("Main");

        ((MenuModel)model).includeMenu("Multiplayer", new MultiMenu(this));
        ((MenuModel)model).includeMenu("Pause", new PauseMenu(this));
        ((MenuModel)model).includeMenu("Select", new SelectMenu(this));
    }

    @Override
    public void update(float dt) {
        ((MenuModel)model).getStage().act(dt);
    }

    @Override
    public void render() {
        if (visible) ((MenuView)view).draw(((MenuModel)model).getStage());
    }

    @Override
    public void resize(int width, int height) {
        ((MenuModel)model).resize(width, height);
    }

    public void startGame() {
        if (multiplayer) {
            ((GameController)ctrl).startMultiplayerMap("maps/" + currentMap + ".json");
        } else {
            ((GameController)ctrl).startSingleplayerMap("maps/" + currentMap + ".json");
        }

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
    }

    public float getWidth()  { return ((MenuModel)model).getStage().getWidth();  }
    public float getHeight() { return ((MenuModel)model).getStage().getHeight(); }
    public void setMultiplayer(boolean b) { multiplayer = b; }

    public void swapMenu(String menu) {
        ((MenuModel)model).swapMenu(menu);
    }
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
}