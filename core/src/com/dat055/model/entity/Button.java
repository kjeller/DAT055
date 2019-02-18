package com.dat055.model.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;


public class Button extends Entity {
    private boolean isActive = false;
    private float disableTime;

    public Button(Vector2 position, int height, int width, String texturePath) {
        super(position, height, width, texturePath);
    }
    public void activate(){
        isActive = true;
        setChanged();
        notifyObservers();
        disableTime = 2;
        this.setTexture("textures/interactables/buttonActive.png");
    }

    public void update(float deltaTime){
        updateDisabled(deltaTime);
    }

    private void updateDisabled(float deltaTime) {
        if (isActive){
            disableTime -= deltaTime;
    }
        if(disableTime < 0 && isActive) {
            isActive = false;
            this.setTexture("textures/interactables/buttonInactive.png");
        }
    }


    public boolean getActive(){
        return isActive;
    }

    @Override
    public void action(String act) {

    }

    @Override
    public void draw(SpriteBatch sb, float rotation) {
        super.draw(sb, rotation);
    }

}
