package com.dat055.Model.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.dat055.Model.Entity.Entity;
import com.dat055.Model.Entity.Player;
import com.dat055.Model.Map.Tile.TileMap;
import com.dat055.Model.Map.Tile.TileMapFactory;

import java.util.ArrayList;
import java.util.Iterator;

public class GameMapFactory {
    private String fileName;
    private GameMap map;
    private TextureAtlas atlas; // texture atlas for map
    private int tileSize;
    private Player player1;
    private Player player2;
    ArrayList<Entity> entities;

    /**
     * Creates and returns a GameMap
     * @param fileName name of json file in assets/maps
     * @param tileSize size of every tile in game
     * @return a gamemap with a set of tilemaps
     */
    public GameMap getMap(String fileName, Integer tileSize) {
        this.fileName = fileName;
        this.tileSize = tileSize;
        map = new GameMap();
        entities = new ArrayList<Entity>();
        readToMap();
        return map;
    }

    /**
     * Reads json file and creates two TileMaps: one front and one back
     */
    private void readToMap() {
        try{
            JsonReader reader = new JsonReader();
            JsonValue root = reader.parse(Gdx.files.internal(fileName));
            JsonValue properties = root.child;
            JsonValue mapJson1 = root.get("map_1");
            JsonValue mapJson2 = root.get("map_2");

            if(mapJson1 != null || mapJson2 != null) {
                setProperties(properties);

                map.front = jsonToTileMap(mapJson1);
                map.back = jsonToTileMap(mapJson2);
                map.entities = getEntities(mapJson1);
                map.entities = getEntities(mapJson2);

                // getEntities sets the players. it is ugly I know
                map.player1 = player1;
                map.player2 = player2;
            }
        } catch (Exception x) {
            System.out.println(x);
        }
    }

    /**
     * Reads the map and calls the tilefactory
     * @param map JSON object that contains map data
     * @return A tilemap created by the tilefactory
     */
    private TileMap jsonToTileMap(JsonValue map) {
        TileMapFactory tileMapFactory = new TileMapFactory(atlas);
        JsonValue properties = map.get("properties");
        return tileMapFactory.getTileMap(tileSize, properties.getInt("width"), properties.getInt("height"),
                map.get("data").iterator());
    }

    // TODO: Very ugly code benath. Need to clean this up someday.

    /**
     * Reads entity array from Json map file and creates the entities
     * @param map which contains the entities
     * @return ArrayList of these entities
     */
    private ArrayList<Entity> getEntities(JsonValue map) {

        // Get array of entities
        Iterator<JsonValue> entitiesJson = map.get("entities").child.iterator();

        // Loop through every entity in array
        while(entitiesJson.hasNext()) {
            JsonValue current = entitiesJson.next();
            if(current != null) {
                Entity entity = null;
                Vector2 start;
                JsonValue position = current.get("position");
                start = new Vector2(position.getInt(0) * tileSize,
                        position.getInt(1)*tileSize);

                if(current.name.equals("player1")) {
                    player1 = new Player(start, current.getString("sprite"), "player1" );
                    entity = player1;
                }
                else if(current.name.equals("player2")) {
                    player2 = new Player(start, current.getString("sprite"), "player2" );
                    entity = player2;
                }
                else if(current.name.equals("enemy")) {
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
    /**
     * Sets the properties of this gamemap defined in json file
     * @param properties json values with the properties
     */
    private void setProperties(JsonValue properties) {
        map.id = properties.getString("id");
        map.name = properties.getString("name");
        atlas = new TextureAtlas(Gdx.files.internal(properties.getString("sprite")));
    }

}
