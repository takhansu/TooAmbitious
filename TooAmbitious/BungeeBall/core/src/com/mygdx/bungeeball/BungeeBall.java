package com.mygdx.bungeeball;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;

public class BungeeBall extends Game implements ApplicationListener
{
	
	@Override
	public void create () 
	{
		setScreen(new Level(this));
	}
	

}
