package com.dat055.net.message;

import com.badlogic.gdx.math.Vector2;
import com.dat055.model.entity.Player;

public class PlayerMessage extends Message {
    Vector2 position;
    Vector2 lookingDirection;
    boolean isAlive;

    public PlayerMessage(Player player) {
        super(Protocol.OP_PLAYER);
        this.position = player.getPosition();
        this.lookingDirection = player.getDirection();
        this.isAlive = player.getIsAlive();
    }

    public String toString() {
        return super.toString() + String.format("pos:(%d, %d), dir:(%d, %d), alive: %s",
                position.x, position.y, lookingDirection.x, lookingDirection.y, isAlive);
    }

    public Vector2 getPosition() { return position; }
    public Vector2 getDir() { return lookingDirection; }
    public boolean getIsAlive () { return isAlive; }

}
