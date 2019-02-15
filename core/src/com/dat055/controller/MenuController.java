package com.dat055.controller;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.dat055.model.GameModel;
import com.dat055.model.MenuModel;
import com.dat055.view.GameView;

public class MenuController extends Controller {
    public MenuController(GameModel model, GameView view) {
        super(model, view);
    }
    private MenuModel menuModel = MenuModel.getInstance();
    Stage stage = menuModel.getStage();
}
