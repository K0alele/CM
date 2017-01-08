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



public class Hud implements Disposable{

    public Stage stage;
    private Viewport viewport;

    private float timeCount;
    private int lvl, maxlvl;
    Label TimeLabel;
    Label LevelLabel;
    Label Score;

    public Hud(SpriteBatch sb)
    {
        lvl = 0;
        maxlvl = 0;
        viewport = new FitViewport(MyGdxGame.V_WIDTH,MyGdxGame.V_HEIGHT, new OrthographicCamera());

        stage = new Stage(viewport,sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        TimeLabel = new Label(java.lang.String.format("TIME: %.2f",timeCount), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Score = new Label(java.lang.String.format("%d - %d",PlayScreen.pontos1,PlayScreen.pontos2), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        LevelLabel = new Label("LEVEL", new Label.LabelStyle(new BitmapFont(), Color.WHITE));


        table.add(TimeLabel).expandX().padTop(10);
        table.add(Score).expandX().padTop(10);
        table.add(LevelLabel).expandX().padTop(10);
        table.row();

        stage.addActor(table);
    }

    public void Update(int lvl, int maxlvl)
    {
        timeCount = PlayScreen.player1.timer;
        TimeLabel.setText(java.lang.String.format("TIME: %.2f",timeCount));
        Score.setText(java.lang.String.format("%d - %d",PlayScreen.pontos1,PlayScreen.pontos2));
        LevelLabel.setText("LEVEL : "+ lvl +" / " + maxlvl);
    }

    public void changeLevl(int _lvl, int _maxlvl)
    {
        lvl = _lvl;
        maxlvl = _maxlvl;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
