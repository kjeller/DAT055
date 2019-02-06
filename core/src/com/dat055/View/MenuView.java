package com.dat055.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.dat055.Model.MenuModel;
import com.dat055.View.Screen.MainScreen;
import com.dat055.View.Screen.Screen;

import java.util.ArrayList;

public class MenuView extends View{
    private static MenuView instance = null;

    private static BitmapFont font;
    private SpriteBatch batch;
    private TextButton button;
    private TextButton.TextButtonStyle textButtonStyle;
    private Skin skin;
    private TextureAtlas buttonAtlas;

    /**
     *
     */
    private MenuView() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Cabin-SemiBold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();

        params.size = 32;
        params.color = Color.WHITE;
        font = generator.generateFont(params);
    }

    /**
     * A singleton object that handles menu screens.
     * @return the instance that exist or has been created.
     */
    public static synchronized MenuView getInstance() {
        if ( instance == null )
            instance = new MenuView();
        return instance;
    }

    public TextButton createButton(String text) {
        skin = new Skin(Gdx.files.internal("UI/ui.json"));
        buttonAtlas = new TextureAtlas(Gdx.files.internal("UI/ui.atlas"));
        skin.addRegions(buttonAtlas);
        button = new TextButton(text, skin, "default");

        return button;
    }

    /**
     *
     * @return the general font.
     */


    /**
     *
     */
    @Override
    public void update() {
        MenuModel.getInstance().getStage().draw();
    }

    /**
     *
     * @param batch
     */
    @Override
    public void draw(SpriteBatch batch) {
        MenuModel.getInstance().getStage().draw();
    }
}
