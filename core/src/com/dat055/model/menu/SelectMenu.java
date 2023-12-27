package com.dat055.model.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.dat055.controller.MenuController;
import java.util.ArrayList;

/**
 *  This class is responsible for the creation of the map selection menu. It inherits from the class {@link Menu}, which
 *  is where the menu table is stored.
 *
 *  @author Erik Börne
 *  @version 2019-03-06
 */
public class SelectMenu extends Menu {
    private FileHandle[] files;
    private TextButton back, select;
    private ButtonGroup<TextButton> textButtonGroup;

    /**
     * The default and standard constructor of {@link SelectMenu}.
     * @param ctrl This is the {@link MenuController} that is used to swap menus, set flags and access
     *             {@link com.dat055.controller.GameController}'s methods.
     */
    public SelectMenu(MenuController ctrl) {
        super(ctrl, false);
        textButtonGroup = new ButtonGroup<TextButton>();
        textButtonGroup.setMaxCheckCount(1);
        textButtonGroup.setMinCheckCount(0);
        textButtonGroup.setUncheckLast(true);
        createTable();
    }

    @Override
    public void createTable() {
        setBg(new Image(new Texture((controller.multiplayer) ? "UI/Bg/Host.png" : "UI/Bg/Singleplayer.png")));
        getBg().setSize(width*4, height*18);
        back = createButton("Back");
        select = createButton("Select");

        addListeners();

        layoutTable(width, height);
    }

    /**
     * This is the method that populates the table that exist in the inherited class {@link Menu}.
     * @param butX This is the width of (primarily) buttons.
     * @param butY This is the height of previously mentioned buttons.
     */
    private void layoutTable(int butX, int butY) {
        int padSmall = butY/2;

        Table table = super.table;
        table.setSize(controller.getWidth(),controller.getWidth());

        table.setPosition(0,0);

        table.add(createSubTable(padSmall)).padBottom(padSmall).colspan(2).expandX().expandY().row();
        table.add(back).width(butX>>1).height(butY).padLeft(padSmall).padBottom(padSmall).bottom().left();
        table.add(select).width(butX>>1).height(butY).padRight(padSmall).padBottom(padSmall).bottom().right();
    }

    /**
     * A help-method to create a part of the table.
     * @param padding The padding to be used when layouting the objects.
     * @return The resulting sub-table.
     */
    private Table createSubTable(int padding) {
        Table table = new Table();
        FileHandle listOfMap = Gdx.files.internal("maps/maps.txt"); // get list of maps
        String files[] = listOfMap.readString().split("\\n");
        ArrayList<FileHandle> fh = new ArrayList<FileHandle>();

        // Get handle for every level
        for (String filename: files) {
            fh.add(Gdx.files.internal("maps/" + filename));
        }

        for(FileHandle file: fh) {
            TextButton tb = createButton(file.nameWithoutExtension(), checkedStyle);
            textButtonGroup.add(tb);
            table.add(tb).width(width).height(height).padBottom(padding).expandX().row();
        }
        return table;
    }

    /**
     * Adds a listener to every actor active in the table. A quite large method, but a necessary one at that.
     */
    private void addListeners() {
        back.addListener(new ClickListener() {
            /**
             * Overrides the method so that {@link MenuController} swaps back to the previous menu. If the multiplayer
             * flag is true it goes back to the {@link MultiMenu}, if it's false it goes to the {@link MainMenu}
             */
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (controller.multiplayer)
                    controller.swapMenu("Multiplayer");
                else
                    controller.swapMenu("Main");
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

        select.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (textButtonGroup.getChecked() != null) {
                    if (controller.multiplayer)
                        controller.name = "Beelzebub";

                    controller.clearStage();
                    controller.currentMap = textButtonGroup.getChecked().getText().toString();
                    controller.startGame();
                }
                super.touchUp(event, x, y, pointer, button);
            }

            /**
             * Overrides the method so that the button changes to its original style when the pointer leaves.
             */
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                select.setStyle(hoverStyle);
                super.enter(event, x, y, pointer, fromActor);
            }

            /**
             * Overrides the method so that the button changes to its original style when the pointer leaves.
             */
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                select.setStyle(txtBtnStyle);
                super.enter(event, x, y, pointer, toActor);
            }
        });
    }
}

