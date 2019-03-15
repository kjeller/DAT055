package com.dat055.model.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.dat055.controller.MenuController;
import com.dat055.model.config.ConfigIO;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * This Class is responsible for creating the settings menu.
 *
 * @author Pontus Johansson
 * @version 2019-03-07
 */
public class SettingsMenu extends Menu {
    private MenuController controller;
    private TextButton apply ,save ,back;
    private TextField resFieldX,resFieldY,fulField,musField,soundField;
    private int resX,resY,fulInt,mutInt;
    private Map<String,String> settingsMap;

    /**
     * The constructor for the settingsMenu
     * @param ctrl a menucontroller
     */
    public SettingsMenu(MenuController ctrl) {
        super(ctrl, false, "UI/Bg/Settings.png");

        settingsMap = new TreeMap<String, String>() {};
        controller = ctrl;

        initConfig();
        createTable();
    }

    /**
     * overrides the method
     * Creates the buttons and text
     */
    @Override
    protected void createTable() {
        String resSettingX,resSettingY,fulSetting,musSetting,soundSetting;

        table.setWidth(controller.getWidth());
        table.setHeight(controller.getHeight());
        table.bottom();

        table.setPosition(0, 0);

        //creates the Buttons in the menu
        save = createButton("Save");
        apply = createButton("Apply");
        back = createButton("Back");

        //gets settings from the config file
        resSettingX = settingsMap.get("resolutionX");
        resSettingY = settingsMap.get("resolutionY");
        fulSetting = settingsMap.get("fullscreen");
        musSetting = settingsMap.get("mute");
       // soundSetting = settingsMap.get("muteeffects");

        //The inputFields
        resFieldX = createTextField(resSettingX);
        resFieldY = createTextField(resSettingY);
        fulField = createTextField(fulSetting);
        musField = createTextField(musSetting);
       // soundField = createTextField(soundSetting);


        //sound = new Label("Mute effects",lblStyle);

        addListeners();

        // the placement and size of the menu items
        table.add(createSubtable()).colspan(3).expand().padBottom(height).row();
        table.add(back).width(170).height(40).expandX().left().pad(20);
        table.add(save).width(170).height(40).center().pad(20);
        table.add(apply).width(170).height(40).right().pad(20);
      //  table.add(sound).padTop(20).height(40).padBottom(10);
       // table.add(soundField).padTop(20).padBottom(10).row();
    }

    private Table createSubtable() {
        //The texts in the menu
        lblStyle = createLblStyle(55);
        Label resolutionX = new Label("Screen width",lblStyle);
        Label resolutionY = new Label("Screen height",lblStyle);
        Label fullscreen = new Label("Fullscreen",lblStyle);
        Label mute = new Label("Mute music",lblStyle);

        Table subTable = new Table();
        subTable.add(resolutionX).padTop(400).padBottom(10).padRight(10).colspan(2);
        subTable.add(resFieldX).padTop(400).padBottom(10).row();

        subTable.add(resolutionY).padTop(20).padBottom(10).padRight(10).colspan(2);
        subTable.add(resFieldY).padTop(20).padBottom(10).row();

        subTable.add(fullscreen).padBottom(10).padTop(20).padRight(10).colspan(2);
        subTable.add(fulField).padTop(20).padBottom(10).row();

        subTable.add(mute).padBottom(10).padTop(20).padRight(10).colspan(2);
        subTable.add(musField).padTop(20).padBottom(10).row();

        return subTable;
    }

    /**
     * Method that makes the string from the config.txt into int
     * @param strn name of the config
     * @return the specified config in int
     */
    private int parseInt(String strn){
        return Integer.parseInt(settingsMap.get(strn));
    }

