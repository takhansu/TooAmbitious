package com.mygdx.bungeeball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Ball 
{

	Body body;
	World world;
	float radius;
	//float mass;
	Sprite playerSprite;
	/* CHANGED BY PETER 10-24-2014 */
	int mass; //mass will be discrete at first
	//Variables for specifying position
	int xpos = 0, ypos = 0;
    /* END             */
	
	public Ball(World world, int x, int y, int r) 
	{
		
		this.world = world;
		/* CHANGED BY PETER 10-24-2014 */
		// Just changed this to have a variable radius at the start of a level
		// Maybe the player can start off big or small later on
		this.radius = r;
		/* END                         */
		xpos = x;
		ypos = y;
		
		Texture texture = new Texture(Gdx.files.internal("PlayerBall.png"), true); // sets sprite of ball with some filter
		texture.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);
		
		body = createCircle(BodyType.DynamicBody, radius);
		body.setTransform(xpos, ypos, 0);																	
		body.setUserData(this);
		
		playerSprite = new Sprite(texture, 128, 128);												
		playerSprite.setSize(radius * 2, radius * 2);
		playerSprite.setOrigin(radius, radius);
	}
	
	private Body createCircle(BodyType type, float radius) 
	{
		
		BodyDef definition = new BodyDef();
		definition.type = type;
		

		Body box = world.createBody(definition);

		Body ball = world.createBody(definition);
		
		CircleShape poly = new CircleShape();	
		
		poly.setRadius(radius);
		
		Fixture fixture = ball.createFixture(poly, 1);
		fixture.setFriction(1);
		fixture.setRestitution(0.75f);								//Makes circle more or less elastic
		fixture.setDensity(mass);
		
		poly.dispose();

		return ball;
	}
	
	public void update(SpriteBatch batch)
	{
		playerSprite.setPosition(body.getPosition().x - radius, body.getPosition().y-radius);
		playerSprite.draw(batch);
	}
	
}
