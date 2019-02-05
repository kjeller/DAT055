package com.dat055.View.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dat055.Model.GameModel;

public class GameScreen implements Screen {
    GameModel gameModel; // logic for game stored here
    OrthographicCamera cam;

    public GameScreen(String fileName) {
        gameModel = new GameModel();
        gameModel.createMap(fileName);
        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }
    public void update (){
        gameModel.update();
        cam.update();
        //cam.translate(gameModel.player);
    }

    public void draw(SpriteBatch batch) {
        batch.setProjectionMatrix(cam.combined);    // Set camera for batch //TODO: This might need to be fixed
        gameModel.map.draw(batch);
        gameModel.player.draw(batch);
    }
}
