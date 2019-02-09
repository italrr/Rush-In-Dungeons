package com.nite.rid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;

class playerDiedT{
	String ttContinue = "Touch to restart";
	Vec2 ttConSize = new Vec2();
	Vec2 lvUpMsgDim = new Vec2();
	Vec2 Position = new Vec2();
	Vec2 realSize= new Vec2();
	Vec2 Size = new Vec2();
	long iniT; 
	boolean Started;
	boolean Actved = false;
	boolean Done = false;
	float bgAlpha = 0;
	long Snd = -1;
	boolean toOpen = true;
	letterByLetterT Title;
	dynamicWindowT Window;
	GameT Game;
	void End(){
		Done = false;
		Actved = false;
		Snd = -1;
		Title.End();
	}
	void Start(){
		iniT = Tools.getTicks();
		Started = false;
		Actved = true;
		bgAlpha = 0;
		Position.x = Game.windowSize.x/2 - Size.x/2;
		Position.y =  Game.windowSize.y/2 - Size.y/2;

		restartGame.Position.y =  Position.y + Size.y - restartGame.Size.y*1.5f;
		Share.Position.y =  Position.y + Size.x - restartGame.Size.y*1.5f;
		
		toOpen = true;
		Title.Start();
		Window.Start();
		Window.Open();
		Window.Position.Set(Position);
	}

