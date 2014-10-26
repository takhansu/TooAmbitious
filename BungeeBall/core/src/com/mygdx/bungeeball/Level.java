package com.mygdx.bungeeball;

import com.badlogic.gdx.Gdx;
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

public class Level implements Screen
{
    // comment just to test github. it will be removed later.
    BungeeBall game;

    private OrthographicCamera camera;
    World world;

    float gravity = -10f;

    SpriteBatch batch; // Object used for rendering graphics onto the screen

    float levelR;
    float levelG;
    float levelB;

    Ball player;
    Box box; // later perhaps this could be changed to a list of boxes so it is easy to keep track of any amount for a given level

    // initialize the level
    public Level(BungeeBall game)
    {
        this.game = game; // save a reference to the main BungeeBall class for ease of switching screens
        camera = new OrthographicCamera(800, 480); // initialize a camera to 800x480 "game units"
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

        /* CHANGED BY PETER 10-24-2014 */
        //Tell level to use BungieInputProcessor
        //(perhaps should be moved to game class later)
        BungieInputProcessor inputProcessor = new BungieInputProcessor();
        Gdx.input.setInputProcessor(inputProcessor);
        /* END                         */

        world = new World(new Vector2(0, gravity), true); // set up the world to handle physics

        /* CHANGED BY PETER 10-24-2014 */
        player = new Ball(world,-200, 0, 20); // make a new ball 200 game units to the left of the center
        /* END                         */

        box = new Box(world, 200f, 0f, 30f, 30f);
		ListenerClass listener = new ListenerClass();
		world.setContactListener(listener); //contact listener checks for collisions.
		
        levelR = 255;
        levelG = 255;
        levelB = 255;

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

    // the "main loop", game logic + graphics updating
    @Override
    public void render(float delta) {

        //Paints background
        Gdx.gl.glClearColor(levelR, levelG, levelB, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.step(Gdx.graphics.getDeltaTime(), 4, 4);

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
        // TODO Auto-generated method stub

    }
}


