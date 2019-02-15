package com.dat055.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.dat055.Controller.GameController.Mode;
import com.dat055.Model.GameModel;
import com.dat055.Model.Map.GameMap;

public class GameView extends View{
    private ShapeRenderer renderer;
    private Mode mode;
    private boolean debug = false;
    private float rotation = 0;
    private String debugString;

    public GameView(GameModel gameModel) {
        this.model = gameModel;
        renderer = new ShapeRenderer();
    }

    public void render(SpriteBatch batch) {
        GameMap map1 = ((GameModel)model).getGameMap1();
        GameMap map2 = ((GameModel)model).getGameMap2();
        batch.setProjectionMatrix(((GameModel)model).getCam().combined);    // Set camera for batch

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
            BitmapFont font = ((GameModel)model).getFont();
            Camera cam = ((GameModel)model).getCam();
            batch.setProjectionMatrix(cam.combined);
            font.setColor(Color.WHITE);
            font.draw(batch, debugString,
                    cam.position.x - Gdx.graphics.getWidth()/2,
                    cam.position.y+Gdx.graphics.getHeight()/2);
        }
    }

    public float getRotation() { return rotation; }

    public void setDebugString(String debugString) { this.debugString = debugString; }
    public void setRotation(float rotation) { this.rotation = rotation;}
    public void setDebug(boolean debug) { this.debug = debug; }
    public void setMode(Mode mode) { this.mode = mode; }
}
