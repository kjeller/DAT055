package com.dat055.Model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dat055.Model.Entity.Player;
import com.dat055.Model.Map.GameMap;

public class GameModel extends Model {
    // Determines what character the player is controlling (single player)
    public enum Mode {
        Front, Back
    }

    public GameMap map;
    Player player;

    public void createMap(String fileName) {
        map = new GameMap(fileName);
        Mode mode = Mode.Front;

        player = new Player(1, 80, 64, "red_penguin_64x80.png",
                "Towbie", 5, 5);
    }

    public void update() {
        player.checkKeyboardInput();
        player.update();
    }
    //Todo: collisionhandler here
}
