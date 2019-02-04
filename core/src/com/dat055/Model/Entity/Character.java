package com.dat055.Model.Entity;

public abstract class Character extends Entity {
    public Character(int id, int height, int width, String texturePath, String name, int healthPoints, float maxVelocity) {
        super(id, height, width, texturePath);
        this.name = name;
        this.healthPoints = healthPoints;
        this.maxVelocity = maxVelocity;
        this.acceleration = 1.05f;
    }

    public String name;
    private int healthPoints;
    private float acceleration;
    private float velocity;
    protected float maxVelocity;
    protected boolean isGrounded;
    protected boolean isAlive;


    /**
     * Method that moves the entity based on acceleration
     * @param direction
     */
    public void move(String direction) {
        //TODO: Fix
        if (direction.equals("left"))
            posX -= 5;
        else
            posX += 5;
    }

    /**
     *  Method for performing attack
     */
    public void attack() {
        System.out.println("Enemy attacks!");
    }

    /**
     * Methods that makes the character jump.
     */
    public void jump() {
        System.out.println("Character jumps.");
    }

    /**
     * Method that makes the character take damage
     * @param damage
     */
    public void takeDamage(int damage) {
        healthPoints = healthPoints-damage;
        if (healthPoints <= 0)
            this.die();
    }


    /**
     * Method that kills the character
     */
    private void die() {
        this.isAlive = false;
    }
}
