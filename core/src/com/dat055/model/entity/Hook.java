package com.dat055.model.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Hook extends Entity {
    private final int velocity = 10;
    private float maxLength;
    private Rectangle wire;
    private Vector2 initDirection;
    private Vector2 direction;
    private boolean apexReached;
    private boolean remove;
    private boolean hasGrip;
    private float playerPosX;

    Hook(Vector2 position, int height, int width, String texturePath, float maxLength, Vector2 initDirection) {
        super(position, height, width, texturePath);
        this.initDirection = new Vector2(initDirection);
        this.maxLength = maxLength;
        initialize();
    }

    /**
     * Initialize the hook correctly.
     */
    private void initialize() {
        setRectangle();
        hasGrip = false;
        remove = false;
        apexReached = false;
        wire = new Rectangle(0, 0, 0, 3);
        direction = new Vector2(Vector2.Zero);
        wire.x = (initDirection.x > 0) ? position.x + 64 : position.x;
        wire.y = position.y + 48;
    }

    @Override
    public void update(float deltaTime) {
        wire.width += (apexReached) ? -velocity : velocity;
        if (wire.width >= maxLength)
            apexReached = true;

        if (initDirection.x > 0)
            hookRight();
        else
            hookLeft();

        if (!hasGrip)
            position.y = wire.y;
        rect.setPosition(position.x, position.y-height/2);
    }

    /**
     * Handle when hook is shot to the left.
     */
    private void hookLeft() {
        if (!hasGrip) {
            wire.x += (!apexReached) ? -velocity : velocity;
            position.x = wire.x-width;
            if (position.x+width > wire.x+wire.width)
                remove = true;
        } else
            wire.width = playerPosX - wire.x;
    }

    /**
     * Handle when hook is shot to the right
     */
    private void hookRight() {
        if (!hasGrip) {
            position.x = wire.x+wire.width;
            if (position.x < wire.x)
                remove = true;
        } else
            wire.width = position.x-(playerPosX+64);

    }


    @Override
    public void action(String act) {

    }

    @Override
    public void draw(SpriteBatch sb, float rotation) {
        super.draw(sb, rotation, new Vector2(0, -25));
    }
    /**
     * Set position where hook should originate from.
     * @param pos position
     */
    void setOriginPosition(Vector2 pos) {
        wire.x = (initDirection.x > 0) ? pos.x + 64 : pos.x-wire.width;
        wire.y = pos.y + 48;
    }

    // Get and set methods galore.
    boolean getHasGrip() {
        return hasGrip;
    }
    public boolean getApexReached() {
        return apexReached;
    }
    boolean getRemoved() {
        return remove;
    }
    public Rectangle getWire() {
        return wire;
    }

    public void setApexReached(boolean val) {
        apexReached = val;
    }
    public void setHasGrip(boolean val) {
        hasGrip = val;
    }
    public void setPlayerPosX(int x) {
        playerPosX = x;
    }
}
