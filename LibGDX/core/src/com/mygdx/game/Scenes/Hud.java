package com.mygdx.game.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;
import com.sun.org.apache.xpath.internal.operations.String;

import java.sql.Time;

/**
 * Created by Diogo on 14/11/2016.
 */

public class Hud implements Disposable{

    public Stage stage;
    private Viewport viewport;

    private int worldTimer;
    private float timeCount;
    private int score;

    Label CountDownLabel;
    Label ScoreLabel;
    Label TimeLabel;
    Label LevelLaber;
    Label WorldLabel;

    public Hud(SpriteBatch sb)
    {
        worldTimer = 300;
        //timeCount = PlayScreen.player1.timer;
        score = 0;

        viewport = new FitViewport(MyGdxGame.V_WIDTH,MyGdxGame.V_HEIGHT, new OrthographicCamera());

        stage = new Stage(viewport,sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        CountDownLabel = new Label(java.lang.String.format("%03d",worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        ScoreLabel = new Label(java.lang.String.format("%05d",score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        TimeLabel = new Label(java.lang.String.format("TIME: %.2f",timeCount), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        //LevelLaber = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        // = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(CountDownLabel).expandX().padTop(10);
        table.add(ScoreLabel).expandX().padTop(10);
        table.add(TimeLabel).expandX().padTop(10);
        table.row();

        stage.addActor(table);
    }

    public void Update()
    {
        timeCount = PlayScreen.player1.timer;
        TimeLabel.setText(java.lang.String.format("TIME: %.2f",timeCount));
    }


    @Override
    public void dispose() {
        stage.dispose();
    }
}
