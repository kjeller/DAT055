package com.dat055.model.entity.interactables;

import com.badlogic.gdx.math.Vector2;
import com.dat055.model.entity.Entity;
import com.dat055.model.entity.character.Player;

public class Spike extends Entity {

    public Spike(Vector2 position, int height, int width, String texturePath) {
        super(position, height, width, texturePath);
    }

    public void death(Player player){
        player.takeDamage(100);
    }

}
