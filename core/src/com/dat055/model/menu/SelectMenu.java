package com.dat055.model.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.dat055.controller.MenuController;
import java.util.ArrayList;


public class SelectMenu extends Menu {
    private FileHandle[] files;
    private TextButton back, select;
    private ButtonGroup<TextButton> textButtonGroup;

    public SelectMenu(MenuController ctrl) {
        super(ctrl, false, "UI/Delta.jpg");
        textButtonGroup = new ButtonGroup<TextButton>();
        textButtonGroup.setMaxCheckCount(1);
        textButtonGroup.setMinCheckCount(0);
        textButtonGroup.setUncheckLast(true);
        createTable();
    }

    @Override
    public void createTable() {
        back = createButton("Back");
        select = createButton("Select");

        addListeners();

        layoutTable(Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()/18);
    }

    private void layoutTable(int width, int height) {
        int padSmall = height/2;

        Table table = super.table;
        table.setSize(controller.getWidth(),controller.getWidth());

        table.setPosition(0,0);

        table.add(createSubTable(padSmall)).padBottom(padSmall).colspan(2).expandX().expandY().row();
        table.add(back).width(width>>1).height(height).padLeft(padSmall).padBottom(padSmall).bottom().left();
        table.add(select).width(width>>1).height(height).padRight(padSmall).padBottom(padSmall).bottom().right();
    }

    private Table createSubTable(int padding) {
        Table table = new Table();
        files = Gdx.files.internal("maps/").list();
        for(FileHandle file: files) {
            TextButton tb = createButton(file.nameWithoutExtension(), checkedStyle);
            textButtonGroup.add(tb);
            //noinspection IntegerDivisionInFloatingPointContext
            table.add(tb).width(Gdx.graphics.getWidth()/3).height(Gdx.graphics.getHeight()/16).padBottom(padding).expandX().row();
        }
        return table;
    }

    private void addListeners() {
        back.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (controller.multiplayer)
                    controller.swapMenu("Multiplayer");
                else
                    controller.swapMenu("Main");
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

        select.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (textButtonGroup.getChecked() != null) {
                    if (controller.multiplayer)
                        controller.name = "Beelzebub";

                    controller.clearStage();
                    controller.currentMap = textButtonGroup.getChecked().getText().toString();
                    controller.startGame();
                }
                super.touchUp(event, x, y, pointer, button);
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                select.setStyle(hoverStyle);
                super.enter(event, x, y, pointer, fromActor);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                select.setStyle(txtBtnStyle);
                super.enter(event, x, y, pointer, toActor);
            }
        });
    }
}

