package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Sprites.Player1;
import com.mygdx.game.Sprites.Player2;
import com.mygdx.game.Tools.B2WorldCreator;
import com.mygdx.game.Tools.WorldContactListener;

/**
 * Created by Diogo on 14/11/2016.
 */

public class PlayScreen implements Screen{

    private static MyGdxGame game;
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

    private static int mapId;
    private final int MaxMapId = 3;

    boolean accelAvail;
    private float accelX;
    private float accelY;
    private float accelZ;

    public static boolean pause;

    public static int pontos1, pontos2;

    public PlayScreen(MyGdxGame _game, int _mapId)
    {
        pause = false;
        accelAvail = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);

        game = _game;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(MyGdxGame.V_WIDTH/2,MyGdxGame.V_HEIGHT/2, gameCam);
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();

        gameCam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2,0);

        world = new World(new Vector2(0,-100),true);
        debugRenderer = new Box2DDebugRenderer();

        hud.changeLevl(_mapId, MaxMapId);
        mapId = _mapId;
        CreateMap(mapId);

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

    private void CreateMap(int id)
    {
        map = mapLoader.load("lvl"+id+".tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        new B2WorldCreator(world,map);
    }

    @Override
    public void show() {

    }

    public void Update(float dt)
    {
        if(accelAvail){
            accelX = Gdx.input.getAccelerometerX();
            accelY = Gdx.input.getAccelerometerY();
            accelZ = Gdx.input.getAccelerometerZ();
        }

        //System.out.println(accelY);
        hud.Update(mapId, MaxMapId);

        player1.Update(accelY, dt);
        player2.Update();

        gameCam.position.x = player1.carBody.getPosition().x;
        gameCam.position.y = player1.carBody.getPosition().y;

        gameCam.update();


        renderer.setView(gameCam);
    }

    @Override
    public void render(float delta) {

        if (!pause)
        {
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
                pontos2++;
                Gdx.input.vibrate(500);
                player1.isDead = false;
                player1.timer = 5f;
                player1.won = false;
                dispose();
                if (mapId + 1 <= MaxMapId)
                    game.setScreen(new PlayScreen(game, mapId + 1));
                else
                {
                    game.batch.dispose();
                    game.batch=new SpriteBatch();
                    game.setScreen(new MainMenuScreen(game,mapId));
                }
            }
            if (player1.won)
            {
                pontos1++;
                Gdx.input.vibrate(500);
                player1.isDead = false;
                player1.timer = 5f;
                player1.won = false;
                dispose();
                if (mapId + 1 <= MaxMapId)
                    game.setScreen(new PlayScreen(game, mapId + 1));
                else
                {
                   goToMenu();
                }
            }
        }else
        {
            Gdx.gl.glClearColor(0f,0f,0f,1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            hud.stage2.draw();
        }
    }

    public static void goToMenu()
    {
        game.batch.dispose();
        game.batch = new SpriteBatch();
        game.setScreen(new MainMenuScreen(game,mapId));
    }

    @Override
    public void resize(int width, int height)
    {
        gamePort.update(width,height);
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        debugRenderer.dispose();
        hud.dispose();
        player2.dispose();
        player1.dispose();
    }
}
