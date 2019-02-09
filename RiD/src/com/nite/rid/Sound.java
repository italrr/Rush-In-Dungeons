package com.nite.rid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

class musicT {
	Music Music;
	musicT(String File){
		Music = Gdx.audio.newMusic(Gdx.files.internal(File));
	}
}

class soundT {
	Sound Sound;
	GameT Game;
	long Play(){
		long id = Sound.play();
		Sound.setVolume(id, Game.Volume);
		return id;
	}
	void setPitch(long id, float p){
		Sound.setPitch(id, p);
	}
	void setVolume(long id, float v){
		Sound.setVolume(id, v*Game.Volume);
	}
	void Stop(){
		Sound.stop();
	}
	soundT(GameT G, String File){
		Game = G;
		Sound = Gdx.audio.newSound(Gdx.files.internal(File));
	}
}