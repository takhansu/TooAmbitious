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
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Ball 
{

	Body body;
	World world;
	float radius;
	//float mass;
	Sprite playerSprite;
	
	public Ball(World world, int x, int y, int r) 
	{
		
		this.world = world;
		/* CHANGED BY PETER 10-24-2014 */
		// Just changed this to have a variable radius at the start of a level
		// Maybe the player can start off big or small later on
		this.radius = r;
		/* END                         */
		
		Texture texture = new Texture(Gdx.files.internal("PlayerBall.png"), true); // sets sprite of ball with some filter
		texture.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);
		
		body = createCircle(BodyType.DynamicBody, radius);
		body.setTransform(x, y, 0);																	
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
		playerSprite.setPosition(body.getPosition().x - radius, body.getPosition().y - radius);
		playerSprite.draw(batch);
	}
	
	/* CHANGED by PETER 10-27-2014 */
	//Move ball by xincr and yincr
	public void move(SpriteBatch batch, int xincr, int yincr)
	{
		playerSprite.setPosition(body.getPosition().x - radius + xincr, body.getPosition().y - radius + yincr);
		playerSprite.draw(batch);
	}
	
	//Increase mass of ball, change must be either positive or negative (nonzero)
	public void changeMass(SpriteBatch batch, int change)
	{
		MassData data = body.getMassData();
		if(change > 0)
			data.mass = data.mass + 2;
		else data.mass = data.mass - 2;
		body.setMassData(data);
	}
	/* END CHANGED */
}
