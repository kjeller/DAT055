package com.dat055.Model.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.dat055.Controller.MenuController;
import com.dat055.Model.Menu.Menu;

public class MultiMenu extends Menu {
    private MenuController controller;
    TextButton join, host, back;
    public MultiMenu(MenuController cntr) {
        super();
        initTxtBtnStyle();
        initLblStyle();

        this.controller = cntr;
        Table table = new Table();
        Table subTable = new Table();

        table.setWidth(controller.getWidth());
        table.align(Align.center | Align.top);

        table.setPosition(0, Gdx.graphics.getHeight());

        join = createButton("Join");
        host = createButton("Host");
        back = createButton("Back");

        addListeners();

        subTable.add(back).width(150).padRight(300);
        subTable.add(host);

        table.padTop(200);
        //table.add(address).width(300).padBottom(30).row();
        table.add(join).width(300).padBottom(30).row();
        table.add(subTable).width(500).padBottom(20);

        super.table = table;
    }

    private void initTxtBtnStyle() {
        TextButton.TextButtonStyle txtBtnStyle = new TextButton.TextButtonStyle();
        BitmapFont font = super.generateFont(30);

        Skin skin = new Skin(Gdx.files.internal("UI/ui.json"));
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("UI/ui.atlas"));
        skin.addRegions(atlas);

        txtBtnStyle.font = font;
        txtBtnStyle.fontColor = Color.BLACK;
        txtBtnStyle.downFontColor = Color.WHITE;

        txtBtnStyle.up = skin.getDrawable("but1_pressed");
        txtBtnStyle.down = skin.getDrawable("but1");

        super.txtBtnStyle = txtBtnStyle;
    }

    private void initTxtFldStyle() {
        TextField.TextFieldStyle txtFldStyle = new TextField.TextFieldStyle();
        BitmapFont font = super.generateFont(30);

        txtFldStyle.font = font;
        txtFldStyle.disabledFontColor = Color.WHITE;
        txtFldStyle.messageFont = super.generateFont(20);
        txtFldStyle.messageFontColor = Color.RED;

        super.txtFldStyle = txtFldStyle;
    }

    private void initLblStyle() {
        Label.LabelStyle lblStyle = new Label.LabelStyle();
        lblStyle.font = super.generateFont(26);
        lblStyle.fontColor = Color.WHITE;
        super.lblStyle = lblStyle;
    }

    private void addListeners() {
        back.addListener(new ClickListener() {
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