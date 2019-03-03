package com.dat055.model.map;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.dat055.model.collision.CollisionHandler;
import com.dat055.model.entity.*;
import com.dat055.model.entity.character.Enemy;
import com.dat055.model.entity.character.Player;
import com.dat055.model.map.tile.Tile;
import com.dat055.model.map.tile.TileMap;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A gamemap contains a {@link TileMap} and a list of {@link Entity}
 * This connects the entities to the map itself and allows us to
 * let entities interact with tiles.
 * @author Karl Strålman
 * @version 2019-02-08
 */
public class GameMap {
    private String name;  // name of map
    private TileMap tileMap;
    private int width;

    private ArrayList<Entity> entities; // List of all entities on map, players included
    private Player player;  // a reference to player - makes it easier to control player
    private CollisionHandler colHandler;

    private boolean restart;
    private boolean finished;

    /**
     * Default constructor for this gamemap
     * @param tileMap
     * @param entities
     * @param player
     * @param name
     * @param width
     */
    public GameMap(TileMap tileMap, ArrayList<Entity> entities, Player player, String name, int width) {
        this.tileMap = tileMap;
        this.entities = entities;
        this.player = player;
        this.name = name;
        this.width = width;
        restart = false;
        colHandler = new CollisionHandler(this);
    }

    /**
     * Updates entities and collision for this map
     * @param deltaTime
     */
    public void update(float deltaTime) {
        Iterator it =  entities.iterator();
        // Player needs to actively be in goal to complete a map.
        // finished needs to be set to false before collision checking updates.
        finished = false;
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

    /**
     * Render all entities and tiles
     * @param batch
     * @param rotation
     */
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

    /**
     * is called if a player dies
     * @param entity
     */
    public void isDead(Entity entity) {
        if (entity instanceof Player && !((Player)entity).getIsAlive())
            restart = true;
    }

    /**
     * Sets finished boolean to true to determine if player has reached goal.
     * Since player must actively be in goal, finished is reset in update()
     */
    public void setFinished() {
        if(!finished) {
            finished = true;
            System.out.println("Goal has been reached!");
        }
    }

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

    /**
     * Draws toStrings() for the entities
     * @param font
     * @param batch
     */
    public void drawEntityText(BitmapFont font, PolygonSpriteBatch batch){
        for(Entity entity : entities)
            font.draw(batch, entity.toString(),
                    entity.getPosition().x , entity.getPosition().y+300);
    }

    /**
     * @return this map's {@link TileMap}.
     */
    public TileMap getTileMap() { return tileMap; }

    /**
     * @return the list of entities.
     */
    public ArrayList<Entity> getEntities() { return entities;}

    /**
     * @return player on this map.
     */
    public Player getPlayer() { return player;}

    /**
     * @return name of this map
     */
    public String getName() { return name; }

    /**
     * @return true if restart is true
     */
    public boolean getRestart() { return restart; }

    /**
     * @return width of this map
     */
    public int getWidth(){ return width; }

    /**
     * @return true if finished
     */
    public boolean isFinished() { return finished; }
    public String toString() {
        return  String.format("GameMap: %s \n -TileMap: %s \n -Player: %s \n -Entities: %s \n",
                this.name, this.tileMap, this.player, this.entities);
    }
}
