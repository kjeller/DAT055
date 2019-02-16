package com.dat055.model.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;

public abstract class Menu {
    protected Table table;
    protected LabelStyle lblStyle;
    protected TextButtonStyle txtBtnStyle;
    protected TextFieldStyle txtFldStyle;
    protected Texture bg;

    protected Menu() {}

    protected Menu(String bgAssetLocation) {
        bg = new Texture(bgAssetLocation);
    }

    public LabelStyle getLblStyle() {
        return this.lblStyle;
    }

    public TextButtonStyle getTxtBtnStyle() {
        return this.txtBtnStyle;
    }

    public TextFieldStyle getTxtFldStyle() {
        return this.txtFldStyle;
    }

    public Table getTable() {
        return this.table;
    }

    public Texture getBg() {
        return this.bg;
    }

    public TextButton createButton(String label) { return new TextButton(label, txtBtnStyle); }

    public TextField createTextField(String label) {
        TextField tf = new TextField(label, txtFldStyle);
        return tf;
    }

    public BitmapFont generateFont(int size) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Cabin-SemiBold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();

        params.size = size;

        return generator.generateFont(params);
    }
}
