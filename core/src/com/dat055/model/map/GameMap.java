package com.dat055.model.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.dat055.model.collision.CollisionHandler;
import com.dat055.model.entity.*;
import com.dat055.model.map.tile.Tile;
import com.dat055.model.map.tile.TileMap;

import java.util.ArrayList;
import java.util.Iterator;

public class GameMap {
    private final Color PLAYER_RECTANGLE = Color.BLUE;
    private final Color TILE_RECTANGLE = Color.RED;
    private final Color ENEMY_RECTANGLE = Color.GOLD;
    private final Color DOOR_RECTANGLE = Color.BROWN;

    private String name, id;  // name: map_0 or map_1, id is the id is json file
    private TileMap tileMap;
    private int width;

    private ArrayList<Entity> entities; // List of all entities on map, players included
    private Player player;  // a reference to player - makes it easier to control player
    private CollisionHandler colHandler;

    public GameMap(TileMap tileMap, ArrayList<Entity> entities, Player player, String name, int width) {
        this.tileMap = tileMap;
        this.entities = entities;
        this.player = player;
        this.name = name;
        this.width = width;
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

    private void checkIfDead(Entity entity) {
       /* if (entity instanceof Character) {

        }*/
    }

    // == Debug stuff below ==
    /**
     * Will render all rectangles known to man. With predefined colors.
     * @param renderer for rendering the rectangles
     */
    public void renderRectangles(ShapeRenderer renderer) {

        for(Entity entity : entities) {
            if (entity instanceof Player)
                drawRectangle(entity.getRect(), PLAYER_RECTANGLE, renderer);
            else if (entity instanceof Enemy) {
                drawRectangle(entity.getRect(), ENEMY_RECTANGLE, renderer);
            }
            else if(entity instanceof Door){
                drawRectangle(entity.getRect(), DOOR_RECTANGLE,renderer );
            }
            if(entity instanceof Player) {
                Hook hook = ((Player)entity).getHook();
                if(hook != null) {
                    drawRectangle(hook.getRect(), PLAYER_RECTANGLE, renderer);
                    drawRectangle(hook.getWire(), PLAYER_RECTANGLE, renderer);
                    drawPolygon(hook.getWire2(), Color.WHITE, renderer);
                }
            }

        }
        for(int i = 0; i < tileMap.getWidth(); i++){
            for(int j = 0; j < tileMap.getHeight(); j++){
                Tile tile = tileMap.getTile(i, j);
                if(tile.getState())  // If the tile is collideable
                    drawRectangle(tile.getRect(), TILE_RECTANGLE, renderer);
            }
        }
    }
    public void drawPolygon(Polygon polygon, Color color, ShapeRenderer renderer) {
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.WHITE);
        renderer.polygon(polygon.getTransformedVertices());
        renderer.end();
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
    public String getId() { return id; }
    public int getWidth(){ return width; }
    public String toString() {
        return  String.format("GameMap: %s \n -TileMap: %s \n -Player: %s \n -Entities: %s \n",
                this.name, this.tileMap, this.player, this.entities);
    }
}
