package com.dat055.Model.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
    public Entity(int id, int height, int width, String texturePath) {
        this.id = id;
        this.height = height;
        this.width = width;
        position = new Vector2(0, 0);
        this.setTexture(texturePath);
        this.setRectangle();
    }

    protected int id;
    protected int height;
    protected int width;
    protected Vector2 position;
    private Texture texture;
    protected Rectangle rect;


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
        // TODO: Parameter spritebatch, then spritebatch.draw(texture), is called in Game.render();
        sb.draw(texture, position.x, position.y);
    }

    /**
     * Sets the entity's texture to what is specified in texturePath
     * @param texturePath
     */
    public void setTexture(String texturePath) {
        texture = new Texture(texturePath);
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
}
