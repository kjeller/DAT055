package com.dat055.model.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public abstract class Menu {
    protected boolean updatable = false;
    protected Table table;
    protected LabelStyle lblStyle;
    protected TextButtonStyle txtBtnStyle, hoverStyle, checkedStyle;
    protected TextFieldStyle txtFldStyle;
    protected Image bg;

    protected Menu(boolean updatable) {
        lblStyle = new LabelStyle();
        txtBtnStyle = new TextButtonStyle();
        txtFldStyle = new TextFieldStyle();
        initStyles(Gdx.graphics.getHeight()/18);
        this.updatable = updatable;
        table = new Table();
    }

    protected Menu(boolean updatable, String bgAssetLocation) {
        lblStyle = new LabelStyle();
        txtBtnStyle = new TextButtonStyle();
        txtFldStyle = new TextFieldStyle();
        initStyles(Gdx.graphics.getHeight()/18);
        this.updatable = updatable;
        table = new Table();
        bg = new Image(new Texture(bgAssetLocation));
    }

    public void createTable() {}

    public void updateTable() {
        table.clear();
        createTable();
    }

    protected void initStyles(int height) {
        initTxtBtnStyle(height);
        initTxtFldStyle(height);
        initLblStyle(height);
    }

    private void initTxtBtnStyle(int height) {
        Skin skin = new Skin(Gdx.files.internal("UI/ui.json"));
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("UI/ui.atlas"));
        skin.addRegions(atlas);

        txtBtnStyle.font = generateFont(height-Gdx.graphics.getHeight()/50);
        txtBtnStyle.fontColor = Color.BLACK;
        txtBtnStyle.downFontColor = Color.CYAN;
        txtBtnStyle.up = skin.getDrawable("but1_pressed");

        hoverStyle = new TextButton.TextButtonStyle(txtBtnStyle);
        hoverStyle.up = skin.getDrawable("but1");
        hoverStyle.fontColor = Color.WHITE;

        checkedStyle = new TextButton.TextButtonStyle(txtBtnStyle);
        checkedStyle.checked = skin.getDrawable("but1");
        checkedStyle.checkedFontColor = Color.WHITE;
    }

    private void initTxtFldStyle(int height) {

        Skin skin = new Skin(Gdx.files.internal("UI/ui.json"));
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("UI/ui.atlas"));
        skin.addRegions(atlas);

        txtFldStyle.font = fontPad(generateFont(height-Gdx.graphics.getHeight()/50), -5, 0);
        txtFldStyle.fontColor = Color.BLACK;
        txtFldStyle.background = skin.getDrawable("but1");
        txtFldStyle.messageFont = generateFont(height-Gdx.graphics.getHeight()/60);
        txtFldStyle.cursor = new NinePatchDrawable(new NinePatch(new Texture("UI/cursor.9.png")));
    }

    private void initLblStyle(int height) {
        lblStyle.font = generateFont(height-Gdx.graphics.getHeight()/60);
        lblStyle.fontColor = Color.WHITE;
    }

    public BitmapFont fontPad(BitmapFont f, int padLeft, int padRight) {
        BitmapFont.BitmapFontData fd = f.getData();
        fd.padLeft = padLeft;
        fd.padRight = padRight;
        return f;
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
        return table;
    }

    public Image getBg() { return bg; }

    public TextButton createButton(String label) { return new TextButton(label, txtBtnStyle); }
    public TextButton createButton(String label,TextButtonStyle style) { return new TextButton(label, style); }

    public TextField createTextField(String label) {
        TextField tf = new TextField(label, txtFldStyle);
        return tf;
    }

    public BitmapFont generateFont(int size) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Cabin-SemiBold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();

        params.size = size;
        BitmapFont font = generator.generateFont(params);
        generator.dispose();
        return font;
    }

    public boolean isUpdatable() { return this.updatable; }
    protected void dispose() {}
}
