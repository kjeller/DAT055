package com.dat055.net.message;

import com.badlogic.gdx.math.Vector2;

public class PlayerMessage extends Message {
    Vector2 position;
    Vector2 lookingDirection;
    boolean isAlive;

    public PlayerMessage(Vector2 position, Vector2 lookingDirection, boolean isAlive) {
        super(Protocol.OP_PLAYER);
        this.position = position;
        this.lookingDirection = lookingDirection;
        this.isAlive = isAlive;
    }

    public String toString() {
        return super.toString() + String.format("pos:(%d, %d), dir:(%d, %d), alive: %s",
                position.x, position.y, lookingDirection.x, lookingDirection.y, isAlive);
    }

    public Vector2 getPosition() { return position; }
    public Vector2 getDir() { return lookingDirection; }
    public boolean getIsAlive () { return isAlive; }

}
