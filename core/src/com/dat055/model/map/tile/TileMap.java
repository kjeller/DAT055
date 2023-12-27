package com.dat055.model.map.tile;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.utils.StringBuilder;

/**
 * A map of {@link Tile}.
 * This class is responsible for drawing all the tiles
 * that are drawable and adding tiles.
 * @author Karl Strålman
 * @version 2019-02-21
 */
public class TileMap {
    private Tile[][] map;
    private int width, height; // n of tiles width/height

    public TileMap(int width, int height) {
        this.width = width;
        this.height = height;
        map = new Tile[width][height];
    }

    /**
     * Renders every tile in array
     * @param batch used to render
     * @param rotation rotates tiles
     */
    public void render(PolygonSpriteBatch batch, float rotation) {
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                if(map[i][j] != null)
                    map[i][j].draw(batch, rotation);
            }
        }
    }

    /**
     * Adds tile to array
     * @param x coord x
     * @param y coord y
     * @param tile that will be added
     */
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

    /**
     * @return all the tiles' positions
     */
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

    /**
     * @return width of this tile in pixels.
     */
    public int getWidthPixels() { return width*64; }

    /**
     * @return height of this tile in pixels.
     */
    public int getHeightPixels() { return height*64; }

    /**
     * @return width of this tile.
     */
    public int getWidth() { return width;}

    /**
     * @return height of this tile.
     */
    public int getHeight() { return height;}

}
