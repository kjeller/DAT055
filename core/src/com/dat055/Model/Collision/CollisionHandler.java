package com.dat055.Model.Collision;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.dat055.Model.Entity.Enemy;
import com.dat055.Model.Entity.Entity;
import com.dat055.Model.Entity.Hook;
import com.dat055.Model.Entity.Player;
import com.dat055.Model.Map.GameMap;
import com.dat055.Model.Map.Tile.Tile;
import com.dat055.Model.Map.Tile.TileMap;



import java.util.ArrayList;


public class CollisionHandler {
    private final int tileSize = 64;
    private TileMap tileMap;
    private ArrayList<Rectangle> rectList;
    private GameMap gameMap;

    public CollisionHandler(TileMap map) {
        tileMap = map;
    }


    public void checkCollision(Entity entity) {
        if (entity == null)
            return;
        if (entity instanceof Player)
            collisionPlayer((Player)entity);
        else if (entity instanceof Hook)
            collisionHook((Hook)entity);
    }

    private Rectangle checkIfWallCollision(Entity entity) {
        Vector2 position = new Vector2(entity.getPosition());
        for (int row = (int) (position.y / tileSize); row < Math.ceil((position.y + entity.getHeight()) / tileSize); row++) {
            for (int col = (int) (position.x / tileSize); col < Math.ceil((position.x + entity.getWidth()) / tileSize); col++) {
                Tile tile = tileMap.getTile((int) Math.floor(col), (int) Math.floor(row));
                Rectangle tileRect = tile.getRect();
                Rectangle intersection = new Rectangle();
                if (tile.getState()) {
                    if (Intersector.intersectRectangles(entity.getRect(), tileRect, intersection)) {
                        return intersection;
                    }
                }
            }
        }
        return null;
    }

    private void collisionHook(Hook hook) {
        Rectangle intersection;
        if (!hook.getApexReached()) {
            if ((intersection = checkIfWallCollision(hook)) != null) {
                hook.setApexReached(true);
                hook.setHasGrip(true);

            }
            /*else if ((intersection = checkIfEntityCollision(hook)) != null) {
                hook.setApexReached(true);
            }*/
        }
    }

    private Rectangle checkIfEntityCollision(Entity entity) {
        Vector2 position = new Vector2(entity.getPosition());
        for (int row = (int) (position.y / tileSize); row < Math.ceil((position.y + entity.getHeight()) / tileSize); row++) {
            for (int col = (int) (position.x / tileSize); col < Math.ceil((position.x + entity.getWidth()) / tileSize); col++) {
                Rectangle intersector = new Rectangle();
                /*for (Entity ent : gameMap.getEntitiesFront()) {
                    if (Intersector.intersectRectangles(entity.getRect(), ent.getRect(), intersector)) {
                        if (ent instanceof Enemy)
                            System.out.println("Enemy detection");

                        // Check if ent is enemy or whatever

                    }
                }*/
            }}
        return new Rectangle(0, 0, 0, 0);
    }

    private void collisionPlayer(Player player) {
        Vector2 position = new Vector2(player.getPosition());
        Rectangle playerRect = new Rectangle((player.getRect()));
        int height = player.getHeight();
        int width = player.getWidth();
        rectList = new ArrayList();

        // Get tile player is currently in
        Tile tile = tileMap.getTile((int)((position.x+width/2)/tileSize), (int)(position.y)/tileSize);

        // If player is outside of the map, stop checking for collisions.
        if (position.x < 0 || position.y < 0 || position.x + width > tileMap.getWidthPixels() || position.y + height > tileMap.getHeightPixels())
            return;

        // Check if entity should fall off edge
        if (!tileMap.getTile((int)((position.x)/tileSize), (int)(position.y-1)/tileSize).getState()) {
            player.setGrounded(false);
        }

        // Check if entity is falling to stop a movement bug.
        if (player.getVelocity().y != 0 && player.getDirection().y < 0 ) {
            if (tile.getState()) {
                player.setYPosition((int)tile.getRect().y + 64);
                player.setGrounded(true);
                return;
            }
        }
        else if (player.getVelocity().y != 0 && player.getDirection().y > 0) {
            tile = tileMap.getTile((int)((position.x+width/2)/tileSize), (int)(position.y+height+1)/tileSize);
            // Check if entity is rising to stop a movement bug.
            if (tile.getState()) {
                player.setYPosition((int)tile.getRect().y-height);
                player.setDirectionY(-1);
                player.setYVelocity(0);

            }
        }

        // Loop through the tiles around the player.
        for (int row = (int) (position.y / tileSize); row < Math.ceil((position.y + height) / tileSize); row++) {
            for (int col = (int) (position.x / tileSize); col < Math.ceil((position.x + width) / tileSize); col++) {
                tile = tileMap.getTile((int) Math.floor(col), (int) Math.floor(row));
                Rectangle tileRect = tile.getRect();
                Rectangle intersection = new Rectangle();
                rectList.add(tileRect);

                // Check for collision
                if (tile.getState()) {
                    if (Intersector.intersectRectangles(playerRect, tileRect, intersection)) {
                        int val;
                        // Check collisions above and below
                        if (checkYCollision(intersection)) {
                            player.setYVelocity(0);
                            val = (tileRect.y < playerRect.y) ?
                                    (int)(intersection.y+intersection.height) : (int)(tileRect.y-playerRect.height);
                            if (tileRect.y < playerRect.y)
                                player.setGrounded(true);

                            player.setYPosition(val);
                        }

                        // Check collisions to the side
                        if (checkXCollision(intersection)) {
                            player.setXVelocity(0);
                            player.setXAcceleration(0);
                            // Value to set as X-pos
                            val = (playerRect.x < tileRect.x + tileRect.width/2) ?
                                    Math.round(tileRect.x - playerRect.width) : (int)(tileRect.x + playerRect.width);
                            player.setXPosition(val);
                        }
                        if (checkBothCollisions(intersection)) System.out.println("mweep");
                        //TODO: Fix corner collisions
                    }
                }
            }
        }
    }

    private boolean checkYCollision(Rectangle intersection) {
        return (intersection.width > intersection.height);
    }
    private boolean checkXCollision(Rectangle intersection) {
        return (intersection.height > intersection.width);
    }
    private boolean checkBothCollisions(Rectangle intersection) {
        return (intersection.height == intersection.width);
    }
    public ArrayList<Rectangle> getCheckedTiles() {
        return rectList;
    }
}
