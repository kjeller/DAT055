package com.dat055.model.collision;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.dat055.model.entity.*;
import com.dat055.model.entity.character.Character;
import com.dat055.model.entity.character.Enemy;
import com.dat055.model.entity.character.Hook;
import com.dat055.model.entity.character.Player;
import com.dat055.model.entity.interactables.Button;
import com.dat055.model.entity.interactables.Door;
import com.dat055.model.entity.interactables.Goal;
import com.dat055.model.entity.interactables.Spike;
import com.dat055.model.map.GameMap;
import com.dat055.model.map.tile.Tile;
import com.dat055.model.map.tile.TileMap;

import java.util.ArrayList;

/**
 * @author Tobias Campbell
 * @version 21-02-2019
 */

public class CollisionHandler {
    private final int tileSize = 64;
    private TileMap tileMap;
    private ArrayList<Tile> tileList;
    private GameMap gameMap;

    public CollisionHandler(GameMap map) {
        gameMap = map;
        tileMap = map.getTileMap();
    }

    /**
     * Method to return all tiles surrounding an entity
     * @param entity entity to be checked
     * @return an array list containing all surrounding tiles.
     */
    private ArrayList<Tile> collisionBoxes(Entity entity) {
        Vector2 position = new Vector2(entity.getPosition());
        ArrayList<Tile> tiles = new ArrayList<Tile>();
        for (int row = (int) (position.y / tileSize); row < Math.ceil((position.y + entity.getHeight()) / tileSize); row++) {
            for (int col = (int) (position.x / tileSize); col < Math.ceil((position.x + entity.getWidth()) / tileSize); col++) {
                Tile tile = tileMap.getTile((int) Math.floor(col), (int) Math.floor(row));
                tiles.add(tile);
            }
        }
        return tiles;
    }

    /**
     * Redirect the right collision checks to an entity
     * @param entity the entity to be checked.
     */
    public void checkCollision(Entity entity) {
        if (entity == null)
            return;
        if (entity instanceof Character) {
            collisionCharacter((Character)entity);
        }
        else if (entity instanceof Hook)
            collisionHook((Hook)entity);
    }


    /**
     * One of the collision cases to be checked.
     * @param character entity to be tested
     */
    private void collisionCharacter(Character character) {
        Vector2 deltaPos = new Vector2();
        deltaPos.set(character.getDeltaPosition());
        tileList = collisionBoxes(character);

        if (checkIfOutside(character))
            checkChangeDirection(character);

        // Check certain specific cases before usual collision check
        if (checkIfFalling(character))
            character.setGrounded(false);

        if (checkBeforeCollision(character))
            return;

        Rectangle intersection;
        // Check if there is an intersection between the entity and the tiles around it
        if ((intersection = checkIfWallCollision(character, tileList)) != null) {
            // Check if collision is above or below
            verticalCollision(character, intersection);

            // Check if collision is to the left or to the right
            horizontalCollision(character, intersection);

            // Check if collision is on a corner
            edgeAdjacentCollision(character, intersection);
            //TODO: Fix corner collisions
        }
        else if ((intersection = checkIfEntityCollision(character)) != null) {
            System.out.println("WAOW");
        }
    }

    /**
     * One of the collision cases to be checked.
     * @param hook that is tested
     */
    private void collisionHook(Hook hook) {
        tileList = collisionBoxes(hook);
        if (checkIfOutside(hook)) {
            hook.setApexReached(true);
        }

        Rectangle intersection;
        if (!hook.getApexReached()) {
            if ((intersection = checkIfWallCollision(hook, tileList)) != null) {

                hook.setApexReached(true);
                if (checkXCollision(intersection))
                    hook.setHasGrip(true);
                Tile tile = getCurrentTile(hook.getPosition());
                if (tile.getRect().contains(hook.getRect()))
                    hook.setHasGrip(false);

            }
            else if ((intersection = checkIfEntityCollision(hook)) != null) {
                 hook.setApexReached(true);


                }
        }
    }

