package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Screens.PlayScreen;

/**
 * Created by DIOGO-PC on 12/29/2016.
 */

public class Player2 extends Sprite implements InputProcessor{

    public String id;
    public World world;

    public Player2(World _world)
    {
        world = _world;
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        CreateBox(screenX + ((int)PlayScreen.player1.carBody.getPosition().x - Gdx.graphics.getWidth() / 2), 60 /*Gdx.graphics.getHeight() - screenY*/);
        return false;
    }

    private void CreateBox(int x, int y) {

        System.out.println("2 : X : " + x + "Y : " + y);
        System.out.println("1 : X : " + PlayScreen.player1.carBody.getPosition().x + "Y : " + PlayScreen.player1.carBody.getPosition().y);


        float bs = 4f;
        BodyDef bDef = new BodyDef();

        bDef.type = BodyDef.BodyType.DynamicBody;
        bDef.position.set(x,y);

        FixtureDef fDef = new FixtureDef();
        Body body;

        PolygonShape bodyShape = new PolygonShape();
        bodyShape.set(new float[] {x - bs, y - bs,x + bs,y - bs, x + bs, y +bs, x-bs, y + bs});

        fDef.shape = bodyShape;

        body = world.createBody(bDef);
        body.createFixture(fDef).setUserData(this);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
