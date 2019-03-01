package com.dat055.model.map.tile;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

/**
 * A tile that will be used by a tilemap.
 * A tile has a rectangle that will be used for collision
 * between entities.
 * @author Karl Strålman
 * @version 2019-02-21
 */
public class Tile {
    protected float x, y; // Positions

    protected boolean isCollideable = false;
    protected Rectangle rect;

    Tile(float x, float y, float tileSize) {
        this.x = x;
        this.y = y;
        rect = new Rectangle(x, y, tileSize, tileSize);
    }

    public void draw(PolygonSpriteBatch batch, float rotation) {}
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
    public String toString() {
        return String.format("tile @ (%.2f, %.2f)\n", x, y);
    }
    public boolean getState(){return isCollideable; }
    public Rectangle getRect() {return rect; };
}
