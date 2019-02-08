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
        height = player.getHeight();
        width = player.getWidth();

        // If player is outside of the map, stop checking for collisions.
        if (position.x < 0 || position.y < 0 || position.x + width > tileMap.getWidthPixels() || position.y + height > tileMap.getHeightPixels())
            return true;
        // Check tiles around entity

        for (int row = (int) (position.y / tileSize); row < Math.ceil((position.y + height) / tileSize); row++) {
            for (int col = (int) (position.x / tileSize); col < Math.ceil((position.x + width) / tileSize); col++) {
                Tile tile = tileMap.getTile((int) Math.floor(col), (int) Math.floor(row));
                Rectangle playerRect = player.getRect();
                Rectangle tileRect = tile.getRect();
                Rectangle intersection = new Rectangle();
                // Check for collision
                if (tile.getState()) {
                    if (Intersector.intersectRectangles(playerRect, tileRect, intersection)) {

                        /*if (checkXCollision(intersection)) {

                            player.setXVelocity(0);
                            //Collision is to the right
                            if (playerRect.x < tileRect.x + tileRect.width) {
                                player.setXPosition(Math.round(tileRect.x + tileRect.width));
                            } else {
                                player.setXPosition((int)(tileRect.x-playerRect.width));
                            }
                        }*/

                        if (checkYCollision(intersection)) {
                            player.setYVelocity(0);
                            player.setGrounded(true);
                            player.setYPosition((int)(intersection.y+intersection.height));
                        }
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
            System.out.println("collision Y");
            return true;
        }
        return false;
    }
    private boolean checkXCollision(Rectangle intersection) {
        System.out.printf("X: %f > %f\n", intersection.height, intersection.width);
        if (intersection.height > intersection.width) {
            System.out.println("Collision X");
            return true;
        }
        return false;
    }
}
