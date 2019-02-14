package com.dat055.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.dat055.Model.Menu.MainMenu;
import com.dat055.Model.Menu.MultiMenu;
import com.dat055.View.MenuView;
import com.dat055.Model.MenuModel;

import java.util.HashMap;
import java.util.Iterator;

public class MenuController {
    private boolean visible;
    private GameController gameController;
    private MenuView menuView;
    private MenuModel menuModel;
    private HashMap<String, InputListener> listeners;

    public MenuController(GameController gameController) {
        visible = true;
        this.gameController = gameController;
        listeners = new HashMap<String, InputListener>();
        menuView = new MenuView();
        menuModel = new MenuModel();

        menuModel.includeMenu("Main", new MainMenu(this));
        menuModel.includeMenu("Multiplayer", new MultiMenu(this));

        Gdx.input.setInputProcessor(menuModel.getStage());

        swapMenu("Main");

    }

    public void update() {
        menuModel.update();
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

    public void draw() {
        if (visible) menuView.draw(menuModel.getStage());
    }

    public float getWidth() {
        return menuModel.getStage().getWidth();
    }

    public void swapMenu(String menu) {
        menuModel.swapMenu(menu);
    }

    public void startGame(String mapPath) {
        gameController.startMap(mapPath);
        gameController.togglePause();
    }
}