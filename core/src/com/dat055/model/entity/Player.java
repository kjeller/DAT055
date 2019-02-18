package com.dat055.model.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Player extends Character {
    Hook hook;
    boolean isInvincible;
    float iframes;
    private boolean movingWithHook;
    public Player(Vector2 startPosition, String texturePath, String name) {
        super(startPosition, 80, 64, texturePath,name, 5, new Vector2(5, 20));
        movingWithHook = false;
        isInvincible = false;
    }
    /**
     * Player does act that is specified in parameter
     * @param act
     */
    public void action(String act) {
        System.out.printf("%s %ss\n", name, act);
    }

    /**
     * Player attacks
     */
    @Override
    public void attack() {
        if (hook == null)
            hook = generateHook();
        else {
            hook.setHasGrip(false);
            movingWithHook = false;
        }

    }

    @Override
    public void draw(SpriteBatch sb, float rotation) {
        super.draw(sb, rotation);
        if (hook != null)
            hook.draw(sb, rotation);
    }
    @Override
    public void update(float deltaTime) {
        // TODO: Toggle methods for booleans?
        if (isAlive) {
            if (hook != null) {
                hookMovement();
                hookUpdate(deltaTime);
            }
            if (maxVelocity.x > 5)
                normalizeMaxVelocityX(deltaTime);
            super.update(deltaTime);
            updateInvincible(deltaTime);
        }
    }

    /**
     * Method used to generate a fresh new hook.
     * @return the newly generated hook.
     */
    private Hook generateHook() {
        return new Hook(new Vector2(position), 20, 20, "hook.png", 250.0f, lookingDirection);
    }
    /**
     * Method that sets up the hook's update method.
     * @param deltaTime time since last frame.
     */
    private void hookUpdate(float deltaTime) {
        hook.setPlayerPosX((int)position.x);
        hook.update(deltaTime);
        hook.setOriginPosition(position);
        if (hook.getRemoved())
            hook = null;
    }

    /**
     * Method used when traveling with the momentum of the hook.
     */
    private void hookMovement() {
        if (hook.getHasGrip())
            movingWithHook = true;

        if (movingWithHook) {
            maxVelocity.x = 10;
            if (lookingDirection.x > 0) {
                acceleration.x = 60;
                if (position.x+width >= hook.getPosition().x) {
                    movingWithHook = false;
                    hook.setHasGrip(false);
                }
            } else {
                acceleration.x = -60;
                if (hook.getPosition().x+hook.width > position.x) {
                    movingWithHook = false;
                    hook.setHasGrip(false);
                }
            }
        }
    }

    /**
     * Take damage and also set invincibility for 2 seconds.
     * @param damage amount of damage to take
     */
    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
        isInvincible = true;
        iframes = 2;
    }

    /**
     * Update iframe timer.
     * @param deltaTime time since last frame.
     */
    private void updateInvincible(float deltaTime) {
        if (isInvincible)
            iframes -= deltaTime;
        if (iframes < 0 && isInvincible)
            isInvincible = false;
    }
    /**
     * Player interacts with something.
     */
    public void interact(String interactable) {
        System.out.printf("%s interacts with %s", name, interactable);
    }
    public Hook getHook() {
        if (hook != null)
            return hook;
        return null;
    }
    public boolean getInvincible() {
        return isInvincible;
    }
    private void normalizeMaxVelocityX(float deltaTime) {
        maxVelocity.x -= 10 * deltaTime;
        if (maxVelocity.x < 5.5 && maxVelocity.x > 4.5)
            maxVelocity.x = 5;
    }
    private void toggleMovingWithHook() {
        movingWithHook = (movingWithHook) ? false : true;
    }

    @Override
    public String toString() {
        return super.toString() + String.format("isInvincible: %s\n", isInvincible);
    }
}
