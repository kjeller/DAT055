package com.dat055.model.menu;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.dat055.controller.GameController;
import com.dat055.controller.MenuController;

/**
 * This class is responsible for the creation and functionality of the pause menu. It inherits from the class {@link Menu},
 * which is where the menu table and styles are created and stored.
 *
 * @author Erik Börne
 * @version 2019-03-06
 */
public class PauseMenu extends Menu {
    public boolean mute;
    private TextButton resume, settings, menu;

    /**
     * The default and standard constructor of {@link PauseMenu}.
     * @param ctrl This is the {@link MenuController} that is used to swap menus, set flags and access
     *             {@link com.dat055.controller.GameController}'s methods.
     */
    public PauseMenu(MenuController ctrl) {
        super(ctrl);
        createTable();
    }

    @Override
    public void createTable() {
        resume = createButton("Resume game");
        settings = createButton("Settings");
        menu = createButton("Exit to main menu");

        addListeners();

        layoutTable(width, height);
    }

    /**
     * This sets the mute flag.
     * @param state sets the state of mute.
     */
    public void setMute(boolean state) {mute = state;}

    /**
     * This is the method that populates the table that exist in the inherited class {@link Menu}.
     * @param butX This is the width of (primarily) buttons.
     * @param butY This is the height of previously mentioned buttons.
     */
    private void layoutTable(int butX, int butY) {
        int padS = butY/2;

        Table table = super.table;
        table.setSize(controller.getWidth(),controller.getWidth());

        table.setPosition(0,0);

        table.add(resume).width(butX).height(butY).padBottom(padS).expandX().row();
        table.add(settings).width(butX).height(butY).padBottom(padS).row();
        table.add(menu).width(butX).height(butY).padBottom(padS).row();
    }

    /**
     * Adds a listener to every actor active in the table. A quite large method, but a necessary one at that.
     */
    private void addListeners() {
        resume.addListener(new ClickListener() {
            /**
             * Overrides the method so that {@link MenuController} triggers {@link GameController}'s toggle pause method.
             */
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                controller.togglePause();
                super.touchUp(event, x, y, pointer, button);
            }

            /**
             * Overrides the method so that the button changes to the style when the pointer is above it.
             */
            @Override
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
                resume.setStyle(hoverStyle);
                super.enter(event,x,y,pointer,fromActor);
            }

            /**
             * Overrides the method so that the button changes to its original style when the pointer leaves.
             */
            @Override
            public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
                resume.setStyle(txtBtnStyle);
                super.enter(event,x,y,pointer,toActor);
            }
        });

        settings.addListener(new ClickListener() {
            /**
             * Overrides the method so that {@link MenuController} swaps to the {@link AlternativeSettingsMenu}.
             */
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                controller.swapMenu("Settings");
                super.touchUp(event, x, y, pointer, button);
            }

            /**
             * Overrides the method so that the button changes to the style when the pointer is above it.
             */
            @Override
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
                settings.setStyle(hoverStyle);
                super.enter(event,x,y,pointer,fromActor);
            }

            /**
             * Overrides the method so that the button changes to its original style when the pointer leaves.
             */
            @Override
            public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
                settings.setStyle(txtBtnStyle);
                super.enter(event,x,y,pointer,toActor);
            }
        });

        menu.addListener(new ClickListener() {
            /**
             * Overrides the method so that {@link MenuController} swaps to the {@link MainMenu} and starts the music.
             */
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                controller.closeGame();
                controller.swapToMain();
                super.touchUp(event, x, y, pointer, button);
            }

            /**
             * Overrides the method so that the button changes to the style when the pointer is above it.
             */
            @Override
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
                menu.setStyle(hoverStyle);
                super.enter(event,x,y,pointer,fromActor);
            }

            /**
             * Overrides the method so that the button changes to its original style when the pointer leaves.
             */
            @Override
            public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
                menu.setStyle(txtBtnStyle);
                super.enter(event,x,y,pointer,toActor);
            }
        });
    }
}