package com.dat055.Model.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {

    protected int id;
    protected int height;
    protected int width;
    protected Vector2 position;
    private Sprite sprite;
    protected Rectangle rect;

    public Entity(int id, int height, int width, String texturePath, Vector2 position) {
        this.id = id;
        this.height = height;
        this.width = width;
        this.position = position;
        this.setTexture(texturePath);
        this.setRectangle();
    }

    /**
     * act is action that the entity takes
     * @param act
     */
    public abstract void action(String act);

    /**
     * Generic methods for updating an entity
     */
    public void update() {
        // TODO: Update entity stuff
    }

    /**
     * Generic method for drawing an entity
     */
    public void draw(SpriteBatch sb) {
        // TODO: Parameter spritebatch, then spritebatch.render(texture), is called in Game.render();
        sb.draw(sprite, position.x, position.y);
    }

    public void draw(SpriteBatch batch, float rotation) {
        batch.draw(sprite, position.x, position.y, width/2, -position.y,
                rect.width, rect.height, 1,1, rotation);
    }

    /**
     * Sets the entity's texture to what is specified in texturePath
     * @param texturePath
     */
    //TODO: Use sprite directly from spritesheet
    public void setTexture(String texturePath) {
        sprite = new Sprite(new Texture(texturePath));
    }

    /**
     * Returns a rectangle
     * @return
     */
    private void setRectangle() {
        rect = new Rectangle();
        rect.setSize(this.width, this.height);
        rect.setPosition(position.x, this.position.y);
    }

    /**
     * Returns vector2 position for entity
     * @return
     */
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

    public Vector2 setPosition() { return position; }
}
