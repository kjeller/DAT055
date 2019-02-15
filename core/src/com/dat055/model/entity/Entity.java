package com.dat055.model.entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
    protected int height;
    protected int width;
    Vector2 position;
    Sprite sprite;
    Rectangle rect;

    public Entity(Vector2 position, int height, int width, String texturePath) {
        this.height = height;
        this.width = width;
        this.position = position;
        this.setTexture(texturePath);
        setRectangle();
    }

    /**
     * act is action that the entity takes
     * @param act action that entity should do
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
        sb.draw(sprite, position.x, position.y);
    }

    public void draw(SpriteBatch batch, float rotation) {
        batch.draw(sprite, position.x, position.y, width/2, -position.y,
                rect.width, rect.height, 1,1, rotation);
    }

    //TODO: Use sprite directly from spritesheet
    public void setTexture(String texturePath) {
        sprite = new Sprite(new Texture(texturePath));
    }

    void setRectangle() {
        rect = new Rectangle();
        rect.setSize(width, height);
        rect.setPosition(position.x, position.y);
    }
    public void setXPosition(int x) { position.x = x; rect.x = position.x; }
    public void setYPosition(int y) { position.y = y; rect.y = position.y; }

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

    @Override
    public String toString() {
        return String.format("(h, w): (%d, %d),\npos: (%.1f, %.1f), \n" +
                        "rect.(x, y): (%.1f, %.1f), \nrect.(h, w): (%.1f, %.1f)\n",
                height, width, position.x, position.y, rect.x, rect.y, rect.height, rect.width);
    }
}
