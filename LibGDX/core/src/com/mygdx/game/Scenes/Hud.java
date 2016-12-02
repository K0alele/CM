package com.mygdx.game.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.sun.glass.ui.View;
import com.sun.org.apache.xpath.internal.operations.String;

/**
 * Created by Diogo on 14/11/2016.
 */

public class Hud {

    public Stage stage;
    private Viewport viewport;

    private Integer worldTimer;
    private float timeCount;
    private Integer score;

    Label CountDownLabel;
    Label ScoreLabel;
    Label TimeLabel;
    Label LevelLaber;
    Label WorldLabel;

    public Hud(SpriteBatch sb)
    {
        worldTimer = 300;
        timeCount = 0;
        score = 0;

        viewport = new FitViewport(MyGdxGame.V_WIDTH,MyGdxGame.V_HEIGHT, new OrthographicCamera());

        stage = new Stage(viewport,sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        CountDownLabel = new Label(java.lang.String.format("%03d",worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        ScoreLabel = new Label(java.lang.String.format("%05d",score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        TimeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        //LevelLaber = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        // = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(CountDownLabel).expandX().padTop(10);
        table.add(ScoreLabel).expandX().padTop(10);
        table.add(TimeLabel).expandX().padTop(10);
        table.row();

        stage.addActor(table);
    }
}
