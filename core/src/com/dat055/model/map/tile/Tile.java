package com.dat055.model.map.tile;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

/**
 * A tile that will be used in a {@link TileMap}
 * The rectangle used in this tile will be used for collision
 * with entities.
 * @author Karl Strålman
 * @version 2019-02-21
 */
public class Tile {
    protected float x, y; // Positions

    boolean isCollideable = false;
    Rectangle rect;

    /**
     * Default constructor for a tile.
     * @param x position.
     * @param y position.
     * @param tileSize - size of this tile
     */
    Tile(float x, float y, float tileSize) {
        this.x = x;
        this.y = y;
        rect = new Rectangle(x, y, tileSize, tileSize);
    }

    /**
     * Not actually implemented here but is needed because
     * tiles are put in a list that will be updated.
     * @param batch
     * @param rotation
     */
    public void draw(PolygonSpriteBatch batch, float rotation){}

    /**
     * Helper method for drawing a rectangle
     * @param renderer will render the rectangle
     */
    public void drawRectangle(ShapeRenderer renderer) {
        if(isCollideable) {
            renderer.begin(ShapeRenderer.ShapeType.Line);
            renderer.setColor(Color.RED);
            renderer.rect(rect.x, rect.y, rect.width, rect.height);
            renderer.end();
        }
    }

    /**
     * @return position of tile
     */
    public String toString() {
        return String.format("tile @ (%.2f, %.2f)\n", x, y);
    }

    /**
     * @return true if tile is collideable
     */
    public boolean getState(){return isCollideable; }

    /**
     * @return this tile's rectangle
     */
    public Rectangle getRect() {return rect; }
}
