package com.dat055.Model.Entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Hook extends Entity {
    private float maxLength;
    private Rectangle wire;
    private Vector2 direction;
    private boolean apexReached;
    private boolean remove;
    private boolean hasGrip;
    private float distanceTraveled;

    Hook(Vector2 position, int height, int width, String texturePath, float maxLength, Vector2 direction) {
        super(position, height, width, texturePath);
        this.direction = new Vector2(direction);
        this.maxLength = maxLength;
        initialize();
    }
    private void initialize() {
        setRectangle();
        hasGrip = false;
        remove = false;
        apexReached = false;
        distanceTraveled = 0;
        wire = new Rectangle(0, 0, 0, 3);
        wire.x = (direction.x > 0) ? position.x + width : position.x;
        wire.y = position.y + 27;
    }

    @Override
    public void update() {
        //TODO: More methods to clean up code
        if (!hasGrip) {
            wire.width += (apexReached) ? -10 : 10;
            if (wire.width >= maxLength) apexReached = true;
            position.x = (direction.x > 0) ? wire.x + wire.width : wire.x - wire.width;
            if (direction.x > 0) {
                if (position.x < wire.x)
                    remove = true;
            } else {
                if (position.x > wire.x)
                    remove = true;
            }
            position.y = wire.y;
        } else {
            direction.x += (direction.x > 0) ? -10 : 10;

        }
        rect.setPosition(position.x, position.y+(3*height)/4);
    }

    @Override
    public void action(String act) {

    }

    @Override
    public void draw(SpriteBatch sb) {
        //TODO: Fix texture-bös
        sb.draw(sprite, position.x, position.y);
    }

    boolean getRemoved() {
        return remove;
    }

    void setOriginPosition(Vector2 pos) {
        wire.x = (direction.x > 0) ? pos.x + width : pos.x;
        wire.y = pos.y + 27;
    }

    boolean getHasGrip() {
        return hasGrip;
    }

    public boolean getApexReached() {
        return apexReached;
    }

    public void setApexReached(boolean val) {
        apexReached = val;
    }

    public void setHasGrip(boolean val) {
        hasGrip = val;
    }
}