    /**
     * Method to check if collision is horizontal and handles it
     * @param character entity to be tested
     * @param intersection intersection between tile and entity
     */
    private void horizontalCollision(Character character, Rectangle intersection) {
        if (checkXCollision(intersection)) {
            int val;
            Rectangle rect = new Rectangle(character.getRect());

            character.setXVelocity(0);
            character.setXAcceleration(0);

            // Value to set as position.x
            val = (rect.x == intersection.x) ?
                    (int)(intersection.x+intersection.width) : (int)(intersection.x-rect.width);
            character.setXPosition(val);
            // If character is an enemy, it should change directions.
            checkChangeDirection(character);
        }
    }

    /**
     * Method to check if collision is vertical and handles it
     * @param character entity to be tested
     * @param intersection intersection between tile and entity
     */
    private void verticalCollision(Character character, Rectangle intersection) {
        if (checkYCollision(intersection)) {
            int val;
            Rectangle rect = new Rectangle(character.getRect());

            character.setYVelocity(0);

            // Value to set as position.y
            val = (rect.y + rect.height == intersection.y + intersection.height) ?
                    (int)(intersection.y-rect.height) : (int)(intersection.y+intersection.height);
            if (!(rect.y + rect.height == intersection.y + intersection.height))
                character.setGrounded(true);
            character.setYPosition(val);
        }
    }

    /**
     * Method to check if collision is horizontal as well as vertical, and handle it
     * @param character entity to be tested
     * @param intersection intersection between tile and entity
     */
    private void edgeAdjacentCollision(Character character, Rectangle intersection) {
        if (checkBothCollisions(intersection)) {
            Vector2 newPosition = new Vector2();
            System.out.println("mweep");
        }
    }

    /**
     * Method to check if there is a collision between an entity and a wall.
     * If there is, return the intersecting rectangle.
     * @param entity entity to be checked
     * @param tiles tiles that surround the entity
     * @return the intersecting rectangle, if there is any
     */
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

    /**
     * Method to check collision between two entities.
     * If there is, return the intersecting rectangle.
     * @param entity entity to check with all entities on map
     * @return the intersecting rectangle, if there is any
     */
    private Rectangle checkIfEntityCollision(Entity entity) {
        tileList = collisionBoxes(entity);
        Rectangle intersection = new Rectangle();
        for (Entity mapEntity : gameMap.getEntities()) {
            if (Intersector.intersectRectangles(entity.getRect(), mapEntity.getRect(), intersection)) {
                if (entity instanceof Hook && mapEntity instanceof Enemy) {
                    ((Enemy) mapEntity).takeDamage(1);
                    return intersection;
                }
                if (entity instanceof Player && mapEntity instanceof Enemy) {
                    enemyInteraction(intersection, (Player) entity, (Enemy) mapEntity);
                }
                if ((entity instanceof Hook || entity instanceof Player) && mapEntity instanceof Button) {
                    Button button = (Button) mapEntity;
                    if (!button.getActive()) {
                        button.activate();
                    }
                }
                if(entity instanceof Hook && mapEntity instanceof Door){
                    if(((Door)mapEntity).getSolid()){
                       return intersection;
                    }
                }
                if(entity instanceof Player && mapEntity instanceof Door){
                    if(!((Door) mapEntity).getState()){
                        horizontalCollision((Character) entity, intersection);
                    }
                }
                if(entity instanceof Player && mapEntity instanceof Spike){
                    if(((Player) entity).getIsAlive())
                    ((Spike) mapEntity).death((Player) entity);
                }
                if(entity instanceof Player && mapEntity instanceof Goal) {
                    gameMap.setFinished();
                }
            }
        }
        return null;
    }

