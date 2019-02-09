package com.nite.rid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

class textureT {
	public Texture Texture;
	public TextureRegion Region;
	textureT(textureT Tex, float ix, float iy, float w, float h, boolean FlipX, boolean FlipY, boolean Smooth){
		Texture = Tex.Texture;
		Region  = new TextureRegion(Texture, (int)((ix-1)*w), (int)((iy-1)*h), (int)w, (int)h);
		Region.flip(FlipX, FlipY);
		setFilterSmooth(Smooth);
	}
	textureT(String File, float ix, float iy, float w, float h, boolean FlipX, boolean FlipY, boolean Smooth){
		Texture = new Texture(Gdx.files.internal(File));
		Region  = new TextureRegion(Texture, (int)((ix-1)*w), (int)((iy-1)*h), (int)w, (int)h);
		Region.flip(FlipX, FlipY);
		setFilterSmooth(Smooth);
	}
	textureT(String File, boolean FlipX, boolean FlipY, boolean Smooth){
		Texture = new Texture(Gdx.files.internal(File));
		Region  = new TextureRegion(Texture, 0, 0, Texture.getWidth(), Texture.getHeight());
		Region.flip(FlipX, FlipY);
		setFilterSmooth(Smooth);
	}
	textureT(String File, boolean FlipX, boolean FlipY){
		Texture = new Texture(Gdx.files.internal(File));
		Region  = new TextureRegion(Texture, 0, 0, Texture.getWidth(), Texture.getHeight());
		Region.flip(FlipX, FlipY);
	}
	textureT(String File){
		Texture = new Texture(Gdx.files.internal(File));
		Region  = new TextureRegion(Texture, 0, 0, Texture.getWidth(), Texture.getHeight());
	}
	void Flip(boolean FlipX, boolean FlipY){
		Region.flip(FlipX, FlipY);		
	}
	void setFilterSmooth(boolean Smooth){
		Texture.setFilter(Smooth ? TextureFilter.Linear : TextureFilter.Nearest , Smooth ? TextureFilter.Linear : TextureFilter.Nearest);
	}
}

class enemyTextureT {
	textureT Face;
	textureT Normal;
	textureT Attack;
	textureT Defense;
	textureT Died;
	enemyTextureT(String N){
		Face	= new textureT(N);
		Normal 	= new textureT(Face, 4, 1, 64, 64, true, false, true);
		Attack 	= new textureT(Face, 1, 1, 64, 64, true, false, true);
		Defense = new textureT(Face, 2, 1, 64, 64, true, false, true);
		Died 	= new textureT(Face, 3, 1, 64, 64, true, false, true);
	}
}