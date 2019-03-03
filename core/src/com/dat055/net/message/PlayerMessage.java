package com.dat055.net.message;

import com.badlogic.gdx.math.Vector2;
import com.dat055.model.entity.character.Player;

/**
 * A message for sending player updates.
 * Will be used when updating player properties.
 * @author Karl Strålman
 * @version 2019-02-22
 */
public class PlayerMessage extends Message {
    private Vector2 position;
    private Vector2 lookingDirection;
    boolean isAlive;

    /**
     * Default constructor for {@link PlayerMessage}
     * Sets protol to OP_PLAYER which can be found in {@link Protocol}
     * @param player
     */
    public PlayerMessage(Player player) {
        super(Protocol.OP_PLAYER);
        this.position = player.getPosition();
        this.lookingDirection = player.getDirection();
        this.isAlive = player.getIsAlive();
    }

    /**
     * @returna a string with values of this instance
     */
    public String toString() {
        return super.toString() + String.format("pos:(%.1f, %.1f), dir:(%.1f, %.1f), alive: %s",
                position.x, position.y, lookingDirection.x, lookingDirection.y, isAlive);
    }

    /**
     * Sets player properties from a this message.
     * position, lookingDirection and isAlive is set.
     * @param player - values from this player
     */
    public void setPlayerProperties(Player player) {
        player.setPosition(position);
        player.setLookingDirection(lookingDirection);
        player.setIsAlive(isAlive);
    }

    public Vector2 getDir() { return lookingDirection; }
    public boolean getIsAlive () { return isAlive; }

}
