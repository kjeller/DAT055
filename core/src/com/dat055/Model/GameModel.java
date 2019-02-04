package com.dat055.Model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dat055.Model.Map.GameMap;

public class GameModel extends Model {
    // Determines what character the player is controlling (single player)
    public enum Mode {
        Front, Back
    }
    GameMap map;
    public GameModel() {
        this.initialize();
    }
    public void initialize() {
        map = new GameMap("maps/map_0.json"); // Debugging map
    }

    public void update() {
        //TODO: Game logic here bois
    }

    public void draw(SpriteBatch batch) {
        map.draw(batch);
    }
    //Todo: collisionhandler here
}
