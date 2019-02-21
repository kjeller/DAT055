package com.dat055.model.entity;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;


public class Interactable extends Entity {

    public Interactable(Vector2 position, int height, int width, String texturePath){
        super(position, 128, 64, texturePath);
    }
    public void draw(PolygonSpriteBatch sb, float position){
        super.draw(sb, position);
    }
    @Override
    public void action(String act){

    }
}
