package com.dat055.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.dat055.Model.Entity.Player;
import com.dat055.Model.GameModel;
import com.dat055.View.GameView;

public class GameController extends Controller {

    public GameController(GameModel model, GameView view) {
        super(model, view);
    }

    public void update(float deltaTime) {
        checkKeyboardInput(((GameModel)model).getCurrentPlayer()); // Handles keyboard input
        super.update(deltaTime);    // Updates GameModel
    }

    /**
     * Handles keyboard input for a specific player.
     */
    private void checkKeyboardInput(Player player) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.move(-1);
            player.setMoving(true);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.move(1);
            player.setMoving(true);
        } else {
            player.setMoving(false);
            player.move(0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
            player.attack();

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
            player.jump();
        if (Gdx.input.isKeyPressed(Input.Keys.D))
            System.out.println(toString());

        if(Gdx.input.isKeyJustPressed(Input.Keys.T))
            ((GameModel)model).toggleCurrentPlayer();

        // Enter debug mode
        if(Gdx.input.isKeyJustPressed((Input.Keys.B)))
            ((GameView)view).toggleRectangle();
    }
    /**
     * Calls gamemodel to create a game map for a specific map.
     * @param fileName of a json file in assets/maps/
     */
    public void startMap(String fileName) {
        ((GameModel)model).createMap(fileName, 64);
    }

    public void pause() { ((GameModel)model).setPause(true); }
    public void resume() {((GameModel)model).setPause(false); }
}
