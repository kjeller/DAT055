package com.dat055.model.entity.character;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Tobias Campbell
 * @version 22-02-2019
 */
public class Player extends Character {
    private Hook hook;
    private boolean isInvincible;
    private float iframes;
    private boolean movingWithHook;

    /**
     * Sets movingWithHook and isInvincible to default values.
     * @param startPosition position where player should spawn on a map.
     * @param texturePath Path to a texture.
     * @param name name of the player entity.
     */
    public Player(Vector2 startPosition, String texturePath, String name) {
        super(startPosition, 80, 64, texturePath,name, 5, new Vector2(5, 20));
        movingWithHook = false;
        isInvincible = false;
    }

    /**
     * Player attacks by shooting a hook.
     */
    public void attack() {
        if (hook == null)
            hook = generateHook();
        else {
            hook.setHasGrip(false);
            movingWithHook = false;
        }
    }

    /**
     * Draw method for the player. Makes player transparent if invincible.
     * @param sb spritebatch that is used.
     * @param rotation sets the rotation when toggling between players.
     */
    @Override
    public void draw(PolygonSpriteBatch sb, float rotation) {
        if (isInvincible)
            sb.setColor(1, 1, 1, 0.5f);
        if (hook != null)
            hook.draw(sb, rotation);
        super.draw(sb, rotation);
        sb.setColor(1,1,1, 1);

    }

    /**
     * Draws the surrounding rectangle of the player.
     * @param renderer renderer used to draw rectangle
     */
    @Override
    public void drawBoundingBox(ShapeRenderer renderer) {
        super.drawBoundingBox(renderer);
        if(hook != null)
            hook.drawBoundingBox(renderer);
    }
    /**
     * The logic of the player.
     * @param deltaTime time since last frame.
     */

    @Override
    public void update(float deltaTime) {
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
     * Method used to generate a new hook.
     * @return the newly generated hook.
     */
    private Hook generateHook() {
        return new Hook(new Vector2(position), 20, 20, 250.0f, lookingDirection);
    }

    /**
     * Method that sets up the hook's update method.
     * @param deltaTime time since last frame.
     */
    private void hookUpdate(float deltaTime) {
        hook.setPlayerPosX((int)rect.x);
        hook.setOriginPosition(new Vector2(rect.x, rect.y));
        hook.update(deltaTime);
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
        playSound("takedamage");
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
     * Method used to slowly stabilize the player's max velocity to normal after hooking.
     * @param deltaTime time since last frame.
     */
    private void normalizeMaxVelocityX(float deltaTime) {
        maxVelocity.x -= 10 * deltaTime;
        if (maxVelocity.x < 5.5 && maxVelocity.x > 4.5)
            maxVelocity.x = 5;
    }

    /**
     * Get method for hook.
     * @return hook if there is one, null if there isn't.
     */
    public Hook getHook() {
        if (hook != null)
            return hook;
        return null;
    }

    /**
     * Sets hook for this player
     * @param hook that is set
     */
    public void setHook(Hook hook) {
        this.hook = hook;
    }

    /**
     * Get isInvincible value.
     * @return boolean
     */
    public boolean getInvincible() {
        return isInvincible;
    }

    /**
     * Get debug text for player.
     * @return String containing good-to-know information.
     */
    @Override
    public String toString() {
        return super.toString() + String.format("isInvincible: %s\n", isInvincible);
    }
}
