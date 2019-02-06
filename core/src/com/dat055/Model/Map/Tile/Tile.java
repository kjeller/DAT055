package com.dat055.Model.Map.Tile;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Tile {
    protected float x, y;
    Rectangle box;
    protected boolean isCollideable = false;

    //protected State state;

    Tile(float x, float y, float tileSize) {
        this.x = x;
        this.y = y;
        box = new Rectangle(x, y, tileSize, tileSize);
    }

    public void draw(SpriteBatch batch) {}

    public String toString() {
        return String.format("Tile @ (%.2f, %.2f)\n", x, y);
    }
    public boolean getState(){return isCollideable; }
    public Rectangle getRect() {return box; };
}
