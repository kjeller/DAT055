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
            int current = data.next().asInt();
            System.out.println(current);
            Tile temp = null;
            switch (current) {
                case 0 :
                    temp = new GroundTile(x * tileSize, y * tileSize);
                    temp.setTexture(debug);
                    break;
                case 1: temp = new GroundTile(x * tileSize, y * tileSize);
                    temp.setTexture(debug2);
                    break;
            }
            x++;
            if(width-1%x == 0)
                y--;
            map.addTile( x % width,  y, temp);
        }
        System.out.println(map);
        return map;
    }
}
