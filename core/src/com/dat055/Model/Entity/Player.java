package com.dat055.Model.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Player extends Character {
    Hook hook;
    private boolean movingWithHook;
    private int distanceTraveled;
    public Player(Vector2 startPosition, String texturePath, String name) {
        // Ändrade det här för att underlätta vid inläsning av mappen - Kjelle
        super(startPosition, 80, 64, texturePath,name, 5, new Vector2(5, 20));
        movingWithHook = false;
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

    private Hook generateHook() {
        return new Hook(new Vector2(position), 20, 20, "hook.png", 250.0f, lookingDirection);
    }

    @Override
    public void draw(SpriteBatch sb, float rotation) {
        super.draw(sb, rotation);
        if (hook != null)
            hook.draw(sb);
    }
    @Override
    public void update() {
        // TODO: Toggle methods for booleans?
        if (hook != null && hook.getHasGrip())
            movingWithHook = true;
        if (movingWithHook) {
            if (lookingDirection.x > 0) {
                position.x += 5;
                if (position.x+width >= hook.getPosition().x) {
                    movingWithHook = false;
                    hook.setHasGrip(false);
                    hook = null;
                }
            } else {
                position.x -= 5;
                if (hook.getPosition().x+20 >= position.x) {
                    movingWithHook = false;
                    hook.setHasGrip(false);
                    hook = null;
                }
            }

        }
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
        System.out.printf("%s interacts with %s", name, interactable);
    }
    public Hook getHook() {
        if (hook != null)
            return hook;
        return null;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
