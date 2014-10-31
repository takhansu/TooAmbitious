package com.mygdx.bungeeball;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class BungieInputProcessor 
   implements InputProcessor 
{
   @Override
   public boolean keyDown (int keycode) {
	  switch (keycode)
	  {
	  case Keys.UP:
		  break;
	  case Keys.DOWN:
		  break;
	  case Keys.LEFT:
		  break;
	  case Keys.RIGHT:
		  break;
	  default:
		  break;
	  }//end switch
      return true;
   }

   @Override
   public boolean keyUp (int keycode) {
      return false;
   }

   @Override
   public boolean keyTyped (char character) {
      return false;
   }

   @Override
   public boolean touchDown (int x, int y, int pointer, int button) {
      return false;
   }

   @Override
   public boolean touchUp (int x, int y, int pointer, int button) {
      return false;
   }

   @Override
   public boolean touchDragged (int x, int y, int pointer) {
      return false;
   }

   @Override
   public boolean mouseMoved (int x, int y) {
      return false;
   }

   @Override
   public boolean scrolled (int amount) {
      return false;
   }
}
