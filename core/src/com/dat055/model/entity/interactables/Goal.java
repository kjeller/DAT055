package com.dat055.model.entity.interactables;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.dat055.model.entity.Entity;

/**
 * An entity that will be used to check if
 * a player is eligible to complete map.
 * @author Karl Strålman
 * @version 2019-02-28
 */
public class Goal extends Entity {
    public Goal(Vector2 position) {
        super(position, 64, 64);
        BOUNDING_BOX_COLOR = Color.GOLD;
    }

    /**
     * There is no sprite for this entity hence there will
     * be an empty draw method.
     * @param sb
     * @param rotation
     */
    @Override
    public void draw(PolygonSpriteBatch sb, float rotation) {}

    @Override
    public void drawBoundingBox(ShapeRenderer renderer) {
        drawRectangle(rect, ShapeRenderer.ShapeType.Filled, renderer);
    }
}
