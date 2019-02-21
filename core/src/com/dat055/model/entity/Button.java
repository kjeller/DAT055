package com.dat055.model.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;


public class Button extends Entity {
    private boolean isActive = false;
    private float disableTime;
    private float timer;
    private String id;
    private String target;

    public Button(Vector2 position, int height, int width, String texturePath, String id, String target, int timer) {
        super(position, height, width, texturePath);
        this.id = id;
        this.target = target;
        this.timer = timer;
    }
    /**
     * Changes the Buttons state to active
     * Also notifies all observers
     * And sets a time for the button to be disabled
    */
    public void activate(){
        isActive = true;
        setChanged();
        notifyObservers(id);
        disableTime = timer;
        this.setTexture("textures/interactables/buttonActive.png");
    }

    public void update(float deltaTime){
        updateDisabled(deltaTime);
    }

    /**
     * Timer that checks to see wether the button should be inactive or not
     * @param deltaTime is how long the button should be disabled for
     * */
    private void updateDisabled(float deltaTime) {
        if (isActive){
            disableTime -= deltaTime;
    }
        if(disableTime < 0 && isActive) {
            isActive = false;
            setChanged();
            notifyObservers(id);
            this.setTexture("textures/interactables/buttonInactive.png");
        }
    }


    public boolean getActive(){
        return isActive;
    }
    public String getTarget(){
        return target;
    }

    @Override
    public void action(String act) {

    }

    @Override
    public void draw(SpriteBatch sb, float rotation) {
        super.draw(sb, rotation);
    }

}
