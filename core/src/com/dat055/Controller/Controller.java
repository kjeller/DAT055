package com.dat055.Controller;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dat055.Model.Model;
import com.dat055.View.View;

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
}
