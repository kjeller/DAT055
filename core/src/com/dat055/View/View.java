package com.dat055.View;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dat055.Controller.Controller;
import com.dat055.Model.Model;
import com.dat055.View.Screen.Screen;

import java.util.ArrayList;

public abstract class View {
    ArrayList<Screen> screens;
    public void changeView(){}
    public void update() {}
    public void initialize(){}
    public void draw(SpriteBatch batch){}
}
