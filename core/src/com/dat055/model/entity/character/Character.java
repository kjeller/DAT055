package com.dat055.model.entity.character;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.dat055.model.entity.Entity;

/**
 * An entity that has properties which an enemy or
 * a player would want, that has physics for moving.
 * @author Tobias Campbell
 * @version 01-03-2019
 */

public abstract class Character extends Entity {
    protected String name;
    private int healthPoints;
    Vector2 acceleration;
    Vector2 velocity;
    private Vector2 deltaPosition;
    private Vector2 direction;
    private Vector2 oldPosition;
    Vector2 maxVelocity;
    Vector2 lookingDirection;
    private boolean isGrounded;
    boolean isAlive;
    boolean isMoving;

    /**
     * Initiates acceleration, velocity, oldPosition, deltaPosition, direcition, lookingDirection.
     * Sets isGrounded, isAlive and isMoving to default values.
     * @param position Position of the lower left rectangle of the character entity.
     * @param height Height of the character entity.
     * @param width Width of the character entity.
     * @param texturePath Path to a texture.
     * @param name name of the character entity.
     * @param healthPoints number of health points for the character entity.
     * @param maxVelocity max velocity of the character entity.
     */

    Character(Vector2 position, int height, int width, String texturePath, String name, int healthPoints, Vector2 maxVelocity) {
        super(position, height, width, texturePath);

        this.name = name;
        this.healthPoints = healthPoints;
        this.maxVelocity = new Vector2(maxVelocity);

        acceleration = new Vector2(Vector2.Zero);
        velocity = new Vector2(Vector2.Zero);
        oldPosition = new Vector2(position);
        deltaPosition = new Vector2(Vector2.Zero);
        direction = new Vector2(Vector2.Zero);
        lookingDirection = new Vector2(1, 0);

        isGrounded = false;
        isAlive = true;
        isMoving = false;
        BOUNDING_BOX_COLOR = Color.BLUE;
    }

    /**
     * Method that moves the entity based on acceleration
     * @param dir direction to move in
     */
    public void move(float dir) {
        if (isMoving) {
            switch((int)dir) {
                case -1:
                    // If already moving in other direction, give stronger acceleration.
                    if (direction.x > 0)
                        acceleration.x = (isGrounded) ? -50 : -25;
                    else
                        acceleration.x = (isGrounded) ? -18 : -9;
                    break;
                case 1:
                    // If already moving in other direction, give stronger acceleration.
                    if (direction.x < 0)
                        acceleration.x = (isGrounded) ? 50 : 25;
                    else
                        acceleration.x = (isGrounded) ? 18 : 9;
                    break;
            }
        // If player isn't holding any key, and should stop moving.
        } else if (velocity.x != 0) {
            acceleration.x = 24 * -direction.x;
            if (velocity.x < 0.5 && velocity.x > -0.5) {
                velocity.x = 0;
                acceleration.x = 0;
            }
        }
    }

    /**
     * Method that makes the character jump.
     */
    public void jump() {
        if (isGrounded) {
            isGrounded = false;
            velocity.y = 8;
            playSound("jump");
        }
    }

    /**
     * Method that works as a kind of physics engine for entities.
     * Velocities, positions, etc.
     */
    @Override
    public void update(float deltaTime) {
        if (isAlive) {
            setFlip();
            updateFalling();
            setVelocityX(deltaTime);
            setVelocityY(deltaTime);
            setPositions();
            setDirection();
        }
    }

    /**
     * Method used to draw the character.
     * @param sb spritebatch that is used.
     * @param rotation sets the rotation when toggling between players.
     */
    @Override
    public void draw(PolygonSpriteBatch sb, float rotation) {
        if (isAlive)
            super.draw(sb, rotation, Vector2.Zero);
    }

    /**
     * Helper method used to set position based on velocity.
     */
    private void setPositions() {
        oldPosition.set(position);
        position.add(Math.round(velocity.x), Math.round(velocity.y));
    }

    /**
     * Helper method used to set horizontal velocity based on acceleration.
     * @param deltaTime time since last frame
     */
    private void setVelocityX(float deltaTime) {
        velocity.x += acceleration.x * deltaTime;

        if (velocity.x > maxVelocity.x)
            velocity.x = maxVelocity.x;
        if (velocity.x < -maxVelocity.x)
            velocity.x = -maxVelocity.x;
    }

    /**
     * Helper method used to set vertical velocity based on acceleration.
     * @param deltaTime time since last frame
     */
    private void setVelocityY(float deltaTime) {
        velocity.y += acceleration.y * deltaTime;
        if (velocity.y < -maxVelocity.y)
            velocity.y = -maxVelocity.y;
    }

