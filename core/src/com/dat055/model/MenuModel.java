package com.dat055.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ObjectMap;
import com.dat055.model.menu.Menu;

import java.util.HashMap;

/**
 * This class is responsible for storing {@link Table}s that are created by the different {@link Menu}
 * and the {@link Stage}. Music methods are also present in this class.
 * It supports the {@link com.dat055.controller.MenuController} with some management methods.
 *
 * @author Erik Börne
 * @version 2019-03-06
 */
public class MenuModel extends Model {
    private ObjectMap<String, Menu> menus;
    private String currentMenu;
    private Stage stage;

    /**
     * This is the default constructor of {@link MenuModel}. It initializes the music and object-maps.
     */
    public MenuModel() {
        super();
        stage = new Stage();
        menus = new ObjectMap<String, Menu>();
        initMusic();
    }

    @Override
    public void initMusic() {
        musicBank.put("title", loadMusic("title.mp3"));
    }

    @Override
    public void playMusic(String ost) {
        Music music = musicBank.get(ost);
        if (ost.equals("title"))
            music.setVolume(0.3f);
        music.play();
    }

    /**
     * This method loads the menu into the object-map and adds a string key to it.
     * @param key The string key used to get, modify etc. a menu.
     * @param menu This is the menu object stored in the object-map.
     */
    public void includeMenu(String key, Menu menu) {
        menus.put(key, menu);
    }

    /**
     * Resizes the viewport to the current window so that the buttons "hitboxes" are scaled.
     * @param width The width of the window.
     * @param height The height of the window.
     */
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    /**
     * This method swaps to the current {@link Menu} to the selected one. If the menu has a background
     * it's swapped aswell.
     * @param menu A string that is used as a key to the object-map of  menus.
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
     * Updates the current {@link Menu}, then acts the stage.
     */
    public void update() {
        if(menus.get(currentMenu).isUpdatable())
            menus.get(currentMenu).updateTable();
        stage.act();
    }

    /**
     * A get-method for the stage
     * @return Returns the stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * A method that gets the name of the current {@link Menu}.
     * @return The current {@link Menu} as a string.
     */
    public String getCurrentMenu() { return currentMenu; }
}
