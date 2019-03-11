package com.dat055.view;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dat055.model.Model;

/**
 * Base class for MenuView and GameView.
 * Is responsible for rendermethods.
 *
 * @author Erik Börne
 * @version 27-02-2019
 */
public abstract class View{
    Model model;
    public void render(PolygonSpriteBatch batch){}
}
