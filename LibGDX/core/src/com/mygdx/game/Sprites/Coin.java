package com.mygdx.game.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by DIOGO-PC on 12/21/2016.
 */

public class Coin extends InteractiveObject {

    public Coin(World _world, TiledMap _map, Rectangle _bounds)
    {
        super(_world,_map,_bounds);
    }
}
