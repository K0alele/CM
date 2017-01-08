package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.MyGdxGame;
import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * Created by DIOGO-PC on 1/3/2017.
 */

public class MainMenuScreen implements Screen {

    private static final int ButtonsWidth = (int)(1f/3f * Gdx.graphics.getWidth());
    private static final int ButtonsHeight = (int)(7f/30f*ButtonsWidth);

    private MyGdxGame game;
    Texture TitleButton;
    Texture ExitButton;
    Texture NewGameButton;
    Texture ContinueButton;
    int lvl = 1;

    public MainMenuScreen(MyGdxGame _game, final int _lvl)
    {
        lvl = _lvl;
        game = _game;
        TitleButton = new Texture("TITLE.png");
        ExitButton = new Texture("EXIT.png");
        NewGameButton = new Texture("NEWGAME.png");
        ContinueButton = new Texture("Continue.png");

        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                screenY = Gdx.graphics.getHeight() - screenY;
                //Exit button
                Rectangle textureBounds1 = new Rectangle(Gdx.graphics.getWidth()/2 - ButtonsWidth/2, 10,ButtonsWidth,ButtonsHeight);
                if (textureBounds1.contains(screenX, screenY))
                {
                    Gdx.input.vibrate(100);
                    System.out.println("EXIT");
                    Gdx.app.exit();
                }

                //Play Singleplayer button
                Rectangle textureBounds2 = new Rectangle(Gdx.graphics.getWidth()/2 - ButtonsWidth/2, Gdx.graphics.getHeight()*2/4+10,ButtonsWidth,ButtonsHeight);
                if (textureBounds2.contains(screenX,screenY))
                {
                    Gdx.input.vibrate(100);
                    System.out.println("PLAY");
                    game.setScreen(new PlayScreen(game, 1));
                    PlayScreen.pontos1=0;
                    PlayScreen.pontos2=0;
                }

                //Play Multiplayer button
                Rectangle textureBounds3 = new Rectangle(Gdx.graphics.getWidth()/2 - ButtonsWidth/2, Gdx.graphics.getHeight()*1/4+10,ButtonsWidth,ButtonsHeight);
                if (textureBounds3.contains(screenX,screenY))
                {
                    Gdx.input.vibrate(100);
                    System.out.println("PLAY");
                    game.setScreen(new PlayScreen(game, lvl));
                    //PlayScreen.pontos1=0;
                    //PlayScreen.pontos2=0;
                }

                //System.out.println("1 : X : " + screenX + " Y : " + screenY);
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

        int x = Gdx.graphics.getWidth()/2 - (int)(ButtonsWidth*1.8f/2);
        int y = Gdx.graphics.getHeight()*3/4+10;
        game.batch.draw(TitleButton,x , y,ButtonsWidth * 1.8f,ButtonsHeight * 1.8f);
        x = Gdx.graphics.getWidth()/2 - ButtonsWidth/2;
        y = Gdx.graphics.getHeight()*2/4+10;
        game.batch.draw(NewGameButton,x , y,ButtonsWidth,ButtonsHeight);
        y = Gdx.graphics.getHeight()*1/4+10;
        game.batch.draw(ContinueButton,x , y,ButtonsWidth,ButtonsHeight);
        y = 10;
        game.batch.draw(ExitButton,x , y,ButtonsWidth,ButtonsHeight);

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
        TitleButton.dispose();
        ExitButton.dispose();
        NewGameButton.dispose();
        ContinueButton.dispose();
    }
}
