package com.nite.rid;


import com.badlogic.gdx.Gdx;

class buttonT {
	Vec2 Position = new Vec2();
	Vec2 Size = new Vec2();
	GameT Game;
	boolean Touched = false;
	long initT;
	long touchTimeout = 150;
	boolean isTouched = false;
	float Alpha;
	float texAlpha = 1;
	Vec2 vP = new Vec2(), Obj = new Vec2();
	String Str;
	int Font;
	Vec2 strSize = new Vec2();
	Vec2 textP = new Vec2();
	float Offset = 0;
	boolean Bypass = false;
	void justTouched(){
		initT = Tools.getTicks();
		Touched = true;
	}
	boolean Enable = true;
	void setEnable (boolean n){
		Enable = n;
	}
	buttonT(GameT G, String Text, int F){
		Game = G;
		Str = Text;
		Font = F;
		Game.setFontSize(F);
		strSize.Set(Game.getStringWidth(Str), Game.getStringHeight("A"));
		Size.Set(strSize.x*1.3f, strSize.y*2.0f);
		textP.Set(Size.x/2 - strSize.x/2, Size.y/2 - strSize.y/2);
		Game.setFontSize(fontSize.Normal);
		Offset = 3;
	}
	void setText(String Text){
		Str = Text;
		Game.setFontSize(Font);
		strSize.Set(Game.getStringWidth(Str), Game.getStringHeight("A"));
		Size.Set(strSize.x, strSize.y*1.8f);
		textP.Set(Size.x/2 - strSize.x/2, Size.y/2 - strSize.y/2);
		Game.setFontSize(fontSize.Normal);
	}
	buttonT(GameT G, String Text, int F, float w, float h){
		Game = G;
		Str = Text;
		Font = F;
		Game.setFontSize(F);
		strSize.Set(Game.getStringWidth(Str), Game.getStringHeight("A"));
		Size.Set(w, h);
		textP.Set(Size.x/2 - strSize.x/2, Size.y/2 - strSize.y/2);
		Game.setFontSize(fontSize.Normal);
		Offset = 3;
	}
	void setSize(Vec2 S){	
		setSize(S.x, S.y);
	}
	void setSize(float x, float y){
		Size.Set(x, y);
		textP.Set(Size.x/2 - strSize.x/2, Size.y/2 - strSize.y/2);
	}
	float R = 0, G = 0, B = 1;
	float BR = .05f, BG = .05f, BB = .05f;
	float TR = 1, TG = 1, TB = 1;
	void Draw(){
		Game.spriteBatch.begin();
		Game.spriteBatch.setColor(R, G, B, 1);
		Game.spriteBatch.draw(Game.whiteTexture.Region,	Position.x-Offset,	Position.y-Offset,	0, 0, Size.x+Offset*2, Size.y+Offset*2,	1, 1,	0);
		Game.spriteBatch.setColor(BR, BG, BB, 1);
		Game.spriteBatch.draw(Game.whiteTexture.Region,	Position.x,	Position.y,	0, 0, Size.x, Size.y,	1,	1,	0);
		Game.spriteBatch.setColor(1, 1, 1, 1);
		Game.spriteBatch.end();
	    Game.setFontSize(Font);
	    Game.spriteBatch.begin();
	    if(Enable)
	    	Game.drawText(Str, Position.x + textP.x, Position.y + textP.y + (Touched ? 1 : 0), TR, TG, TB, 1);
	    else
	    	Game.drawText(Str, Position.x + textP.x, Position.y + textP.y + (Touched ? 1 : 0), BR+0.08f, BG+0.08f, BB+0.08f, 1);
	    Game.spriteBatch.end();
	    Game.setFontSize(fontSize.Normal);
	}
	void Update(){
		if(Gdx.input.justTouched() && Enable){
			Obj.Set(Gdx.input.getX(), Gdx.input.getY());
			Game.unProject(Obj);
			vP.Set(Position.x + Size.x, Position.y + Size.y);
			if(Tools.isInRegion(Obj, Position, vP)) justTouched();
		}
		if(Tools.getTicks()-initT >= touchTimeout) Touched = false;
		if(Touched){
			Alpha = Tools.Translate(Alpha, 40, 0.08f);
		}else{
			Alpha = Tools.Translate(Alpha, 10, 0.08f);
		}
		Draw();
	}
	boolean isPress(){
		if(!Gdx.input.isTouched()) return false;
		Obj.Set(Gdx.input.getX(), Gdx.input.getY());
		Game.unProject(Obj);
		vP.Set(Position.x + Size.x, Position.y + Size.y);
		if(Tools.isInRegion(Obj, Position, vP)){
			return true;
		}
		return false;
	}
	boolean isPressed(){
		if(!Enable) return false;
		if(!Bypass){
			if(Game.showMessage) return false;
		}
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
