package com.dat055.Model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class MenuModel extends Model {
    private HashMap<String, Menu> menus;
    private String currentMenu;
    private Stage stage;
    private Sprite bgSprite;

    public MenuModel() {
        stage = new Stage();
        menus = new HashMap<String, Menu>();
    }

    public void includeMenu(String label, Menu menu) {
        menus.put(label, menu);
        bgSprite = menu.getBgSprite();
    }

    public void swapMenu(String menu) {
        stage.clear();
        stage.addActor(menus.get(menu).getTable());
        currentMenu = menu;
    }

    public Stage getStage() {
        return stage;
    }

    public HashMap<String, TextButton> getButtons() {
        HashMap<String, TextButton> btns = new HashMap<String, TextButton>();
        Iterator<String> itr = menus.keySet().iterator();
        while (itr.hasNext()) {
            String key = itr.next();
            if (menus.get(key) != null) {
                btns.putAll(menus.get(key).getButtons());
            }
        } return btns;
    }

    public void update() { stage.act(); }
}
