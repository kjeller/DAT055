package com.dat055.Model.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.dat055.Model.Map.Tile.TileFactory;

public class GameMap {
    TileMap front, back;

    // Current map id and name
    private String id, name;
    TextureAtlas atlas;

    public GameMap(String fileName) {
        id = "Not set.";
        name = "Not set.";
        readToMap(fileName);
    }

    /**
     * Reads json file and creates two TileMaps: one front and one back
     * @param fileName name of JSON file
     */
    private void readToMap(String fileName) {
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

                this.front = jsonToMap(mapJson0);
                this.back = jsonToMap(mapJson1);
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
        return tileFactory.getTileMap(64, map.getInt("width"), map.getInt("height"),
                map.get("data").iterator());
    }

    /**
     * Sets the properties of this gamemap defined in json file
     * @param properties json values with the properties
     */
    private void setProperties(JsonValue properties) {
        this.id = properties.getString("id");
        this.name = properties.getString("name");
        this.atlas = new TextureAtlas(Gdx.files.internal(properties.getString("spritesheet")));
    }

    public void draw(SpriteBatch batch) {
        front.draw(batch);

        // TODO: Fix camera "angle" on back map somewhere
        //back.draw(batch);
    }

    public String toString() {
        return  String.format("Properties: id=%s, name=%s", this.id, this.name);
    }
}
