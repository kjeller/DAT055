package com.dat055.Controller;

import com.dat055.Model.GameModel;
import com.dat055.View.GameView;

public class GameController extends Controller {

    public GameController(GameModel model, GameView view) {
        super(model, view);
    }

    public void update(float deltaTime) {
        ((GameModel)model).getPlayer().checkKeyboardInput();
        super.update(deltaTime);
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
