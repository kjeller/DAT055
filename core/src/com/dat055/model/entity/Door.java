package com.dat055.model.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Observable;
import java.util.Observer;

public class Door extends Entity implements Observer {
    private int height;
    private int width;
    private boolean isOpen = false;
    private Rectangle doorRect;
    private String texturePath;


    public Door(Vector2 position, int height, int width, String texturePath) {
        super(position, height, width, texturePath);
        this.height = height;
        //System.out.println(getRect());
        System.out.println(width + "   " + height);
    }

    @Override
    public void action(String act) {

    }

    public void open() {

        if(texturePath == "textures/interactables/doorOpen.png") {
            this.isOpen = false;
            setTexture("textures/interactables/doorClosed.png");
            this.texturePath = "textures/interactables/doorClosed.png";
        } else {
            this.isOpen = true;
            setTexture("textures/interactables/doorOpen.png");
            this.texturePath = "textures/interactables/doorOpen.png";

        }
        System.out.println(texturePath);
    }
    public boolean getState(){
        return isOpen;
    }

    @Override
    public void draw(SpriteBatch sb, float rotation) {
        super.draw(sb, rotation);
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Button was updated");
        open();
    }
}
