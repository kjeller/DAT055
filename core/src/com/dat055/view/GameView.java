package com.dat055.view;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.*;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.dat055.controller.GameController.Mode;
import com.dat055.model.GameModel;
import com.dat055.model.map.GameMap;

/**
 * Renders everything that is used in game.
 * @author Karl Strålman
 * @version 2019-02-21
 */
public class GameView extends View{
    private ShapeRenderer renderer;
    private Mode mode;
    private float rotation = 0;

    private boolean debug = false;
    private boolean singleMap;
    private boolean showMapName;
    private String debugString;

    /**
     * Default constructor for this view
     * @param gameModel model where every game asset is loaded in
     */
    public GameView(GameModel gameModel) {
        this.model = gameModel;
        renderer = new ShapeRenderer();
    }

    public void render(PolygonSpriteBatch batch) {
        GameMap map1 = ((GameModel)model).getGameMap1();
        GameMap map2 = ((GameModel)model).getGameMap2();
        BitmapFont mapFont = ((GameModel)model).getMapFont();
        Camera cam = ((GameModel)model).getCam();
        batch.setProjectionMatrix(cam.combined);    // Set camera for batch
        Vector2 camViewDist = ((GameModel)model).getCamViewDistance();

        map1.render(batch, rotation);

        // Render map2 if map2 exist
        if(!singleMap)
            map2.render(batch, rotation - 180);

        // Render map name
        if(showMapName) {
            String name = ((GameModel)model).getMapName();
            mapFont.setColor(Color.YELLOW);
            mapFont.draw(batch, name,
                    cam.position.x, cam.position.y);
        }

        // Debug
        if(debug) {
            batch.end();
            renderer.setProjectionMatrix(((GameModel)model).getCam().combined);

            // Map specific debug tec
            if(mode == Mode.FRONT) {
                map1.drawBoundingBoxes(renderer);
                batch.begin();
                map1.drawEntityText(((GameModel)model).getDebugFont(), batch);
            } else {
                map2.drawBoundingBoxes(renderer);
                batch.begin();
                map2.drawEntityText(((GameModel)model).getDebugFont(), batch);
            }

            //Debug text for whole map
            BitmapFont debugFont = ((GameModel)model).getDebugFont();
            debugFont.setColor(Color.WHITE);
            debugFont.draw(batch, debugString,
                    cam.position.x - camViewDist.x,
                    cam.position.y + camViewDist.y);
            batch.setProjectionMatrix(cam.combined);
        }
    }

    /**
     * @return gets rotation
     */
    public float getRotation() { return rotation; }

    /**
     * Sets string that will be printed
     * @param debugString string that will be printed
     */
    public void setDebugString(String debugString) { this.debugString = debugString; }

    /**
     * Sets rotation for this game
     * @param rotation value
     */
    public void setRotation(float rotation) { this.rotation = rotation;}

    /**
     * If debug is true debugstring will be printed
     * @param debug value
     */
    public void setDebug(boolean debug) { this.debug = debug; }

    /**
     * Sets mode, will be used to know which map should be rotated
     * @param mode FRONT or BACK
     */
    public void setMode(Mode mode) { this.mode = mode; }

    /**
     * True if only map1 should be rendered
     * @param singleMap value
     */
    public void setSingleMap(boolean singleMap) { this.singleMap = singleMap; }

    /**
     * True if view should render name of map.
     * @param showMapName value
     */
    public void setShowMapName(boolean showMapName) { this.showMapName = showMapName;}
}
