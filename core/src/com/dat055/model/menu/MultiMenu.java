package com.dat055.model.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.dat055.controller.MenuController;

public class MultiMenu extends Menu {
    private MenuController controller;
    String ip;
    TextButton join, host, back;
    TextField address;
    private TextButton.TextButtonStyle hoverStyle;

    public MultiMenu(MenuController ctrl) {
        super("UI/Delta.jpg");

        controller = ctrl;
        createTable(Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/18);
    }

    @Override
    public void createTable(int width, int height) {
        initStyles(height);

        join = createButton("Join");
        host = createButton("Host");
        back = createButton("Back");
        address = createTextField("Enter host IP");

        addListeners();

        layoutTable(width, height);
    }

    private void layoutTable(int butX, int butY) {
        int padL, padS;
        padL = butX/2;
        padS = butY/2;

        Table table = new Table();
        table.setSize(controller.getWidth(),controller.getWidth());
        table.bottom();

        table.setPosition(0,0);

        table.add(address).width(butX).height(butY).padBottom(padS).expandX().colspan(2).row();
        table.add(join).width(butX).height(butY).padBottom(padL).colspan(2).row();
        table.add(back).width(butX/2).height(butY).padLeft(padS).padBottom(padS).left();
        table.add(host).width(butX/2).height(butY).padRight(padS).padBottom(padS).right();

        super.table = table;
    }

    private void initStyles(int height) {
        initTxtBtnStyle(height);
        initTxtFldStyle(height);
        initLblStyle(height);
    }

    private void initTxtBtnStyle(int height) {
        TextButton.TextButtonStyle txtBtnStyle = new TextButton.TextButtonStyle();

        Skin skin = new Skin(Gdx.files.internal("UI/ui.json"));
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("UI/ui.atlas"));
        skin.addRegions(atlas);

        txtBtnStyle.font = generateFont(height-Gdx.graphics.getHeight()/50);
        txtBtnStyle.fontColor = Color.BLACK;
        txtBtnStyle.downFontColor = Color.WHITE;

        txtBtnStyle.up = skin.getDrawable("but1_pressed");
        txtBtnStyle.down = skin.getDrawable("but1");

        hoverStyle = new TextButton.TextButtonStyle(txtBtnStyle);
        hoverStyle.up = skin.getDrawable("but1");
        hoverStyle.fontColor = Color.WHITE;

        super.txtBtnStyle = txtBtnStyle;
    }

    private void initTxtFldStyle(int height) {
        TextField.TextFieldStyle txtFldStyle = new TextField.TextFieldStyle();

        Skin skin = new Skin(Gdx.files.internal("UI/ui.json"));
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("UI/ui.atlas"));
        skin.addRegions(atlas);

        txtFldStyle.font = fontPad(generateFont(height-Gdx.graphics.getHeight()/50));
        txtFldStyle.background = skin.getDrawable("but1");
        txtFldStyle.fontColor = Color.WHITE;
        txtFldStyle.messageFont = generateFont(height-Gdx.graphics.getHeight()/60);
        txtFldStyle.messageFontColor = Color.RED;
        txtFldStyle.cursor = new NinePatchDrawable(new NinePatch(new Texture("UI/cursor.9.png")));

        super.txtFldStyle = txtFldStyle;
    }

    private void initLblStyle(int height) {
        Label.LabelStyle lblStyle = new Label.LabelStyle();
        lblStyle.font = generateFont(height-Gdx.graphics.getHeight()/60);
        lblStyle.fontColor = Color.WHITE;
        super.lblStyle = lblStyle;
    }

    public BitmapFont fontPad(BitmapFont f) {
        BitmapFont.BitmapFontData fd = f.getData();
        fd.padLeft = -5;
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
                System.out.println("[Multi:136]Debug: " + "IP input: " + ip);
                controller.name = "Kjeller";    //TODO: Name from a textfield
                if(ip != null || !(ip.equals(""))) {
                    if(controller.joinGame(ip));
                        controller.clearStage();
                }
                        //TODO: Create label or something..
                super.touchUp(event, x, y, pointer, button);
            }

            @Override
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
                join.setStyle(hoverStyle);
                super.enter(event,x,y,pointer,fromActor);
            }

            @Override
            public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
                join.setStyle(txtBtnStyle);
                super.enter(event,x,y,pointer,toActor);
            }
        });

        host.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                controller.swapMenu("Select");
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

            @Override
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
                back.setStyle(hoverStyle);
                super.enter(event,x,y,pointer,fromActor);
            }

            @Override
            public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
                back.setStyle(txtBtnStyle);
                super.enter(event,x,y,pointer,toActor);
            }
        });

        host.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                controller.swapMenu("Select");
                super.touchUp(event, x, y, pointer, button);
            }

            @Override
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
                host.setStyle(hoverStyle);
                super.enter(event,x,y,pointer,fromActor);
            }

            @Override
            public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
                host.setStyle(txtBtnStyle);
                super.enter(event,x,y,pointer,toActor);
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