package com.dat055.View;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.dat055.Model.Entity.Player;
import com.dat055.Model.GameModel;
import com.dat055.Model.Map.Tile.Tile;
import com.dat055.Model.Map.Tile.TileMap;

public class GameView extends View{
    private ShapeRenderer renderer;
    private boolean showRectangle;
    private float rotationTimer = 0;

    public GameView(GameModel gameModel) {
        this.model = gameModel;
        renderer = new ShapeRenderer();
        showRectangle = false;
    }

    public void render(SpriteBatch batch) {

        batch.setProjectionMatrix(((GameModel)model).getCam().combined);    // Set camera for batch //TODO: This might need to be fixed
        ((GameModel)model).getGameMap().draw(batch, rotationTimer, ((GameModel)model).getMode());
        Player player1 = ((GameModel)model).getPlayer1();
        Player player2 = ((GameModel)model).getPlayer2();
        player1.draw(batch);
        player2.draw(batch);

        if(showRectangle) {
            renderer.setProjectionMatrix(((GameModel)model).getCam().combined);

            drawMapRectangle(((GameModel)model).getGameMap().getFrontTileMap(), Color.RED);
            drawMapRectangle(((GameModel)model).getGameMap().getBackTileMap(), Color.RED);

            drawRectangle(player1.getRect(), Color.BLUE);
            drawRectangle(player2.getRect(), Color.BLUE);
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

    public boolean getShowRectangle() { return showRectangle; }

    public void setShowRectangle(boolean bool) { showRectangle = bool; }
    public void setRotationTimer(float val) {this.rotationTimer = val;}
}
