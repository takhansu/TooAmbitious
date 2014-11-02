package com.mygdx.bungeeball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
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
	private World world;
	private float radius;
	private float minMass;
	private float maxMass;
	private Sprite playerSprite;
	
	public Ball(World world, int x, int y, int r, float minMass, float maxMass) 
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
		
		this.minMass = minMass;
		this.maxMass = maxMass;
		this.body.getMassData().mass = minMass;
	}
	
	private Body createCircle(BodyType type, float radius) 
	{
		
		BodyDef definition = new BodyDef();
		definition.type = type;

		Body ball = world.createBody(definition);
		
		CircleShape poly = new CircleShape();	
		
		poly.setRadius(radius);
		
		Fixture fixture = ball.createFixture(poly, 1);
		fixture.setFriction(1);
		fixture.setRestitution(0.75f);								//Makes circle more or less elastic
		fixture.setDensity(minMass);

		poly.dispose();

		return ball;
	}
	
	public void update(SpriteBatch batch)
	{
		playerSprite.setPosition(body.getPosition().x - radius, body.getPosition().y - radius);
		updateColor();
		updateSize();
		playerSprite.draw(batch);
	}
	
	/* CHANGED by ERIC 10-28-2014 */
	// Change the color of the ball based on the mass
	// If the setColor function ends up being too expensive to be called every update,
	// consider creating a boolean value to be set in move(), and only call updateColor if the boolean is set
	// using 200 kg as the "midway" point for now
	// public void setColor(float r, float g, float b, float a)
	private void updateColor()
	{
		float mass = body.getMass();
		
		// Get a green value inversely proportional to mass, will make the ball more bright yellow at lower mass and more dark red at higher mass
		float green = 200.0f / mass;		
		if (green > 1.0f)
		{
			green = 1.0f;
		}
		if (mass <= 0.0f)
		{
			green = 0.0f;
		}
		
		playerSprite.setColor(1f, green, 1f, 1f);
		
	}
	/* END CHANGE */
	
	private void updateSize() {
		// Ref: you can’t scale a Box2D body -_-
		// Ref: you only need to recreate the fixture, not the whole body
		// 0. if body != null
		// 1. float mass = body.getMass(); set new radius based on new mass (100 mass/20 radius)
		// 2. destroy the current fixture
		// 3. update circle's radius/shape/texture based on new mass
		// 4. create/redefine the new fixture
		// 5. attach fixture to the body
	}
		
	/* CHANGED by PETER 10-27-2014 */
	//Move ball by xincr and yincr
	public void move(Vector2 force)
	{
		body.applyForceToCenter(force, true);
	}
	
	//Increase mass of ball, change must be either positive or negative (nonzero)
	public void changeMass(int change)
	{
		MassData newMass = body.getMassData();
		newMass.mass += change;
		if (newMass.mass < minMass) newMass.mass = minMass;
		if (newMass.mass > maxMass) newMass.mass = maxMass;
		this.body.setMassData(newMass); 
		
		System.out.printf("Object mass: %f\n", newMass.mass);
	}
	/* END CHANGED */
}
