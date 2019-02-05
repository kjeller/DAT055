package com.dat055.Model.Entity;

import com.badlogic.gdx.Gdx;

public abstract class Character extends Entity {
    public Character(int id, int height, int width, String texturePath, String name, int healthPoints, float maxVelocity) {
        super(id, height, width, texturePath);
        this.name = name;
        this.healthPoints = healthPoints;
        this.maxVelocity = maxVelocity;
        this.accelerationX = 0;
        this.accelerationY = -10;
        this.directionRight = true;
    }

    public String name;
    private boolean directionRight;
    private int healthPoints;
    private float accelerationX;
    private float accelerationY;
    private float velocityX;
    private float velocityY;
    protected float maxVelocity;
    protected boolean isGrounded;
    protected boolean isAlive;


    /**
     * Method that moves the entity based on acceleration
     * @param direction
     */
    public void move(String direction) {
        //TODO: Fix
        if (direction.equals("left")) {
            accelerationX = -30;
            directionRight = false;
        }

        else if (direction.equals("right")) {
            accelerationX = 30;
            directionRight = true;
        }

        else {
            if (directionRight) {
                accelerationX -= 9;

            }
            else {
                accelerationX += 9;
            }
            if (velocityX > -2 && velocityX < 2) {
                accelerationX = 0;
                velocityX = 0;
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
            System.out.println("Character jumps.");
            isGrounded = false;
            this.velocityY = 5;
        }
    }
    @Override
    public void update() {
        velocityX += accelerationX * Gdx.graphics.getDeltaTime();
        velocityY += accelerationY * Gdx.graphics.getDeltaTime();
        if (velocityX > maxVelocity)
            velocityX = maxVelocity;
        if (velocityX < -maxVelocity)
            velocityX = -maxVelocity;

        float oldPosX = posX;
        posX += velocityX;
        posY += velocityY;

        if (this.posY <= 0) {
            this.isGrounded = true;
            this.velocityY = 0;
            posY=0;
        }
        rect.setPosition(posX, posY);
        //debug();
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
     * Method for debugging purposes
     */
    private void debug() {
        System.out.println("\n\n\nAcceleration X: " + accelerationX);
        System.out.println("Acceleration Y: " + accelerationY);
        System.out.println("Velocity X: " + velocityX);
        System.out.println("Velocity Y: " +  velocityY);
        System.out.println("PosX: " + posX + " posY: " + posY);
        System.out.println("Rectangle x: " + rect.toString());
    }
}
