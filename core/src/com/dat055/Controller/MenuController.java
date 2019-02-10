package com.dat055.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.dat055.View.MenuView;
import com.dat055.Model.MenuModel;

import java.awt.*;

public class MenuController {
    private float dt;
    private MenuView menuView;
    private MenuModel menuModel;

    public MenuController() {
        menuView = new MenuView();
        menuModel = new MenuModel();
        Gdx.input.setInputProcessor(menuModel.getStage());
    }

    public void update(float dt) {
        menuModel.update(dt);
    }

    public void draw() {
        menuView.draw(menuModel.getStage());
    }
}
