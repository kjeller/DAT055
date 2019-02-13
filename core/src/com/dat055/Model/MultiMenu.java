package com.dat055.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;

public class MultiMenu extends Menu {
    public MultiMenu(float width) {
        super();
        initTxtBtnStyle();
        initLblStyle();

        Table table = new Table();
        Table subTable = new Table();

        table.setWidth(width);
        table.align(Align.center|Align.top);

        table.setPosition(0, Gdx.graphics.getHeight());

        TextButton join = createButton("Join");
        TextButton host = createButton("Host");
        TextButton back = createButton("Back");
        //TextField address = createTextField("Enter the host IP");

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
}
