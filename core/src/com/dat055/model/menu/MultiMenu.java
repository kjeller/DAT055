package com.dat055.model.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.utils.Align;
import com.dat055.controller.MenuController;

public class MultiMenu extends Menu {
    private MenuController controller;
    String ip;
    TextButton join, host, back;
    TextField address;
    public MultiMenu(MenuController ctrl) {
        super("UI/Delta.jpg");
        initTxtBtnStyle();
        initTxtFldStyle();
        initLblStyle();

        controller = ctrl;
        Table table = new Table();
        Table subTable = new Table();

        table.setWidth(controller.getWidth());
        table.align(Align.center | Align.top);

        table.setPosition(0, Gdx.graphics.getHeight());

        join = createButton("Join");
        host = createButton("Host");
        back = createButton("Back");
        address = createTextField("Enter host IP");

        addListeners();

        subTable.add(back).width(150).padRight(300);
        subTable.add(host).width(150);

        table.padTop(200);
        table.add(address).width(300).padBottom(30).row();
        table.add(join).width(300).padBottom(100).row();
        table.add(subTable).width(500).padBottom(20);

        super.table = table;
    }

    private void initTxtBtnStyle() {
        TextButton.TextButtonStyle txtBtnStyle = new TextButton.TextButtonStyle();

        Skin skin = new Skin(Gdx.files.internal("UI/ui.json"));
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("UI/ui.atlas"));
        skin.addRegions(atlas);

        txtBtnStyle.font = generateFont(30);
        txtBtnStyle.fontColor = Color.BLACK;
        txtBtnStyle.downFontColor = Color.WHITE;

        txtBtnStyle.up = skin.getDrawable("but1_pressed");
        txtBtnStyle.down = skin.getDrawable("but1");

        super.txtBtnStyle = txtBtnStyle;
    }

    private void initTxtFldStyle() {
        TextField.TextFieldStyle txtFldStyle = new TextField.TextFieldStyle();

        Skin skin = new Skin(Gdx.files.internal("UI/ui.json"));
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("UI/ui.atlas"));
        skin.addRegions(atlas);

        txtFldStyle.font = fontPad(generateFont(30));
        txtFldStyle.background = skin.getDrawable("but1_pressed");
        txtFldStyle.fontColor = Color.BLACK;
        txtFldStyle.focusedBackground = skin.getDrawable("but1");
        txtFldStyle.focusedFontColor = Color.WHITE;
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

    public BitmapFont fontPad(BitmapFont f) {
        BitmapFont.BitmapFontData fd = f.getData();
        fd.padLeft = -5;
        fd.padRight = -10;
        return f;
    }

    public String getIp() {
        return ip;
    }

    private void addListeners() {
        join.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                ip = address.getText();
                System.out.println("[Multi:115]Debug: " + "IP input: " + ip);
                if(ip != null || ip.equals(""))
                    controller.joinMultiplayer(ip, "Default name"); //TODO: Name from a textfield
                super.touchUp(event, x, y, pointer, button);
            }
        });

        host.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                controller.startMultiplayer("maps/map_0.json", "Default name");
                //TODO: Be able to change map and name
                super.touchUp(event, x, y, pointer, button);
            }

        });


        back.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                controller.swapMenu("Main");
                super.touchUp(event, x, y, pointer, button);
            }

        });


        address.addListener(new FocusListener() {
            @Override
            public void keyboardFocusChanged (FocusEvent event, Actor actor, boolean focused) {
                if(focused) { address.setText(""); }
                else { address.setText("Enter host IP"); }
            }
        });
    }
}