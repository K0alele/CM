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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
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
import com.mygdx.game.Sprites.Player2;
import com.mygdx.game.Tools.B2WorldCreator;
import com.mygdx.game.Tools.WorldContactListener;
import com.sun.corba.se.impl.oa.poa.ActiveObjectMap;
import com.sun.corba.se.spi.orbutil.fsm.Input;

import java.io.Console;

/**
 * Created by Diogo on 14/11/2016.
 */

public class PlayScreen implements Screen{

    private MyGdxGame game;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer debugRenderer;
    public static Player1 player1;
    public static Player2 player2;

    boolean gyroscopeAvail;
    float gyroX = 0;
    float gyroY = 0;
    float gyroZ = 0;
    public PlayScreen(MyGdxGame _game)
    {
        gyroscopeAvail = Gdx.input.isPeripheralAvailable(com.badlogic.gdx.Input.Peripheral.Gyroscope);

        game = _game;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(MyGdxGame.V_WIDTH/2,MyGdxGame.V_HEIGHT/2, gameCam);
        hud = new Hud(game.batch);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("lvl1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

        gameCam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2,0);

        world = new World(new Vector2(0,-100),true);
        debugRenderer = new Box2DDebugRenderer();

        new B2WorldCreator(world,map);

        CreatePlayer();
        player2 = new Player2(world);
        world.setContactListener(new WorldContactListener());
    }

    private void CreatePlayer() {
        FixtureDef BodyFDef = new FixtureDef(), wheelsFDef = new FixtureDef();

        BodyFDef.density = 15f;
        BodyFDef.friction = 0.4f;
        BodyFDef.restitution = 0.3f;

        wheelsFDef.density = BodyFDef.density - 5f;
        wheelsFDef.friction = 10f;
        wheelsFDef.restitution = 0.8f;

        player1 = new Player1(world,BodyFDef,wheelsFDef,32,30,20,10);
    }


    @Override
    public void show() {

    }

    public void Update(float dt)
    {
        if(gyroscopeAvail){
            gyroX = Gdx.input.getGyroscopeX();
            gyroY = Gdx.input.getGyroscopeY();
            gyroZ = Gdx.input.getGyroscopeZ();
        }

        //System.out.println(gyroZ);
        //handleInput(dt);

        player1.Update(gyroZ, dt);
        player2.Update();

        gameCam.position.x = player1.carBody.getPosition().x;
        gameCam.position.y = player1.carBody.getPosition().y;

        gameCam.update();

        renderer.setView(gameCam);
    }

    private void handleInput(float dt)
    {
        //System.out.println(player1.isGround);

    }

    @Override
    public void render(float delta) {

        Update(delta);

        Gdx.gl.glClearColor(56/255f,168/255f,233/255f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();
        debugRenderer.render(world, gameCam.combined);
        game.batch.setProjectionMatrix(gameCam.combined);

        game.batch.begin();

        player1.wheelSprite1.draw(game.batch);
        player1.wheelSprite2.draw(game.batch);
        player1.bodySprite.draw(game.batch);

        for (int i = 0; i < player2.data.size; i++) {
            player2.data.getValueAt(i).draw(game.batch);
        }

        game.batch.end();

        hud.stage.draw();

        world.step(1f/60f, 6,2);

        if (player1.isDead)
        {
            world.dispose();
            game.setScreen(new MainMenuScreen(game));
        }
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        debugRenderer.dispose();
        hud.dispose();
    }
}
