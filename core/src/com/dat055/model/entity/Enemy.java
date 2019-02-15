package com.dat055.model.entity;

import com.badlogic.gdx.math.Vector2;

public class Enemy extends Character {
    public Enemy(int height, int width, String texturePath, String name, int healthPoints, Vector2 maxVelocity, Vector2 position) {
        super(position, height, width, texturePath,name, healthPoints, maxVelocity);
        this.position.x = 300;
    }

    public void action(String act) {

    }

}


