package com.dat055.view;

import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * This class is responsible with rendering and drawing of the stage.
 * Has room for future expansions; For example animated backgrounds for the menus.
 * @author Erik Börne
 * @version 2019-03-04
 */
public class MenuView extends View {
    /**
     * The default constructor.
     */
    public MenuView() {  }
    /**
     * A draw function that draws the stage stored in the model
     */
    public void draw(Stage stage) { stage.draw(); }
}
