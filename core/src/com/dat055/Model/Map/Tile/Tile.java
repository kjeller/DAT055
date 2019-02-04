package com.dat055.Model.Map.Tile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Tile {
    private float x, y;
    private Texture texture;

    Tile(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public String toString() {
        return String.format("Tile @ (%.2f, %.2f)\n", x, y);
    }
}
