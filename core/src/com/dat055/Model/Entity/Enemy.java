package com.dat055.Model.Entity;

import com.badlogic.gdx.math.Vector2;

public class Enemy extends Character {
    public Enemy(Vector2 position, int height, int width, String texturePath, String name, int healthPoints, float maxVelocity) {
        super(position, height, width, texturePath,name, healthPoints, maxVelocity);
        this.position.x = 300;
    }

    public void action(String act) {

    }

}


