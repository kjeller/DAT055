package com.dat055.Model.Map.Tile;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * A tile with collision and a texture
 */
public class TexturedTile extends Tile {
    private Sprite sprite;


    public TexturedTile(float x, float y, float tileSize) {
        super(x, y, tileSize);
    }

    public TexturedTile(float x, float y, float tileSize, Sprite sprite) {
        super(x, y, tileSize);
        this.sprite = sprite;
        this.isCollideable = true;


        //state = State.SOLID;
    }

    @Override
    public void draw(SpriteBatch batch, float rotation) {
        batch.draw(sprite, x, y, 32, -y,
                box.width, box.height, 1,1, rotation);
    }

    /**
     * Set sprite on Tile
     * @param sprite
     */
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
