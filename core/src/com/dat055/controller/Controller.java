package com.dat055.controller;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dat055.model.Model;
import com.dat055.view.View;

import javax.naming.ldap.Control;

public abstract class Controller {
    protected View view;
    protected Model model;
    protected Controller ctrl;

    Controller(Model model, View view) {
        this.view = view;
        this.model = model;
    }

    public void update(float deltaTime) {}

    public void render(PolygonSpriteBatch batch) {
        view.render(batch);
    }
    public void render() {}

    public void resize(int width, int height) {}

    protected void setController(Controller ctrl) {
        this.ctrl = ctrl;
    }
    public Model getModel() { return model; }
}
