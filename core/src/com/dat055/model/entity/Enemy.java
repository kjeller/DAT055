package com.dat055.model.entity;

import com.badlogic.gdx.math.Vector2;

public class Enemy extends Character {

    public Enemy(Vector2 startPosition, String texturePath, String name) {
        super(startPosition, 64, 64, texturePath, name, 5, new Vector2(3, 20));
    }
    public void action(String act) {

    }
    @Override
    public void update() {
        super.update();
    }
}


