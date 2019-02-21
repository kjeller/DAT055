package com.dat055.Model.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.dat055.controller.MenuController;
import com.dat055.model.menu.Menu;

import java.util.ArrayList;

public class SettingsMenu extends Menu {
    private MenuController controller;
    private ArrayList<TextButton> list = new ArrayList();
    private Image image;
    private TextButton video, audio, other, back;
    public SettingsMenu(MenuController ctrl) {
        super("UI/Delta.jpg");
        initTxtBtnStyle();
        initLblStyle();

        //super.getBg("UI/but1.png");

        list.add(video);
        list.add(audio);
        list.add(other);

        controller = ctrl;
        // Table table = new Table();
        Table topTable = new Table();

        //table.setWidth(controller.getWidth());
        //table.align(Align.center | Align.bottom);
        topTable.setWidth(controller.getWidth());
        topTable.setHeight(controller.getHeight());
        topTable.align(Align.center | Align.top);

        topTable.setPosition(0, 0);
        // table.setPosition(0, Gdx.graphics.getHeight());

        video = createButton("Video");
        audio = createButton("Audio");
        other = createButton("Other");
        back = createButton("Back");

        addListeners();

        topTable.add(back).width(150).height(40).padBottom(40).padLeft(40).left();
        topTable.add(video).width(150).height(40);
        topTable.add(audio).width(150).height(40);
        topTable.add(other).width(150).height(40);

        super.table = topTable;
    }

    private void initTxtBtnStyle() {
        TextButton.TextButtonStyle txtBtnStyle = new TextButton.TextButtonStyle();
        BitmapFont font = generateFont(30);

        Skin skin = new Skin(Gdx.files.internal("UI/ui.json"));
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("UI/ui.atlas"));
        skin.addRegions(atlas);

        txtBtnStyle.font = font;
        txtBtnStyle.fontColor = Color.BLACK;
        txtBtnStyle.downFontColor = Color.WHITE;
        txtBtnStyle.disabledFontColor = Color.WHITE;

        txtBtnStyle.up = skin.getDrawable("but1_pressed");
        txtBtnStyle.down = skin.getDrawable("but1");
        txtBtnStyle.disabled = skin.getDrawable("but1");

        super.txtBtnStyle = txtBtnStyle;
    }

    private void initTxtFldStyle() {
        TextField.TextFieldStyle txtFldStyle = new TextField.TextFieldStyle();
        BitmapFont font = generateFont(30);

        txtFldStyle.font = font;
        txtFldStyle.disabledFontColor = Color.WHITE;
        txtFldStyle.messageFont = generateFont(20);
        txtFldStyle.messageFontColor = Color.RED;

        super.txtFldStyle = txtFldStyle;
    }

    private void initLblStyle() {
        Label.LabelStyle lblStyle = new Label.LabelStyle();
        lblStyle.font = super.generateFont(26);
        lblStyle.fontColor = Color.WHITE;
        super.lblStyle = lblStyle;
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
