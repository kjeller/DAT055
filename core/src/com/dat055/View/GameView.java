package com.dat055.View;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.dat055.Controller.GameController.Mode;
import com.dat055.Model.GameModel;
import com.dat055.Model.Map.GameMap;

public class GameView extends View{
    private ShapeRenderer renderer;
    private Mode mode;
    private boolean debug = false;
    private float rotationInc = 0;

    public GameView(GameModel gameModel) {
        this.model = gameModel;
        renderer = new ShapeRenderer();
    }

    public void render(SpriteBatch batch) {
        GameMap map1 = ((GameModel)model).getGameMap1();
        GameMap map2 = ((GameModel)model).getGameMap2();
        batch.setProjectionMatrix(((GameModel)model).getCam().combined);    // Set camera for batch

        float rotation = mode == Mode.FRONT ? 180f + rotationInc : rotationInc; // Sets rotation for planes

        map1.render(batch, rotation);
        map2.render(batch, rotation - 180);

        // Debug
        if(debug) {
            batch.end();
            renderer.setProjectionMatrix(((GameModel)model).getCam().combined);
            if(mode == Mode.FRONT) {
                map1.renderRectangles(renderer);
                batch.begin();
                map1.drawEntityText(((GameModel)model).getFont(), batch);
            } else {
                map2.renderRectangles(renderer);
                batch.begin();
                map2.drawEntityText(((GameModel)model).getFont(), batch);
            }


        }
    }
    public void setRotationInc(float timer) { rotationInc = timer;}
    public void setDebug(boolean bool) { debug = bool; }
    public void setMode(Mode mode) { this.mode = mode; }
}
