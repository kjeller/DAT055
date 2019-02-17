package com.dat055.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dat055.model.menu.MainMenu;
import com.dat055.model.menu.Menu;
import com.dat055.model.menu.MultiMenu;
import com.dat055.model.menu.PauseMenu;
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
        ((MenuModel)model).includeMenu("Pause", new PauseMenu(this));

        Gdx.input.setInputProcessor(((MenuModel)model).getStage());

        swapMenu("Main");
    }

    @Override
    public void update(float dt) {
        ((MenuModel)model).getStage().act(dt);
    }

    @Override
    public void resize(int width, int height) {
        ((MenuModel)model).resize(width, height);
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

    public float getWidth()  { return ((MenuModel)model).getStage().getWidth();  }
    public float getHeight() {
        return ((MenuModel)model).getStage().getHeight();
    }

    public void swapMenu(String menu) {
        ((MenuModel)model).swapMenu(menu);
    }

    public void startGame(String mapPath) {
        gameController.startSingleplayerMap(mapPath);
    }
}