    /**
     * Helper method that updates the entity's acceleration.
     */
    private void updateFalling() {
        if (isGrounded) {
            velocity.y = 0;
            acceleration.y = 0;
        } else {
            acceleration.y = -20;
        }
    }

    /**
     * Helper method that sets the entity's direction.
     */
    private void setDirection() {
        deltaPosition.set(oldPosition.x-position.x, oldPosition.y-position.y);
        // direction.x = -1: left, direction.x = 1: right
        if (deltaPosition.x > 0) direction.x = -1; else if (deltaPosition.x < 0) direction.x = 1;
        // direction.y = -1: down. direction.y = 1: up
        if (deltaPosition.y > 0) direction.y = -1; else if (deltaPosition.y < 0) direction.y = 1;
        rect.setPosition(position.x, position.y);
    }
    /**
     * Method that makes the character take damage
     * @param damage amount of damage to take
     */
    public void takeDamage(int damage) {
        healthPoints -= damage;
        if (healthPoints <= 0)
            die();
    }

    /**
     * Method that kills the entity.
     */
    private void die() {
        if (isAlive) {
            isAlive = false;
        }
    }

    /**
     * Method that flips the entity if looking in a certain direction.
     */
    private void setFlip() {
        float temp;
        temp = (lookingDirection.x > 0) ? 1 : -1;
        sprite.setScale(temp, 1);
    }

    // Get and set methods galore.

    /**
     * Set the character's horizontal velocity.
     * @param x value to be set.
     */
    public void setXVelocity(int x) { velocity.x = x; }

    /**
     * Set the character's vertical velocity.
     * @param y value to be set.
     */
    public void setYVelocity(int y) { velocity.y = y; }

    /**
     * Set the character's horizontal acceleration..
     * @param x value to be set.
     */
    public void setXAcceleration(int x) { acceleration.x = x; }

    /**
     * Set the character's isGrounded state.
     * @param val value to be set.
     */
    public void setGrounded(boolean val) { isGrounded = val; }

    /**
     * Set the character's setMoving state.
     * @param val value to be set.
     */
    public void setMoving(boolean val) {isMoving = val;}

    /**
     * Set the character's horizontal looking direction.
     * @param dir value to be set.
     */
    public void setLookingDirectionX(int dir) {lookingDirection.x = dir; }

    /**
     * Set the character's vertical looking direction.
     * @param dir value to be set.
     */
    public void setLookingDirectionY(int dir) {lookingDirection.y = dir; }

    /**
     * Set the character's looking direction.
     * @param dir value to be set.
     */
    public void setLookingDirection(Vector2 dir) {lookingDirection = dir;}

    /**
     * Set the character's vertical direction.
     * @param val value to be set.
     */
    public void setDirectionY(int val) { direction.y = val; }

    /**
     * Set the character's isAlive state.
     * @param bool value to be set.
     */
    public void setIsAlive(boolean bool) { isAlive = bool;}

    /**
     * Get the character's direction.
     * direction.x = -1: left, direction.x = 1: right
     * direction.y = -1: down. direction.y = 1: up
     * @return direction of the character.
     */
    public Vector2 getDirection() { return direction; }

    /**
     * Get the character's looking direction.
     * direction.x = -1: left, direction.x = 1: right
     * direction.y = -1: down. direction.y = 1: up
     * @return direction of the character.
     */
    public Vector2 getLookDirection() { return lookingDirection; }

    /**
     * Get the character's velocity.
     * @return velocity of the character.
     */
    public Vector2 getVelocity() { return velocity; }

    /**
     *  Get the character's deltaPosition.
     * @return deltaPosition of the character.
     */
    public Vector2 getDeltaPosition() { return deltaPosition; }

    /**
     * Get the character's isAlive state.
     * @return isAlive of the character.
     */
    public boolean getIsAlive() { return isAlive; }

    /**
     * Get if the character is in motion.
     * @return false if not in motion, else true.
     */
    public boolean getInMotion() { return (velocity.x != 0 || velocity.y !=0); }

    /**
     * Method that returns the character's variables. Used for debugging.
     * @return the string containing all data.
     */
    public String toString() {
        return super.toString() + String.format("hp: %d,\nvelocity: (%.1f, %.1f),\n acc: (%.1f, %.1f),\n" +
                        "dir: (%.1f, %.1f), lookDir: (%.0f, %.0f),\nisGrounded: %s," +
                        "\nisAlive: %s,\nisMoving: %s\n",
                healthPoints, velocity.x, velocity.y, acceleration.x, acceleration.y, direction.x,
                direction.y, lookingDirection.x, lookingDirection.y, isGrounded, isAlive, isMoving);
    }

}
