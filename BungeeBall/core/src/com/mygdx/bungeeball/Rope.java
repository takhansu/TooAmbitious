package com.mygdx.bungeeball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.RopeJoint;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Rope {
	
	private World world;
	private float height = 15;
	private float width = 5;
	private int length;
	//Sprite ropeSprite;
	
	public Rope(World world, int length)
	{
		this.world = world;
		this.length = length;
	}
	
	public void attach(Box box, Ball ball) {
		createRope(box, ball);
	}
	
	
	public void detach() {
		// dispose rope
	}
	
	// The rope consists of Bodies and Joints holding them together
	private Body[] createRope(Box box, Ball ball) 
	{
		Body[] segments = new Body[length];
		RevoluteJoint[] joints = new RevoluteJoint[length - 1];
		RopeJoint[] ropeJoints = new RopeJoint[length - 1];

		// define the body type (static, dynamic, kinematic, etc.)
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;

		// define the body's shape (box)
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2, height / 2);

		// create the rope segments in the world, and make it a fixture
		for(int i = 0; i < segments.length; i++) {
			segments[i] = world.createBody(bodyDef);
			segments[i].createFixture(shape, 2);
		}

		shape.dispose();

		// To Do: find a way to make the joints elastic
		// To Do: attach the rope to the box.body and the ball.body
		
		// A revolute joint forces two bodies to share a common
		// anchor point, often called a hinge point. The revolute
		RevoluteJointDef jointDef = new RevoluteJointDef();
		jointDef.localAnchorA.y = -height / 2;
		jointDef.localAnchorB.y = height / 2;

		for(int i = 0; i < joints.length; i++) {
			jointDef.bodyA = segments[i];
			jointDef.bodyB = segments[i + 1];
			joints[i] = (RevoluteJoint) world.createJoint(jointDef);
		}

		// The rope joint restricts the maximum distance between
		// two points.This can be useful to prevent chains of
		// bodies from stretching, even under high load.
		RopeJointDef ropeJointDef = new RopeJointDef();
		ropeJointDef.localAnchorA.set(0, -height / 2);
		ropeJointDef.localAnchorB.set(0, height / 2);
		ropeJointDef.maxLength = height;

		for(int i = 0; i < ropeJoints.length; i++) {
			ropeJointDef.bodyA = segments[i];
			ropeJointDef.bodyB = segments[i + 1];
			ropeJoints[i] = (RopeJoint) world.createJoint(ropeJointDef);
		}

		return segments;
	}
	
	public void update(SpriteBatch batch)
	{
	}
}
