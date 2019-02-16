package com.dat055.model.entity;

import com.badlogic.gdx.math.Vector2;

public class Enemy extends Character {

    public Enemy(Vector2 startPosition, String texturePath, String name) {
        super(startPosition, 64, 64, texturePath, name, 5, new Vector2(2, 20));
        isMoving = true;
        lookingDirection.x = 1;
    }
    public void action(String act) {

    }
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (isAlive) {
            move(lookingDirection.x);
        }
    }
    public void changeLookingDirectionX() {
        velocity.x = 0;
        lookingDirection.x = -lookingDirection.x;
    }
}


