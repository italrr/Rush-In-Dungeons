package com.nite.rid;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

public class RiD implements ApplicationListener {	
	public GameT Game;
	Social Social;
	@Override
	public void create() {
		Gdx.input.setCatchBackKey(true);
		Game = new GameT(Social);
	}

	@Override
	public void dispose() {
		if ((Game.Step == GameT.stepPlayerPick|| Game.Step == GameT.stepInGameMenu ||
			Game.Step == GameT.stepInGameEquip || Game.Step == GameT.stepInShop ||
			Game.Step == GameT.stepInSkill) && !Game.inLoadScreen  && !Game.inStartScreen){
			Game.Save("game.sav");
		}
		Game.saveSettings();
	}

	@Override
	public void render() {
		Game.Update();		
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
		
	}
	
	public RiD(Social Soc){
		Social = Soc;
	}
}
