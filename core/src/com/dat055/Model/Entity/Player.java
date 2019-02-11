package com.dat055.Model.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
        System.out.printf("%s attacks!\n", this.name);
        hook = generateHook();
    }

    private Hook generateHook() {
        int directionOffset;
        //if (direction.x )
        return new Hook(position, height, width, "hook.png", 5.0f);
    }
    /**
     * Player interacts with something
     */
    public void interact(String interactable) {
        System.out.printf("%s inteacts with %s", this.name, interactable);
    }
}
