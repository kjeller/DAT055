package com.dat055.model.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.dat055.model.entity.Entity;
import com.dat055.model.entity.character.Enemy;
import com.dat055.model.entity.character.Player;
import com.dat055.model.entity.interactables.Button;
import com.dat055.model.entity.interactables.Door;
import com.dat055.model.entity.interactables.Goal;
import com.dat055.model.entity.interactables.Spike;
import com.dat055.model.map.tile.TileMap;
import com.dat055.model.map.tile.TileMapFactory;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Responisble for reading from a json file and creating {@link GameMap}
 * filling them with entities. Maps are returned with an iterator.
 * @author Karl Strålman
 * @version 2019-02-18
 */
public class GameMapFactory {
    private final int TILESIZE = 64;
    private final String MAP_PROPERTIES = "properties";
    private final String MAP_NAME = "map";
    private final String MAP_SPRITE = "sprite";
    private final String MAP_DESC = "desc";
    private final String MAP_ENTITIES = "entities";
    private final String MAP_PLAYER = "player";
    private ArrayList<Entity> interactables = new ArrayList<Entity>();

    private Iterator<GameMap> iterator;
    private String nextMap;

    public GameMapFactory(String fileName) {
        initialize(fileName);
    }

    /**
     * Creates and returns a GameMap
     * @return a gamemap with a set of tilemaps
     */
    public GameMap getMap() {
        return iterator.next();
    }

    /**
     * Reads json file and creates two TileMaps: one front and one back
     * @param fileName
     * @return true if file read successfully
     */
    public boolean initialize(String fileName) {
        JsonValue root;
        ArrayList<GameMap> mapList = new ArrayList<GameMap>();
        try{
            JsonReader reader = new JsonReader();
            root = reader.parse(Gdx.files.internal(fileName));
        } catch (Exception x) { System.out.println("Error: Map file could not be read."); return false;}

        // Search for next map in JSON
        try {
            nextMap = root.get(MAP_PROPERTIES).getString("nextmap"); // Sets next map
        } catch (Exception x) { System.out.println(x);}

        // Reads every and creates a gamemap for every map
        for(JsonValue jsonMap : root.get(MAP_NAME)) {
        GameMap map = getGameMap(jsonMap, getTextureAtlas(jsonMap));
        mapList.add(map);
        }
        Button entityButton;
        Door entityDoor;
        for (Entity entity : interactables) {

            //Checks all buttons in entities
            if (entity instanceof Button) {
                entityButton = (Button) entity;
                for (Entity entity2 : interactables) {
                    //Checks all doors in entities
                    if (entity2 instanceof Door) {
                        entityDoor = (Door) entity2;
                        //If Doors ID is in buttons target, make door observer for button
                        if ((entityDoor.getId()).equals(entityButton.getTarget())) {
                            entityButton.addObserver(entityDoor);
                        }
                    }
                }
            }
        }
        iterator = mapList.iterator();
        return true;
    }

    /**
     * Returns a texture atlas for a specific map
     * @param map
     */
    private TextureAtlas getTextureAtlas(JsonValue map) {
        String filePath = map.get(MAP_PROPERTIES).getString(MAP_SPRITE);
        try {
            if(filePath == null || filePath.isEmpty()) {
                throw new Exception("Error: Spritesheet could not be found.");
            }
        } catch (Exception e) {
            System.out.println(e);
            filePath = "textures/blue_penguin_64x80.png"; // debug texture
        }
        return new TextureAtlas(Gdx.files.internal(filePath));
    }

    /**
     * Use helper methods to create a gamemap with all entities for that map
     * @param map
     * @param atlas
     * @return {@link GameMap} read from JSON map
     */
    private GameMap getGameMap(JsonValue map, TextureAtlas atlas) {
        Player player = null;
            try {
                player = findPlayer(map);
                if(player == null)
                    throw new Exception("Error: Could not find player in map.");
            } catch (Exception e) { System.out.println(e);
            }
        return new GameMap(jsonToTileMap(map, atlas),
                getEntities(map, player), player, map.get(MAP_PROPERTIES).getString(MAP_DESC), map.get(MAP_PROPERTIES).getInt("width"));
    }

