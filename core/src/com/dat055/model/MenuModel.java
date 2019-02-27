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

    /**
     * This method swaps to the current {@link Menu} to the selected one
     * @param menu A string that is used as a key to the hashmap of  menus
     */
    public void swapMenu(String menu) {
        stage.clear();
        Menu next = menus.get(menu);
        next.updateTable();
        if(next.getBg() != null) {
            next.getBg().setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            stage.addActor(next.getBg());
        }
        Table tbl = next.getTable();
        tbl.setFillParent(true);
        stage.addActor(tbl);
        currentMenu = menu;
    }

    /**
     * Updates the current {@link Menu}, then acts the stage (updates it)
     */
    public void update() {
        menus.get(currentMenu).updateTable();
        stage.act();
    }

    /**
     * A get-method
     * @return Returns the stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * A method that names the current {@link Menu}
     * @return
     */
    public String getCurrentMenu() { return currentMenu; }
}
