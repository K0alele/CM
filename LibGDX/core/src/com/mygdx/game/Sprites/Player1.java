package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Diogo on 29/11/2016.
 */

public class Player1 extends Sprite {

    public World world;
    public Body b2Body;

    public  Player1(World _world)
    {
        world = _world;
        definePlayer1();
    }

    private void definePlayer1()
    {
        BodyDef bDef = new BodyDef();
        bDef.type = BodyDef.BodyType.DynamicBody;
        bDef.position.set(32,32);

        b2Body = world.createBody(bDef);

        FixtureDef fDef = new FixtureDef();
        CircleShape shape = new CircleShape();

        shape.setRadius(5);

        fDef.shape = shape;
        fDef.density = 0.5f;
        fDef.friction = 0.4f;
        fDef.restitution = 0.6f;

        b2Body.createFixture(fDef);

        shape.dispose();
    }
}
