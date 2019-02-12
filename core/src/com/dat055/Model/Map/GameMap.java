package com.dat055.Model.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.dat055.Model.Entity.Entity;
import com.dat055.Model.Entity.Player;
import com.dat055.Model.GameModel;
import com.dat055.Model.Map.Tile.Tile;
import com.dat055.Model.Map.Tile.TileMap;

import java.util.ArrayList;

public class GameMap {
    TileMap front, back;
    String id, name;  // Current map id and name
    int tileSize;

    // Separate front and back entities to rotate them easier
    ArrayList<Entity> entitiesFront;
    ArrayList<Entity> entitiesBack;

    Player player1;
    Player player2;

    GameMap() {
        id = "Not set.";
        name = "Not set.";
    }

    public void render(SpriteBatch batch, float rotation, boolean isBackActive) {
        if(isBackActive) {
            drawRotated(batch, back, front, entitiesBack, entitiesFront, rotation);
        }
        else {
            drawRotated(batch, front, back, entitiesFront, entitiesBack, rotation);
        }
    }

    private void drawRotated(SpriteBatch batch, TileMap active, TileMap paused, ArrayList<Entity> activeE, ArrayList<Entity> pausedE, float rotation) {
        paused.render(batch, rotation, tileSize);
        active.render(batch, 180f + rotation, tileSize);
        drawEntities(batch, activeE, 180f + rotation);
        drawEntities(batch, pausedE, rotation);
    }
    private void drawEntities(SpriteBatch batch, ArrayList<Entity> entities, float rotation) {
        for(Entity entity : entities) {
            entity.draw(batch, rotation);
        }
    }
    public void drawAllRectangles(ShapeRenderer renderer) {
        drawMapRectangle(front, Color.RED, renderer);
        drawMapRectangle(back, Color.RED, renderer);

        for(Entity entity : entitiesFront) {
            drawRectangle(entity.getRect(), Color.BLUE, renderer);
        }
        for(Entity entity : entitiesBack) {
            drawRectangle(entity.getRect(), Color.BLUE, renderer);
        }
    }
    private void drawMapRectangle(TileMap map, Color color, ShapeRenderer renderer) {
        for(int i = 0; i < map.getWidth(); i++){
            for(int j = 0; j < map.getHeight(); j++){
                Tile tile = map.getTile(i, j);
                if((tile).getState())  // If the tile is collideable
                    drawRectangle(tile.getRect(), color, renderer);
            }
        }
    }
    private void drawRectangle(Rectangle rectangle, Color color, ShapeRenderer renderer) {
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(color);
        renderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        renderer.end();
    }

    public TileMap getFrontTileMap() { return front; }
    public TileMap getBackTileMap() { return back; }
    public ArrayList<Entity> getEntitiesFront() { return entitiesFront;}
    public ArrayList<Entity> getEntitiesBack() { return entitiesBack;}
    public String getId() { return id; }
    public String getName() { return name; }
    public Player getPlayer1() {return player1;}
    public Player getPlayer2() {return player2;}
    public String toString() {
        return  String.format("Properties: id=%s, name=%s, frontmap=%s, backmap=%s",
                this.id, this.name, this.front, this.back);
    }
}
