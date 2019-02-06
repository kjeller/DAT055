package com.dat055.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dat055.View.Screen.MainScreen;

import java.util.ArrayList;

public class MenuView extends View{
    private static MenuView instance = null;
    private MainScreen menuScreen;
    ArrayList<Screen> screens;

    private static BitmapFont font;

    /**
     *
     */
    private MenuView() { screens = new ArrayList<Screen>(); }
    private static void initFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Cabin-SemiBold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();

        params.size = 32;
        params.color = Color.WHITE;
        params.borderWidth = 3;
        params.borderColor = Color.BLACK;
        font = generator.generateFont(params);
    }

    /**
     * A singleton object that handles menu screens.
     * @return the instance that exist or has been created.
     */
    public static synchronized MenuView getInstance() {
        if ( instance == null ) {
            instance = new MenuView();
            initFonts();
        } return instance;
    }

    /**
     * A simple get function
     * @return the general font.
     */
    public BitmapFont getFont() { return font; }

    /**
     *
     */
    /*public void update() {
        for(Screen screen : screens) {screen.update();}
    }
*/
    /**
     *
     * @param batch
     */
    public void draw(SpriteBatch batch) {
        //for(Screen screen : screens) {screen.draw(batch);}
    }

}
