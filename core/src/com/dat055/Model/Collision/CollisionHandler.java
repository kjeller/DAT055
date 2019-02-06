package com.dat055.Model.Collision;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.dat055.Model.Entity.Character;
import com.dat055.Model.Entity.Entity;
import com.dat055.Model.Entity.Player;
import com.dat055.Model.Map.Tile.Tile;
import com.dat055.Model.Map.TileMap;



public class CollisionHandler {
    private final int tileSize = 64;
    private int height;
    private int width;
    private Vector2 position;
    private Rectangle rect;
    private TileMap tileMap;
    private ShapeRenderer renderer;

    public CollisionHandler(Player player, TileMap map) {
        //position = new Vector2(player.getPosition());
        rect = new Rectangle((player.getRect()));
        position = new Vector2();
        tileMap = map;
        renderer = new ShapeRenderer();
    }

    public boolean checkCollision(Player player) {
        position.set(player.getPosition());


        if (position.x < 0 || position.y < 0 || position.x + width > tileMap.getWidthPixels() || position.y + height > tileMap.getHeightPixels())
            return true;
        // Check tiles around entity

         for (int row = (int) (position.y / tileSize); row < Math.ceil((position.y + height) / tileSize); row++) {
            for (int col = (int) (position.x / tileSize); col < Math.ceil((position.x + width) / tileSize); col++) {
                Tile tile = tileMap.getTile((int)Math.floor(col), (int)Math.floor(row));

                // Is collideable
                //if (tile.getState()) {
                    //System.out.println("BONK INC");
                    //System.out.println(tile.getRect());
                    if (player.getRect().overlaps(tile.getRect())) {
                        System.out.printf("row: %d, col: %d \n", row, col);
                    }
                    return true;
              //  }
            }
        }
        return false;
    }
}
