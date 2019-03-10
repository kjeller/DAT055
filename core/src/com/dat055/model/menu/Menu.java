package com.dat055.model.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.dat055.controller.MenuController;

/**
 * This is the groundwork for every {@link Menu}. It is responsible for holding a {@link Table} that will be
 * populated by the inheriting class. It also provides som standard styles for the menus and holds the menu background.
 *
 * @author Erik Börne
 * @version 2019-03-04
 */

public abstract class Menu {
    private boolean updatable;
    MenuController controller;
    Table table;
    LabelStyle lblStyle;
    TextButtonStyle txtBtnStyle, hoverStyle, checkedStyle;
    int width, height;
    private TextFieldStyle txtFldStyle;
    private Image bg;

    /**
     * The default constructor for {@link Menu}. Sets the flag updatable to false.
     */
    Menu(MenuController ctrl) {
        lblStyle = new LabelStyle();
        txtBtnStyle = new TextButtonStyle();
        txtFldStyle = new TextFieldStyle();
        initStyles(Gdx.graphics.getHeight()/18);
        table = new Table();
        controller = ctrl;
        updatable = false;
        width = Gdx.graphics.getWidth()/4;
        height = Gdx.graphics.getHeight()/18;
    }

    /**
     * The standard constructor for {@link Menu}.
     * @param updatable Determines if the menu should be updated every few cycles.
     */
    Menu(MenuController controller, boolean updatable) {
        this(controller);
        this.updatable = updatable;
    }

    /**
     * The extended constructor for {@link Menu} with backgrounds.
     * @param updatable Determines if the menu should be updated every few cycles.
     * @param bgAssetLocation This is the path from the assets folder to the background.
     */
    Menu(MenuController controller, boolean updatable, String bgAssetLocation) {
        this(controller, updatable);
        bg = new Image(new Texture(bgAssetLocation));
        bg.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
    }

    /**
     * Creates the table for the menu.
     */
    protected abstract void createTable();

    /**
     * The update function clears the table, then recreates it. (A reload of sorts.)
     */
    public void updateTable() {
        table.clear();
        createTable();
    }

    /**
     * Initializes the most common styles used in the menus.
     * @param height This is the height used for fonts.
     */
    private void initStyles(int height) {
        initTxtBtnStyle(height);
        initTxtFldStyle(height);
        initLblStyle(height);
    }

    /**
     * Initializes the different text button styles.
     * @param height Scales the font used on the buttons.
     */
    private void initTxtBtnStyle(int height) {
        // Load skins
        Skin skin = new Skin(Gdx.files.internal("UI/ui.json"));
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("UI/ui.atlas"));
        skin.addRegions(atlas);

        // Create the standard style.
        txtBtnStyle.font = generateFont((int) (height/1.5));
        txtBtnStyle.fontColor = Color.BLACK;
        txtBtnStyle.downFontColor = Color.CYAN;
        txtBtnStyle.up = skin.getDrawable("but1_pressed");

        // Create the style for hovered buttons.
        hoverStyle = new TextButton.TextButtonStyle(txtBtnStyle);
        hoverStyle.up = skin.getDrawable("but1");
        hoverStyle.fontColor = Color.WHITE;

        // Create the style for checked buttons.
        checkedStyle = new TextButton.TextButtonStyle(txtBtnStyle);
        checkedStyle.checked = skin.getDrawable("but1");
        checkedStyle.checkedFontColor = Color.WHITE;
    }

    /**
     * Initializes the text field style.
     * @param height Scales the font used in the text field.
     */
    private void initTxtFldStyle(int height) {
        // load skins
        Skin skin = new Skin(Gdx.files.internal("UI/ui.json"));
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("UI/ui.atlas"));
        skin.addRegions(atlas);

        // Create the standard style.
        txtFldStyle.font = fontPad(generateFont((int) (height/1.5)), -5, 0);
        txtFldStyle.fontColor = Color.WHITE;
        txtFldStyle.background = skin.getDrawable("but1");
        txtFldStyle.messageFont = generateFont(height/2);
        txtFldStyle.cursor = new NinePatchDrawable(new NinePatch(new Texture("UI/cursor.9.png")));
    }

    /**
     * Initializes the label style.
     * @param height Scales the font used on the labels.
     */
    protected void initLblStyle(int height) {
        // Create the standard style.
        lblStyle.font = generateFont(height/2);
        lblStyle.fontColor = Color.WHITE;
    }

    /**
     * This method is used to apply padding to text that requires it. This method is chainable.
     * @param f This is the font that needs padding.
     * @param padLeft The amount of padding on the left side in pixels.
     * @param padRight The amount of padding on the right side in pixels.
     * @return The font is returned so that the method may be chained with future methods.
     */
    private BitmapFont fontPad(BitmapFont f, int padLeft, int padRight) {
        BitmapFont.BitmapFontData fd = f.getData();
        fd.padLeft = padLeft;
        fd.padRight = padRight;
        return f;
    }

    /**
     * A get method for the text button style.
     * @return The text button style used in this object.
     */
    public TextButtonStyle getTxtBtnStyle() {
        return this.txtBtnStyle;
    }

    /**
     * A get method for the text field style.
     * @return The text field style used in this object.
     */
    public TextFieldStyle getTxtFldStyle() {
        return this.txtFldStyle;
    }

    /**
     * A get method for the label style.
     * @return The label style used in this object.
     */
    public LabelStyle getLblStyle() {
        return this.lblStyle;
    }

    /**
     * A get method for the menu table.
     * @return The table that the menu uses.
     */
    public Table getTable() {
        return table;
    }

    /**
     * A get method for the menu's background.
     * @return The background image used by the menu.
     */
    public Image getBg() { return bg; }

    /**
     * A set method for the menu's background. Only to be used by inheriting classes.
     * @return The background image used by the menu.
     */
    protected void setBg(Image newBg) {
        bg = newBg;
    }

    /**
     * Creates a {@link TextButton} with a text label. Uses the standard skin.
     * @param label This is the text that is displayed on the button
     * @return The {@link TextButton} that has been produced.
     */
    TextButton createButton(String label) { return new TextButton(label, txtBtnStyle); }


    /**
     * Creates a text button with the desired style.
     * @param label This is the text that is displayed on the button
     * @param style This is the {@link TextButtonStyle} that is used for the button produced.
     * @return The {@link TextButton} that has been produced.
     */
    TextButton createButton(String label, TextButtonStyle style) { return new TextButton(label, style); }

    /**
     * Creates a text field that displays a given text. The maximum length of input is set to 24.
     * @param label Is the text that
     * @return The created text field.
     */
    TextField createTextField(String label) {
        TextField tf = new TextField(label, txtFldStyle);
        tf.setMaxLength(24);
        return tf;
    }

    /**
     * A method that creates a standard font based on a size.
     * @param size Size is the fonts size in pixels.
     * @return The method returns the font created.
     */
    private BitmapFont generateFont(int size) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Cabin-SemiBold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();

        params.size = size;
        BitmapFont font = generator.generateFont(params);
        generator.dispose();
        return font;
    }

    /**
     * Checks if the menu requires active reloads to operate as designed.
     * @return The boolean updatable.
     */
    public boolean isUpdatable() { return this.updatable; }
}
