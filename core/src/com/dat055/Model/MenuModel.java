package com.dat055.Model;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Align;
import com.dat055.View.MenuView;

import java.util.ArrayList;

// TODO: Singleton
public class MenuModel extends Model {
    private ArrayList<Table> tables;
    private Stage stage;
    private BitmapFont mainFont;
    private Skin stdSkin;
    private TextButtonStyle stdStyle;
    private TextButton start, settings, exit;

    public MenuModel() {
        stage = new Stage();
        tables = new ArrayList<Table>();
        initFonts();
        initSkins();
        initStyles();
        initMainMenu();
        stage.addActor(tables.get(0));
    }

    private void
    initFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Cabin-SemiBold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();

        params.size = 32;
        params.color = Color.WHITE;
        mainFont = generator.generateFont(params);
    }

    private void initSkins() {
        stdSkin = new Skin(Gdx.files.internal("UI/ui.json"));
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("UI/ui.atlas"));
        stdSkin.addRegions(atlas);
    }

    private void initStyles() {
        stdStyle = new TextButtonStyle();
        stdStyle.font  = mainFont;
        stdStyle.up = stdSkin.getDrawable("but1");
        stdStyle.down = stdSkin.getDrawable("but1_pressed");
        stdStyle.checked = stdSkin.getDrawable("but1_pressed");
    }

    private void initMainMenu() {
        Table table = new Table();
        table.setWidth(stage.getWidth());
        table.align(Align.center|Align.top);

        table.setPosition(0, Gdx.graphics.getHeight());

        start = createButton("Play");
        settings = createButton("Settings");
        exit = createButton("Exit");

        table.padTop(200);
        table.add(start).width(300).padBottom(30).row();
        table.add(settings).width(300).padBottom(30).row();
        table.add(exit).width(300).padBottom(30).row();

        tables.add(table);
    }

    public TextButton createButton(String label) {
        return new TextButton(label, stdStyle);
    }

    public Stage getStage() {
        return stage;
    }

    public void update(float dt) {}
}
