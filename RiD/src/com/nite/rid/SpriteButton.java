package com.nite.rid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;

class spriteButtonT {
	Vec2 Position = new Vec2();
	Vec2 Size = new Vec2();
	GameT Game;
	textureT Texture;
	boolean Touched = false;
	long initT;
	long touchTimeout = 150;
	boolean isTouched = false;
	float Alpha;
	float texAlpha = 1;
	float R = 1, G = 1, B = 1;
	float TR = 0.11f, TG = 0.11f, TB = 0.11f;
	float minAlpha = 30;
	float maxAlpha = 100;
	Vec2 vP = new Vec2(), Obj = new Vec2();
	void justTouched(){
		initT = Tools.getTicks();
		Touched = true;
	}
	spriteButtonT(GameT G, textureT Face){
		Game = G;
		Texture = Face;
		if(Face == null) return;
		Size.x = Face.Texture.getWidth();
		Size.y = Face.Texture.getHeight();
	}
	boolean Selected = false;
	boolean Shadow = false;
	void Draw(){
		Gdx.gl.glEnable(GL10.GL_BLEND);
		Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);		
	
		Game.spriteBatch.begin();
		if(Selected)
			Game.spriteBatch.setColor(1, 0, 0, 1);
		else
			Game.spriteBatch.setColor(TR, TG, TB, 1);
		Game.spriteBatch.draw(Game.whiteTexture.Region, Position.x-3, Position.y-3,	0, 0,Size.x+6, Size.y+6,	1, 1,	0);
		Game.spriteBatch.setColor(1, 1, 1, 1);
		Game.spriteBatch.end();
		
		Game.spriteBatch.begin();
		Game.spriteBatch.setColor(0.20f, 0.20f, 0.20f, 1);
		Game.spriteBatch.draw(Game.whiteTexture.Region, Position.x, Position.y,	0, 0,Size.x, Size.y,	1, 1,	0);
		Game.spriteBatch.setColor(1, 1, 1, 1);
		Game.spriteBatch.end();
		
		Game.spriteBatch.begin();
		Game.spriteBatch.setColor(TR, TG, TB, Alpha/100f);
		Game.spriteBatch.draw(Game.whiteTexture.Region, Position.x, Position.y,	0, 0,Size.x, Size.y,	1, 1,	0);
		Game.spriteBatch.setColor(1, 1, 1, 1);
		Game.spriteBatch.end();
		
	    if(Texture == null) return;
	    Game.spriteBatch.begin();
	    if(Shadow){
	    	Game.spriteBatch.setColor(1, 0, 0, 0.35f);
	    	Game.spriteBatch.draw(Texture.Region, Position.x + Size.x + Size.x*.075f, Position.y + Size.y + Size.y*.075f, 0, 0, Size.x*Offset, Size.y*Offset, 1, 1, 180);
	    }
	    Game.spriteBatch.setColor(R,G,B,texAlpha);
		Game.spriteBatch.draw(Texture.Region, Position.x + Size.x, Position.y + Size.y, 0, 0, Size.x, Size.y, 1, 1, 180);
		Game.spriteBatch.setColor(1,1,1,1);
		Game.spriteBatch.end();

	}
	float Offset = 1.15f;
	void Update(){
		if(Gdx.input.justTouched()){
			Obj.Set(Gdx.input.getX(), Gdx.input.getY());
			Game.unProject(Obj);
			vP.Set(Position.x + Size.x, Position.y + Size.y);
			if(Tools.isInRegion(Obj, Position, vP)) justTouched();
		}
		if(Tools.getTicks()-initT >= touchTimeout) Touched = false;
		if(Touched){
			Alpha = Tools.Translate(Alpha, maxAlpha, 0.08f);
		}else{
			Alpha = Tools.Translate(Alpha, minAlpha, 0.08f);
		}
		Draw();
	}
	boolean isPressed(){
		if(!Gdx.input.justTouched()) return false;
		Obj.Set(Gdx.input.getX(), Gdx.input.getY());
		Game.unProject(Obj);
		vP.Set(Position.x + Size.x, Position.y + Size.y);
		if(Tools.isInRegion(Obj, Position, vP)){
			return true;
		}
		return false;
	}
}
