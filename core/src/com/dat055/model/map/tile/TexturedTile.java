package com.dat055.model.map.tile;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * A tile with a texture.
 * @author Karl Strålman
 * @version 2019-02-21
 */
class TexturedTile extends Tile {
    private Sprite sprite;

    /**
     * Default constructor for this tile.
     * @param x position.
     * @param y position.
     * @param tileSize size of this tile.
     * @param sprite for this tile.
     */
    TexturedTile(float x, float y, float tileSize, Sprite sprite) {
        super(x, y, tileSize);
        this.sprite = sprite;
        this.isCollideable = true;
    }

    /**
     * Draws tile with sprite.
     * @param batch used to draw.
     * @param rotation used to rotate before drawn.
     */
    @Override
    public void draw(PolygonSpriteBatch batch, float rotation) {
        batch.draw(sprite, x, y, 32, -y,
                rect.width, rect.height, 1,1, rotation);
    }
}
