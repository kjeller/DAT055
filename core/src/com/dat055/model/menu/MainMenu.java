package com.dat055.model.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
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
    private TextButtonStyle hoverStyle;

    public MainMenu(MenuController cntr) {
        super(false, "UI/Delta.jpg");

        this.controller = cntr;
        createTable();
    }

    @Override
    public void createTable() {
        int width = Gdx.graphics.getWidth()/4;
        int height = Gdx.graphics.getHeight()/18;

        // Create the actors
        play = createButton("Play");
        multi = createButton("Multiplayer");
        settings = createButton("Settings");
        exit = createButton("Exit");
        credits = createButton("Credits");
        verNr = new Label("Version: 0.43", super.lblStyle);

        // Add listeners to the actors
        addListeners();

        // Create the actual table
        layoutTable(width, height);
    }

    private void layoutTable(int butX, int butY) {
        int padL, padS;
        padL = butX/3;
        padS = butY/2;

        Table table = super.table;
        table.setSize(controller.getWidth(),controller.getHeight());
        table.bottom();

        table.setPosition(0, 0);

        table.add(play).width(butX).height(butY).padBottom(padS).expandX().colspan(2).row();
        table.add(multi).width(butX).height(butY).padBottom(padS).colspan(2).row();
        table.add(settings).width(butX).height(butY).padBottom(padS).colspan(2).row();
        table.add(exit).width(butX).height(butY).padBottom(padL).colspan(2).row();
        table.add(credits).width(butX/2).height(butY).padLeft(padS).padBottom(padS).left();
        table.add(verNr).width(butX/2).height(butY).padRight(padS).padBottom(padS).right();
    }

    /**
     * Adds a listener to every actor active in the table (meaty method)
     */

    private void addListeners() {
        play.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                controller.setMultiplayer(false);
                controller.swapMenu("Select");
                super.touchUp(event, x, y, pointer, button);
            }

            @Override
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
                play.setStyle(hoverStyle);
                super.enter(event,x,y,pointer,fromActor);
            }

            @Override
            public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
                play.setStyle(txtBtnStyle);
                super.enter(event,x,y,pointer,toActor);
            }
        });

        multi.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                controller.setMultiplayer(true);
                controller.swapMenu("Multiplayer");
            }

            @Override
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
                multi.setStyle(hoverStyle);
                super.enter(event,x,y,pointer,fromActor);
            }

            @Override
            public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
                multi.setStyle(txtBtnStyle);
                super.enter(event,x,y,pointer,toActor);
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

            @Override
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
                settings.setStyle(hoverStyle);
                super.enter(event,x,y,pointer,fromActor);
            }

            @Override
            public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
                settings.setStyle(txtBtnStyle);
                super.enter(event,x,y,pointer,toActor);
            }
        });

        exit.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                super.touchUp(event, x, y, pointer, button);
            }

            @Override
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
                exit.setStyle(hoverStyle);
                super.enter(event,x,y,pointer,fromActor);
            }

            @Override
            public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
                exit.setStyle(txtBtnStyle);
                super.enter(event,x,y,pointer,toActor);
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

            @Override
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
                credits.setStyle(hoverStyle);
                super.enter(event,x,y,pointer,fromActor);
            }

            @Override
            public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
                credits.setStyle(txtBtnStyle);
                super.enter(event,x,y,pointer,toActor);
            }
        });
    }
}
