package com.dat055.Model.Map.Tile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * A tile with collision and a texture
 */
public class TexturedTile extends Tile {
    private Texture texture;

    public TexturedTile(float x, float y, float tileSize) {
        super(x, y, tileSize);
    }

    public TexturedTile(float x, float y, Texture texture, float tileSize) {
        super(x, y, tileSize);
        this.texture = texture;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
