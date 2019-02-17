package com.dat055.controller;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dat055.model.Model;
import com.dat055.view.View;

public abstract class Controller {
    protected View view;
    protected Model model;

    Controller(Model model, View view) {
        this.view = view;
        this.model = model;
    }

    public void update(float deltaTime) {}

    public void render(SpriteBatch batch) {
        view.render(batch);
    }
    public void render() {}

    public void resize(int width, int height) {}
}
