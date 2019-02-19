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
    private MenuController controller;
    private FileHandle[] files;
    private TextButton back, select, current;
    private ButtonGroup<TextButton> textButtonGroup;
    private TextButton.TextButtonStyle hoverStyle, checkedStyle;

    public SelectMenu(MenuController ctrl) {
        super("UI/Delta.jpg");
        textButtonGroup = new ButtonGroup<TextButton>();
        textButtonGroup.setMaxCheckCount(1);
        textButtonGroup.setMinCheckCount(0);
        textButtonGroup.setUncheckLast(true);

        controller = ctrl;
        createTable(Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()/18);
    }

    @Override
    public void createTable(int width, int height) {
        int padLarge, padSmall;
        padLarge = width/2;
        padSmall = height/2;

        initStyles(height);

        back = createButton("Back");
        select = createButton("Select");

        Table table = new Table();
        table.setSize(controller.getWidth(),controller.getWidth());
        Table subTable = new Table();

        table.setPosition(0,0);
        files = Gdx.files.internal("maps/").list();
        for(FileHandle file: files) {
            TextButton tb = createButton(file.nameWithoutExtension(), checkedStyle);
            textButtonGroup.add(tb);
            subTable.add(tb).width(width).height(height).padBottom(padSmall).expandX().row();
        }

        table.add(subTable).padBottom(padSmall).colspan(2).expandX().expandY().row();
        table.add(back).width(width/2).height(height).padLeft(padSmall).padBottom(padSmall).bottom().left();
        table.add(select).width(width/2).height(height).padRight(padSmall).padBottom(padSmall).bottom().right();

        table.setDebug(true);

        addListeners();

        super.table = table;
    }

    private void initStyles(int height) {
        initTxtBtnStyle(height);
        initLblStyle(height);
    }

    private void initTxtBtnStyle(int height) {
        TextButton.TextButtonStyle txtBtnStyle = new TextButton.TextButtonStyle();

        Skin skin = new Skin(Gdx.files.internal("UI/ui.json"));
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("UI/ui.atlas"));
        skin.addRegions(atlas);

        txtBtnStyle.font = generateFont(height-Gdx.graphics.getHeight()/50);
        txtBtnStyle.fontColor = Color.BLACK;
        txtBtnStyle.downFontColor = Color.WHITE;

        txtBtnStyle.up = skin.getDrawable("but1_pressed");
        txtBtnStyle.down = skin.getDrawable("but1");

        hoverStyle = new TextButton.TextButtonStyle(txtBtnStyle);
        hoverStyle.up = skin.getDrawable("but1");
        hoverStyle.fontColor = Color.WHITE;

        checkedStyle = new TextButton.TextButtonStyle(txtBtnStyle);
        checkedStyle.checked = skin.getDrawable("but1");
        checkedStyle.checkedFontColor = Color.WHITE;

        super.txtBtnStyle = txtBtnStyle;
    }

    private void initLblStyle(int height) {
        Label.LabelStyle lblStyle = new Label.LabelStyle();
        lblStyle.font = generateFont(height-Gdx.graphics.getHeight()/60);
        lblStyle.fontColor = Color.WHITE;
        super.lblStyle = lblStyle;
    }

    public BitmapFont fontPad(BitmapFont f) {
        BitmapFont.BitmapFontData fd = f.getData();
        fd.padLeft = -5;
        fd.padRight = -10;
        return f;
    }
    private void addListeners() {
        back.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return super.touchDown(event, x, y, pointer, button);
            }

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

        select.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (controller.multiplayer)
                    controller.name = "Beelzebub";

                if (textButtonGroup.getChecked() != null) {
                    controller.clearStage();
                    controller.currentMap = textButtonGroup.getChecked().getText().toString();
                    controller.startGame();
                }

                System.out.println(controller.currentMap);
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

