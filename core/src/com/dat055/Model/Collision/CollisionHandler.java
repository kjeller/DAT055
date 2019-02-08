package com.dat055.Model.Collision;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.dat055.Model.Entity.Player;
import com.dat055.Model.Map.Tile.Tile;
import com.dat055.Model.Map.Tile.TileMap;

public class CollisionHandler {
    private final int tileSize = 64;
    private TileMap tileMap;

    public CollisionHandler(TileMap map) {
        tileMap = map;
    }


    public boolean checkCollision(Player player) {
        Vector2 position = new Vector2(player.getPosition());
        Rectangle playerRect = new Rectangle((player.getRect()));
        int height = player.getHeight();
        int width = player.getWidth();

        // If player is outside of the map, stop checking for collisions.
        if (position.x < 0 || position.y < 0 || position.x + width > tileMap.getWidthPixels() || position.y + height > tileMap.getHeightPixels())
            return true;

        // Loop through the tiles around the player.
        for (int row = (int) (position.y / tileSize); row < Math.ceil((position.y + height) / tileSize); row++) {
            for (int col = (int) (position.x / tileSize); col < Math.ceil((position.x + width) / tileSize); col++) {
                Tile tile = tileMap.getTile((int) Math.floor(col), (int) Math.floor(row));
                Rectangle tileRect = tile.getRect();
                Rectangle intersection = new Rectangle();
                // Check for collision
                if (tile.getState()) {
                    if (Intersector.intersectRectangles(playerRect, tileRect, intersection)) {

                        // Check collisions above and below
                        if (checkYCollision(intersection)) {
                            player.setYVelocity(0);
                            if (tileRect.y < playerRect.y) {
                                player.setGrounded(true);
                                player.setYPosition((int)(intersection.y+intersection.height));
                            } else {
                                player.setYPosition((int)(tileRect.y-playerRect.height));
                            }
                        }

                        // Check collisions to the side
                        if (checkXCollision(intersection)) {
                            player.setXVelocity(0);
                            if (playerRect.x < tileRect.x + tileRect.width/2)
                                player.setXPosition(Math.round(tileRect.x - playerRect.width));
                            else
                                player.setXPosition((int)(tileRect.x + playerRect.width));
                        }
                    }
                    return true;
                }

            }
        }
        return false;
    }
    private boolean checkYCollision(Rectangle intersection) {

        if (intersection.width > intersection.height) {
            System.out.println("Collision Y");
            return true;
        }
        return false;
    }
    private boolean checkXCollision(Rectangle intersection) {

        if (intersection.height >= intersection.width) {
            System.out.println("Collision X");
            return true;
        }
        return false;
    }
}
