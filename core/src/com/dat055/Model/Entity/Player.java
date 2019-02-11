package com.dat055.Model.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class Player extends Character {
    public Player(Vector2 startPos, String texturePath, String name) {
        // Ändrade det här för att underlätta vid inläsning av mappen - Kjelle
        super(1, 80, 64, texturePath,name, 5, 5, startPos);


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
}
