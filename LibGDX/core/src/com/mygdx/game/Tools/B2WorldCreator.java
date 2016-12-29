package com.mygdx.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Sprites.Coin;
import com.sun.org.apache.xpath.internal.operations.String;


/**
 * Created by DIOGO-PC on 12/21/2016.
 */

public class B2WorldCreator {

    public java.lang.String id;

    public B2WorldCreator(World world, TiledMap map)
    {
        id = "WORLD";
        BodyDef bDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fDef = new FixtureDef();
        Body body;

        //Ground
        for (MapObject object: map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rec = ((RectangleMapObject) object).getRectangle();

            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set(rec.getX() + rec.getWidth()/2 ,rec.getY() + rec.getHeight()/2);

            body = world.createBody(bDef);
            shape.setAsBox(rec.getWidth()/2, rec.getHeight()/2);
            fDef.shape = shape;
            body.createFixture(fDef).setUserData(this);
        }
        for (MapObject object: map.getLayers().get(2).getObjects().getByType(PolygonMapObject.class))
        {
            Polygon rec = ((PolygonMapObject) object).getPolygon();

            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set(rec.getX(),rec.getY());

            body = world.createBody(bDef);
            shape.set(rec.getVertices());
            fDef.shape = shape;
            body.createFixture(fDef).setUserData(this);
        }
        //Coins
        for (MapObject object: map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rec = ((RectangleMapObject) object).getRectangle();

            new Coin(world,map, rec);
        }
    }
}
