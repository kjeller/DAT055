package com.dat055.model.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.dat055.controller.MenuController;

/**
 * This class is not used in the demo. It was meant to work as a multiplayer lobby of sorts. This is where the concept
 * of an updatable menu was needed. This is because of the static nature of menus, they don't update to
 * external information unless recreated. Might result in a re-design of the menu system.
 *
 * @author Erik Börne
 * @version 2019-03-11
 */
public class CharacterMenu extends Menu {
    private TextField username;
    private TextButton ready, back, start;

    /**
     * The default constructor for the currently obsolete class.
     * @param ctrl This is the {@link MenuController} that is used to swap menus, set flags and access
     *             {@link com.dat055.controller.GameController}'s methods.
     */
    public CharacterMenu(MenuController ctrl) {
        super(ctrl,true, "UI/Bg/Main.png");
        createTable();
    }


    @Override
    public void createTable() {
        // Create the buttons
        username = createTextField("Enter a username");
        ready = createButton("Ready");
        back = createButton("Back");
        start = createButton("Start");

        addListeners();

        layoutTable(width,  height);
    }

    /**
     * This is the method that populates the table that exist in the inherited class {@link Menu}.
     * @param butX This is the width of (primarily) buttons.
     * @param butY This is the height of previously mentioned buttons.
     */
    private void layoutTable(int butX, int butY) {
        // Declare and assign padding (it scales with window size, since butY is assigned (window size)/constant)
        int padSmall = butY/2;

        Table table = super.table;
        table.setSize(controller.getWidth(),controller.getWidth());

        table.setPosition(0,0);

        table.setFillParent(true);
        table.add(createSubTable()).padBottom(padSmall).colspan(2).expand().row();
        table.add(username).width(butX).height(butY).padBottom(padSmall >> 1).colspan(2).row();
        table.add(ready).width(butX).height(butY).padBottom(padSmall*2).colspan(2).row();
        table.add(back).width(butX >> 1).height(butY).padLeft(padSmall).padBottom(padSmall).bottom().left();
        table.add(start).width(butX >> 1).height(butY).padRight(padSmall).padBottom(padSmall).bottom().right();
        table.setDebug(true);
    }

    /**
     * This is a method that creates a sub-table that is placed in the table.
     * @return Returns the table so that it may be chained when placed in another table.
     */
    private Table createSubTable() {
        Skin skin = new Skin(new TextureAtlas("UI/char_select-packed/pack.atlas"));
        Table subTable = new Table();

        Button charOne = new Button(skin.getDrawable("blue"),skin.getDrawable("blue_select"), skin.getDrawable("blue_select"));
        charOne.getStyle().disabled = skin.getDrawable("blue_checked");
        charOne.scaleBy(Gdx.graphics.getHeight() / charOne.getHeight());

        Button charTwo = new Button(skin.getDrawable("red"), skin.getDrawable("red_select"), skin.getDrawable("red_select"));
        charTwo.getStyle().disabled = skin.getDrawable("red_checked");
        charTwo.scaleBy(Gdx.graphics.getHeight() / charOne.getHeight());

        Table table = new Table();
        ButtonGroup<Button> buttonGroup = new ButtonGroup<Button>();
        Label name1 = new Label("", lblStyle);
        Label name2 = new Label("Yowbie", lblStyle);

        buttonGroup.add(charOne);
        buttonGroup.add(charTwo);

        float buttonSize = Gdx.graphics.getHeight()>>2;
        float padSize = Gdx.graphics.getWidth()>>4;
        table.add(charOne).padRight(padSize).size(buttonSize).expand();
        table.add(charTwo).padLeft(padSize).size(buttonSize).expand().row();
        table.add(name1).padRight(padSize);
        table.add(name2).padLeft(padSize);
        table.setDebug(true);

        if (controller.isCharOneBlocked()) {
            charOne.setDisabled(true);
            subTable.add(charOne);
        } else if (controller.isCharTwoBlocked()){
            charTwo.setDisabled(true);
            charTwo.add();
        } else {
            buttonGroup.setMaxCheckCount(1);
            buttonGroup.setMinCheckCount(0);
        }

        return table;
    }

    /**
     * Adds listeners to every actor (button, text fields etc.).
     */
    private void addListeners() {
        back.addListener(new ClickListener() {
            /**
             * Overrides the method so that {@link MenuController} goes to the multiplayer menu.
             */
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                controller.swapMenu("Multiplayer");
                super.touchUp(event, x, y, pointer, button);
            }

            /**
             * Overrides the method so that the button changes to the style when the pointer is above it.
             */
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                back.setStyle(hoverStyle);
                super.enter(event, x, y, pointer, fromActor);
            }

            /**
             * Overrides the method so that the button changes to its original style when the pointer leaves.
             */
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                back.setStyle(txtBtnStyle);
                super.enter(event, x, y, pointer, toActor);
            }
        });

        start.addListener(new ClickListener() {
            /**
             * Overrides the method so that the button changes to the style when the pointer is above it.
             */
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                start.setStyle(hoverStyle);
                super.enter(event, x, y, pointer, fromActor);
            }

            /**
             * Overrides the method so that the button changes to its original style when the pointer leaves.
             */
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                start.setStyle(txtBtnStyle);
                super.enter(event, x, y, pointer, toActor);
            }
        });

        username.addListener(new FocusListener() {
            /**
             * If the text field gains focus, its text is removed. This makes it much more user-friendly
             */
            @Override
            public void keyboardFocusChanged (FocusEvent event, Actor actor, boolean focused) {
                if(focused) { username.setText(""); }
                else { username.setText("Enter host IP"); }
            }
        });
    }
}


