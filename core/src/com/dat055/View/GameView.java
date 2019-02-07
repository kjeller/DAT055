package com.dat055.View;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dat055.Model.GameModel;

public class GameView extends View{
    private static GameView instance = null;
    private GameView(GameModel gameModel) {
        this.model = gameModel;
    }

    public static synchronized GameView getInstance(GameModel gameModel) {
        if ( instance == null )
            instance = new GameView(gameModel);

        return instance;
    }

    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(((GameModel)model).getCam().combined);    // Set camera for batch //TODO: This might need to be fixed
        ((GameModel)model).getGameMap().draw(batch);
        ((GameModel)model).getPlayer1().draw(batch);
        ((GameModel)model).getPlayer2().draw(batch);
    }
}
