package com.dat055.model.collision;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.dat055.model.entity.Enemy;
import com.dat055.model.entity.Entity;
import com.dat055.model.entity.Hook;
import com.dat055.model.entity.Player;
import com.dat055.model.map.GameMap;
import com.dat055.model.map.tile.Tile;
import com.dat055.model.map.tile.TileMap;



import java.util.ArrayList;


public class CollisionHandler {
    private final int tileSize = 64;
    private TileMap tileMap;
    private ArrayList<Rectangle> rectList;
    private ArrayList<Tile> tileList;
    private GameMap gameMap;

    public CollisionHandler(GameMap map) {
        gameMap = map;
        tileMap = map.getTileMap();
    }


    public void checkCollision(Entity entity) {
        if (entity == null)
            return;
        if (entity instanceof Player)
            collisionPlayer((Player)entity);
        else if (entity instanceof Hook)
            collisionHook((Hook)entity);
    }

    private Rectangle checkIfWallCollision(Entity entity, ArrayList<Tile> tiles) {
        Rectangle intersection = new Rectangle();
        for (Tile tile : tiles) {
            if (tile != null && tile.getState()) {
                if (Intersector.intersectRectangles(entity.getRect(), tile.getRect(), intersection)) {
                    return intersection;
                }
            }
        }
        return null;
    }

    private void collisionHook(Hook hook) {
        tileList = collisionBoxes(hook);
        if (checkIfOutside(hook)) {
            hook.setApexReached(true);
        }
        Rectangle intersection;
        if (!hook.getApexReached()) {
            if ((intersection = checkIfWallCollision(hook, tileList)) != null) {
                hook.setApexReached(true);
                hook.setHasGrip(true);
            }
            else if ((intersection = checkIfEntityCollision(hook)) != null) {
                hook.setApexReached(true);
            }
        }
    }
    private ArrayList<Tile> collisionBoxes(Entity entity) {
        Vector2 position = new Vector2(entity.getPosition());
        ArrayList<Tile> tiles = new ArrayList();
        for (int row = (int) (position.y / tileSize); row < Math.ceil((position.y + entity.getHeight()) / tileSize); row++) {
            for (int col = (int) (position.x / tileSize); col < Math.ceil((position.x + entity.getWidth()) / tileSize); col++) {
                Tile tile = tileMap.getTile((int) Math.floor(col), (int) Math.floor(row));
                tiles.add(tile);
            }
        }
        return tiles;
    }

    private Rectangle checkIfEntityCollision(Entity entity) {
        tileList = collisionBoxes(entity);
        Vector2 position = new Vector2(entity.getPosition());
        Rectangle intersection = new Rectangle();
        for (Entity mapEntity : gameMap.getEntities()) {
            if (Intersector.intersectRectangles(entity.getRect(), mapEntity.getRect(), intersection)) {
                if (mapEntity instanceof Enemy) {
                    System.out.println("Enemy detection");
                    return intersection;
                }
            }
        }
        return null;
    }

    private void collisionPlayer(Player player) {
        tileList = collisionBoxes(player);
        checkIfOutside(player);

        Rectangle playerRect = new Rectangle((player.getRect()));
        if (checkBeforeCollision(player))
            return;
        Rectangle intersection;
        // Loop through the tiles around the player.
        if ((intersection = checkIfWallCollision(player, tileList)) != null) {
            int val;
            // Check collisions above and below
            if (checkYCollision(intersection)) {
                player.setYVelocity(0);
                val = (playerRect.y + playerRect.height == intersection.y + intersection.height) ?
                        (int)(intersection.y-playerRect.height) : (int)(intersection.y+intersection.height);
                if (!(playerRect.y + playerRect.height == intersection.y + intersection.height))
                    player.setGrounded(true);
                player.setYPosition(val);
            }
            // Check collisions to the side
            if (checkXCollision(intersection)) {
                player.setXVelocity(0);
                player.setXAcceleration(0);
                // Value to set as X-pos
                val = (playerRect.x == intersection.x) ?
                        Math.round(playerRect.x+1) : Math.round(playerRect.x-1);
                player.setXPosition(val);
            }
            if (checkBothCollisions(intersection)) System.out.println("mweep");
            //TODO: Fix corner collisions
        }
    }
    private boolean checkIfOutside(Entity entity) {
        boolean ret = false;
        if (entity.getPosition().x  < 0) {
            entity.setXPosition(0);
            entity.setRectX((int)entity.getPosition().x);
            ret = true;
        }
        if (entity.getPosition().x+entity.getWidth() > tileMap.getWidthPixels()) {
            entity.setXPosition(tileMap.getWidthPixels()-entity.getWidth());
            entity.setRectX((int)entity.getPosition().x);
            ret = true;
        }


        if (entity instanceof Player) {
            Player player = (Player)entity;
            if (player.getPosition().y < 0) {
                player.takeDamage(9999);
                ret = true;
            }
        }
        return ret;
    }
    /**
     * Method that checks stuff before collision is checked
     * @param player player to be tested
     * @return value that determines if collision should be checked after method has checked cases.
     */
    private boolean checkBeforeCollision(Player player) {
        Vector2 position = new Vector2(player.getPosition());

        int height = player.getHeight();
        int width = player.getWidth();

        // Get tile player is currently in
        Tile tile = tileMap.getTile((int)((position.x+width/2)/tileSize), (int)(position.y)/tileSize);

        // If player is outside of the map, stop checking for collisions.
        if (position.x < 0 || position.y < 0 || position.x + width > tileMap.getWidthPixels() || position.y + height > tileMap.getHeightPixels())
            return true;

        // Check if entity should fall off edge
        if (!tileMap.getTile((int)((position.x)/tileSize), (int)(position.y-1)/tileSize).getState())
            player.setGrounded(false);

        // Check if entity is falling to stop a movement bug.
        if (player.getVelocity().y != 0 && player.getDirection().y < 0 ) {
            if (tile.getState()) {
                player.setYPosition((int)tile.getRect().y + 64);
                player.setGrounded(true);
                return true;
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
        return false;
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
