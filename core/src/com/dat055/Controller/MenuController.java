package com.dat055.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.dat055.Model.Menu.MainMenu;
import com.dat055.Model.Menu.Menu;
import com.dat055.Model.Menu.MultiMenu;
import com.dat055.View.MenuView;
import com.dat055.Model.MenuModel;

import java.util.HashMap;
import java.util.Iterator;

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
        gameController.startMap(mapPath);
        gameController.togglePause();
    }
}