	void Draw(){
	    Gdx.gl.glEnable(GL10.GL_BLEND);
	    Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
	        
   		Game.spriteBatch.begin();
		Game.spriteBatch.setColor(0f, 0f, 0f, bgAlpha/100f);
		Game.spriteBatch.draw(Game.whiteTexture.Region, 0, 0,	0, 0, Game.windowSize.x,  Game.windowSize.y,	1, 1,	0);
		Game.spriteBatch.setColor(1, 1, 1, 1);
		Game.spriteBatch.end();
	    
	    Window.Draw();
	    
	    if(!toOpen) return;
	    Game.spriteBatch.begin();
	    Title.Draw(Position.x + Size.x/2 - lvUpMsgDim.x/2, Position.y + lvUpMsgDim.y*.10f, 1, 0, 0, 1);
		if(Window.isOpen()){
			 //Game.drawText(ttContinue, Position.x + Size.x/2 - ttConSize.x/2, Position.y + Size.y - ttConSize.y*.20f, 1, 1, 1, 1);
			String Str;
			float X, Y, W, H;
			
			/* Turns */
			Str = "Turns";
			W =  Game.getStringWidth(Str);
			H =  Game.getStringHeight("AAA");
			X = Position.x + Size.x/4 - W/2;
			Y = Position.y + lvUpMsgDim.y*.10f  +  Game.disappearSize.x*3;
			Game.drawText(Str,  X, Y - H/2, 1, 1, 1, 1);
			Str = String.valueOf(Game.currentTurn);
			W =  Game.getStringWidth(Str);
			X = Position.x + Size.x/2 + Size.x/4 - W/2;
			Game.drawText(Str,  X, Y - H/2, 0, 0, 1, 1);
			
			/* Score */
			Str = "Score";
			W = Game.getStringWidth(Str);
			H = Game.getStringHeight("AAA");
			X = Position.x + Size.x/4 - W/2;
			Y = Position.y + lvUpMsgDim.y*.10f  + Game.disappearSize.x*2;
			Game.drawText(Str,  X, Y - H/2, 1, 1, 1, 1);
			Str = String.valueOf(Game.Score);
			W = Game.getStringWidth(Str);
			X = Position.x + Size.x/2 + Size.x/4 - W/2;
			Game.drawText(Str,  X, Y - H/2, 0, 1, 0, 1);
			
			/* Enemies killed */
			Str = "Killed";
			W = Game.getStringWidth(Str);
			H = Game.getStringHeight("AAA");
			X = Position.x + Size.x/4 - W/2;
			Y = Position.y + lvUpMsgDim.y*.10f  + Game.disappearSize.x*4;
			Game.drawText(Str,  X, Y - H/2, 1, 1, 1, 1);
			Str = String.valueOf(Game.enemyKilled);
			W = Game.getStringWidth(Str);
			X = Position.x + Size.x/2 + Size.x/4 - W/2;
			Game.drawText(Str,  X, Y - H/2, 1, 1, 0, 1);
			
			/* Your Best*/
			Str = "Best Score";
			W = Game.getStringWidth(Str);
			H = Game.getStringHeight("AAA");
			X = Position.x + Size.x/4 - W/2;
			Y = Position.y + lvUpMsgDim.y*.10f  + Game.disappearSize.x*5f;
			Game.drawText(Str,  X, Y - H/2, 1, 0, 0, 1);
			Str = String.valueOf(Game.yourBest);
			W = Game.getStringWidth(Str);
			X = Position.x + Size.x/2 + Size.x/4 - W/2;
			Game.drawText(Str,  X, Y - H/2, 1, 0, 0, 1);
		}		
		
		Game.spriteBatch.end();
		if(Window.isOpen()){
			Share.Update();
			restartGame.Update();
		}
		
		
	}
	boolean isDone(){
		return Done;
	}
	void Update(){
		if(!Actved) return;
		if (Tools.getTicks() - iniT > 30) Started = true;
		if(!Started) return;
		if(Snd == -1)Snd = Game.diedMusic.Play(); 
		bgAlpha = Tools.Translate(bgAlpha, 100, 0.02f);
		Window.Update();
		if(!toOpen && Window.isClose()){
			Done = true;
		}
		if(toOpen && Window.isOpen()){
			Title.Update();
			if(restartGame.isPressed()){
				toOpen = false;
				Window.Close();
			}
			if(Share.isPressed()){
				if(!Game.Social.isInit()){
					Game.Social.Init();
				}
				Game.Social.submitScore(Game.Score);
				Game.Social.showLeaderboard();
			}
			if (Gdx.input.isKeyPressed(Keys.BACK) && !goingMainMenu){
				goingMainMenu = true;
				Game.Transition.Transition();
				Game.saveEmptyGame("game.sav");
			}
			if(goingMainMenu && Game.Transition.isInBlack()){
				Game.Save("game.sav");
				Game.inStartScreen = true;
				Game.startScreen.Start();
				goingMainMenu = false;
			}
		}
		Draw();
	}
	boolean goingMainMenu = false;
	buttonT Share;
	buttonT restartGame;
	playerDiedT(GameT G){
		Game = G;
		Window = new dynamicWindowT(Game);
		Title = new letterByLetterT(Game);
		Title.setText("You're dead");
		Game.setFontSize(fontSize.Big);
		lvUpMsgDim = new Vec2(Game.getStringWidth(Title.toMessage), Game.getStringHeight(Title.toMessage));
		Game.setFontSize(fontSize.Normal);
		Size.Set(lvUpMsgDim.x *1.5f, lvUpMsgDim.y * 1.5f);
		Window.Size.Set(Size);
		ttConSize = new Vec2(Game.getStringWidth(ttContinue), Game.getStringHeight(ttContinue));
		Window.R = 1;
		Window.G = 0;
		Window.B = 0;
	
		
		restartGame = new buttonT(Game, "Restart", fontSize.Normal);
		restartGame.Position.Set(Position.x + Size.x*.30f - restartGame.Size.x/2, Window.Position.y);
		restartGame.R = 1; restartGame.G = 0; restartGame.B = 0;
		restartGame.BR = 0.05f; restartGame.BG = 0.05f; restartGame.BB = 0.05f;
		
		Share = new buttonT(Game, "Scores", fontSize.Normal, restartGame.Size.x, restartGame.Size.y);
		Game.setFontSize(fontSize.Normal);
		float W = Game.getStringWidth("Scores");
		Share.Position.Set(Position.x + Size.x*.70f - W/2, Window.Position.y);
		Share.R = 1; Share.G = 0; Share.B = 0;
		Share.BR = 0.05f; Share.BG = 0.05f; Share.BB = 0.05f;
		

		
	}
}
