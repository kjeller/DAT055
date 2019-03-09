package com.dat055.model.entity.interactables;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
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
    private boolean isActive = false;

    public Goal(Vector2 position, String TexturePath) {
        super(position, 64, 64, TexturePath);
        BOUNDING_BOX_COLOR = Color.GOLD;
    }
    public Goal(Vector2 position) {
        super(position, 64, 64);
        BOUNDING_BOX_COLOR = Color.GOLD;
    }

    /**
     * Update method that updates the texture of goal depending on if
     * isActive is true or false.
     * */
    public void update(float deltaTime) {
        if(sprite != null) {
            if(texturePath.equals("textures/interactables/flag.png") || !isActive){
                this.setTexture("textures/interactables/flagdown.png");
                isActive = false;
            }
            if(texturePath.equals("textures/interactables/flagdown.png") && isActive){
                this.setTexture("textures/interactables/flag.png");
                isActive = false;
            }
        }
    }
    /**
     * Activates the flag at goal. isActive is used by the update method to
     * check if the player is at the goal.
     * */
    public void activate() {
        isActive = true;
    }
    /**
     * Draws the flag texture
     * @param sb
     * @param rotation
     */
    @Override
    public void draw(PolygonSpriteBatch sb, float rotation) {
        if(sprite != null)
            super.draw(sb, rotation);
    }
    @Override
    public void drawBoundingBox(ShapeRenderer renderer) {
        drawRectangle(rect, ShapeRenderer.ShapeType.Filled, renderer);
    }
}
