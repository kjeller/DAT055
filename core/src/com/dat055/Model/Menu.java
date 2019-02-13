package com.dat055.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import javafx.scene.layout.Background;

import java.util.HashMap;

public abstract class Menu {
    protected HashMap<String, TextButton> txtButtons;
    protected Table table;
    protected LabelStyle lblStyle;
    protected TextButtonStyle txtBtnStyle;
    protected TextFieldStyle txtFldStyle;
    protected Sprite bgSprite;

    protected Menu() { txtButtons = new HashMap<String, TextButton>(); }

    protected Menu(String bgAssetLocation) {
        txtButtons = new HashMap<String, TextButton>();
        Texture bgTexture = new Texture(bgAssetLocation);
        bgSprite = new Sprite(bgTexture);
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

    public Sprite getBgSprite() {
        return this.bgSprite;
    }

    public HashMap<String, TextButton> getButtons() {
        return txtButtons;
    }

    public TextButton createButton(String label) {
        TextButton txtBtn = new TextButton(label, txtBtnStyle);
        txtButtons.put(label, txtBtn);
        return txtBtn;
    }

    public TextField createTextField(String label) {
        TextField txtFld = new TextField(label, txtFldStyle);
        txtFld.setText(label);
        return txtFld;
    }

    public BitmapFont generateFont(int size) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Cabin-SemiBold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();

        params.size = size;
        return generator.generateFont(params);
    }
}
