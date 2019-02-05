package com.dat055.View.Screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dat055.Model.GameModel;

public class GameScreen implements Screen {
    GameModel gameModel; // logic for game stored here
    public GameScreen(String fileName) {
        gameModel = new GameModel();
        gameModel.createMap(fileName);
    }
    public void update (){
        gameModel.update();
    }

    public void draw(SpriteBatch batch) {
        gameModel.map.draw(batch);
    }
}