    /**
     * Handles collision between enemy and player.
     * @param intersection rectangle that intersects player and enemy.
     * @param player player that is colliding.
     * @param enemy enemy that is colliding.
     */
    private void enemyInteraction(Rectangle intersection, Player player, Enemy enemy) {
        if (player.getInvincible())
            return;

        if (checkXCollision(intersection)) {
            player.takeDamage(1);
            player.setXVelocity(-5*(int)player.getDirection().x);
            player.setXAcceleration(50);
            player.jump();
            player.setYVelocity(3);
        }
        else if (player.getRect().y > (enemy.getRect().y)) {
            player.setGrounded(true);
            player.jump();
            enemy.takeDamage(1);
        }
    }
    /**
     * Method to check and correct if entity is moving outside map border
     * @param entity entity to be checked
     * @return value that is used to cancel any further handling this frame.
     */
    private boolean checkIfOutside(Entity entity) {
        boolean ret = false;
        if (entity.getPosition().x < 0) {
            entity.setXPosition(0);
            ret = true;
        }

        if (entity.getPosition().x+entity.getWidth() > tileMap.getWidthPixels()) {
            entity.setXPosition(tileMap.getWidthPixels()-entity.getWidth());
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
     * @param character entity to be tested
     * @return value that determines if collision should be checked after method has checked cases.
     */
    private boolean checkBeforeCollision(Character character) {
        Vector2 position = new Vector2(character.getPosition());
        int height = character.getHeight();
        int width = character.getWidth();

        // Get tile that the player is currently in.
        Tile tile = getCurrentTile(new Vector2(position.x+width/2, position.y));//tileMap.getTile((int)((position.x+width/2)/tileSize), (int)(position.y)/tileSize);

        // Check if entity is falling to stop a movement bug.
        if (character.getVelocity().y < 0) {
            if (tile.getState()) {
                if (character.getRect().x < tile.getRect().x+tile.getRect().width/2)
                    character.setYPosition((int)(tile.getRect().y + tile.getRect().height));
                    character.setGrounded(true);
                    return true;
            }
        }

        // Check if entity is rising/jumping to stop a movement bug.
        else if (character.getVelocity().y != 0 && character.getDirection().y > 0) {
            tile = getCurrentTile(new Vector2(position.x+width/2, position.y+height+1));//tileMap.getTile((int)((position.x+width/2)/tileSize), (int)(position.y+height+1)/tileSize);
            // Check if entity is rising to stop a movement bug.
            if (tile.getState()) {
                character.setYPosition((int)tile.getRect().y-height);
                character.setDirectionY(-1);
                character.setYVelocity(0);
            }
        }
        return false;
    }

    /**
     * Check if tiles that are below the player are solid.
     * @param character character that is tested.
     * @return return whether the tile is solid or not.
     */
    private boolean checkIfFalling(Character character) {
        Tile tile1 = getCurrentTile(new Vector2(character.getRect().x, character.getRect().y-1));
        Tile tile2 = getCurrentTile(new Vector2(character.getRect().x + character.getRect().width-1, character.getRect().y-1));
        Tile tile3 = getCurrentTile(new Vector2(character.getRect().x + character.getRect().width/2, character.getRect().y-1));
        if (tile1 != null && tile1.getState() ||
            tile2 != null && tile2.getState() ||
            tile3 != null && tile3.getState()) {
            return false;
        }

        return true;
    }


    /**
     * Method that changes an enemy's direction.
     * @param entity enemy whose direction should change
     */
    private void checkChangeDirection(Entity entity) {
        if (entity instanceof Enemy) {
            ((Enemy) entity).changeLookingDirectionX();
        }
    }

    /**
     * Checks if a collision is vertical depending on intersection
     * @param intersection rectangle that forms between two colliding rectangles.
     * @return if intersection width is greater than height
     */
    private boolean checkYCollision(Rectangle intersection) {
        return (intersection.width > intersection.height);
    }
    /**
     * Checks if a collision is horizontal depending on intersection
     * @param intersection rectangle that forms between two colliding rectangles.
     * @return if intersection height is greater than width
     */
    private boolean checkXCollision(Rectangle intersection) {
        return (intersection.height > intersection.width);
    }
    /**
     * Checks if a collision is both horizontal and vertical depending on intersection
     * @param intersection rectangle that forms between two colliding rectangles.
     * @return if intersection height is equal to width
     */
    private boolean checkBothCollisions(Rectangle intersection) {
        return (intersection.height == intersection.width);
    }

    /**
     * Gets current tile from position.
     * @param position Vector2 containing x and y positions.
     * @return a tile from the map.
     */
    private Tile getCurrentTile(Vector2 position) {
        return tileMap.getTile((int)position.x/tileSize, (int)position.y/tileSize);
    }
}
