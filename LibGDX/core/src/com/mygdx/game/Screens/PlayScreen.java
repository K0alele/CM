package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.IntFloatMap;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Sprites.Player1;
import com.sun.corba.se.impl.oa.poa.ActiveObjectMap;
import com.sun.corba.se.spi.orbutil.fsm.Input;

import java.io.Console;

/**
 * Created by Diogo on 14/11/2016.
 */

public class PlayScreen implements Screen {

    private MyGdxGame game;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Player1 player1;

    public PlayScreen(MyGdxGame _game)
    {
        game = _game;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(MyGdxGame.V_WIDTH,MyGdxGame.V_HEIGHT, gameCam);
        hud = new Hud(game.batch);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("lvl1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

        gameCam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2,0);

        world = new World(new Vector2(0,-100),true);
        debugRenderer = new Box2DDebugRenderer();

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
            body.createFixture(fDef);
        }
        for (MapObject object: map.getLayers().get(2).getObjects().getByType(PolygonMapObject.class))
        {
            Polygon rec = ((PolygonMapObject) object).getPolygon();

            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set(rec.getX(),rec.getY());

            body = world.createBody(bDef);
            shape.set(rec.getVertices());
            fDef.shape = shape;
            body.createFixture(fDef);
        }
        //Coins
        for (MapObject object: map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rec = ((RectangleMapObject) object).getRectangle();

            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set(rec.getX() + rec.getWidth()/2 ,rec.getY() + rec.getHeight()/2);

            body = world.createBody(bDef);
            shape.setAsBox(rec.getWidth()/2, rec.getHeight()/2);
            fDef.shape = shape;
            body.createFixture(fDef);
        }

        FixtureDef BodyFDef = new FixtureDef(), wheelsFDef = new FixtureDef();

        BodyFDef.density = 15f;
        BodyFDef.friction = 0.4f;
        BodyFDef.restitution = 0.3f;

        wheelsFDef.density = BodyFDef.density - 5f;
        wheelsFDef.friction = 10f;
        wheelsFDef.restitution = 0.8f;

        player1 = new Player1(world,BodyFDef,wheelsFDef,32,50,20,10);
    }


    @Override
    public void show() {

    }


    public void Update(float dt)
    {
        handleInput(dt);

        gameCam.position.x = player1.carBody.getPosition().x;

        gameCam.update();

        renderer.setView(gameCam);
    }

    private void handleInput(float dt)
    {
        player1.backWheel.applyLinearImpulse(new Vector2(5000f,0),player1.backWheel.getWorldCenter(),true);
    }

    @Override
    public void render(float delta) {

        Update(delta);

        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();
        debugRenderer.render(world, gameCam.combined);
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);

        hud.stage.draw();

        world.step(1f/60f, 6,2);
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
