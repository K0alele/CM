package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ArrayMap;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;

/**
 * Created by DIOGO-PC on 12/29/2016.
 */

public class Player2 extends Sprite implements InputProcessor{

    public String id;
    public World world;
    public ArrayMap <Body, Sprite> data;
    Sprite[] crates;

    public Player2(World _world)
    {
        data = new ArrayMap<Body, Sprite>();
        world = _world;
        Gdx.input.setInputProcessor(this);
        crates = new Sprite[3];
        for (int i = 0; i < 3; i++) {
            crates[i] = new Sprite(new Texture("Crate"+(i+1)+".png"));
            crates[i].setSize(9,9);
            crates[i].setOrigin(crates[i].getWidth()/2, crates[i].getHeight()/2);
        }
    }

    public void Update()
    {
        for (int i = 0; i < data.size; i++) {
            Vector2 SpriteData = new Vector2(data.getValueAt(i).getWidth()/2,data.getValueAt(i).getHeight()/2);
            Vector2 BodyPos = data.getKeyAt(i).getPosition();
            data.getValueAt(i).setPosition(BodyPos.x - SpriteData.x, BodyPos.y - SpriteData.y);
            data.getValueAt(i).setRotation(data.getKeyAt(i).getAngle() * MathUtils.radiansToDegrees);
        }
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

        if (pointer >= 1)
            PlayScreen.pause = !PlayScreen.pause;
        else if (pointer < 1 && !PlayScreen.pause)
        {
            CreateBox(screenX * MyGdxGame.V_WIDTH/Gdx.graphics.getWidth()/2 + (((int)PlayScreen.player1.carBody.getPosition().x - MyGdxGame.V_WIDTH/4)),(int)PlayScreen.player1.carBody.getPosition().y + 30);
        }
        return false;
    }

    private void CreateBox(int x, int y) {

        float bs = 4f;
        BodyDef bDef = new BodyDef();

        bDef.type = BodyDef.BodyType.DynamicBody;
        bDef.position.set(x,y);

        FixtureDef fDef = new FixtureDef();
        Body body;

        fDef.density =  0.3f;
        fDef.friction = 0.4f;
        fDef.restitution = 0.3f;

        PolygonShape bodyShape = new PolygonShape();
        bodyShape.setAsBox(bs,bs);

        fDef.shape = bodyShape;

        body = world.createBody(bDef);
        body.createFixture(fDef).setUserData(this);
        data.put(body,new Sprite(crates[MathUtils.random(0,2)]));
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

    public void dispose() {
        for (int i = 0; i < data.size; i++) {
            data.getValueAt(i).getTexture().dispose();
        }
        data.clear();
    }
}
