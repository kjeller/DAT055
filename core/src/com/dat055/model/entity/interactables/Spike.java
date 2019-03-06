package com.dat055.model.entity.interactables;

import com.badlogic.gdx.math.Vector2;
import com.dat055.model.entity.Entity;
import com.dat055.model.entity.character.Player;

/**
 * An entity made to kill the player upon touch.
 * @author Marcus Schmidt
 */

public class Spike extends Entity {

    public Spike(Vector2 position, int height, int width, String texturePath) {
        super(position, height, width, texturePath);
    }
/**
 * A method that kills the player.
 **/
    public void death(Player player){
        player.takeDamage(100);
    }

}
