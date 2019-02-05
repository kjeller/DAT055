package com.dat055.Model.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Player extends Character {
    public Player(int id, int height, int width, String texturePath, String name, int healthPoints, float maxVelocity) {
        super(id, height, width, texturePath,name, healthPoints, maxVelocity);
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
    }

    /**
     * Player interacts with something
     */
    public void interact(String interactable) {
        System.out.printf("%s inteacts with %s", this.name, interactable);
    }

    /**
     * Begone, foul beast
     */
    public void checkKeyboardInput() {
        //TODO: Put in controller
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.move(-1);
            isMoving = true;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.move(1);
            isMoving = true;
        } else {
            isMoving = false;
            this.move(0);
        }


        if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
            jump();
        if (Gdx.input.isKeyPressed(Input.Keys.D))
            System.out.println(toString());
    }
}
