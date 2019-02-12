package com.dat055.Model.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Player extends Character {
    Hook hook;
    public Player(Vector2 position, int height, int width, String texturePath, String name, int healthPoints, float maxVelocity) {
        super(position, height, width, texturePath,name, healthPoints, maxVelocity);
    }

    /**
     * Player does act that is specified in parameter
     * @param act
     */
    public void action(String act) {
        System.out.printf("%s %ss\n", this.name, act);
    }

    /**
     * Player attacks
     */
    @Override
    public void attack() {
        if (hook == null)
            hook = generateHook();
    }
    @Override
    public void draw(SpriteBatch sb) {
        super.draw(sb);
        if (hook != null)
            hook.draw(sb);
    }
    private Hook generateHook() {
        int directionOffset;
        return new Hook(position, height, width, "hook.png", 100.0f, direction);

    }
    public void removeHook() {
        hook = null;
    }
    @Override
    public void update() {
        super.update();
        if (hook != null) {
            hook.setOriginPosition(position);
            hook.update();
            if (hook.getRemoved())
                hook = null;
        }

    }
    /**
     * Player interacts with something
     */
    public void interact(String interactable) {
        System.out.printf("%s interacts with %s", this.name, interactable);
    }

}
