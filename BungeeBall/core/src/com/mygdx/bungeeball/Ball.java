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
	Sprite playerSprite;
	
	public Ball(World world, int x, int y) 
	{
		
		this.world = world;
		this.radius = 20;
		
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
		
		CircleShape poly = new CircleShape();	
		
		poly.setRadius(radius);
		
		Fixture fixture = box.createFixture(poly, 1);
		fixture.setFriction(1);
		fixture.setRestitution(0.75f);								//Makes circle more or less elastic
		
		poly.dispose();

		return box;
	}
	
	public void update(SpriteBatch batch)
	{
		playerSprite.setPosition(body.getPosition().x - radius, body.getPosition().y-radius);
		playerSprite.draw(batch);
	}
	
}
