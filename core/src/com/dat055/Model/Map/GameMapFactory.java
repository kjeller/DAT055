package com.dat055.Model.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.dat055.Model.Entity.Enemy;
import com.dat055.Model.Entity.Entity;
import com.dat055.Model.Entity.Player;
import com.dat055.Model.Map.Tile.TileMap;
import com.dat055.Model.Map.Tile.TileMapFactory;

import java.util.ArrayList;
import java.util.Iterator;

public class GameMapFactory {
    private final int TILESIZE = 64;
    private final String MAP_PROPERTIES = "properties";
    private final String MAP_NAME = "map";
    private final String MAP_SPRITE = "sprite";
    private final String MAP_DESC = "desc";
    private final String MAP_ENTITIES = "entities";
    private final String MAP_PLAYER = "player";

    private Iterator<GameMap> iterator;

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

        for(JsonValue jsonMap : root.get(MAP_NAME)) {
        GameMap map = getGameMap(jsonMap, getTextureAtlas(jsonMap));
        mapList.add(map);
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

    private GameMap getGameMap(JsonValue map, TextureAtlas atlas) {
        Player player = null;
            try {
                player = findPlayer(map);
                if(player == null)
                    throw new Exception("Error: Could not find player in map.");
            } catch (Exception e) { System.out.println(e);
            }
        return new GameMap(jsonToTileMap(map, atlas),
                getEntities(map, player), player, map.get(MAP_PROPERTIES).getString(MAP_DESC));
    }

    /**
     * Searches through entity list in a map and returns player created from properties
     * @param map json
     * @return player object tied to map
     */
    private Player findPlayer(JsonValue map) {
        Iterator<JsonValue> entities = map.get(MAP_ENTITIES).child.iterator();
        JsonValue player = null;
        boolean playerFound = false;
        while(entities.hasNext() && !playerFound) {
            JsonValue entity = entities.next();
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
     * Reads the map and calls the tilefactory
     * @param map JSON object that contains map data
     * @return A tilemap created by the tilefactory
     */
    private TileMap jsonToTileMap(JsonValue map, TextureAtlas atlas) {
        TileMapFactory tileMapFactory = new TileMapFactory(atlas);
        JsonValue properties = map.get(MAP_PROPERTIES);
        return tileMapFactory.getTileMap(TILESIZE, properties.getInt("width"),
                properties.getInt("height"), map.get("data").iterator());
    }


    /**
     * Reads entity array from Json map file and creates the entities
     * @param map which contains the entities
     * @return ArrayList of these entities
     */
    private ArrayList<Entity> getEntities(JsonValue map, Player player) {
        ArrayList<Entity> entities = new ArrayList<Entity>();
        entities.add(player); // Adds player to list
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
                    //entity = new Enemy(start, current.getString("sprite"), "enemy" );
                }
                else if(current.name.equals("door")) {
                    //TODO: Door goes here
                }

                if(entity != null)
                    entities.add(entity);
            }
        }
        return entities;
    }
}
