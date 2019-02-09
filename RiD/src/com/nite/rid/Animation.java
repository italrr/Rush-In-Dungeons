package com.nite.rid;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

class animationT {
	Vec2 framSize;
	float frameRate = 0.10f;
	float Current;
	boolean Once = false;
	public Texture Texture;
	ArrayList<TextureRegion> Frames = new ArrayList<TextureRegion>();
	TextureRegion Draw(){
		float F = Current;
		if(!Once){
			F = Current + frameRate;
			Current = F;
			F = Tools.Round(F) % Frames.size();
		}else{
			if(F<Frames.size()-1) F += frameRate;
			Current = F;
		}
		return Frames.get((int)F);
	}
	void Reset(){
		Current = 0;
	}
	void setSpeed(float s){
		frameRate = s;
	}
	animationT(String File, boolean FlipX, boolean FlipY, boolean Smooth, int frameN, float fX, float fY, float Speed){
		Texture = new Texture(Gdx.files.internal(File));
		frameRate = Speed;
		for(int i=0; i<frameN; ++i){
			TextureRegion R;
			R  = new TextureRegion(Texture, (int)(i*fX), 0, (int)fX, (int)fY);
			R.flip(FlipX, FlipY);
			Frames.add(R);
		}
		setFilterSmooth(Smooth);
	}
	void Flip(boolean FlipX, boolean FlipY){
		for(int i=0; i<Frames.size(); ++i){
			Frames.get(i).flip(FlipX, FlipY);	
		}	
	}
	void setFilterSmooth(boolean Smooth){
		Texture.setFilter(Smooth ? TextureFilter.Linear : TextureFilter.Nearest , Smooth ? TextureFilter.Linear : TextureFilter.Nearest);
	}
}
