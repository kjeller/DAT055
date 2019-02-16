package com.dat055.model.map.tile;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.utils.JsonValue.JsonIterator;


public class TileMapFactory {
    TextureAtlas atlas;

    public TileMapFactory(TextureAtlas atlas) {
        this.atlas = atlas;
    }

    /**
     * Creates a tilemap from tiles from a predefined json file
     */
    public TileMap getTileMap(Integer tileSize, Integer width, Integer height, JsonIterator data) {
        TileMap map = new TileMap(width, height);
        int x = 0, y = height-1;

        // Read data from json file
        while(data.hasNext()) {
            String current = data.next().asString(); // Convert JsonValue to integer

            // Since (0,0) is in the lower left corner, we have to
            // adjust where to place the tile.
            if(x >= width) {
                y--;
                x=0;
            }
            Tile temp = new Tile(x * tileSize, y *tileSize, tileSize);  // For airtiles/non-tiles

            // If not airtiles/non-tiles give them sprites from spritesheet
            if(!current.equals("0")) {
                Sprite sprite = getSprite(current); // Get current data value

                // Set sprite as debug if it cannot be found in spritesheet
                if(sprite == null)
                    sprite = getSprite("debug");
                temp = new TexturedTile(x*tileSize, y * tileSize, tileSize, sprite);
            }
            map.addTile( x++,  y, temp);
        }
        // Debug: System.out.println(map);
        return map;
    }

    /**
     * Searches through a texture atlas to find a specfic region.
     * Returns that region as a sprite.
     * @param region
     * @return
     */
    public Sprite getSprite(String region) {
        TextureRegion r = atlas.findRegion(region);
        if(r == null)
            return null;
        return new Sprite(r);
    }
}
