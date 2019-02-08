package com.dat055.View;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.dat055.Model.GameModel;
import com.dat055.Model.Map.Tile.Tile;
import com.dat055.Model.Map.TileMap;

import java.awt.*;

public class GameView extends View{
    private static GameView instance = null;
    private ShapeRenderer renderer;
    private boolean showRectangle = false;

    private GameView(GameModel gameModel) {
        this.model = gameModel;
        renderer = new ShapeRenderer();
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

        // Ugly shite for debugging below and above - Kjelle
        if(showRectangle) {
            drawRectangle(((GameModel)model).getPlayer1().getRect(), Color.BLUE);
            drawRectangle(((GameModel)model).getPlayer2().getRect(), Color.BLUE);
            TileMap front = ((GameModel)model).getGameMap().getFrontTileMap();
            for(int i = 0; i < front.getWidth(); i++){
                for(int j = 0; j < front.getHeight(); j++){
                    Tile tile = front.getTile(i, j);
                    if((tile).getState())
                        drawRectangle(tile.getRect(), Color.RED);
                }
            }
        }
    }

    public void toggleRectangle() {
        if(!showRectangle)
            showRectangle = true;
        else
            showRectangle = false;
    }
    private void drawRectangle(Rectangle rectangle, Color color) {
        renderer.setProjectionMatrix(((GameModel)model).getCam().combined);
        renderer.begin(ShapeType.Filled);
        renderer.setColor(color);
        renderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        renderer.end();
    }
}
