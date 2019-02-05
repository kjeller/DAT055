package com.dat055.Model.Map.Tile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.JsonValue.JsonIterator;
import com.dat055.Model.Map.TileMap;

public class TileFactory {
    Texture debug = new Texture("textures/debug_texture2.png");
    Texture debug2 = new Texture("textures/debug_texture.png");

    /**
     * Creates a tilemap from tiles
     */
    public TileMap getTileMap(Integer tileSize, Integer width, Integer height, JsonIterator data) {
        TileMap map = new TileMap(width, height);
        int x = 0, y = height-1;
        while(data.hasNext()) {
            int current = data.next().asInt(); // Convert JsonValue to integer

            // Since (0,0) is in the lower left corner, we have to
            // adjust where to place the tile.
            if(x >= width) {
                y--;
                x=0;
            }
            Tile temp = new Tile(x * tileSize, y *tileSize); // For airtiles
            if(current != 0)
                temp = new TexturedTile(x * tileSize, y * tileSize);
            switch(current) {
                case 1 :
                    //TODO: implement a texture pack
                    ((TexturedTile) temp).setTexture(debug); // This will be selected from a texture pack
                    break;
                case 2 : break;
                case 3 : break;
                case 4 : break;
            }

            System.out.printf("current:%d, x:%d, y:%d \n", current, x, y);
            map.addTile( x++,  y, temp);
        }
        System.out.println(map);
        return map;
    }
}
