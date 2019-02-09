package com.nite.rid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;

class sbT{
	private float Alpha = 0;
	public boolean sceneBackground = false;
	private static final float Speed = 0.30f;
	private float Full 		= 90;
	private float Empty  	= 0;	
	GameT Game;
	sbT(GameT G){
		Game = G;
	}
	public boolean isIn(){
		return Alpha == Full;
	}
	public boolean isOut(){
		return Empty == 0;
	}
	public void Update(){
		if(sceneBackground)
			Alpha = Tools.Translate(Alpha, Full, Speed);
		else
			Alpha = Tools.Translate(Alpha, Empty, Speed);
		if(Alpha == 0) return;
	    Gdx.gl.glEnable(GL10.GL_BLEND);
	    Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
	    
		Game.spriteBatch.begin();
		Game.spriteBatch.setColor(1f/45f, 1f/45f, 1f/45f, Alpha/100f);
		Game.spriteBatch.draw(Game.whiteTexture.Region,	Game.gridPosition.x, Game.gridPosition.y,	0, 0, Game.gridSize.x, Game.gridSize.y,	1, 1,	0);
		Game.spriteBatch.setColor(1, 1, 1, 1);
		Game.spriteBatch.end();
		
	}
}