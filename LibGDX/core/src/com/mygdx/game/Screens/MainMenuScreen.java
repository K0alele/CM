package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.MyGdxGame;

/**
 * Created by DIOGO-PC on 1/3/2017.
 */

public class MainMenuScreen implements Screen {

    private static final int ButtonsWidth = 300;
    private static final int ButtonsHeight = 150;

    private MyGdxGame game;
    Texture TitleButton;
    Texture ExitButton;
    Texture SinglePlayerButton;
    Texture MultiPlayerButton;


    public MainMenuScreen(MyGdxGame _game)
    {
        game = _game;
        TitleButton = new Texture("TitleButton.png");
        //ExitButton = new Texture("ExitButton");
        //SinglePlayerButton = new Texture("SinglePlayerButton");
        //MultiPlayerButton = new Texture("MultiPlayerButton");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(56/255f,168/255f,233/255f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        int x = MyGdxGame.V_WIDTH/2 - ButtonsWidth/2;
        int y = 100;

        game.batch.draw(TitleButton,x , y,ButtonsWidth,ButtonsHeight);

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

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
