package com.dat055.Model.Map.Tile;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Tile {
    protected float x, y;

    Tile(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void draw(SpriteBatch batch) {}

    public String toString() {
        return String.format("Tile @ (%.2f, %.2f)\n", x, y);
    }
}
