package com.mygdx.bungeeball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

public class Level implements Screen
{
	BungeeBall game;
	
	private OrthographicCamera camera;
	World world;
	private Box2DDebugRenderer renderer;
	
	float gravity = -10f;
	
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
		
		//Tell level to use BungieInputProcessor (later should be moved to game class)
		//BungieInputProcessor inputProcessor = new BungieInputProcessor();
		//Gdx.input.setInputProcessor(inputProcessor);
		
		world = new World(new Vector2(0, gravity), true); // set up the world to handle physics
		
		player = new Ball(world,-200, 0, 20); // make a new ball 200 game units to the left of the center
		box = new Box(world, 200f, 0f, 30f, 30f);
		rope = new Rope(world, 16);
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
		
		//Should move this into a switch statement
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			System.out.println("Move Left");
		    player.move(batch, -5, 0);
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			System.out.println("Move Right");
		    player.move(batch, 5, 0);
		}
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			System.out.println("Move up");
		    player.move(batch, 0, 5);
		}
		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			System.out.println("Move Down");
		    player.move(batch, 0, -5);
		}
		if (Gdx.input.isKeyPressed(Keys.U)) {
			System.out.println("Increase Mass (in kg");
		    player.changeMass(batch, 1);
		}
		if (Gdx.input.isKeyPressed(Keys.I)) {
			System.out.println("Decrease Mass (in kg)");
		    player.changeMass(batch, -1);
		}
		// temporary key to detach from and to delete the rope
		if (Gdx.input.isKeyPressed(Keys.D)) {
		    rope.delete();
		}
	}

	public class ListenerClass implements ContactListener {
        @Override
        public void endContact(Contact contact) {
        	//Accessed as the two objects seize to collide/overlap.
        	if (contact.getFixtureA().getBody().getUserData() instanceof Ball){
        		//Accessed when fixture A is the ball, and fixture B is something else.
        	} else {
        		//Accessed when fixture B is the ball, and fixture A is something else.
        	}
        }
        
        @Override
        public void beginContact(Contact contact) {
        	//Accessed as the two objects begin to collide/overlap.
        	if (contact.getFixtureA().getBody().getUserData() instanceof Ball){
        		//Accessed when fixture A is the ball, and fixture B is something else.
        	} else {
        		//Accessed when fixture B is the ball, and fixture A is something else.
        	}
        }

		@Override
		public void preSolve(Contact contact, Manifold oldManifold) {
			// TODO Auto-generated method stub
		}

		@Override
		public void postSolve(Contact contact, ContactImpulse impulse) {
			// TODO Auto-generated method stub
		}  
	};
        
    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    	world.dispose();
		renderer.dispose();
    }
}


