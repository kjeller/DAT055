package com.dat055.Model;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;

import java.util.HashMap;

// TODO: Singleton
public class MenuModel extends Model {
    private HashMap<String, Table> tables;
    private Stage stage;

    public MenuModel() {
        stage = new Stage();
        tables = new HashMap<String, Table>();
        includeMenu("Main", new MainMenu(stage.getWidth()));
        stage.addActor(tables.get("Main"));
    }

    private void includeMenu(String label, Menu menu) {
        tables.put(label, menu.getTable());
    }

    private void initSettingsMenu () {
        // Needs new button style with toggle.
    }

    public void swapMenu(String menu) {
        stage.clear();
        stage.addActor(tables.get(menu));
    }

    public Stage getStage() {
        return stage;
    }

    public void update(float dt) {}
}
