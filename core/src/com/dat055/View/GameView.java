package com.dat055.View;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.dat055.Model.GameModel;
import com.dat055.Model.Map.GameMap;
import com.dat055.Model.Map.Tile.Tile;
import com.dat055.Model.Map.Tile.TileMap;

public class GameView extends View{
    private ShapeRenderer renderer;
    private boolean isBackActive = false; // Front map is active if this is false
    private boolean debug = false;
    private float rotationTimer = 0;

    public GameView(GameModel gameModel) {
        this.model = gameModel;
        renderer = new ShapeRenderer();
    }

    public void render(SpriteBatch batch) {
        GameMap map = ((GameModel)model).getGameMap();
        batch.setProjectionMatrix(((GameModel)model).getCam().combined);    // Set camera for batch
        map.render(batch, rotationTimer, isBackActive);

        if(debug) {
            renderer.setProjectionMatrix(((GameModel)model).getCam().combined);
            map.drawAllRectangles(renderer);
        }
    }

    private void drawMapRectangle(TileMap map, Color color) {
        for(int i = 0; i < map.getWidth(); i++){
            for(int j = 0; j < map.getHeight(); j++){
                Tile tile = map.getTile(i, j);
                if((tile).getState())
                    drawRectangle(tile.getRect(), color);
            }
        }
    }

    private void drawRectangle(Rectangle rectangle, Color color) {
        renderer.begin(ShapeType.Filled);
        renderer.setColor(color);
        renderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        renderer.end();
    }

    public void setRotationTimer(float timer) { rotationTimer = timer;}
    public void setDebug(boolean bool) { debug = bool; }
    public void setBackActive(boolean bool) {isBackActive = bool;}
}
