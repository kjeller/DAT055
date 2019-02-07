package com.dat055.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.dat055.Model.GameModel;
import com.dat055.Model.MenuModel;
import com.dat055.View.GameView;

public class MenuController extends Controller {
    public MenuController(GameModel model, GameView view) {
        super(model, view);
    }
    private MenuModel menuModel = MenuModel.getInstance();
    Stage stage = menuModel.getStage();
}
