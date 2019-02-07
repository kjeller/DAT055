package com.dat055.Model.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.dat055.Model.Map.Tile.TileFactory;

public class GameMapFactory {
    private String fileName;
    private GameMap map;
    private TextureAtlas atlas;
    private Integer tileSize;

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
        readToMap();
        return map;
    }

    /**
     * Reads json file and creates two TileMaps: one front and one back
     * @param fileName name of JSON file
     */
    private void readToMap() {
        try{
            JsonReader reader = new JsonReader();
            JsonValue root = reader.parse(Gdx.files.internal(fileName));
            JsonValue properties = root.child;
            JsonValue mapJson0 = root.get("map_0");
            JsonValue mapJson1 = root.get("map_1");

            System.out.println(mapJson0);
            System.out.println(mapJson1);
            if(mapJson0 != null || mapJson1 != null) {
                setProperties(properties);

                map.front = jsonToMap(mapJson0);
                map.back = jsonToMap(mapJson1);
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
    private TileMap jsonToMap(JsonValue map) {
        TileFactory tileFactory = new TileFactory(atlas);
        return tileFactory.getTileMap(tileSize, map.getInt("width"), map.getInt("height"),
                map.get("data").iterator());
    }

    /**
     * Sets the properties of this gamemap defined in json file
     * @param properties json values with the properties
     */
    private void setProperties(JsonValue properties) {
        map.id = properties.getString("id");
        map.name = properties.getString("name");
        atlas = new TextureAtlas(Gdx.files.internal(properties.getString("spritesheet")));
    }

}
