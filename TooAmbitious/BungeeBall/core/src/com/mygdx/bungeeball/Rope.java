package com.mygdx.bungeeball;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.World;

public class Rope {
	
	private World world;
	private RevoluteJoint revJoint; // sets the hinge
	private DistanceJoint distJoint; // sets the distance/stretch properties
	private int restLength;
	private int maxLength;
	public boolean isEmpty;
	
	public Rope(World world, int restLength, int maxLength)
	{
		this.world = world;
		this.restLength = restLength;
		this.maxLength = maxLength;
		this.revJoint = null;
		this.distJoint = null;
		this.isEmpty = true;
	}
	
	public void attach(Box box, Ball ball) {
		createRope(box, ball);
	}
	
	public void attach(Ball ball, Box box) {
		createRope(box, ball);
	}
	
	public void delete() {
		if (revJoint != null) {
			world.destroyJoint(revJoint);
			revJoint = null;
		}
		if (distJoint != null) {
			world.destroyJoint(distJoint);
			distJoint = null;
		}
		this.isEmpty = true;
	}
	
	void createRope(Box box, Ball ball) {
		revJoint = setRevoluteJointse(box, ball);
		distJoint = setDistanceJoints(box, ball);
		isEmpty = false;
	}
	
	private RevoluteJoint setRevoluteJointse(Box box, Ball ball) {
		RevoluteJointDef revJointDef = new RevoluteJointDef();
		revJointDef.bodyA = box.body;
		revJointDef.bodyB = ball.body;
		revJointDef.localAnchorA.set(Vector2.Zero);
		revJointDef.localAnchorB.set(Vector2.Zero);
		revJointDef.collideConnected = false;
		
		RevoluteJoint revJoint = (RevoluteJoint) world.createJoint(revJointDef);
		isEmpty = false;
		
		return revJoint;
	}
	
	private DistanceJoint setDistanceJoints(Box box, Ball ball) {
		DistanceJointDef distJointDef = new DistanceJointDef();
		distJointDef.bodyA = box.body;
		distJointDef.bodyB = ball.body;
		distJointDef.localAnchorA.set(Vector2.Zero);
		distJointDef.localAnchorB.set(Vector2.Zero);
		distJointDef.collideConnected = false;
		
		distJointDef.length = restLength; // the max resting length of the joint
		distJointDef.frequencyHz = 2.0f; // the rate of oscillation/bounce, 1-5
		distJointDef.dampingRatio = 0.2f; // the rate of reaching equilibrium/rest, 0-1
		 
		DistanceJoint distJoint = (DistanceJoint) world.createJoint(distJointDef);
		isEmpty = false;
		
		return distJoint;
	}
	
	public void update(SpriteBatch batch)
	{
	}
}
