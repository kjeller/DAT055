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

/**
 * This class is responsible for the creation of the multiplayer menu. It inherits from the class {@link Menu}, which
 * is where the menu table and styles are created and stored.
 *
 * @author Erik Börne
 * @version 2019-03-06
 */
public class MultiMenu extends Menu {
    private String ip;
    private TextButton join, host, back;
    private TextField address;

    /**
     * The default constructor of {@link MultiMenu}.
     * @param ctrl This is the {@link MenuController} that is used to swap menus, set flags and access
     *             {@link com.dat055.controller.GameController}'s methods.
     */
    public MultiMenu(MenuController ctrl) {
        super(ctrl, false, "UI/Delta.jpg");
        createTable();
    }

    @Override
    public void createTable() {
        int width = Gdx.graphics.getWidth()/4;
        int height = Gdx.graphics.getHeight()/18;

        join = createButton("Join");
        host = createButton("Host");
        back = createButton("Back");
        address = createTextField("Enter host IP");

        addListeners();

        layoutTable(width, height);
    }

    /**
     * This is the method that populates the table that exist in the inherited class {@link Menu}.
     * @param butX This is the width of (primarily) buttons.
     * @param butY This is the height of previously mentioned buttons.
     */
    private void layoutTable(int butX, int butY) {
        int padL, padS;
        padL = butX/2;
        padS = butY/2;

        Table table = super.table;
        table.setSize(controller.getWidth(),controller.getWidth());
        table.bottom();

        table.setPosition(0,0);

        table.add(address).width(butX).height(butY).padBottom(padS).expandX().colspan(2).row();
        table.add(join).width(butX).height(butY).padBottom(padL).colspan(2).row();
        table.add(back).width(butX>>1).height(butY).padLeft(padS).padBottom(padS).left();
        table.add(host).width(butX>>1).height(butY).padRight(padS).padBottom(padS).right();
    }

    public String getIp() {
        return ip;
    }

    /**
     * Adds a listener to every actor active in the table. A quite large method, but a necessary one at that.
     */
    private void addListeners() {
        join.addListener(new ClickListener() {
            /**
             * Overrides the method so that {@link MenuController} joins a hosted game.
             */
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                controller.host = false;
                ip = address.getText();
                System.out.println("[Multi:136]Debug: " + "IP input: " + ip);
                controller.name = "Kjeller";    //TODO: Name from a textfield
                if(!(ip == null || ip.equals(""))) {
                    controller.clearStage();
                    controller.joinGame(ip);
                }
                        //TODO: Create label or something..
                super.touchUp(event, x, y, pointer, button);
            }

            /**
             * Overrides the method so that the button changes to the style when the pointer is above it.
             */
            @Override
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
                join.setStyle(hoverStyle);
                super.enter(event,x,y,pointer,fromActor);
            }

            /**
             * Overrides the method so that the button changes to its original style when the pointer leaves.
             */
            @Override
            public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
                join.setStyle(txtBtnStyle);
                super.enter(event,x,y,pointer,toActor);
            }
        });

        host.addListener(new ClickListener() {
            /**
             * Overrides the method so that {@link MenuController} goes to the multiplayer menu.
             */
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                controller.host = true;
                controller.startGame();
                super.touchUp(event, x, y, pointer, button);
            }

            /**
             * Overrides the method so that the button changes to the style when the pointer is above it.
             */
            @Override
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
                host.setStyle(hoverStyle);
                super.enter(event,x,y,pointer,fromActor);
            }

            /**
             * Overrides the method so that the button changes to its original style when the pointer leaves.
             */
            @Override
            public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
                host.setStyle(txtBtnStyle);
                super.enter(event,x,y,pointer,toActor);
            }
        });

        back.addListener(new ClickListener() {
            /**
             * Overrides the method so that {@link MenuController} swaps to {@link MainMenu}.
             */
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                controller.swapMenu("Main");
                super.touchUp(event, x, y, pointer, button);
            }

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
        });

        address.addListener(new FocusListener() {
            /**
             * If the text field gains focus, its text is removed. This makes it much more user-friendly
             */
            @Override
            public void keyboardFocusChanged (FocusEvent event, Actor actor, boolean focused) {
                if(focused) { address.setText(""); }
                else { address.setText("Enter host IP"); }
            }
        });
    }
}