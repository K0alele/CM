package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WheelJoint;
import com.badlogic.gdx.physics.box2d.joints.WheelJointDef;

/**
 * Created by Diogo on 29/11/2016.
 */

public class Player1 extends Sprite {

    public String id;
    public static boolean isGround = false;
    public Body frontWheel, backWheel, carBody;
    public WheelJoint backJoint,frontJoint;

    public  Player1(World _world, FixtureDef _carBodyFDef, FixtureDef _wheelsFDef, float x , float y , float width, float height)
    {
        id = "PLAYER";

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x,y);
        //CarBody

        PolygonShape bodyShape = new PolygonShape();
        bodyShape.set(new float[] {-width/2 * 0.8f,height* 0.05f,width/2,-height * 0.1f,width/2 * 0.4f,height/2, -width/2f,height/2});

        _carBodyFDef.shape = bodyShape;

        carBody = _world.createBody(bodyDef);
        carBody.createFixture(_carBodyFDef).setUserData(this);

        //backWheel
        CircleShape wheelShape = new CircleShape();
        wheelShape.setRadius(height/2.5f);

        _wheelsFDef.shape = wheelShape;

        backWheel = _world.createBody(bodyDef);
        backWheel.createFixture(_wheelsFDef).setUserData(this);

        //rightWheel

        frontWheel = _world.createBody(bodyDef);
        frontWheel.createFixture(_wheelsFDef).setUserData(this);

        //backJoint
        WheelJointDef wheelJointDef = new WheelJointDef();
        wheelJointDef.bodyA = carBody;
        wheelJointDef.bodyB = frontWheel;
        wheelJointDef.localAnchorA.set(-width/2 * 0.75f, -height/2 * 1.25f);
        wheelJointDef.frequencyHz = 5f;
        wheelJointDef.localAxisA.set(Vector2.Y);

        backJoint = (WheelJoint)_world.createJoint(wheelJointDef);

        //frontWheel
        wheelJointDef.bodyB = backWheel;
        wheelJointDef.localAnchorA.x *= -1;

        frontJoint = (WheelJoint)_world.createJoint(wheelJointDef);
    }

    public void Hit()
    {
        System.out.println(id + " : I have been hit!!");
        carBody.applyAngularImpulse((float) Math.PI, true);
    }

    public void Update()
    {
        //backWheel.applyLinearImpulse(new Vector2(1500f,0),backWheel.getWorldCenter(),true);
        //frontWheel.applyLinearImpulse(new Vector2(1500f,0),frontWheel.getWorldCenter(),true);
        backWheel.applyAngularImpulse(-4000f,true);//-4000 works
        frontWheel.applyAngularImpulse(-4000f,true);

        if (!isGround)
        {
            carBody.setAngularVelocity(-0.5f);//-0.5 looks ok
            isGround = false;
        }
    }

    public void HitGround() {
        isGround = true;
    }
}
