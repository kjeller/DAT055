package com.dat055.Model.Entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Hook extends Entity {
    private double length;
    private double maxLength;
    private Rectangle wire;
    private Vector2 originPosition;
    public Hook (Vector2 position, int height, int width, String texturePath, double maxLength) {
        super(position, height, width, texturePath);
        originPosition = position;
        this.maxLength = maxLength;
        action("hej");
    }

    @Override
    public void action(String act) {

    }
    @Override
    public void draw(SpriteBatch sb) {
        //TODO: Fix texture-bös
        System.out.println("hej");
        sb.draw(texture, position.x, position.y);
    }
}
