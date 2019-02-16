package com.dat055.model;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.dat055.model.menu.Menu;

import java.util.HashMap;


public class MenuModel extends Model {
    private HashMap<String, Menu> menus;
    private String currentMenu;
    private Stage stage;

    public MenuModel() {
        stage = new Stage();
        menus = new HashMap<String, Menu>();
    }

    public void includeMenu(String label, Menu menu) {
        menus.put(label, menu);
    }

    public void swapMenu(String menu) {
        stage.clear();
        stage.addActor(new Image(menus.get(menu).getBg()));
        stage.addActor(menus.get(menu).getTable());
        currentMenu = menu;
    }

    public Stage getStage() {
        return stage;
    }
}
