package com.dat055.model.entity.character;

import com.badlogic.gdx.math.Vector2;

/**
 * @author Tobias Campbell
 * @version 17-02-2019
 */
public class Enemy extends Character {

    public Enemy(Vector2 startPosition, String texturePath, String name) {
        super(startPosition, 64, 64, texturePath, name, 5, new Vector2(2, 20));
        isMoving = true;
        lookingDirection.x = 1;
    }

    /**
     * Basic logic of a simple enemy.
     * @param deltaTime time since last frame.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (isAlive) {
            move(lookingDirection.x);
        }
    }

    /**
     * Switches the enemy's direction to the other side.
     */
    public void changeLookingDirectionX() {
        velocity.x = 0;
        lookingDirection.x = -lookingDirection.x;
    }
}


