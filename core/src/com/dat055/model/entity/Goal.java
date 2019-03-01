package com.dat055.model.entity;

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
    }

    @Override
    public void update(float deltaTime) {
        hasBeenReached = false;
    }

    @Override
    public void drawBoundingBox(ShapeRenderer renderer) {
        drawRectangle(rect, renderer);
    }

    public void set(boolean bool) { hasBeenReached = bool; }
}
