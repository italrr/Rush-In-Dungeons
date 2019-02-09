package com.nite.rid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

class backgroundT {
	Texture Texture;
	TextureRegion Region;
	Vec2 Size;
	GameT Game;
	void Draw(){
		Game.spriteBatch.draw(Region, Game.windowSize.x, Game.windowSize.y, 0, 0, Game.windowSize.x, Game.windowSize.y, 1, 1, 180);
	}
	backgroundT(String File, boolean FlipX, boolean FlipY, boolean Smooth, float sX, float sY, GameT G){
		Game = G;
		Size = new Vec2(sX, sY);
		Texture = new Texture(Gdx.files.internal(File));
		Region  = new TextureRegion(Texture, 0, 0, (int)sX, (int)sY);
		Flip(FlipX, FlipY);
		setFilterSmooth(Smooth);
	}
	void Flip(boolean FlipX, boolean FlipY){
		Region.flip(FlipX, FlipY);		
	}
	void setFilterSmooth(boolean Smooth){
		Texture.setFilter(Smooth ? TextureFilter.Linear : TextureFilter.Nearest , Smooth ? TextureFilter.Linear : TextureFilter.Nearest);
	}
}
