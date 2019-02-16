package com.dat055.model.entity;

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
}
