package com.dat055.Model.Map;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.dat055.Model.Collision.CollisionHandler;
import com.dat055.Model.Entity.Entity;
import com.dat055.Model.Entity.Player;
import com.dat055.Model.Map.Tile.Tile;
import com.dat055.Model.Map.Tile.TileMap;

import java.util.ArrayList;

public class GameMap {
    private final Color PLAYER_RECTANGLE = Color.BLUE;
    private final Color TILE_RECTANGLE = Color.RED;

    private String name, id;  // name: map_0 or map_1, id is the id is json file
    private TileMap tileMap;

    private ArrayList<Entity> entities; // List of all entities on map, players included
    private Player player;  // a reference to player - makes it easier to control player
    private CollisionHandler colHandler;

    public GameMap(TileMap tileMap, ArrayList<Entity> entities, Player player, String name) {
        this.tileMap = tileMap;
        this.entities = entities;
        this.player = player;
        this.name = name;
        colHandler = new CollisionHandler(tileMap);
    }
    public void update(float deltaTime) {
        // Updates entities position, health etc.
        for(Entity entity : entities) {
            entity.update();
            if(entity instanceof Player) //TODO: Fix
                colHandler.checkCollision(entity);
        }
        //Todo: towbie fix
        colHandler.checkCollision(player.getHook());
    }

    public void render(SpriteBatch batch, float rotation) {
        for(Entity entity : entities) {
            entity.draw(batch, rotation);
        }
        for(int i = 0; i < tileMap.getWidth(); i++){
            for(int j = 0; j < tileMap.getHeight(); j++){
                Tile tile = tileMap.getTile(i, j);
                    tile.draw(batch, rotation);
            }
        }
    }

    /**
     * Will render all rectangles known to man. With predefined colors.
     * @param batch the spritebatch used
     * @param renderer for rendering the rectangles
     */
    public void renderRectangles(ShapeRenderer renderer) {

        for(Entity entity : entities) {
            drawRectangle(entity.getRect(), PLAYER_RECTANGLE, renderer);
        }
        for(int i = 0; i < tileMap.getWidth(); i++){
            for(int j = 0; j < tileMap.getHeight(); j++){
                Tile tile = tileMap.getTile(i, j);
                if(tile.getState())  // If the tile is collideable
                    drawRectangle(tile.getRect(), TILE_RECTANGLE, renderer);
            }
        }
    }

    /**
     * Helper method for drawing a rectangle
     * @param rectangle the rectangle that will be drawed
     * @param color the color of the rectangle
     * @param renderer will render the rectangle
     */
    private void drawRectangle(Rectangle rectangle, Color color, ShapeRenderer renderer) {
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(color);
        renderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        renderer.end();
    }

    public TileMap getTileMap() { return tileMap; }
    public ArrayList<Entity> getEntities() { return entities;}
    public Player getPlayer() { return player;}
    public String getName() { return name; }
    public String getId() { return id; }
    public String toString() {
        return  String.format("GameMap: %s \n -TileMap: %s \n -Player: %s \n -Entities: %s \n",
                this.name, this.tileMap, this.player, this.entities);
    }
}
