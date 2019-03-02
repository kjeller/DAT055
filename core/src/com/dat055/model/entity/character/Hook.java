package com.dat055.model.entity.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.dat055.model.entity.Entity;

import static com.badlogic.gdx.graphics.Texture.TextureWrap.Repeat;

/**
 * @author Tobias Campbell
 * @version 02-22-2019
 */
public class Hook extends Entity {
    private float maxLength;
    private Rectangle wire;
    private Vector2 initDirection;
    private boolean apexReached;
    private boolean remove;
    private boolean hasGrip;
    private float playerPosX;
    private float rotate;
    private Polygon wire2;
    private Texture texture;
    private PolygonSprite poly;
    private Vector2 originPos;
    private Rectangle test;

    Hook(Vector2 position, int height, int width, float maxLength, Vector2 initDirection) {
        super(position, height, width);
        this.initDirection = new Vector2(initDirection);
        originPos = new Vector2(Vector2.Zero);

        this.maxLength = maxLength;
        textureSet();
        initialize();
        BOUNDING_BOX_COLOR = Color.RED;
    }

    /**
     * Get the correct sprite depending on the angle of the hook.
     * @param region region that is used to identify the correct sprite from the atlas file.
     * @return the sprite for the hook.
     */
    private Sprite getSprite(String region) {
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("textures/spritesheets/hook/hookSheet.atlas"));
        TextureRegion r =  atlas.findRegion(region);
        if (r == null)
            return null;
        return new Sprite(r);
    }

    /**
     * Sets the correct sprite and texture for the hook and wire, respectively.
     */
    private void textureSet() {
        String name;
        if (initDirection.y > 0)
            name = (initDirection.x > 0) ? "hookupright" : "hookupleft";
        else if (initDirection.y == 0)
            name = (initDirection.x > 0) ? "hookright" : "hookleft";
        else
            name = (initDirection.x > 0) ? "hookdownright": "hookdownleft";
        sprite = getSprite(name);
        texture = new Texture("wire.png");
        texture.setWrap(Repeat, Repeat);
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
        //wire.y = position.y + 47;
    }

    /**
     * The logic of the hook.
     * @param deltaTime time since last frame.
     */
    @Override
    public void update(float deltaTime) {
        int velocity = 10;
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
        rect.setPosition(position.x, position.y-(float)height/2+1);
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
        } else {
            wire.setWidth(position.x-(playerPosX+64));
            test.setY(originPos.y);
            wire.setY(position.y);
        }


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
            wire.y = position.y;
        }
    }

    /**
     * Draw method for the hook and wire.
     * @param sb spritebatch that is used.
     * @param rotation sets the rotation when toggling between players.
     */
    @Override
    public void draw(PolygonSpriteBatch sb, float rotation) {
        poly.draw(sb);
        poly.setOrigin(poly.getWidth()/2, -position.y);
        poly.setRotation(rotation);
        super.draw(sb, rotation, new Vector2(-5, -23));
    }

    @Override
    public void drawBoundingBox(ShapeRenderer renderer) {
        super.drawBoundingBox(renderer);
        drawPolygon(wire2, renderer);
    }

    private void drawPolygon(Polygon polygon, ShapeRenderer renderer) {
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(BOUNDING_BOX_COLOR);
        renderer.polygon(polygon.getTransformedVertices());
        renderer.end();
    }

    /**
     * Set position where hook should originate from.
     * @param pos position
     */
    void setOriginPosition(Vector2 pos) {
        originPos.x = (initDirection.x > 0) ? pos.x + 64 : pos.x-wire.width;
        originPos.y = pos.y+48;
        wire.x = (initDirection.x > 0) ? pos.x + 64 : pos.x-wire.width;
        if (!hasGrip)
            wire.y = pos.y + 48;
    }

    /**
     * Method that sets the wire of the hook, to angle it correctly.
     */
    private void setWire2() {
        wire2 = new Polygon(new float []{ wire.x, wire.y,
                wire.x, wire.y + wire.height,
                wire.x + wire.width, wire.y + wire.height,
                wire.x + wire.width, wire.y});
        // Sets the polygon region that makes up the wire with texture.
        PolygonRegion p = new PolygonRegion(new TextureRegion(texture), wire2.getVertices(),
                new short[] {0, 1, 2, 0, 2, 3 });
        poly = new PolygonSprite(p);


        if (initDirection.x > 0) {
            if (!hasGrip) {
                wire2.setOrigin(wire.x, wire.y+wire.height/2);
                poly.setOrigin(wire.x, wire.y+wire.height/2);

                if (initDirection.y > 0) {
                    wire2.rotate(rotate);
                    poly.rotate(rotate);
                }

                else if (initDirection.y < 0) {
                    wire2.rotate(-rotate);
                    poly.rotate(-rotate);
                }
            } else {
                wire2.setOrigin(position.x, position.y+(float)height/2);
                poly.setOrigin(position.x, position.y+(float)height/2);

                wire2.setRotation(getAngle());
                poly.setRotation(getAngle());
            }
        } else {
            if (!hasGrip) {
                wire2.setOrigin(wire.x+wire.width, wire.y+wire.height/2);
                poly.setOrigin(wire.x+wire.width, wire.y+wire.height/2);
                if (initDirection.y > 0) {
                    wire2.rotate(-rotate);
                    poly.rotate(-rotate);
                }
                else if (initDirection.y < 0) {
                    wire2.rotate(rotate);
                    poly.rotate(rotate);
                }
            } else {
                wire2.setOrigin(position.x+(float)width/2, position.y+(float)height/2);
                poly.setOrigin(position.x+(float)width/2, position.y+(float)height/2);
                wire2.setRotation(getAngle());
                poly.setRotation(getAngle());
            }
        }
        Rectangle temp = new Rectangle(wire2.getBoundingRectangle());
        test = new Rectangle((int)temp.x, (int)temp.y, (int)temp.width, (int)temp.height);
    }

    /**
     * Calculate the angle of the wire of the hook when gripped on wall.
     * @return the angle that is rotated.
     */
    private float getAngle() {
        float angle = (initDirection.x > 0) ? (float) Math.toDegrees(Math.acos((originPos.y-position.y)/wire.width))
                : (float) Math.toDegrees(Math.acos((position.y-originPos.y)/wire.width));
        return angle-90;
    }

    // Get and set methods galore.
    boolean getHasGrip() {
        return hasGrip;
    }
    boolean getRemoved() {
        return remove;
    }
    public boolean getApexReached() {
        return apexReached;
    }
    public Rectangle getWire() {
        return test;
    }
    public Polygon getWire2() {
        return wire2;
    }

    void setPlayerPosX(int x) {
        playerPosX = x;
    }
    public void setApexReached(boolean val) {
        apexReached = val;
    }
    public void setHasGrip(boolean val) {
        hasGrip = val;
    }
}