    /**
     * Searches through entity list in a map and returns player created from properties
     * @param map json
     * @return player object tied to map
     */
    private Player findPlayer(JsonValue map) {
        // iterator with all entities for one map
        Iterator<JsonValue> entities = map.get(MAP_ENTITIES).child.iterator();
        JsonValue player = null;
        boolean playerFound = false;

        // Search through entities
        while(entities.hasNext() && !playerFound) {
            JsonValue entity = entities.next();

            // If player entity is found
            if(entity.name.equals(MAP_PLAYER)) {
                playerFound = true;
                player = entity;
            }
        }
        if(player == null)
            return null;
        Vector2 start;
        JsonValue position = player.get("position");
        start = new Vector2(position.getInt(0) * TILESIZE,
                position.getInt(1) * TILESIZE);
        return new Player(start, player.getString(MAP_SPRITE), MAP_PLAYER );
    }

    /**
     * Creates a {@link Goal} read from map properties
     * @param map The current map
     * @return returns {@link Goal}
     */
    private Goal findGoal(JsonValue map) {
        int x, y;
        String texture = null;
        x = y = -1;
        try{
            JsonValue pos = map.get(MAP_PROPERTIES).get("finish").get("position");
            texture = map.get(MAP_PROPERTIES).get("finish").getString("sprite");
            x = pos.getInt(0);
            y = pos.getInt(1);
        } catch (Exception ignored) {}

        if(x >= 0 && y >= 0) {
            return new Goal(new Vector2(x* TILESIZE,y *TILESIZE), texture);
        }
        return new Goal(Vector2.Zero, null);
    }

    /**
     * Reads the map and calls the tilefactory
     * @param map {@link JsonValue} object that contains map data
     * @return A {@link TileMap} created by the tilefactory
     */
    private TileMap jsonToTileMap(JsonValue map, TextureAtlas atlas) {
        TileMapFactory tileMapFactory = new TileMapFactory(atlas);
        JsonValue properties = map.get(MAP_PROPERTIES);
        return tileMapFactory.getTileMap(TILESIZE, properties.getInt("width"),
                properties.getInt("height"), map.get("data").iterator());
    }


    /**
     * Reads entity array from Json map file and creates the entities
     * @param map {@link JsonValue} which contains the entities
     * @return ArrayList of {@link Entity} in map
     */
    private ArrayList<Entity> getEntities(JsonValue map, Player player) {
        ArrayList<Entity> entities = new ArrayList<Entity>();

        // Get array of entities
        Iterator<JsonValue> entitiesJson = map.get(MAP_ENTITIES).iterator();

        // Loop through every entity in array
        while(entitiesJson.hasNext()) {
            JsonValue current = entitiesJson.next().child;
            if(current != null) {
                Entity entity = null;
                JsonValue position = current.get("position");
                Vector2 start = new Vector2(position.getInt(0) * TILESIZE,
                        position.getInt(1) * TILESIZE);

                if(current.name.equals("enemy")) {
                    //Todo: add different enemy types to make it easier for map boi
                    entity = new Enemy(start, current.getString("sprite"), "enemy");
                }
                else if(current.name.equals("door")) {
                    //TODO: Door goes here
                    entity = new Door(start, 128, 64, current.getString("sprite"), current.getString("id"));
                    JsonValue required = current.get("rqs");
                    Door door = (Door)entity;
                    for(JsonValue req : required){
                        door.addRequired(req.asString());
                    }
                }
                else if(current.name.equals("button")) {
                    JsonValue button = current.child;
                    entity = new Button(start, 64, 64, current.getString("sprite"),
                                        current.getString("id"), current.getString("target"),
                                        current.getInt("timer"));

                    }
                 else if(current.name.equals("spike")){
                     JsonValue spike = current.child;
                     entity = new Spike(start, 32, 64, current.getString("sprite"));
                }
                if(entity != null)
                    entities.add(entity);
            }
        }

        for(Entity entity : entities){
            if(entity instanceof Door || entity instanceof Button)
                    interactables.add(entity);
            }

        entities.add(player); // Adds player to list
        entities.add(findGoal(map));
        return entities;
    }

    /**
     * Returns next map filepath
     * @return
     */
    public String getNextMap() { return nextMap; }
}
