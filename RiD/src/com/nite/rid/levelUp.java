package com.nite.rid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;

class levelUpT{
	String ttContinue = "Touch to continue";
	Vec2 ttConSize = new Vec2();
	Vec2 lvUpMsgDim = new Vec2();
	Vec2 Position = new Vec2();
	Vec2 realSize= new Vec2();
	Vec2 Zero = new Vec2(0,0);
	Vec2 Size = new Vec2();
	long iniT; 
	boolean Started;
	boolean Actved = false;
	boolean Done = false;
	boolean Opened = false;
	boolean Close = false;
	long Snd = -1;
	GameT Game; 
	letterByLetterT Title;
	letterByLetterT Level;
	dynamicWindowT Window;
	boolean toOpen = true;
	void End(){
		Done = false;
		Actved = false;
		Snd = -1;
		Title.End();
		Level.End();
	}
	void Start(){
		iniT = Tools.getTicks();
		Started = false;
		Actved = true;
		Position.x = Game.windowSize.x/2 - Size.x/2;
		Position.y = Game.windowSize.y/2 - Size.y/2;
		toOpen = true;
		Title.Start();
		Window.Start();
		Window.Open();
		Window.Position.Set(Position);
		Level.setText("Level "+String.valueOf(Game.Player.Level+1));
		Level.Start();
	}
	void Draw(){
	    Gdx.gl.glEnable(GL10.GL_BLEND);
	    Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

	    Window.Draw();
	    
	    if(!toOpen) return;
	    Game.spriteBatch.begin();
	    Title.Draw(Position.x + Size.x/2 - lvUpMsgDim.x/2, Position.y + lvUpMsgDim.y*.10f, 1, 0, 0, 1);
	    Level.Draw(Position.x + Size.x/2 - Game.getStringWidth(Title.toMessage)/2, Position.y + lvUpMsgDim.y*.10f + Game.disappearSize.x*1.5f, 0, 0.635294f, 0.909803f, 1);
	    if(Window.isOpen()){
			String Curr;
			float W, X, Y;
			float Hy = Game.getStringHeight("AAA");
			Game.drawText(ttContinue, Position.x + Size.x/2 - ttConSize.x/2, Position.y + Size.y - ttConSize.y*.20f, 1, 1, 1, 1);
			/* New HP */
			Curr = String.valueOf(Game.Player.maxHP);
			W = Game.getStringWidth(Curr) + Game.disappearSize.x;
			X =	Position.x + Size.x/4;
			Y =	Position.y + lvUpMsgDim.y*.10f  + Game.disappearSize.x*3;
			Game.spriteBatch.draw(Game.heartTexture.Region, X, Y, 0, 0, Game.disappearSize.x, Game.disappearSize.y, 1, 1, 180);
			Game.drawText(Curr, X, Y - Hy/2, 1, 1, 1, 1);
			Curr = "+"+String.valueOf(Game.Player.newHealth);
			W = Game.getStringWidth(Curr);
			X =	Position.x + Size.x/2;
			Game.drawText(Curr, X - W/2, Y - Hy/2, 0, 1, 0, 1);
			W = Game.getStringWidth(Curr);
			Curr = "=";
			Game.drawText(Curr, X+W, Y - Hy/2, 1, 1, 1, 1);
			Curr = String.valueOf(Game.Player.maxHP + Game.Player.newHealth);
			W = Game.getStringWidth(Curr) + Game.disappearSize.x;
			X =	Position.x + Size.x/2 + Size.x/4;
			Game.drawText(Curr, X, Y - Hy/2, 1, 1, 1, 1);
			/* New Attack */
			Curr = String.valueOf(Game.Player.Attack);
			W = Game.getStringWidth(Curr) + Game.disappearSize.x;
			X =	Position.x + Size.x/4;
			Y =	Position.y + lvUpMsgDim.y*.10f  + Game.disappearSize.x*4;
			Game.spriteBatch.draw(Game.swordTexture.Region, X, Y, 0, 0, Game.disappearSize.x, Game.disappearSize.y, 1, 1, 180);
			Game.drawText(Curr, X, Y - Hy/2, 1, 1, 1, 1);
			Curr = "+"+String.valueOf(Game.Player.newAttack);
			W = Game.getStringWidth(Curr);
			X =	Position.x + Size.x/2;
			Game.drawText(Curr, X - W/2, Y - Hy/2, 0, 1, 0, 1);
			W = Game.getStringWidth(Curr);
			Curr = "=";
			Game.drawText(Curr, X+W, Y - Hy/2, 1, 1, 1, 1);
			Curr = String.valueOf(Game.Player.Attack + Game.Player.newAttack);
			W = Game.getStringWidth(Curr) + Game.disappearSize.x;
			X =	Position.x + Size.x/2 + Size.x/4;
			Game.drawText(Curr, X, Y - Hy/2, 1, 1, 1, 1);
			/* New Defense */
			Curr = String.valueOf(Game.Player.Defense);
			W = Game.getStringWidth(Curr) + Game.disappearSize.x;
			X =	Position.x + Size.x/4;
			Y =	Position.y + lvUpMsgDim.y*.10f  + Game.disappearSize.x*5;
			Game.spriteBatch.draw(Game.shieldTexture.Region, X, Y, 0, 0, Game.disappearSize.x, Game.disappearSize.y, 1, 1, 180);
			Game.drawText(Curr, X, Y - Hy/2, 1, 1, 1, 1);
			Curr = "+"+String.valueOf(Game.Player.newDefense);
			W = Game.getStringWidth(Curr);
			X =	Position.x + Size.x/2;
			Game.drawText(Curr, X - W/2, Y - Hy/2, 0, 1, 0, 1);
			W = Game.getStringWidth(Curr);
			Curr = "=";
			Game.drawText(Curr, X+W, Y - Hy/2, 1, 1, 1, 1);
			Curr = String.valueOf(Game.Player.Defense + Game.Player.newDefense);
			W = Game.getStringWidth(Curr) + Game.disappearSize.x;
			X =	Position.x + Size.x/2 + Size.x/4;
			Game.drawText(Curr, X, Y - Hy/2, 1, 1, 1, 1);
			/* New Skill */
			if(Game.Player.freeSkill != null){
				Curr = String.valueOf("Learned skill "+Game.Player.freeSkill.getName()+"!");
				Y =	Position.y + lvUpMsgDim.y*.10f  + Game.disappearSize.x*6;
				Game.drawText(Curr, Position.x + Size.x/2 - Game.getStringWidth(Curr)/2, Y - Hy/2, 0, 1, 0, 1);
			}
		}			
		Game.spriteBatch.end();
		
	}
	boolean isDone(){
		return Done;
	}
	void Update(){
		if(!Actved) return;
		if (Tools.getTicks() - iniT > 30) Started = true;
		if(!Started) return;
		if(Snd == -1) Snd = Game.levelUpMusic.Play();
		Window.Update();
		if(!toOpen && Window.isClose()){
			Done = true;
		}
		if(toOpen && Window.isOpen()){
			Title.Update();
			Level.Update();
			if(Gdx.input.justTouched()){
				toOpen = false;
				Window.Close();
			}
		}
		Draw();
	}
	levelUpT(GameT G){
		Game = G;
		Window = new dynamicWindowT(Game);
		Title = new letterByLetterT(Game);
		Title.setText("Level up!");
		Level = new letterByLetterT(Game);
		Level.fntSize = fontSize.Normal;
		Level.letterTime = 50;
		Game.setFontSize(fontSize.Big);
		lvUpMsgDim = new Vec2(Game.getStringWidth(Title.toMessage), Game.getStringHeight(Title.toMessage));
		Game.setFontSize(fontSize.Normal);
		Size = new Vec2(lvUpMsgDim.x * 2f, lvUpMsgDim.y * 2.15f);
		Window.Size.Set(Size);
		ttConSize = new Vec2(Game.getStringWidth(ttContinue), Game.getStringHeight(ttContinue));
		Window.R = 136f/255f;
		Window.G = 0;
		Window.B = 21f/255f;
	}
}