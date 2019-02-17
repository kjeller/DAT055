package com.dat055.model.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.dat055.controller.MenuController;

public class MainMenu extends Menu {
    private MenuController controller;
    private TextButton play, multi, settings, exit, credits;
    private Label verNr;

    public MainMenu(MenuController cntr) {
        super("UI/Delta.jpg");

        this.controller = cntr;
        createTable(Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/18);
    }

    @Override
    public void createTable(int width, int height) {
        // Initiate the styles used by super
        initStyles(height);

        // Create the actors
        play = createButton("Play");
        multi = createButton("Multiplayer");
        settings = createButton("Settings");
        exit = createButton("Exit");
        credits = createButton("Credits");
        verNr = new Label("Ver: 0.17", super.lblStyle);

        // Add listeners to the actors
        addListeners();

        // Create the actual table
        layoutTable(width, height);
    }

    private void layoutTable(int butX, int butY) {
        int padL, padS;
        padL = butX/3;
        padS = butY/2;

        Table table = new Table();
        table.setSize(controller.getWidth(),controller.getHeight());
        table.bottom();

        table.setPosition(0, 0);

        table.add(play).width(butX).height(butY).padBottom(padS).expandX().colspan(2).row();
        table.add(multi).width(butX).height(butY).padBottom(padS).colspan(2).row();
        table.add(settings).width(butX).height(butY).padBottom(padS).colspan(2).row();
        table.add(exit).width(butX).height(butY).padBottom(padL).colspan(2).row();
        table.add(credits).width(butX/2).height(butY).padLeft(padS).padBottom(padS).left();
        table.add(verNr).width(butX/2).height(butY).padRight(padS).padBottom(padS).right();

        table.setDebug(true);

        super.table = table;
    }

    private void initStyles(int height) {
        initLblStyle(height);
        initTxtBtnStyle(height);
    }

    private void initTxtBtnStyle(int height) {
        TextButtonStyle txtBtnStyle;
        BitmapFont font = generateFont(height-Gdx.graphics.getHeight()/50);

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

    private void initLblStyle(int height) {
        LabelStyle lblStyle = new Label.LabelStyle();
        lblStyle.font = generateFont(height-Gdx.graphics.getHeight()/30);
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
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                controller.toggleVisibility();
                controller.clearStage();
                controller.startGame("maps/map_0.json");
                super.touchUp(event, x, y, pointer, button);
            }
        });

        multi.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                controller.swapMenu("Multiplayer");
                super.touchUp(event, x, y, pointer, button);
            }
        });

        settings.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
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
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.exit(0);
                super.touchUp(event, x, y, pointer, button);
            }
        });

        credits.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                controller.swapMenu("Main");
                super.touchUp(event, x, y, pointer, button);
            }
        });
    }
}
