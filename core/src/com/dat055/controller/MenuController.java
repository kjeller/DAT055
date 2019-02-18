package com.dat055.controller;

import com.badlogic.gdx.Gdx;
import com.dat055.model.menu.MainMenu;
import com.dat055.model.menu.MultiMenu;
import com.dat055.view.MenuView;
import com.dat055.model.MenuModel;

public class MenuController extends Controller{
    private boolean visible;
    private GameController gameController;

    public MenuController(GameController gameController) {
        super(new MenuModel(), new MenuView());
        visible = true;
        this.gameController = gameController;

        ((MenuModel)model).includeMenu("Main", new MainMenu(this));
        ((MenuModel)model).includeMenu("Multiplayer", new MultiMenu(this));

        Gdx.input.setInputProcessor(((MenuModel)model).getStage());

        swapMenu("Main");
    }

    @Override
    public void update(float dt) {
        ((MenuModel)model).getStage().act(dt);
    }

    public void toggleVisibility() {
        if (visible == true)
            visible = false;
        else
            visible = true;
    }

    public boolean isVisible() {
        return visible;
    }

    @Override
    public void render() {
        if (visible) ((MenuView)view).draw(((MenuModel)model).getStage());
    }

    public void clearStage() {
        ((MenuModel)model).getStage().clear();
    }

    public float getWidth() {
        return ((MenuModel)model).getStage().getWidth();
    }

    public void swapMenu(String menu) {
        ((MenuModel)model).swapMenu(menu);
    }

    public void startGame(String mapPath) {
       // gameController.startSingleplayerMap(mapPath);
        //gameController.startMultiplayerMap(mapPath);
        gameController.joinMultiplayerMap("192.168.0.105");
        //gameController.togglePause();
    }
}