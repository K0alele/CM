package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Coin;
import com.mygdx.game.Sprites.InteractiveObject;
import com.mygdx.game.Sprites.Player1;
import com.mygdx.game.Sprites.Player2;




public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact)
    {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa == null || fb == null ) return;
        if (fa.getUserData() != null || fb.getUserData() != null)
        {
            PlayScreen.player1.isGround = isPlayerColidingWithGround(fa,fb);
        }

        if (isPlayerColidingWithCoins(fa,fb))
        {
            PlayScreen.player1.won = true;
        }
    }

    @Override
    public void endContact(Contact contact)
    {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa == null || fb == null ) return;
        if (fa.getUserData() != null || fb.getUserData() != null)
        {
            PlayScreen.player1.isGround = !isPlayerColidingWithGround(fa, fb);
        }
        //PlayScreen.player1.isGround = false;

        //Gdx.app.log("Collision Stoped ","");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    private boolean isPlayerColidingWithGround(Fixture fa, Fixture fb)
    {
        if (fa.getUserData() instanceof B2WorldCreator || fb.getUserData() instanceof  B2WorldCreator)
        {
            if ((fa.getUserData().toString() == "LEFTW" || fb.getUserData().toString() == "LEFTW")
                    ||(fa.getUserData().toString() == "RIGHTW" || fb.getUserData().toString() == "RIGHTW")) {
                return true;
            }
        }
        return false;
    }

    private boolean isPlayerColidingWithCoins(Fixture fa, Fixture fb)
    {
        if (fa.getUserData() instanceof InteractiveObject || fb.getUserData() instanceof InteractiveObject)
        {
            if ((fa.getUserData() == "LEFTW" || fb.getUserData() == "LEFTW")
                    ||(fa.getUserData() == "RIGHTW" || fb.getUserData() == "RIGHTW"
                    || (fa.getUserData() == "BODY" || fb.getUserData() == "BODY"))) {
                return true;
            }
        }
        return false;
    }

    private boolean isPlayer1CollidingWithPlayer2(Fixture fa, Fixture fb)
    {
        if (fa.getUserData() instanceof Player2 || fb.getUserData() instanceof  Player2)
        {
            if ((fb.getUserData() == "LEFTW" || fb.getUserData() == "LEFTW")
                    ||(fb.getUserData() == "RIGHTW" || fb.getUserData() == "RIGHTW"
                    || (fb.getUserData() == "BODY" || fb.getUserData() == "BODY"))) {
                return true;
            }
        }
        return false;
    }
}
