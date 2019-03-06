package com.dat055.model.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.dat055.controller.MenuController;

public class SettingsMenu extends Menu {
    private TextButton video, audio, other, back;
    private Label resolution, fullscreen, graphics, volume;
    private TextField resText, fulText, volText;
    public SettingsMenu(MenuController ctrl) {
        super(ctrl, false, "UI/Delta.jpg");
        controller = ctrl;
        createTable();
    }

    @Override
    protected void createTable() {
        // Table table = new Table();
        Table table = new Table();

        table.setWidth(controller.getWidth());
        table.setHeight(controller.getHeight());
        table.align(Align.center | Align.top);

        table.setPosition(0, 0);

        //Buttons
        video = createButton("Video");
        audio = createButton("Audio");
        other = createButton("Other");
        back = createButton("Back");
        resText = createTextField("1024x768");
        fulText = createTextField("0");
        volText = createTextField("1");

        //Labels
        resolution = new Label("Resolution",lblStyle);
        fullscreen = new Label("FullScreen",lblStyle);
        volume = new Label("Volume",lblStyle);



        addListeners();

        table.debug();
        table.add(back).width(150).height(40);
        table.add(video).width(170).height(40);
        table.add(audio).width(170).height(40);
        table.add(other).width(170).height(40).row();
        table.add();
        table.add(resolution).padTop(20).height(40).padBottom(10);
        table.add(resText).padTop(20).padBottom(10).row();
        table.add();
        table.add(fullscreen).height(40).padBottom(20).padTop(10);
        table.add(fulText).padTop(10).padBottom(20).row();
        table.add();
        table.add(volume).height(40);
        table.add(volText);

        super.table = table;
    }

    private void addListeners() {
        video.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //buttonThing();
                audio.setDisabled(false);
                other.setDisabled(false);
                video.setDisabled(true);
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });
        audio.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                video.setDisabled(false);
                other.setDisabled(false);
                audio.setDisabled(true);
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });
        other.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                video.setDisabled(false);
                audio.setDisabled(false);
                other.setDisabled(true);
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });
        back.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                controller.swapMenu("Main");
                super.touchUp(event, x, y, pointer, button);
            }
        });
    }

/*    public void buttonThing() {
        for(TextButton btn : list) {
            btn.setDisabled(false);
        }
    }
*/
}
