package com.dat055.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Align;

public class MainMenu extends Menu {
    MainMenu(float width) {
        initTxtBtnStyle();
        initLblStyle();

        Table table = new Table();
        Table subTable = new Table();
        Label verNr = new Label("Ver: 0.17", super.getLblStyle());

        table.setWidth(width);
        table.align(Align.center|Align.top);

        table.setPosition(0, Gdx.graphics.getHeight());

        TextButton start = createButton("Play");
        TextButton settings = createButton("Settings");
        TextButton exit = createButton("Exit");
        TextButton credits = createButton("Credits");

        subTable.add(credits).width(150).padRight(300);
        subTable.add(verNr);

        table.padTop(200);
        table.add(start).width(300).padBottom(30).row();
        table.add(settings).width(300).padBottom(30).row();
        table.add(exit).width(300).padBottom(60).row();
        table.add(subTable).width(500).padBottom(20);

        super.setTable(table);
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
        super.setTxtButtons(txtBtnStyle);
    }

    private void initLblStyle() {
        LabelStyle lblStyle = new Label.LabelStyle();
        lblStyle.font = super.generateFont(26);
        lblStyle.fontColor = Color.WHITE;
        setLblStyle(lblStyle);
    }
}
