package com.dat055.model.entity.character;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

/**
 * A character that is defined as an enemy.
 * Designed to have a specific behavior.
 * @author Tobias Campbell
 * @version 17-02-2019
 */
public class Enemy extends Character {
    /**
     * Sets isMoving and lookingDirection to default values.
     * @param startPosition position where enemy should spawn on a map.
     * @param texturePath Path to a texture.
     * @param name name of the enemy entity.
     */
    public Enemy(Vector2 startPosition, String texturePath, String name) {
        super(startPosition, 64, 64, texturePath, name, 5, new Vector2(2, 20));
        isMoving = true;
        lookingDirection.x = 1;
        BOUNDING_BOX_COLOR = Color.GOLD;
        initSounds();
    }

    /**
     * Plays an enemy-specific sound effect when taking damage.
     * @param damage amount of damage to take
     */
    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
        playSound("takedamage");
    }

    /**
     * Initializes soundBank with enemy-specific sounds.
     */
    @Override
    protected void initSounds() {
        super.initSounds();
        soundBank.put("takedamage", loadFile("enemy_damage.mp3"));
        soundBank.put("death", loadFile("enemy_death.mp3"));
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
     * Reverses the enemy's horizontal velocity.
     */
    public void changeLookingDirectionX() {
        velocity.x = 0;
        lookingDirection.x = -lookingDirection.x;
    }
}


