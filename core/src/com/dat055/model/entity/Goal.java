package com.dat055.model.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * An entity that will check if a player
 * has completed a map.
 * @author Karl Strålman
 * @version 2019-02-28
 */
public class Goal extends Entity {
    private boolean hasBeenReached;
    public Goal(Vector2 position) {
        super(position, 64, 64);
        hasBeenReached = false;
        BOUNDING_BOX_COLOR = Color.GOLD;
    }

    @Override
    public void update(float deltaTime) {
        hasBeenReached = false;
    }

    @Override
    public void draw(PolygonSpriteBatch sb, float rotation) {}

    @Override
    public void drawBoundingBox(ShapeRenderer renderer) {
        drawRectangle(rect, ShapeRenderer.ShapeType.Filled, renderer);
    }

    public void set(boolean bool) { hasBeenReached = bool; }

    public boolean getIfReached() { return hasBeenReached; }
}
