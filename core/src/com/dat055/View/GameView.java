package com.dat055.View;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.dat055.Controller.GameController;
import com.dat055.Model.Collision.CollisionHandler;
import com.dat055.Model.Entity.Player;
import com.dat055.Model.GameModel;
import com.dat055.Model.Map.Tile.Tile;
import com.dat055.Model.Map.Tile.TileMap;

import java.util.ArrayList;

public class GameView extends View{
    private ShapeRenderer renderer;
    private boolean showRectangle;

    public GameView(GameModel gameModel) {
        this.model = gameModel;
        renderer = new ShapeRenderer();
        showRectangle = false;
    }

    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(((GameModel)model).getCam().combined);    // Set camera for batch //TODO: This might need to be fixed
        ((GameModel)model).getGameMap().draw(batch);
        Player player1 = ((GameModel)model).getPlayer1();
        Player player2 = ((GameModel)model).getPlayer2();
        player1.draw(batch);
        player2.draw(batch);

        if(showRectangle) {
            renderer.setProjectionMatrix(((GameModel)model).getCam().combined);
            TileMap front = ((GameModel)model).getGameMap().getFrontTileMap();
            for(int i = 0; i < front.getWidth(); i++){
                for(int j = 0; j < front.getHeight(); j++){
                    Tile tile = front.getTile(i, j);
                    if((tile).getState())
                        drawRectangle(tile.getRect(), Color.RED);
                }
            }
            ArrayList<Rectangle> hitboxRectangles = ((GameModel)model).getHandler1().getCheckedTiles();
            for (int i = 0; i < hitboxRectangles.size(); i++) {
                drawRectangle(hitboxRectangles.get(i), Color.MAGENTA);
            }
            drawRectangle(player1.getRect(), Color.BLUE);
            drawRectangle(player2.getRect(), Color.BLUE);
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
}
