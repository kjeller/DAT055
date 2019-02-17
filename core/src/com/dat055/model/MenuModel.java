package com.dat055.model;

import com.badlogic.gdx.Gdx;
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

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void swapMenu(String menu) {
        stage.clear();
        menus.get(menu).getBg().setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(menus.get(menu).getBg());
        Table tbl = menus.get(menu).getTable();
        tbl.setFillParent(true);
        stage.addActor(tbl);
        currentMenu = menu;
    }

    public Stage getStage() {
        return stage;
    }

    public String getCurrentMenu() { return currentMenu; }
}
