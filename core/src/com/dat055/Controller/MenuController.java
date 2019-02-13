package com.dat055.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.dat055.Model.MainMenu;
import com.dat055.Model.MultiMenu;
import com.dat055.View.MenuView;
import com.dat055.Model.MenuModel;
import sun.tools.jar.Main;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MenuController {
    private boolean visible;
    private MenuView menuView;
    private MenuModel menuModel;
    private HashMap <String, InputListener> listeners;

    public MenuController() {
        unhide();
        listeners = new HashMap<String, InputListener>();
        menuView = new MenuView();
        menuModel = new MenuModel();
        float width = menuModel.getStage().getWidth();

        Gdx.input.setInputProcessor(menuModel.getStage());
        menuModel.includeMenu("Main", new MainMenu(width));
        menuModel.includeMenu("Multiplayer", new MultiMenu(width));

        addListeners();
        swapMenu("Main");

    }

    public void update() {
        menuModel.update();
    }

    public void hide() {
        visible = false;
    }

    public void unhide() {
        visible = true;
    }

    public void draw() {
        if (visible) menuView.draw(menuModel.getStage());
    }

    public void swapMenu(String menu) {
        // remove listeners
        menuModel.swapMenu(menu);
        initListeners();
    }

    private void initListeners() {
        HashMap<String, TextButton> buttons = menuModel.getButtons();
        Iterator<String> itr = buttons.keySet().iterator();
        while (itr.hasNext()) {
            String key = itr.next();
            if (listeners.get(key) != null) {
                TextButton but = buttons.get(key);
                but.addListener(listeners.get(key));
            }
        }
    }

    /**
     * Adds listeners to its hashmap.
     * All of the meaty button functions are placed here. Handle strings with care
     */
    private void addListeners() {
         listeners.put("Play", new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                HashMap<String, TextButton> buttons = menuModel.getButtons();
                buttons.get("Play").setText("boom");
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
            }
        });

        listeners.put("Multiplayer", new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                menuModel.swapMenu("Multiplayer");
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
            }
        });

        listeners.put("Back", new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                swapMenu("Main");
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
            }
        });
    }
}
