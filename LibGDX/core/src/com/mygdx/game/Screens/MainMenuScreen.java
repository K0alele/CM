package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;

/**
 * Created by DIOGO-PC on 1/3/2017.
 */

public class MainMenuScreen implements Screen {

    private static final int ButtonsWidth = 300;
    private static final int ButtonsHeight = 70;
    private OrthographicCamera gameCam;

    private Viewport gamePort;

    private MyGdxGame game;
    Texture TitleButton;
    Texture ExitButton;
    Texture SinglePlayerButton;
    Texture MultiPlayerButton;

    public MainMenuScreen(MyGdxGame _game)
    {
        game = _game;
        TitleButton = new Texture("TITLE.png");
        ExitButton = new Texture("EXIT.png");
        SinglePlayerButton = new Texture("SINGLEPLAYER.png");
        MultiPlayerButton = new Texture("MULTIPLAYER.png");

        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(MyGdxGame.V_WIDTH/2,MyGdxGame.V_HEIGHT/2, gameCam);
        gameCam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2,0);

        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                screenY = Gdx.graphics.getHeight() - screenY;
                //Exit button
                Rectangle textureBounds1 = new Rectangle(Gdx.graphics.getWidth()/2 - ButtonsWidth/2, 10,ButtonsWidth,ButtonsHeight);
                if (textureBounds1.contains(screenX, screenY))
                {
                    System.out.println("EXIT");
                    Gdx.app.exit();
                }

                //Play game button
                Rectangle textureBounds2 = new Rectangle(MyGdxGame.V_WIDTH/2 - ButtonsWidth/2, 210,ButtonsWidth,ButtonsHeight);
                if (textureBounds2.contains(screenX,screenY))
                {
                    System.out.println("PLAY");
                    game.setScreen(new PlayScreen(game));
                }

                //System.out.println("1 : X : " + screenX + " Y : " + screenY);
                //System.out.println("2 : X : " + textureBounds1.x + " Y : " + textureBounds1.y);
                //System.out.println("3 : X : " + (textureBounds1.x + textureBounds1.getWidth()) + " Y : " + (10 + textureBounds1.getHeight()));

                return super.touchUp(screenX, screenY, pointer, button);
            }
        });
    }

    @Override
    public void show()
    {

    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(56/255f,168/255f,233/255f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        int x = Gdx.graphics.getWidth()/2 - ButtonsWidth/2;
        int y = 300;
        game.batch.draw(TitleButton,x , y,ButtonsWidth * 1.8f,ButtonsHeight * 1.8f);
        y = 210;
        game.batch.draw(SinglePlayerButton,x , y,ButtonsWidth,ButtonsHeight);
        y = 110;
        game.batch.draw(MultiPlayerButton,x , y,ButtonsWidth,ButtonsHeight);
        y = 10;
        game.batch.draw(ExitButton,x , y,ButtonsWidth,ButtonsHeight);

        game.batch.end();
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
