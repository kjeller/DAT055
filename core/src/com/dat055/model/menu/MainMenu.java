package com.dat055.model.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.dat055.controller.MenuController;

/**
 * This class is responsible for the creation and functionality of the main menu. It inherits from the class
 * {@link Menu}, which is where the menu table and styles are created and stored.
 *
 * @author Erik Börne
 * @version 2019-03-04
 */
public class MainMenu extends Menu {
    private TextButton play, multi, settings, exit, credits;
    private Label verNr;

    /**
     * The default constructor of {@link MainMenu}.
     * @param ctrl This is the {@link MenuController} that is used to swap menus, set flags and access
     *             {@link com.dat055.controller.GameController}'s methods.
     */
    public MainMenu(MenuController ctrl) {
        super(ctrl,false, "UI/Delta.jpg");
        createTable();
    }

    @Override
    public void createTable() {
        int width = Gdx.graphics.getWidth()/4;
        int height = Gdx.graphics.getHeight()/18;

        // Create the actors
        play = createButton("Play");
        multi = createButton("Multiplayer");
        settings = createButton("Settings");
        exit = createButton("Exit");
        credits = createButton("Credits");
        verNr = new Label("Version: 0.43", super.lblStyle);

        // Add listeners to the actors
        addListeners();

        // Create the actual table
        layoutTable(width, height);
    }

    /**
     * This is the method that populates the table that exist in the inherited class {@link Menu}.
     * @param butX This is the width of (primarily) buttons.
     * @param butY This is the height of previously mentioned buttons.
     */
    private void layoutTable(int butX, int butY) {
        int padL, padS;
        padL = butX/3;
        padS = butY>>1;

        Table table = super.table;
        table.setSize(controller.getWidth(),controller.getHeight());
        table.bottom();

        table.setPosition(0, 0);

        table.add(play).width(butX).height(butY).padBottom(padS).expandX().colspan(2).row();
        table.add(multi).width(butX).height(butY).padBottom(padS).colspan(2).row();
        table.add(settings).width(butX).height(butY).padBottom(padS).colspan(2).row();
        table.add(exit).width(butX).height(butY).padBottom(padL).colspan(2).row();
        table.add(credits).width(butX>>1).height(butY).padLeft(padS).padBottom(padS).left();
        table.add(verNr).width(butX>>1).height(butY).padRight(padS).padBottom(padS).right();
    }

    /**
     * Adds listeners to every actor (button, text fields etc.).
     */
    private void addListeners() {
        play.addListener(new ClickListener() {
            /**
             * Overrides the method so that {@link MenuController} multiplayer flag is set false (singleplayer). Then
             * swaps to {@link SelectMenu}.
             */
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                controller.multiplayer = false;
                controller.swapMenu("Select");
                super.touchUp(event, x, y, pointer, button);
            }

            /**
             * Overrides the method so that the button changes to the style when the pointer is above it.
             */
            @Override
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
                play.setStyle(hoverStyle);
                super.enter(event,x,y,pointer,fromActor);
            }

            /**
             * Overrides the method so that the button changes to its original style when the pointer leaves.
             */
            @Override
            public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
                play.setStyle(txtBtnStyle);
                super.enter(event,x,y,pointer,toActor);
            }
        });

        multi.addListener(new ClickListener() {
            /**
             * Overrides the method so that {@link MenuController} multiplayer flag is set to true. Then swaps to the
             * {@link MultiMenu}.
             */
           @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                controller.multiplayer = true;
                controller.swapMenu("Multiplayer");
            }

            /**
             * Overrides the method so that the button changes to the style when the pointer is above it.
             */
            @Override
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
                multi.setStyle(hoverStyle);
                super.enter(event,x,y,pointer,fromActor);
            }

            /**
             * Overrides the method so that the button changes to its original style when the pointer leaves.
             */
            @Override
            public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
                multi.setStyle(txtBtnStyle);
                super.enter(event,x,y,pointer,toActor);
            }
        });

        settings.addListener(new ClickListener() {
            /**
             * Overrides the method so that {@link MenuController} multiplayer flag is set false (singleplayer), then
             * swaps to {@link SettingsMenu}.
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

        exit.addListener(new ClickListener() {
            /**
             * Overrides the method so that the application is shutdown when the button is clicked.
             */
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                super.touchUp(event, x, y, pointer, button);
            }

            /**
             * Overrides the method so that the button changes to the style when the pointer is above it.
             */
            @Override
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
                exit.setStyle(hoverStyle);
                super.enter(event,x,y,pointer,fromActor);
            }

            /**
             * Overrides the method so that the button changes to its original style when the pointer leaves.
             */
            @Override
            public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
                exit.setStyle(txtBtnStyle);
                super.enter(event,x,y,pointer,toActor);
            }
        });

        credits.addListener(new ClickListener() {
            /**
             * Overrides the method so that {@link MenuController} swaps to the .
             */
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                controller.swapMenu("Finish");
                super.touchUp(event, x, y, pointer, button);
            }

            /**
             * Overrides the method so that the button changes to the style when the pointer is above it.
             */
            @Override
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
                credits.setStyle(hoverStyle);
                super.enter(event,x,y,pointer,fromActor);
            }

            /**
             * Overrides the method so that the button changes to its original style when the pointer leaves.
             */
            @Override
            public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
                credits.setStyle(txtBtnStyle);
                super.enter(event,x,y,pointer,toActor);
            }
        });
    }
}
