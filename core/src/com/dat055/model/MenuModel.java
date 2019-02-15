package com.dat055.model;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.dat055.view.MenuView;

// TODO: Singleton
public class MenuModel extends Model {
    static MenuModel instance = null;

    private Table table;
    private Stage stage;
    private MenuView menuView;
    private TextButton start, settings, exit;

    private MenuModel() {
        stage = new Stage();
        menuView = MenuView.getInstance();

        start = menuView.createButton("Play");
        settings = menuView.getInstance().createButton("Settings");
        exit = menuView.getInstance().createButton("Exit");

        table = new Table();
        table.setWidth(stage.getWidth());
        table.align(Align.center|Align.top);

        table.setPosition(0, Gdx.graphics.getHeight());

        start = menuView.createButton("Play");
        settings = menuView.getInstance().createButton("Settings");
        exit = menuView.getInstance().createButton("Exit");

        table.padTop(200);
        table.add(start).width(300).padBottom(30).row();
        table.add(settings).width(300).padBottom(30).row();
        table.add(exit).width(300).padBottom(30).row();

        stage.addActor(table);
    }

    public static synchronized MenuModel getInstance() {
        if (instance == null)
            instance = new MenuModel();
        return instance;
    }

    public Stage getStage() {
        return stage;
    }

    public void update() {}

    public void addButton(TextButton button) { stage.addActor(button); }
}
