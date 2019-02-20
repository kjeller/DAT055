package com.dat055.model.map.tile;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.StringBuilder;

public class TileMap {
    private Tile[][] map;
    private int width, height;

    public TileMap(int width, int height) {
        this.width = width;
        this.height = height;
        map = new Tile[width][height];
    }

    public void render(PolygonSpriteBatch batch, float rotation) {
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                if(map[i][j] != null)
                    map[i][j].draw(batch, rotation);
            }
        }
    }

    public void addTile(int x, int y, Tile tile) {
        if(x >= 0 && x < width && y >= 0 && y < height && tile != null){
            map[x][y] = tile;
        }
    }

    /**
     * Returns tile at given coord in tilemap
     * @param x
     * @param y
     * @return tile at given (x, y)
     */
    public Tile getTile(int x, int y) {
        if(x >= 0 && x < width && y >= 0 && y < height)
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
