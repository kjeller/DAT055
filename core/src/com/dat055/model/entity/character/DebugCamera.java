package com.dat055.model.entity.character;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Karl Strålman
 * @version 17-02-2019
 */

public class DebugCamera extends Player {
    public DebugCamera(Vector2 startPos) {
        super(startPos, null, "camcam");
    }

    /**
     * The logic of the camera player.
     * @param deltaTime time since last frame.
     */
    @Override
    public void update(float deltaTime) {
        velocity.x += acceleration.x * deltaTime;
        if (velocity.x > maxVelocity.x)
            velocity.x = maxVelocity.x;
        if (velocity.x < -maxVelocity.x)
            velocity.x = -maxVelocity.x;

        velocity.y += acceleration.y * deltaTime;
        oldPosition.set(position);
        position.add(velocity);

        setDirection();
        rect.setPosition(1, 1);
    }

    /**
     * Camera is not meant to have a texture, so it is simply overridden.
     * @param texturePath file path to texture.
     */
    @Override
    public void setTexture(String texturePath) {}
}
