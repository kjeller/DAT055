package com.dat055.Model.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public abstract class Character extends Entity {
    public String name;
    private int healthPoints;
    protected Vector2 acceleration;
    protected Vector2 velocity;
    protected Vector2 deltaPosition;
    protected Vector2 direction;
    protected Vector2 oldPosition;

    protected float maxVelocity;
    protected int gravity;
    protected boolean isGrounded;
    protected boolean isAlive;
    protected boolean isMoving;


    public Character(Vector2 position, int height, int width, String texturePath, String name, int healthPoints, float maxVelocity) {
        super(position, height, width, texturePath);

        this.name = name;
        this.healthPoints = healthPoints;
        this.maxVelocity = maxVelocity;

        acceleration = new Vector2(Vector2.Zero);
        velocity = new Vector2(Vector2.Zero);
        oldPosition = new Vector2(position);
        deltaPosition = new Vector2(Vector2.Zero);
        direction = new Vector2(Vector2.Zero);

        isGrounded = false;
        isAlive = true;
        isMoving = false;
        gravity = 20;
    }

    /**
     * Method that moves the entity based on acceleration
     * @param dir direction to move in
     */
    public void move(float dir) {
        //TODO: Clean up

        if (isMoving) {

            switch((int)dir) {
                case -1:
                    // If already moving in other direction, give stronger acceleration.
                    if (direction.x > 0) {
                        if (isGrounded)
                            acceleration.x = -50;
                        else
                            acceleration.x = -25;
                    } else {
                        if (isGrounded)
                            acceleration.x = -18;
                        else
                            acceleration.x = -9;
                    }
                    break;
                case 1:
                    // If already moving in other direction, give stronger acceleration.
                    if (direction.x < 0) {
                        if (isGrounded)
                            acceleration.x = 50;
                        else
                            acceleration.x = 25;
                    } else {
                        if (isGrounded)
                            acceleration.x = 18;
                        else
                            acceleration.x = 9;
                    }
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
        if (this.isGrounded) {
            isGrounded = false;
            velocity.y = 8;
        }
    }

    /**
     * Method that works as a kind of physics engine for entities.
     */
    @Override
    public void update() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        updateFalling();
        velocity.x += acceleration.x * deltaTime;

        if (velocity.x > maxVelocity)
            velocity.x = maxVelocity;
        if (velocity.x < -maxVelocity)
            velocity.x = -maxVelocity;

        velocity.y += acceleration.y * deltaTime;
        oldPosition.set(position);

        position.add(Math.round(velocity.x), Math.round(velocity.y));

        deltaPosition.set(oldPosition.x-position.x, oldPosition.y-position.y);

        // direction.x = -1: left, direction.x = 1: right
        if (deltaPosition.x > 0) direction.x = -1; else if (deltaPosition.x < 0) direction.x = 1;
        // direction.y = -1: down. direction.y = 1: up
        if (deltaPosition.y > 0) direction.y = -1; else if (deltaPosition.y < 0) direction.y = 1;
        rect.setPosition(position.x, position.y);
    }

    private void updateFalling() {
        //TODO: Fix when collision is online
        if (isGrounded) {
            this.velocity.y = 0;
            acceleration.y = 0;
        } else {
            acceleration.y = -20;
        }
    }

    /**
     * Method that makes the character take damage
     * @param damage
     */
    public void takeDamage(int damage) {
        healthPoints -= damage;
        if (healthPoints <= 0)
            this.die();
    }

    /**
     * Method that kills the character
     */
    private void die() {
        this.isAlive = false;
    }

    /**
     * * Method for debugging purposes
     */
    public String toString() {
        return String.format("Properties:position: (%f,%f), height: %d, width: %d, accelerationX: %f, isMoving: %s, velocity: (%f,%f), direction: %f",
                position.x, position.y, height, width, acceleration.x, isMoving, rect.x, rect.y, velocity.x, velocity.y, direction.x);
    }
    public void setXPosition(int x) { position.x = x; }
    public void setYPosition(int y) { position.y = y; }
    public void setXVelocity(int x) { velocity.x = x; }
    public void setYVelocity(int y) { velocity.y = y; }
    public void setXAcceleration(int x) { acceleration.x = x; }
    public void setGrounded(boolean val) { isGrounded = val; }
    public void setMoving(boolean val) {isMoving = val;}
    public void setDirectionY(int y) { direction.y = y; }

    public boolean getGrounded() { return isGrounded; }
    public Vector2 getPosition() {
        return position;
    }
    public Vector2 getOldPosition() {
        return oldPosition;
    }
    public Vector2 getDirection() { return direction; }
    public Vector2 getVelocity() { return velocity; }



}
