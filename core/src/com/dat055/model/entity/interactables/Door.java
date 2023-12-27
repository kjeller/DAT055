package com.dat055.model.entity.interactables;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;
import com.dat055.model.entity.Entity;

import java.util.Observable;
import java.util.Observer;
/**
 * A class that handles door entities. Used to block the players path until they
 * touch a button. Is an observer that observes buttons.
 * @author Marcus Schmidt
 *
 **/
public class Door extends Entity implements Observer {
    private ObjectMap<String, Boolean> requirements;
    private boolean isOpen = false;
    private boolean isSolid = true;
    private String id;


    public Door(Vector2 position, int height, int width, String texturePath, String id) {
        super(position, height, width, texturePath);
        this.id = id;
        requirements = new ObjectMap<String, Boolean>();
    }


    /**
     * Checks if Door should be opened
     * if all values in requirements = true, the door is open
     * if requirements contains value false, door should not open
     * Texture is changed by changing texturePath
     */
    public void open() {
        if(requirements.containsValue(false, true)){
            if(texturePath == "textures/interactables/doorOpen.png") {
                this.isOpen = false;
                this.isSolid = true;
                setTexture("textures/interactables/doorClosed.png");
                this.texturePath = "textures/interactables/doorClosed.png";
                }
            } else {
            this.isOpen = true;
            this.isSolid = false;
            setTexture("textures/interactables/doorOpen.png");
            this.texturePath = "textures/interactables/doorOpen.png";
        }
    }

    /**
     * Each required button is added to requirements list
     * @param req is buttons ID
     * false is default state
     */
    public void addRequired(String req){
        requirements.put(req, false);
    }

    /**
     * Toggles the buttons state in requirements
     * @param id is buttons id
     * If ID is found in requirements and is false, toggle to true
     * If ID is ofund in requirements and is true, toggle to false
     * */
    public void toggleRequired(String id){
        if(requirements.get(id) == false)
            requirements.put(id, true);
        else
            requirements.put(id, false);
    }

    /**
     * Method that is notified when a observable button is pressed.
     * @param o is the observable button
     * @param arg is argument passed by button, in this case buttons ID
     * */
    @Override
    public void update(Observable o, Object arg) {
        toggleRequired(arg.toString());
        open();
    }

    @Override
    public void draw(PolygonSpriteBatch sb, float rotation) {
        super.draw(sb, rotation);
    }

    public boolean getState()   { return isOpen; }
    public String getId()       { return id; }
    public boolean getSolid(){ return isSolid; }
}
