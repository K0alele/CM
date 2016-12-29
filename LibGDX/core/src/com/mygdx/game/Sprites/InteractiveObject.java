package com.mygdx.game.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by DIOGO-PC on 12/21/2016.
 */

public abstract class InteractiveObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    public String id;

    public InteractiveObject(World _world, TiledMap _map, Rectangle _bounds)
    {
        id = "INTERACTIVE";
        world = _world;
        map = _map;
        bounds = _bounds;

        BodyDef bDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fDef = new FixtureDef();

        bDef.type = BodyDef.BodyType.StaticBody;
        bDef.position.set(bounds.getX() + bounds.getWidth()/2 ,bounds.getY() + bounds.getHeight()/2);

        body = world.createBody(bDef);
        shape.setAsBox(bounds.getWidth()/2, bounds.getHeight()/2);
        fDef.shape = shape;
        body.createFixture(fDef).setUserData(this);
    }
}
