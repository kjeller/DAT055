package com.dat055.model.map.tile;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * A tile with a texture.
 * @author Karl Strålman
 * @version 2019-02-21
 */
public class TexturedTile extends Tile {
    private Sprite sprite;

    public TexturedTile(float x, float y, float tileSize, Sprite sprite) {
        super(x, y, tileSize);
        this.sprite = sprite;
        this.isCollideable = true;
    }

    @Override
    public void draw(PolygonSpriteBatch batch, float rotation) {
        batch.draw(sprite, x, y, 32, -y,
                box.width, box.height, 1,1, rotation);
    }

    /**
     * Set sprite on tile
     * @param sprite
     */
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
