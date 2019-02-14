package com.dat055.Model.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.dat055.Controller.MenuController;
import com.dat055.Model.Menu.Menu;

public class MainMenu extends Menu {
    private MenuController controller;
    private TextButton play, multi, settings, exit, credits;

    public MainMenu(MenuController cntr) {
        super("UI/Delta.jpg");
        initTxtBtnStyle();
        initLblStyle();

        this.controller = cntr;
        Table table = new Table();
        Table subTable = new Table();
        Label verNr = new Label("Ver: 0.17", super.lblStyle);

        table.setWidth(controller.getWidth());
        table.align(Align.center|Align.top);

        table.setPosition(0, Gdx.graphics.getHeight());

        play = createButton("Play");
        multi = createButton("Multiplayer");
        settings = createButton("Settings");
        exit = createButton("Exit");
        credits = createButton("Credits");

        addListeners();

        subTable.add(credits).width(150).padRight(300);
        subTable.add(verNr);

        table.padTop(200);
        table.add(play).width(300).padBottom(15).row();
        table.add(multi).width(300).padBottom(15).row();
        table.add(settings).width(300).padBottom(15).row();
        table.add(exit).width(300).padBottom(45).row();
        table.add(subTable).width(500).padBottom(20);

        super.table = table;
    }

    private void initTxtBtnStyle() {
        TextButtonStyle txtBtnStyle;
        BitmapFont font = super.generateFont(30);

        Skin skin = new Skin(Gdx.files.internal("UI/ui.json"));
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("UI/ui.atlas"));
        skin.addRegions(atlas);

        txtBtnStyle = new TextButton.TextButtonStyle();

        txtBtnStyle.font = font;
        txtBtnStyle.fontColor = Color.BLACK;
        txtBtnStyle.downFontColor = Color.WHITE;

        txtBtnStyle.up = skin.getDrawable("but1_pressed");
        txtBtnStyle.down = skin.getDrawable("but1");

        super.txtBtnStyle = txtBtnStyle;
    }

    private void initLblStyle() {
        LabelStyle lblStyle = new Label.LabelStyle();
        lblStyle.font = super.generateFont(26);
        lblStyle.fontColor = Color.WHITE;
        super.lblStyle = lblStyle;
    }

    /**
     * A meaty function that stores every buttons listener.
     * Does a lot of stuff, trust me.
     */

    private void addListeners() {
        play.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                controller.toggleVisibility();
                controller.startGame("maps/map_0.json");
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
            }
        });

        multi.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                controller.swapMenu("Multiplayer");
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
            }
        });

        settings.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                controller.swapMenu("Main");
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
            }
        });

        exit.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                controller.swapMenu("Main");
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
            }
        });

        credits.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                controller.swapMenu("Main");
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
            }
        });
    }
}
