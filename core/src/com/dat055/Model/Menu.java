package com.dat055.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Align;

import java.util.HashMap;

public abstract class Menu {
    private HashMap<String, TextButton> txtButtons;
    private Table table;
    private LabelStyle lblStyle;
    private TextButtonStyle txtBtnStyle;

    protected Menu() {}

    protected Menu(LabelStyle labelStyle, TextButtonStyle textButtonStyle) {
        this.lblStyle = labelStyle;
        this.txtBtnStyle = textButtonStyle;
    }

    public void setLblStyle(LabelStyle lblStyle) {
        this.lblStyle = lblStyle;
    }

    public LabelStyle getLblStyle() {
        return this.lblStyle;
    }

    public void setTxtButtons(TextButtonStyle txtBtnStylet) {
        this.txtBtnStyle = txtBtnStylet;
    }

    public TextButtonStyle getTxtBtnStyle() {
        return this.txtBtnStyle;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Table getTable() {
        return this.table;
    }

    public TextButton createButton(String label) {
        return new TextButton(label, txtBtnStyle);
    }

    public BitmapFont generateFont(int size) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Cabin-SemiBold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();

        params.size = size;
        return generator.generateFont(params);
    }
}
