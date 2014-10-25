package com.mygdx.bungeeball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

public class Level implements Screen 
{
	
	BungeeBall game;
	
	private OrthographicCamera camera;
	World world;
	private Box2DDebugRenderer renderer;
	
	float gravity = -9.81f;
	
	SpriteBatch batch; // Object used for rendering graphics onto the screen
	
	float levelR;
	float levelG;
	float levelB;

	Ball player;
	Box box; // later perhaps this could be changed to a list of boxes so it is easy to keep track of any amount for a given level
	Rope rope;
	
	// initialize the level
	public Level(BungeeBall game)
	{
		this.game = game; // save a reference to the main BungeeBall class for ease of switching screens
		camera = new OrthographicCamera(800, 480); // initialize a camera to 800x480 "game units"
		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);
		renderer = new Box2DDebugRenderer();
		
		world = new World(new Vector2(0, gravity), true); // set up the world to handle physics
		
		player = new Ball(world,-200, 0); // make a new ball 200 game units to the left of the center
		box = new Box(world, 200f, 0f, 30f, 30f);
		rope = new Rope(world, 20);
		rope.attach(box, player);

		levelR = 255;
		levelG = 255;
		levelB = 255;
	}

	// the "main loop", game logic + graphics updating
	@Override
	public void render(float delta) {
		
		//Paints background
		Gdx.gl.glClearColor(levelR, levelG, levelB, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		world.step(Gdx.graphics.getDeltaTime(), 4, 4);	
		renderer.render(world, camera.combined);
		
		batch.begin();
		player.update(batch);
		box.update(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		world.dispose();
		renderer.dispose();
	}
	
	

}
