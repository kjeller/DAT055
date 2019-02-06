package com.dat055.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.dat055.Model.MenuModel;

public class MenuController extends Controller {
    MenuModel menuModel = MenuModel.getInstance();
    Stage stage = menuModel.getStage();

}
