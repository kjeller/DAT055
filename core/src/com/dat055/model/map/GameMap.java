package com.dat055.model.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.dat055.model.map.tile.TileMap;

public class GameMap {
    TileMap front, back;
    String id, name;  // Current map id and name
    Vector2 frontStartPos, backStartPos;

    GameMap() {
        id = "Not set.";
        name = "Not set.";
    }

    public void draw(SpriteBatch batch) {
        front.draw(batch);
        back.draw(batch);
    }

    public TileMap getFrontTileMap() { return front; }
    public TileMap getBackTileMap() { return back; }
    public String getId() { return id; }
    public String getName() { return name; }
    public Vector2 getFrontStartPos() { return frontStartPos; }
    public Vector2 getBackStartPos() { return backStartPos; }
    public String toString() {
        return  String.format("Properties: id=%s, name=%s", this.id, this.name);
    }
}
