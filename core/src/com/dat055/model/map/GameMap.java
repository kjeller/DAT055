package com.dat055.model.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.dat055.model.collision.CollisionHandler;
import com.dat055.model.entity.*;
import com.dat055.model.map.tile.Tile;
import com.dat055.model.map.tile.TileMap;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A gamemap contains a tilemap with entities.
 * This connects the entities to the map itself.
 * @author Karl Strålman
 * @version 2019-02-08
 */
public class GameMap {
    private final Color PLAYER_RECTANGLE = Color.BLUE;
    private final Color TILE_RECTANGLE = Color.RED;
    private final Color ENEMY_RECTANGLE = Color.GOLD;
    private final Color DOOR_RECTANGLE = Color.BROWN;

    private String name;  // name: map_0 or map_1, id is the id is json file
    private TileMap tileMap;
    private int width;

    private ArrayList<Entity> entities; // List of all entities on map, players included
    private Player player;  // a reference to player - makes it easier to control player
    private CollisionHandler colHandler;

    private boolean restart;

    public GameMap(TileMap tileMap, ArrayList<Entity> entities, Player player, String name, int width) {
        this.tileMap = tileMap;
        this.entities = entities;
        this.player = player;
        this.name = name;
        this.width = width;
        restart = false;
        colHandler = new CollisionHandler(this);

    }
    public void update(float deltaTime) {
        Iterator it =  entities.iterator();
        // Updates entities position, health etc.
        while (it.hasNext()) {
            Entity entity = (Entity)it.next();
            entity.update(deltaTime);

            if (entity instanceof Enemy) {
                if (!((Enemy)entity).getIsAlive())
                    it.remove();
            }
            colHandler.checkCollision(entity);
            colHandler.checkCollision(player.getHook());
            isDead(entity);
        }
    }

    public void render(PolygonSpriteBatch batch, float rotation) {
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

    public void isDead(Entity entity) {
        if (entity instanceof Player && !((Player)entity).getIsAlive())
            restart = true;
    }

    // == Debug stuff below ==
    /**
     * Will render all rectangles known to man. With predefined colors.
     * @param renderer for rendering the rectangles
     */
    public void drawBoundingBoxes(ShapeRenderer renderer) {

        for(Entity entity : entities) {
            entity.drawBoundingBox(renderer);
        }

        for(int i = 0; i < tileMap.getWidth(); i++){
            for(int j = 0; j < tileMap.getHeight(); j++){
                Tile tile = tileMap.getTile(i, j);
                tile.drawRectangle(renderer);
            }
        }
    }

    public void drawEntityText(BitmapFont font, PolygonSpriteBatch batch){
        for(Entity entity : entities)
            font.draw(batch, entity.toString(),
                    entity.getPosition().x , entity.getPosition().y+300);
    }

    // Getters and setters
    public TileMap getTileMap() { return tileMap; }
    public ArrayList<Entity> getEntities() { return entities;}
    public Player getPlayer() { return player;}
    public String getName() { return name; }
    public boolean getRestart() { return restart; }
    public int getWidth(){ return width; }
    public String toString() {
        return  String.format("GameMap: %s \n -TileMap: %s \n -Player: %s \n -Entities: %s \n",
                this.name, this.tileMap, this.player, this.entities);
    }
}
