package com.dat055.Model.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dat055.Model.Entity.Entity;
import com.dat055.Model.Entity.Player;
import com.dat055.Model.GameModel;
import com.dat055.Model.Map.Tile.TileMap;

import java.util.ArrayList;

public class GameMap {
    TileMap front, back;
    String id, name;  // Current map id and name
    ArrayList<Entity> entities;

    Player player1;
    Player player2;

    GameMap() {
        id = "Not set.";
        name = "Not set.";
    }

    public void draw(SpriteBatch batch, float rotation, GameModel.Mode mode) {
        if(mode == GameModel.Mode.FRONT) {
            front.draw(batch, rotation);
            back.draw(batch, 0f);
        }
        else if(mode == GameModel.Mode.BACK) {
            back.draw(batch, rotation);
            front.draw(batch, 0f);
        }
    }

    public ArrayList<Entity> getEntities() {return entities; }
    public TileMap getFrontTileMap() { return front; }
    public TileMap getBackTileMap() { return back; }
    public String getId() { return id; }
    public String getName() { return name; }
    public Player getPlayer1() {return player1;}
    public Player getPlayer2() {return player2;}
    public String toString() {
        return  String.format("Properties: id=%s, name=%s, frontmap=%s, backmap=%s",
                this.id, this.name, this.front, this.back);
    }
}
