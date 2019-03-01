package com.dat055.model.entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.Observable;

/**
 * Author: Tobias Campbell
 * Version: 22-02-2019
 */
public abstract class Entity extends Observable {
    protected Color BOUNDING_BOX_COLOR;
    protected int height;
    public int width;
    protected Vector2 position;
    protected Sprite sprite;
    protected String texturePath;
    protected Rectangle rect;
    private ObjectMap<String, Sound> soundBank;

    protected Entity(Vector2 position, int height, int width, String texturePath) {
        this(position, height, width);
        this.setTexture(texturePath);
        this.texturePath = texturePath;
    }
    protected Entity(Vector2 position, int height, int width) {
        this.height = height;
        this.width = width;
        this.position = position;
        setRectangle();
        initSounds();
        BOUNDING_BOX_COLOR = Color.CYAN;
    }

    //TODO: Move this to Model
    private Sound loadFile(String file) {
        return Gdx.audio.newSound(Gdx.files.internal("audio/sfx/" + file));
    }

    private void initSounds() {
        soundBank = new ObjectMap();
        soundBank.put("takedamage", loadFile("oof.mp3"));
        soundBank.put("jump", loadFile("jump.wav"));
    }

    /**
     * Plays a specific sound.
     * @param sound the filename
     */
    protected void playSound(String sound) {
        soundBank.get(sound).play();
    }

    /**
     * Generic methods for updating an entity
     */
    public void update(float deltaTime) {
        // TODO: Update entity stuff
    }

    /**
     * Generic method for drawing an entity
     */
    public void draw(PolygonSpriteBatch sb, float rotation) {
        draw(sb, rotation, Vector2.Zero);
    }

    public void draw(PolygonSpriteBatch batch, float rotation, Vector2 offset) {
        sprite.setFlip(rotation > 90  && rotation < 270, false); // Rotates sprite correctly to plane

        batch.draw(sprite, position.x+offset.x, position.y+offset.y, (float)width/2, -position.y,
                sprite.getWidth(), sprite.getHeight(), 1,1, rotation);
    }

    public void drawBoundingBox(ShapeRenderer renderer) {
        drawRectangle(rect, renderer);
    }

    /**
     * Helper method for drawing a rectangle
     * @param renderer will render the rectangle
     */
    protected void drawRectangle(Rectangle rect, ShapeRenderer renderer) {
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(BOUNDING_BOX_COLOR);
        renderer.rect(rect.x, rect.y, rect.width, rect.height);
        renderer.end();
    }

    public void setTexture(String texturePath) {
        sprite = new Sprite(new Texture(texturePath));
    }
    protected void setRectangle() {
        rect = new Rectangle();
        rect.setSize(width, height);
        rect.setPosition(position.x, position.y);
    }
    public void setXPosition(int x) { position.x = x; rect.x = position.x; }
    public void setYPosition(int y) { position.y = y; rect.y = position.y; }
    public void setPosition(Vector2 position) { this.position = position; }
    public Vector2 getPosition() {
        return position;
    }
    public Rectangle getRect() { return rect; }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public Color getColor() { return BOUNDING_BOX_COLOR; }

    @Override
    public String toString() {
        return String.format("(h, w): (%d, %d),\npos: (%.1f, %.1f), \n" +
                        "rect.(x, y): (%.1f, %.1f), \nrect.(h, w): (%.1f, %.1f)\n",
                height, width, position.x, position.y, rect.x, rect.y, rect.height, rect.width);
    }
}
