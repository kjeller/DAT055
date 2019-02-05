package com.dat055.Model.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Entity {
    public Entity(int id, int height, int width, String texturePath) {
        this.id = id;
        this.height = height;
        this.width = width;
        this.posX = 0.0f;
        this.posY = 0.0f;
        this.setTexture(texturePath);
    }

    protected int id;
    protected int height;
    protected int width;
    protected float posX;
    protected float posY;
    private Texture texture;

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
        //System.out.println("draw entity");
        sb.draw(texture, (int)posX, (int)posY);
    }

    /**
     * Sets the entity's texture to what is specified in texturePath
     * @param texturePath
     */
    public void setTexture(String texturePath) {
        texture = new Texture(texturePath);
    }
}