    /**
     * Adds listeners for the buttons
     */
    private void addListeners() {
        apply.addListener(new ClickListener() {
            /**
             * Overrides the method so that the button changes to the style when the pointer is above it.
             */
            @Override
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
                apply.setStyle(hoverStyle);
                super.enter(event,x,y,pointer,fromActor);
            }
            /**
             * Overrides the method so that the button changes to its original style when the pointer leaves.
             */
            @Override
            public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
                apply.setStyle(txtBtnStyle);
                super.enter(event,x,y,pointer,toActor);
            }
            /**
             * Override of method
             * gets the new settings from inputfields and applies them
             */
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                settingsMap.put("resolutionX", resFieldX.getText());
                settingsMap.put("resolutionY", resFieldY.getText());
                settingsMap.put("fullscreen",fulField.getText());
                settingsMap.put("mute",musField.getText());
                if (inputSanitizer()) {
                    resX = parseInt("resolutionX");
                    resY = parseInt("resolutionY");
                    fulInt = parseInt("fullscreen");
                    mutInt = parseInt("mute");

                    if ( mutInt == 1) {
                        controller.setMute(true);
                        controller.playMusic();
                        controller.getCtrl().setMute(true);
                    } else if (mutInt == 0) {
                        controller.setMute(false);
                        controller.playMusic();
                        controller.getCtrl().setMute(false);
                    }

                    if (fulInt == 1) {
                        Gdx.graphics.setWindowedMode(resX, resY);
                        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                        controller.resize(resX, resY);
                    } else if (fulInt == 0) {
                        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                        Gdx.graphics.setWindowedMode(resX, resY);
                        controller.resize(resX, resY);
                    } else
                        System.out.println("I can't let you do that Dave");
                }
                controller.swapMenu("Settings");
            }
        });

        save.addListener(new ClickListener() {
            /**
             * Overrides the method so that the button changes to the style when the pointer is above it.
             */
            @Override
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
                save.setStyle(hoverStyle);
                super.enter(event,x,y,pointer,fromActor);
            }

            /**
             * Overrides the method so that the button changes to its original style when the pointer leaves.
             */
            @Override
            public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
                save.setStyle(txtBtnStyle);
                super.enter(event,x,y,pointer,toActor);
            }

            /**
             * overrides the method and saves the inputFields to the config.txt
             */
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                settingsMap.put("resolutionX", resFieldX.getText());
                settingsMap.put("resolutionY", resFieldY.getText());
                settingsMap.put("fullscreen", fulField.getText());
                settingsMap.put("mute", musField.getText());
              //  settingsMap.put("muteeffects", soundField.getText());

                if (inputSanitizer()) {
                    try {
                        ConfigIO.save(settingsMap, "config.txt");
                    } catch (IOException e) {
                        System.out.println("Something Hideous Intentionally Transpired");
                    }
                }
                else
                    System.out.println("I can't let you do that Dave");
                controller.swapMenu("Settings");
            }
        });
        back.addListener(new ClickListener() {
            /**
             * Overrides the method so that the button changes to the style when the pointer is above it.
             */
            @Override
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
                back.setStyle(hoverStyle);
                super.enter(event,x,y,pointer,fromActor);
            }

            /**
             * Overrides the method so that the button changes to its original style when the pointer leaves.
             */
            @Override
            public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
                back.setStyle(txtBtnStyle);
                super.enter(event,x,y,pointer,toActor);
            }
            /**
             * overrides the method, swaps to main menu
             */
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (controller.getCtrl().isRunning()) controller.swapMenu("Pause");
                else controller.swapMenu("Main");
                super.touchUp(event, x, y, pointer, button);
            }
        });
    }

    /**
     * initialises the config file and creates one in case of it does not exist
     */
    private void initConfig(){
        try {
            settingsMap = ConfigIO.load("config.txt");
        }
        catch(IOException e){
            System.out.println("config.txt does not exist");
            settingsMap.put("resolutionX","1280");
            settingsMap.put("resolutionY","720");
            settingsMap.put("fullscreen","0");
            settingsMap.put("mute","0");
            //settingsMap.put("muteeffects","0");
            try {
                ConfigIO.save(settingsMap, "config.txt");
            }
            catch(IOException i){
                System.out.println("OhNo!");
            }
        }
    }

    /**
     * makes sure that inputs are correct values
     * @return true if sanitizers are all true
     */
    private boolean inputSanitizer(){
        return (resSanitizerX() && resSanitizerY() && fulSanitizer() && musicSanitizer() );
    }

    /**
     * sanitizes the height inputs
     */
    private boolean resSanitizerX(){
        if( settingsMap.put("resolutionX", resFieldX.getText()).equals( "1280") ) //1280x720
            return true;
        else if ( settingsMap.put("resolutionX", resFieldX.getText()).equals("1920"))//1920x1080
            return true;
        else if (settingsMap.put("resolutionX", resFieldX.getText()).equals("1366")) //1366x768
            return true;
        return (settingsMap.put("resolutionX", resFieldX.getText()).equals("1600")); //1600x900

    }

    /**
     * Sanitises the width input
     */
    private boolean resSanitizerY(){
        if( settingsMap.put("resolutionY", resFieldY.getText()).equals("720") ) //1280x720
            return true;
        else if ( settingsMap.put("resolutionY", resFieldY.getText()).equals("1080"))//1920x1080
            return true;
        else if (settingsMap.put("resolutionY", resFieldY.getText()).equals("768")) //1366x768
            return true;
        return ( settingsMap.put("resolutionY", resFieldY.getText()).equals("900")); //1600x900

    }

    /**
     * makes sure that fullscreen input is 1 or 0
     */
    private boolean fulSanitizer(){
        if (settingsMap.put("fullscreen", fulField.getText()).equals("1"))
            return true;
        return(settingsMap.put("fullscreen", fulField.getText()).equals("0"));
    }

    /**
     * makes sure that mute is 1 or 0
     */
    private boolean musicSanitizer(){
        if(settingsMap.put("mute", musField.getText()).equals("1"))
            return true;
        return ( settingsMap.put("mute", musField.getText()).equals("0"));
    }

    /*
     * makes sure that mute sound effects is 1 or 0

    private boolean soundSanitizer(){
        if (settingsMap.put("muteeffects", soundField.getText()).equals("1"))
            return true;
        return (settingsMap.put("muteeffects", soundField.getText()).equals("0"));
    }
     */
}
