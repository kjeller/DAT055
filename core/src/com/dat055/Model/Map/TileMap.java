package com.dat055.Model.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.StringBuilder;
import com.dat055.Model.Map.Tile.Tile;

import java.util.Arrays;
import java.util.Iterator;

public class TileMap {
    private Tile[][] map;
    Integer width, height;

    public TileMap(Integer width, Integer height) {
        this.width = width;
        this.height = height;
        map = new Tile[width][height];
    }

    public void draw(SpriteBatch batch) {
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                if(map[i][j] != null)
                    map[i][j].draw(batch);
            }
        }
    }

    public void addTile(Integer x, Integer y, Tile tile) {
        if(x >= 0 && x < width && y >= 0 && y < height && tile != null){
            map[x][y] = tile;
        }
    }

    /**
     * Returns Tile at given coord in tilemap
     * @param x
     * @param y
     * @return Tile at given (x, y)
     */
    public Tile getTile(Integer x, Integer y) {
        if(x >= 0 && x <= width && y >= 0 && y <= height)
            return map[x][y];
        return null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                if(map[i][j] != null)
                    sb.append(String.format("InArray (%d, %d), %s", i, j, map[i][j].toString()));
            }
        }
        return sb.toString();
    }
    public int getWidthPixels() { return width*64; }
    public int getHeightPixels() { return height*64; }
    public int getWidth() { return width;}
    public int getHeight() { return height;}

}
