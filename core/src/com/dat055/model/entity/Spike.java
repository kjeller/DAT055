package com.dat055.model.entity;

import com.badlogic.gdx.math.Vector2;

public class Spike extends Entity{

    public Spike(Vector2 position, int height, int width, String texturePath) {
        super(position, height, width, texturePath);
    }

    public void death(Player player){
        player.takeDamage(100);
    }

}
