package com.dat055.model.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Hook extends Entity {
    private final int velocity = 10;
    private float maxLength;
    private Rectangle wire;
    private Vector2 initDirection;
    private boolean apexReached;
    private boolean remove;
    private boolean hasGrip;
    private float playerPosX;
    private float rotate;
    private Polygon wire2;
    private PolygonSpriteBatch pb;
    // test is pretty much wire2 in rectangle form.
    private Rectangle test;
    private TextureAtlas atlas;

    Hook(Vector2 position, int height, int width, float maxLength, Vector2 initDirection) {
        super(position, height, width);
        this.initDirection = new Vector2(initDirection);
        this.maxLength = maxLength;
        textureSet();
        initialize();
    }
    private Sprite getSprite(String region) {
        atlas = new TextureAtlas(Gdx.files.internal("textures/spritesheets/hook/hookSheet.atlas"));
        TextureRegion r =  atlas.findRegion(region);
        if (r == null)
            return null;
        return new Sprite(r);
    }
    private void textureSet() {
        String name;
        if (initDirection.y > 0)
            name = (initDirection.x > 0) ? "hookupright" : "hookupleft";
        else if (initDirection.y == 0)
            name = (initDirection.x > 0) ? "hookright" : "hookleft";
        else
            name = (initDirection.x > 0) ? "hookdownright": "hookdownleft";

        sprite = getSprite(name);
        sprite.setColor(1, 1, 1, 0f);
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
        wire2 = new Polygon();
        rotate = 20;
        wire.x = (initDirection.x > 0) ? position.x + 64 : position.x;
        wire.y = position.y + 48;
    }

    @Override
    public void update(float deltaTime) {
        wire.width += (apexReached) ? -velocity : velocity;
        if (initDirection.x < 0) {
            wire.x += (!apexReached) ? -velocity : velocity;
        }
        setWire2();
        if (wire.width >= maxLength)
            apexReached = true;

        if (initDirection.x > 0)
            hookRight();
        else
            hookLeft();

        if (!hasGrip) {
            position.y = (initDirection.y > 0) ? test.y+test.height : test.y;
        }
        rect.setPosition(position.x, position.y-height/2);
    }
    /**
     * Handle when hook is shot to the right
     */
    private void hookRight() {
        if (!hasGrip) {
            //setWire2();
            position.x = test.x + test.width;
            if (position.x <= playerPosX+64)
                remove = true;
        } else
            wire.width = position.x-(playerPosX+64);

    }

    /**
     * Handle when hook is shot to the left.
     */
    private void hookLeft() {
        if (!hasGrip) {
            position.x = test.x - width;
            if (test.x+test.width > playerPosX) {
                remove = true;
            }
        } else {
            wire.width = playerPosX - position.x-width;
        }
    }

    @Override
    public void action(String act) {

    }

    @Override
    public void draw(SpriteBatch sb, float rotation) {
        super.draw(sb, rotation, new Vector2(0, -25));
        //TODO: Fix hook rotation + flipping.
    }
    /**
     * Set position where hook should originate from.
     * @param pos position
     */
    void setOriginPosition(Vector2 pos) {
        wire.x = (initDirection.x > 0) ? pos.x + 64 : pos.x-wire.width;
        wire.y = pos.y + 48;
    }
    private void setWire2() {
        wire2 = new Polygon(new float []{ wire.x, wire.y,
                wire.x, wire.y + wire.height,
                wire.x + wire.width, wire.y + wire.height,
                wire.x + wire.width, wire.y});

        if (initDirection.x > 0) {
            wire2.setOrigin(wire.x, wire.y+wire.height/2);
            if (initDirection.y > 0)
                wire2.rotate(rotate);
            else if (initDirection.y < 0)
                wire2.rotate(-rotate);
        } else {
            wire2.setOrigin(wire.x+wire.width, wire.y+wire.height/2);
            if (initDirection.y > 0)
                wire2.rotate(-rotate);
            else if (initDirection.y < 0)
                wire2.rotate(rotate);
        }
        Rectangle temp = new Rectangle(wire2.getBoundingRectangle());
        test = new Rectangle((int)temp.x, (int)temp.y, (int)temp.width, (int)temp.height);
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
        return test;
    }
    public Polygon getWire2() {
        return wire2;
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
