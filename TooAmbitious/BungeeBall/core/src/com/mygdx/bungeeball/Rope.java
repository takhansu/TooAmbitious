package com.mygdx.bungeeball;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.JointEdge;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;

public class Rope {
	
	private World world;
	private Body[] bodies;
	private float height;
	private float width;
	private int length;
	private final short GROUP_ROPE_SEGMENTS = -1;
	
	public boolean isEmpty;
	
	public Rope(World world, int length)
	{
		this.world = world;
		this.length = length;
		//this.height = 10;
		//this.width = 2;
		this.height = 4;
		this.width = 1;
		this.bodies = new Body[length];
		this.isEmpty = true;
	}
	
	public void attach(Box box, Ball ball) {
		bodies = createRope(box, ball);
	}
	
	public void attach(Ball ball, Box box) {
		bodies = createRope(box, ball);
	}
	
	public void delete() {
		if (!isEmpty) {
			for (int i = 0; i < bodies.length; i++) {
				Array<JointEdge> joints = bodies[i].getJointList();
				for (int j = 0; j < joints.size; j++) {
					world.destroyJoint(joints.get(j).joint);
				}
				world.destroyBody(bodies[i]);
				bodies[i] = null;
			}
		}
		this.isEmpty = true;
	}
	
	// The rope consists of Bodies and Joints holding them together
	private Body[] createRope(Box box, Ball ball) 
	{
		Body[] segments = new Body[length];

		// define the body type (static, dynamic, kinematic, etc.)
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		
		// define the body's shape (box)
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2, height / 2);

		// Create a fixture definition to apply to the segments
		// To Do: adjust the fields to make the joints more elastic
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 0;
		fixtureDef.friction = 0;
		fixtureDef.restitution = 0;
		fixtureDef.filter.groupIndex = GROUP_ROPE_SEGMENTS; // disables collision
		
		// create the rope segments in the world, and make it a fixture
		for(int i = 0; i < segments.length; i++) {
			segments[i] = world.createBody(bodyDef);
			segments[i].createFixture(fixtureDef);
		}

		shape.dispose();
		
		// Java can't pass by reference
		segments = setRevoluteJoints(box, ball, segments);
		segments = setDistanceJoints(box, ball, segments);
		
		this.isEmpty = false;
		
		return segments;
	}
	
	private Body[] setRevoluteJoints(Box box, Ball ball, Body[] segments) {
		// A revolute joint forces two bodies to share a common anchor point, often called a hinge point.
		RevoluteJoint[] joints = new RevoluteJoint[length - 1];
		RevoluteJointDef jointDef = new RevoluteJointDef();
		jointDef.localAnchorA.y = -box.height / 2;
		jointDef.localAnchorB.y = height / 2;

		// initial attachment of the rope to the box
		jointDef.bodyA = box.body;
		jointDef.bodyB = segments[0];
		joints[0] = (RevoluteJoint) world.createJoint(jointDef);
		
		jointDef.localAnchorA.y = -height / 2;
		
		// chain the rope segments
		for(int i = 0; i < joints.length; i++) {
			jointDef.bodyA = segments[i];
			jointDef.bodyB = segments[i + 1];
			joints[i] = (RevoluteJoint) world.createJoint(jointDef);
		}
		
		// finally, attach the rope's end to the ball
		jointDef.bodyA = segments[length - 2];
		jointDef.bodyB = ball.body;
		joints[length - 2] = (RevoluteJoint) world.createJoint(jointDef);
	 	
		return segments;
	}

	private Body[] setDistanceJoints(Box box, Ball ball, Body[] segments) {
		DistanceJoint[] distJoints = new DistanceJoint[length - 1];
		DistanceJointDef distJointDef = new DistanceJointDef();
		
		// set the initial anchor point (-box.height / 2)
		distJointDef.localAnchorA.set(0, -box.height / 2);
		distJointDef.localAnchorB.set(0, height / 2);
		
		// set the joint's properties
		distJointDef.dampingRatio = 0.1f;//.2f; // the rate of reaching equilibrium/rest, 0-1
		distJointDef.frequencyHz = 2f;//3; // the rate of oscillation/bounce, 1-5
		//distJointDef.length = 1; // the max resting length of the joint
		
		// initial attachment of the rope to the box
		distJointDef.bodyA = box.body;
		distJointDef.bodyB = segments[0];
		distJoints[0] = (DistanceJoint) world.createJoint(distJointDef);
		
		// redefine the joint's anchor point to the height of one rope segment
		distJointDef.localAnchorA.set(0, -height / 2);
		
		// chain the rope segments
		for(int i = 0; i < distJoints.length; i++) {
			distJointDef.bodyA = segments[i];
			distJointDef.bodyB = segments[i + 1];
			distJoints[i] = (DistanceJoint) world.createJoint(distJointDef);
		}
		
		// finally, attach the rope's end to the ball
		distJointDef.bodyA = segments[length - 2];
		distJointDef.bodyB = ball.body;
		distJoints[length - 2] = (DistanceJoint) world.createJoint(distJointDef);
		
		return segments;
	}
}
