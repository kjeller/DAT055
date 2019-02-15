package com.dat055.model.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class Character extends Entity {
    protected String name;
    private int healthPoints;
    Vector2 acceleration;
    Vector2 velocity;
    Vector2 deltaPosition;
    Vector2 direction;
    Vector2 oldPosition;
    Vector2 maxVelocity;
    Vector2 lookingDirection;
    private boolean isGrounded;
    private boolean isAlive;
    private boolean isMoving;


    public Character(Vector2 position, int height, int width, String texturePath, String name, int healthPoints, Vector2 maxVelocity) {
        super(position, height, width, texturePath);

        this.name = name;
        this.healthPoints = healthPoints;
        this.maxVelocity = new Vector2(maxVelocity);

        acceleration = new Vector2(Vector2.Zero);
        velocity = new Vector2(Vector2.Zero);
        oldPosition = new Vector2(position);
        deltaPosition = new Vector2(Vector2.Zero);
        direction = new Vector2(Vector2.Zero);
        lookingDirection = new Vector2(Vector2.Zero);

        isGrounded = false;
        isAlive = true;
        isMoving = false;
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
     *  Method for performing attack
     */
    public void attack() {
        System.out.println("Enemy attacks!");
    }

    /**
     * Method that makes the character jump.
     */
    public void jump() {
        if (isGrounded) {
            isGrounded = false;
            velocity.y = 8;
        }
    }

    /**
     * Method that works as a kind of physics engine for entities.
     */
    @Override
    public void update() {
        if (isAlive) {
            updateFalling();
            setVelocityX();
            setVelocityY();
            setPositions();
            setDirection();
        }
    }
    @Override
    public void draw(SpriteBatch sb, float rotation) {
        if (isAlive)
            super.draw(sb, rotation);
    }
    void setPositions() {
        oldPosition.set(position);
        position.add(Math.round(velocity.x), Math.round(velocity.y));
    }
    void setVelocityX() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        velocity.x += acceleration.x * deltaTime;

        if (velocity.x > maxVelocity.x)
            velocity.x = maxVelocity.x;
        if (velocity.x < -maxVelocity.x)
            velocity.x = -maxVelocity.x;
    }
    void setVelocityY() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        velocity.y += acceleration.y * deltaTime;
        if (velocity.y < -maxVelocity.y)
            velocity.y = -maxVelocity.y;
    }

    /**
     * Methods that updates the entity's y-position
     */
    private void updateFalling() {
        if (isGrounded) {
            velocity.y = 0;
            acceleration.y = 0;
        } else {
            acceleration.y = -20;
        }
    }
    void setDirection() {
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
     * Method that kills the character
     */
    private void die() {
        if (isAlive == true) {
            System.out.println("Player died!");
            isAlive = false;
        }

    }



    public void setXVelocity(int x) { velocity.x = x; }
    public void setYVelocity(int y) { velocity.y = y; }
    public void setXAcceleration(int x) { acceleration.x = x; }
    public void setGrounded(boolean val) { isGrounded = val; }
    public void setMoving(boolean val) {isMoving = val;}
    public void setDirectionY(int y) { direction.y = y; }
    public void setLookingDirection(Vector2 dir) {lookingDirection.set(dir); }

    public Vector2 getDirection() { return direction; }
    public Vector2 getVelocity() { return velocity; }


    public String toString() {
        return super.toString() + String.format("hp: %d, acc: (%.1f, %.1f),\n" +
                        "dir: (%.1f, %.1f), lookDir: %.1f,\nisGrounded: %s," +
                        "\nisAlive: %s,\nisMoving: %s\n",
                healthPoints, acceleration.x, acceleration.y, direction.x,
                direction.y, lookingDirection.x, isGrounded, isAlive, isMoving);
    }

}
