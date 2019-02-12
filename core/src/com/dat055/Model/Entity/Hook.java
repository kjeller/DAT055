package com.dat055.Model.Entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Hook extends Entity {
    private float length;
    private float maxLength;
    private Rectangle wire;
    private Vector2 originPosition;
    private Vector2 direction;
    private boolean apexReached;
    private boolean remove;
    public Hook (Vector2 position, int height, int width, String texturePath, float maxLength, Vector2 direction) {
        super(position, height, width, texturePath);
        originPosition = new Vector2();
        this.direction = new Vector2(direction);
        this.maxLength = maxLength;
        remove = false;
        apexReached = false;
        initialize();
        action("hej");
    }

    private void initialize() {
        // Player faces the right

        if (direction.x > 0)
            originPosition.x = position.x+width;
        else
            originPosition.x = position.x;
        originPosition.y = position.y+27;

    }
    @Override
    public void update() {
        if (apexReached)
            length -= 5;
        else
            length += 5;

        if (length >= maxLength)
            apexReached = true;

        position.x = (direction.x > 0) ? originPosition.x + length : originPosition.x - length;

        if (direction.x > 0) {
            if (position.x < originPosition.x)
                remove = true;
        }
        else {
            if (position.x > originPosition.x)
                remove = true;
        }
        position.y = originPosition.y;

    }

    @Override
    public void action(String act) {

    }
    @Override
    public void draw(SpriteBatch sb) {
        //TODO: Fix texture-bös
        sb.draw(texture, position.x, position.y);
    }
    public boolean getRemoved() {
        return remove;
    }
    public float getLength() {
        return length;
    }
    public float getXPosition() {
        return position.x;
    }
    public void setOriginPosition(Vector2 pos) {
        originPosition.set(pos);
    }
}
