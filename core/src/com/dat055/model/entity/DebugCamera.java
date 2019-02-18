package com.dat055.model.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class DebugCamera extends Player{
    public DebugCamera(Vector2 startPos) {
        super(startPos, null, "camcam");
    }
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
    @Override
    public void setTexture(String texturePath) {}
}
