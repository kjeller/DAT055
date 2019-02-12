package com.dat055.Model.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class DebugCamera extends Player{
    public DebugCamera(Vector2 startPos) {
        super(startPos, null, "camcam");
    }
    @Override
    public void update() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        velocity.x += acceleration.x * deltaTime;

        if (velocity.x > maxVelocity)
            velocity.x = maxVelocity;
        if (velocity.x < -maxVelocity)
            velocity.x = -maxVelocity;

        velocity.y += acceleration.y * deltaTime;
        oldPosition.set(position);
        position.add(velocity);


        deltaPosition.set(oldPosition.x-position.x, oldPosition.y-position.y);

        if (deltaPosition.x > 0) direction.x = -1; else if (deltaPosition.x < 0) direction.x = 1;
        if (deltaPosition.y > 0) direction.y = -1; else if (deltaPosition.y < 0) direction.y = 1;

        rect.setPosition(position.x, position.y);
    }
    @Override
    public void setTexture(String texturePath) {}

    @Override
    public void draw(SpriteBatch batch) {}
}
