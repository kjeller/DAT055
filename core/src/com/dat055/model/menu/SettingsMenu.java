package com.dat055.model.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.dat055.Game;
import com.dat055.controller.MenuController;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;


public class SettingsMenu extends Menu {
    private MenuController controller;
    private TextButton apply ,save ,back;
    private Label resolutionX,resolutionY, fullscreen, music, sound;
    private TextField resFieldX,resFieldY,fulField,musField,soundField;
    private String resSettingX,resSettingY,fulSetting,musSetting,soundSetting;
    private int resX,resY,fulInt;
    //private boolean fulBool;
    private Map<String,String> settingsMap;
    public SettingsMenu(MenuController ctrl) {
        super(ctrl, false, "UI/Delta.jpg");

        settingsMap = new TreeMap<String, String>() {};

        initConfig();
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

        //Buttons <-- todo: rework these
        save = createButton("Save");
        apply = createButton("Apply");
        back = createButton("Back");

        //Settings <-- todo: rework these
        resSettingX = settingsMap.get("resolutionX");
        resSettingY = settingsMap.get("resolutionY");
        fulSetting = settingsMap.get("fullscreen");
        musSetting = settingsMap.get("music");
        soundSetting = settingsMap.get("soundeffects");


        //Textfield <-- todo: rework these
        resFieldX = createTextField(resSettingX);
        resFieldY = createTextField(resSettingY);
        fulField = createTextField(fulSetting);
        musField = createTextField(musSetting);
        soundField = createTextField(soundSetting);


        //Texts <-- todo: rework these
        resolutionX = new Label("Screen width",lblStyle);
        resolutionY = new Label("Screen height",lblStyle);
        fullscreen = new Label("Fullscreen",lblStyle);
        music = new Label("Music",lblStyle);
        sound = new Label("Sound",lblStyle);


        addListeners();

        table.debug();
        table.add(back).width(150).height(40);
        table.add(save).width(170).height(40);
        table.add(apply).width(170).height(40).row();
        table.add();
        table.add(resolutionX).padTop(20).height(40).padBottom(10);
        table.add(resFieldX).padTop(20).padBottom(10).row();
        table.add();
        table.add(resolutionY).padTop(20).height(40).padBottom(10);
        table.add(resFieldY).padTop(20).padBottom(10).row();
        table.add();
        table.add(fullscreen).height(40).padBottom(10).padTop(20);
        table.add(fulField).padTop(20).padBottom(10).row();
        table.add();
        table.add(music).height(40).padBottom(10).padTop(20);
        table.add(musField).padTop(20).padBottom(10).row();
        table.add();
        table.add(sound).padTop(20).height(40).padBottom(10);
        table.add(soundField).padTop(20).padBottom(10).row();

        super.table = table;
    }

    private void addListeners() {
        apply.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                settingsMap.put("resolutionX", resFieldX.getText());
                settingsMap.put("resolutionY", resFieldY.getText());
                settingsMap.put("fullscreen",fulField.getText());
                resX = Integer.parseInt(settingsMap.get("resolutionX"));
                resY = Integer.parseInt(settingsMap.get("resolutionY"));
                fulInt = Integer.parseInt(settingsMap.get("fullscreen"));

                if (inputSanitizer()&&fulInt==1){
                    Gdx.graphics.setWindowedMode(resX,resY);
                    Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                    controller.resize(resX,resY);
                }

                else if (inputSanitizer()&&fulInt==0){
                    Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                    Gdx.graphics.setWindowedMode(resX, resY);
                    controller.resize(resX, resY);
                }
                else
                    System.out.println("You can't do that Dave");
            }
        });
        save.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                settingsMap.put("resolutionX", resFieldX.getText());
                settingsMap.put("resolutionY", resFieldY.getText());
                settingsMap.put("fullscreen", fulField.getText());
                settingsMap.put("music", musField.getText());
                settingsMap.put("soundeffects", soundField.getText());

                if (inputSanitizer()) {
                    try {
                        ConfigIO.save(settingsMap, "config.txt");
                    } catch (IOException e) {
                        System.out.println("Something Hideous Intentionally Transpired");
                    }
                }
                else
                    System.out.println("You can't do that Dave");
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
            settingsMap = ConfigIO.load("config.txt");
        }
        catch(IOException e){
            System.out.println("OH,Sugar Honey Ice Tea");
        }
    }

    public boolean inputSanitizer(){
        if (resSanitizerX() && resSanitizerY() && fulSanitizer() && musicSanitizer() && soundSanitizer())
            return true;
        return false;
    }

    private boolean resSanitizerX(){
        if( settingsMap.put("resolutionX", resFieldX.getText()).equals("1280") ) //1280x720
            return true;
        else if ( settingsMap.put("resolutionX", resFieldX.getText()).equals("1920"))//1920x1080
            return true;
        else if (settingsMap.put("resolutionX", resFieldX.getText()).equals("1366")) //1366x768
            return true;
        else if ( settingsMap.put("resolutionX", resFieldX.getText()).equals("1600")) //1600x900
            return true;
        return false;
    }

    private boolean resSanitizerY(){
        if( settingsMap.put("resolutionY", resFieldY.getText()).equals("720") ) //1280x720
            return true;
        else if ( settingsMap.put("resolutionY", resFieldY.getText()).equals("1080"))//1920x1080
            return true;
        else if (settingsMap.put("resolutionY", resFieldY.getText()).equals("768")) //1366x768
            return true;
        else if ( settingsMap.put("resolutionY", resFieldY.getText()).equals("900")) //1600x900
            return true;
        return false;
    }

    private boolean fulSanitizer(){
        if (settingsMap.put("fullscreen", fulField.getText()).equals("1"))
            return true;
        else if (settingsMap.put("fullscreen", fulField.getText()).equals("0"))
            return true;
        return false;
    }

    private boolean musicSanitizer(){
        if(settingsMap.put("music", musField.getText()).equals("1"))
            return true;
        else if( settingsMap.put("music", musField.getText()).equals("0"))
            return true;
        return false;
    }

    private boolean soundSanitizer(){
        if (settingsMap.put("soundeffects", soundField.getText()).equals("1"))
            return true;
        else if (settingsMap.put("soundeffects", soundField.getText()).equals("0"))
            return true;
        return false;
    }
}
