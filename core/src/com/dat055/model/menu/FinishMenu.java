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
public class FinishMenu extends Menu {
    private TextButton proceed;

    /**
     * The default constructor of {@link CreditsMenu}.
     * @param ctrl This is the {@link MenuController} that is used to swap menus, set flags and access
     *             {@link com.dat055.controller.GameController}'s methods.
     */
    public FinishMenu(MenuController ctrl) {
        super(ctrl,false, "UI/Bg/Finish.png");
        createTable();
    }

    @Override
    public void createTable() {
        int width = Gdx.graphics.getWidth()/4;
        int height = Gdx.graphics.getHeight()/18;

        // Create the actors
        proceed = createButton("Back to menu");

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
        int padS = butY>>1;

        table.setSize(controller.getWidth(),controller.getHeight());
        table.bottom();

        table.setPosition(0, 0);

        table.add(proceed).width(butX>>1).height(butY).padRight(padS).padBottom(padS).expandX().right();
    }

    /**
     * Adds listeners to every actor (button, text fields etc.).
     */
    private void addListeners() {
        proceed.addListener(new ClickListener() {
            /**
             * Overrides the method so that {@link MenuController} swaps back to the {@link MainMenu}.
             */
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                controller.swapMenu("Credits");
                super.touchUp(event, x, y, pointer, button);
            }

            /**
             * Overrides the method so that the button changes to the style when the pointer is above it.
             */
            @Override
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
                proceed.setStyle(hoverStyle);
                super.enter(event,x,y,pointer,fromActor);
            }

            /**
             * Overrides the method so that the button changes to its original style when the pointer leaves.
             */
            @Override
            public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
                proceed.setStyle(txtBtnStyle);
                super.enter(event,x,y,pointer,toActor);
            }
        });
    }
}