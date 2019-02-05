package com.dat055.View.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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
        batch.setProjectionMatrix(gameModel.cam.combined);    // Set camera for batch //TODO: This might need to be fixed
        gameModel.map.draw(batch);
        gameModel.player.draw(batch);
    }
}
