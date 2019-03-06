package com.dat055.controller;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.dat055.model.Model;
import com.dat055.view.View;

/**
 * Controls what is rendered in view.
 * @author Karl Strålman
 * @version 2019-02-22
 */
public abstract class Controller {
    protected View view;
    protected Model model;
    protected Controller ctrl; // Used to communicate with other controller

    /**
     * Default constructor for {@link Controller}
     * @param model
     * @param view
     */
    Controller(Model model, View view) {
        this.view = view;
        this.model = model;
    }

    /**
     * Updates before rendered
     * @param deltaTime time since last update
     */
    public abstract void update(float deltaTime);

    /**
     * Render view.
     * @param batch that is used to render textures/sprites etc.
     */
    public void render(PolygonSpriteBatch batch) {
        view.render(batch);
    }

    /**
     * Sets controller for this controller.
     * @param ctrl
     */
    protected void setController(Controller ctrl) {
        this.ctrl = ctrl;
    }

    /**
     * @return this controller's model
     */
    public Model getModel() { return model; }
}
