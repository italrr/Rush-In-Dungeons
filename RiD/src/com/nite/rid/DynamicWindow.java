package com.nite.rid;

class dynamicWindowT {
	Vec2 Position = new Vec2();
	Vec2 realSize = new Vec2();
	Vec2 Size = new Vec2();
	GameT Game;
	float R = 0, G = 0.46666f, B = 0.56862f;
	boolean toOpen = true;
	float Offset = 0;
	dynamicWindowT(GameT G){
		Game = G;
		Offset = 4;
	}
	void Draw(){
   		Game.spriteBatch.begin();
		Game.spriteBatch.setColor(R, G, B, 1);
		Game.spriteBatch.draw(Game.whiteTexture.Region, Position.x - Offset, Position.y - Offset,	0, 0, realSize.x + Offset*2, realSize.y + Offset*2,	1, 1,	0);
		Game.spriteBatch.setColor(1, 1, 1, 1);
		Game.spriteBatch.end();
		
   		Game.spriteBatch.begin();
		Game.spriteBatch.setColor(.05f, .05f, .05f, 1f);
		Game.spriteBatch.draw(Game.whiteTexture.Region, Position.x, Position.y,	0, 0, realSize.x, realSize.y,	1, 1,	0);
		Game.spriteBatch.setColor(1, 1, 1, 1);
		Game.spriteBatch.end();
	    
	}
	void Update(){
		if(toOpen){
			realSize.Translate(Size, 0.25f);
		}else{
			realSize.x = Tools.Translate(realSize.x, 0, 0.25f); 
		}
	}
	boolean isOpen(){
		return Size.x == realSize.x;
	}
	boolean isClose(){
		return realSize.x == 0;
	}
	boolean isDone(){
		return toOpen ? Size.x == realSize.x : realSize.x == 0;
	}
	void Close(){
		toOpen = false;
	}
	void Open(){
		toOpen = true;
	}
	void Start(){
		realSize.x 	= 0;
		realSize.y 	= Size.y;
	}
	void setSize(float w, float h){
		Size.x 		= w;
		Size.y 		= h;
		realSize.x	= 0;
		realSize.y 	= h;
	}
	void setPosition(float x, float y){
		Position.x = x;
		Position.y = y;
	}
}
