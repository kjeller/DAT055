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

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;


public class SettingsMenu extends Menu {
    private MenuController controller;
    private TextButton apply ,save , back;
    private Label resolution, fullscreen, graphics, volume;
    private TextField resText, fulText, volText;
    private String resSetting,fulSetting,volSetting;
    private Map<String,String> settingsMap;
    public SettingsMenu(MenuController ctrl) {
        super("UI/Delta.jpg");

        settingsMap = new TreeMap<String, String>() {};

        initConfig();
        initTxtBtnStyle();
        initLblStyle();
        initTxtFldStyle(40);

        //temporary to test ConfigIO, todo: remove
        //for(String s,)
        settingsMap.put("a","100");

        controller = ctrl;
        Table table = new Table();

        table.setWidth(controller.getWidth());
        table.setHeight(controller.getHeight());
        table.align(Align.center | Align.top);

        table.setPosition(0, 0);

        //Buttons
        save = createButton("Save");
        apply = createButton("Apply");
        back = createButton("Back");

        //settings
        resSetting = settingsMap.get("resolution");
        fulSetting = settingsMap.get("fullscreen");
        volSetting = settingsMap.get("volume");


        //Textfield
        resText = createTextField(resSetting);
        fulText = createTextField(fulSetting);
        volText = createTextField(volSetting);
        resText.getDefaultInputListener();


        //Labels
        resolution = new Label("Resolution",lblStyle);
        fullscreen = new Label("FullScreen",lblStyle);
        volume = new Label("Volume",lblStyle);



        addListeners();

        table.debug();
        table.add(back).width(150).height(40);
        table.add(save).width(170).height(40);
        table.add(apply).width(170).height(40).row();
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

    private BitmapFont fontPad(BitmapFont f) {
        BitmapFont.BitmapFontData fd = f.getData();
        fd.padLeft = -5;
        return f;
    }

    private void initLblStyle() {
        Label.LabelStyle lblStyle = new Label.LabelStyle();
        lblStyle.font = super.generateFont(36);
        lblStyle.fontColor = Color.WHITE;
        super.lblStyle = lblStyle;
    }

    private void addListeners() {
        apply.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });
        save.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    settingsMap.put("resolution", resText.getMessageText());
                    settingsMap.put("fullscreen", fulSetting);
                    settingsMap.put("volume", volSetting);
                try{
                    configIO.save(settingsMap,"config.txt");
                    }
                catch(IOException e){
                    System.out.println("Something Hideous Intentionally Transpired");
                    }
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

    /**
     * laddar från config till texter
     */
    private void initConfig(){
        try {
            settingsMap = configIO.load("config.txt");
        }
        catch(IOException e){
            System.out.println("OH,Sugar Honey Ice Tea");
        }
    }
}
