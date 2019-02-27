package com.dat055.model.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.dat055.controller.MenuController;


public class CharacterMenu extends Menu {
    private MenuController controller;
    private TextField username;
    private TextButton ready, back, start;
    private TextButton.TextButtonStyle hoverStyle, checkedStyle;

    public CharacterMenu(MenuController ctrl) {
        super(true, "UI/Delta.jpg");
        controller = ctrl;
        createTable();
    }

    @Override
    public void createTable() {
        int width = Gdx.graphics.getWidth()/4;
        int height = Gdx.graphics.getHeight()/18;
        initStyles(height);

        username = createTextField("Enter a username");
        ready = createButton("Ready");
        back = createButton("Back");
        start = createButton("Start");

        addListeners();

        layoutTable(width, height);
    }

    private void layoutTable(int butX, int butY) {
        int padLarge, padSmall;
        padLarge = butX/2;
        padSmall = butY/2;

        Table table = super.table;
        table.setSize(controller.getWidth(),controller.getWidth());

        table.setPosition(0,0);

        table.add(createSubTable()).padBottom(padSmall).colspan(2).expandX().expandY().row();
        table.add(username).width(butX).height(butY).padBottom(padSmall/2).colspan(2).row();
        table.add(ready).width(butX).height(butY).padBottom(padSmall*2).colspan(2).row();
        table.add(back).width(butX/2).height(butY).padLeft(padSmall).padBottom(padSmall).bottom().left();
        table.add(start).width(butX/2).height(butY).padRight(padSmall).padBottom(padSmall).bottom().right();

        //table.setDebug(true);
    }

    private Table createSubTable() {
        Skin skin = new Skin(new TextureAtlas("UI/char_select-packed/pack.atlas"));
        Table subTable = new Table();

        Button charOne = new Button(skin.getDrawable("blue"),skin.getDrawable("blue_select"), skin.getDrawable("blue_select"));
        charOne.getStyle().disabled = skin.getDrawable("blue_checked");
        charOne.scaleBy((Gdx.graphics.getHeight() / 2) / charOne.getHeight());
        Button charTwo = new Button(skin.getDrawable("red"), skin.getDrawable("red_select"), skin.getDrawable("red_select"));
        charTwo.getStyle().disabled = skin.getDrawable("red_checked");
        charTwo.scaleBy((Gdx.graphics.getHeight() / 2) / charOne.getHeight());


        Table table = new Table();
        ButtonGroup<Button> buttonGroup = new ButtonGroup<Button>();

        if (controller.isCharOneBlocked()) {
            charOne.setDisabled(true);
            subTable.add(charOne);
        } else if (controller.isCharTwoBlocked()){
            charTwo.setDisabled(true);
            charTwo.add();
        } else {
            buttonGroup.setMaxCheckCount(1);
            buttonGroup.setMinCheckCount(0);
        }

        return table;
    }

    private void addListeners() {
        back.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                controller.swapMenu("Multiplayer");
                super.touchUp(event, x, y, pointer, button);
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                back.setStyle(hoverStyle);
                super.enter(event, x, y, pointer, fromActor);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                back.setStyle(txtBtnStyle);
                super.enter(event, x, y, pointer, toActor);
            }
        });

        start.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                start.setStyle(hoverStyle);
                super.enter(event, x, y, pointer, fromActor);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                start.setStyle(txtBtnStyle);
                super.enter(event, x, y, pointer, toActor);
            }
        });

        username.addListener(new FocusListener() {
            @Override
            public void keyboardFocusChanged (FocusEvent event, Actor actor, boolean focused) {
                if(focused) { username.setText(""); }
                else { username.setText("Enter host IP"); }
            }
        });
    }
}


