package com.mygdx.bungeeball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Box 
{

	public Body body;
	World world;
	
	float height;
	float width;
	
	Sprite boxSprite;
	
	public Box(World world, float x, float y, float width, float height)
	{
		
		this.width = width;
		this.height = height;
		
		body = createBox(world, width, height, 1);
		body.setTransform(x, y, 0);
		body.setUserData(this);
		
		Texture texture = new Texture(Gdx.files.internal("Box.png"), true);
		texture.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);
		boxSprite = new Sprite(texture, 128, 128);
		
		boxSprite.setSize(width, height);
		boxSprite.setOrigin(width / 2, height / 2);
	}
	
	private Body createBox(World world, float width, float height, float density) 
	{
		
		BodyDef definition = new BodyDef();
		definition.type = BodyType.KinematicBody;
		Body box = world.createBody(definition);

		PolygonShape poly = new PolygonShape();
		poly.setAsBox(width/2, height/2);
		box.createFixture(poly, density);
		poly.dispose();

		return box;
	}
	
	public void update(SpriteBatch batch)
	{
		
		boxSprite.setPosition(body.getPosition().x-width/2, body.getPosition().y-height/2);
		boxSprite.draw(batch);
	}
	
	
}
