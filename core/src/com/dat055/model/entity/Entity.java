package com.dat055.model.entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.Observable;

/**
 * General purpose class for entity-based objects that
 * has all the properties that any entity would need.
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
    protected ObjectMap<String, Sound> soundBank;

    /**
     * Initiates an entity with a texturePath, used when an entity should have a sprite.
     * @param position Position of the lower left rectangle of the entity.
     * @param height Height of the entity.
     * @param width Width of the entity.
     * @param texturePath Path to a texture.
     */

    protected Entity(Vector2 position, int height, int width, String texturePath) {
        this(position, height, width);
        this.setTexture(texturePath);
        this.texturePath = texturePath;
    }

    /**
     * Used with the other constructor or alone when entity doesn't need to have a sprite.
     * Sets the rectangle and initiates sound effects.
     * @param position Position of the lower left rectangle of the entity.
     * @param height Height of the entity.
     * @param width Width of the entity.
     */
    protected Entity(Vector2 position, int height, int width) {
        this.height = height;
        this.width = width;
        this.position = position;
        setRectangle();
        initSounds();
        BOUNDING_BOX_COLOR = Color.CYAN;
    }

    //TODO: Move this to Model
    /**
     * Loads a sound file to the sound bank.
     * @param file filename of sound
     * @return Sound object with the sound effect.
     */
    protected Sound loadFile(String file) {
        return Gdx.audio.newSound(Gdx.files.internal("audio/sfx/" + file));
    }

    /**
     * Initializes the sound bank ObjectMap.
     */
    protected void initSounds() {
        soundBank = new ObjectMap<String, Sound>();
        soundBank.put("jump", loadFile("jump.mp3"));
    }

    /**
     * Plays a specific sound.
     * @param sound the filename
     */
    protected void playSound(String sound) {
            soundBank.get(sound).play();
    }

    /**
     * Method that updates the entity.
     * @param deltaTime time since last frame.
     */
    public void update(float deltaTime) { }

    /**
     * Mehod that is used to draw entitys without care for any offset.
     * @param sb Spritebatch that is being used.
     * @param rotation how rotated the entity is.
     */
    public void draw(PolygonSpriteBatch sb, float rotation) {
        draw(sb, rotation, Vector2.Zero);
    }

    /**
     * Method that draws an entity. Rotates the sprites correctly.
     * @param batch Spritebatch that is being used.
     * @param rotation how rotated the entity is.
     * @param offset Offset from center of map.
     */
    public void draw(PolygonSpriteBatch batch, float rotation, Vector2 offset) {
        sprite.setFlip(rotation > 90  && rotation < 270, false); // Rotates sprite correctly to plane
        batch.draw(sprite, position.x+offset.x, position.y+offset.y, (float)width/2, -position.y,
                sprite.getWidth(), sprite.getHeight(), sprite.getScaleX(), 1, rotation);
    }

    /**
     * Method that draws an entity's rectangle.
     * @param renderer renderer that draws the rectangle.
     */
    public void drawBoundingBox(ShapeRenderer renderer) {
        drawRectangle(rect, ShapeType.Line, renderer);
    }

    /**
     * Helper method for drawing a rectangle
     * @param renderer will render the rectangle
     */
    protected void drawRectangle(Rectangle rect, ShapeType type, ShapeRenderer renderer) {
        renderer.begin(type);
        renderer.setColor(BOUNDING_BOX_COLOR);
        renderer.rect(rect.x, rect.y, rect.width, rect.height);
        renderer.end();
    }

    /**
     * Sets the entity sprite.
     * @param texturePath path to texture.
     */
    public void setTexture(String texturePath) {
        sprite = new Sprite(new Texture(texturePath));
    }

    /**
     * Sets the entity's rectangle depending on width, height and position.
     */
    protected void setRectangle() {
        rect = new Rectangle();
        rect.setSize(width, height);
        rect.setPosition(position.x, position.y);
    }

    /**
     * Sets the entity's and its rectangle's x-position.
     * @param x value to be set.
     */
    public void setXPosition(int x) { position.x = x; rect.x = position.x; }

    /**
     * Sets the entity's and its rectangle's y-position.
     * @param y value to be set.
     */
    public void setYPosition(int y) { position.y = y; rect.y = position.y; }

    /**
     * Sets the entity's position.
     * @param position position to be set.
     */
    public void setPosition(Vector2 position) { this.position = position; }

    /**
     * Get the entity's position.
     * @return position of the entity.
     */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * Get the entity's rectangle.
     * @return rect of the entity.
     */
    public Rectangle getRect() { return rect; }

    /**
     * Get the entity's width.
     * @return width of the entity.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the entity's height.
     * @return height of the entity.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Get the entity's rectangle box color.
     * @return BOUND_BOX_COLOR of the entity.
     */
    public Color getColor() { return BOUNDING_BOX_COLOR; }

    /**
     * Method that returns the entity's variables. Used for debugging.
     * @return the string containing all data.
     */
    @Override
    public String toString() {
        return String.format("(h, w): (%d, %d),\npos: (%.1f, %.1f), \n" +
                        "rect.(x, y): (%.1f, %.1f), \nrect.(h, w): (%.1f, %.1f)\n",
                height, width, position.x, position.y, rect.x, rect.y, rect.height, rect.width);
    }
}
