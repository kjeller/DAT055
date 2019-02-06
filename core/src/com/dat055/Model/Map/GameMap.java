package com.dat055.Model.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameMap {
    TileMap front, back;
    String id, name;  // Current map id and name

    GameMap() {
        id = "Not set.";
        name = "Not set.";
    }

    public void draw(SpriteBatch batch) {
        front.draw(batch);
        //back.draw(batch); //TODO: Implement back tilemap
    }

    public TileMap getFrontTileMap() { return front; }
    public TileMap getBackTileMap() { return back; }
    public String getId() { return id; }
    public String getName() { return name; }
    public String toString() {
        return  String.format("Properties: id=%s, name=%s", this.id, this.name);
    }
}
