package com.dat055.View.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.dat055.Model.MenuModel;
import com.dat055.View.MenuView;

public class MainScreen implements Screen {
    MenuModel menuModel; // logic for game stored here
    private SpriteBatch batch = new SpriteBatch();
    private TextButton button;
    private TextButton.TextButtonStyle textButtonStyle;
    private Skin skin;
    private TextureAtlas buttonAtlas;

    public MainScreen(String fileName) {
        skin = new Skin();
        buttonAtlas = new TextureAtlas(Gdx.files.internal("ui/uiskin.atlas"));
        skin.addRegions(buttonAtlas);
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = MenuView.getInstance().getFont();
        textButtonStyle.up = skin.getDrawable("Button-normal");
        textButtonStyle.down = skin.getDrawable("Button-active");
        button = new TextButton("Button1", textButtonStyle);
    }

    public void update(){
      //  menuModel.update();
    }

    public void draw(SpriteBatch batch) {
        
    }
}
