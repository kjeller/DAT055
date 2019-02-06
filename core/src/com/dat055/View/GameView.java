package com.dat055.View;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dat055.View.Screen.GameScreen;
import com.dat055.View.Screen.Screen;

import java.util.ArrayList;

public class GameView extends View{
    private static GameView instance = null;

    private GameScreen gameScreen;

    private GameView() {screens = new ArrayList<Screen>();}

    public static synchronized GameView getInstance() {
        if ( instance == null )
            instance = new GameView();

        return instance;
    }

    @Override
    public void update() {
        for(Screen screen : screens) {screen.update();}
    }

    @Override
    public void draw(SpriteBatch batch) {
        for(Screen screen : screens) {screen.draw(batch);}
    }

    /**
     * Creates a gamescreen for a specific map.
     * @param fileName
     */
    public void startMap(String fileName) {
        gameScreen = new GameScreen(fileName);
        screens.add(gameScreen);
    }
}
