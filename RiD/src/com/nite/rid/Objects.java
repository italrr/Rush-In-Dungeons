package com.nite.rid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

class playerAttackType {
	public static int Normal 	= 0;
	public static int Skill 	= 1;
}

class battleState {
	static final int None 		= 0;
	static final int Burn 		= 1;
	static final int Wet 		= 2;
	static final int Cold 		= 3;
	static final int Slash 		= 4;
	static final int Spine 		= 5;
	static final int Bubbles 	= 6;
}

class objectType {
	public static int Unknown 		= -1;
	public static int Sword 		= 0;
	public static int Shield		= 1;
	public static int Monster		= 2;
	public static int Coin			= 3;
	public static int Heart			= 4;
	public static int disappear		= 5;
	public static int Volatile		= 6;
	public static int Boss			= 7;
}

class elementType {
	public static int Normal	= 0;
	public static int Fire		= 1;
	public static int Water		= 2;
	public static int Grass		= 3;
	/* Effective Factor */
	class effFactor {
		public static final int Uneffective 	= -1;
		public static final int Normal		 	= 0;
		public static final int Effective		= 1;
	}
	/* Get damage based on the factor */
	static int getDamage(int efF, int Damage){
		switch (efF){
			case effFactor.Uneffective:
				Damage *= 0.5;
			case effFactor.Effective:
				Damage *= 2.0;
		}
		return Damage;
	}
	static int getRandomElement(){
		return Tools.Random(1, 4)-1;
	}
	static String getElementName(int e){
		if(e == 0) return "Normal";
		if(e == 1) return "Fire";
		if(e == 2) return "Water";
		if(e == 3) return "Grass";
		return "NULL";
	}
	/* Check if an attack to a target is effective */
	static int isEffective(int Attack, int Target){
		if(Attack == Fire && Target == Grass) 	return effFactor.Effective;
		if(Attack == Fire && Target == Water) 	return effFactor.Uneffective;
		if(Attack == Fire && Target == Fire) 	return effFactor.Uneffective;
		if(Attack == Water && Target == Fire) 	return effFactor.Effective;
		if(Attack == Water && Target == Grass)	return effFactor.Uneffective;
		if(Attack == Water && Target == Water)	return effFactor.Uneffective;
		if(Attack == Grass && Target == Water)	return effFactor.Effective;
		if(Attack == Grass && Target == Grass)	return effFactor.Uneffective;
		if(Attack == Grass && Target == Fire) 	return effFactor.Uneffective;
		return effFactor.Normal;
	}
}

class monsterState {
	static final int Normal 	= 0;
	static final int Attack 	= 1;
	static final int Defense 	= 2;
}

class monsterTypeT {
	String Name;
	enemyTextureT Common;
	enemyTextureT Boss;
	int Element;
	soundT Died;
	monsterTypeT(String Nam, enemyTextureT Com, enemyTextureT B, soundT Di, int El){
		Name 	= Nam;
		Common	= Com;
		Boss	= B;
		Element = El;
		Died	= Di;
	}
}


class objectT {
	int Type = objectType.Unknown;
	void Update(){
	}
	boolean isGridObject(){
		return Type != objectType.Unknown && Type != objectType.Volatile && Type != objectType.disappear && Type != objectType.Boss;
	}
}

class effectTextT extends objectT{
	GameT Game;
	float Alpha;
	int fontType = fontSize.Normal; 
	Vec2 Position = new Vec2();
	Vec2 Target = new Vec2();
	int Index;
	String Str;
	float w, h;
	float R, G, B;
	float Speed = 0.05f;
	Vec2 FS = new Vec2();
	textureT Face = null;
	effectTextT(GameT Ga, String Text, float x, float y, float Re, float Gr, float Bl, int Font, float Sp){
		Game = Ga;
		Position.Set(x, y);
		Target.Set(x, y - 128);
		Str = Text;
		R = Re;
		G = Gr;
		B = Bl;
		fontType = Font;
		Speed = Sp;
		fixPosition();
	}
	effectTextT(GameT Ga, String Text, textureT F, float x, float y, float Re, float Gr, float Bl, int Font, float Sp){
		Game = Ga;
		Position.Set(x, y);
		Target.Set(x, y - 128);
		Str = Text;
		R = Re;
		G = Gr;
		B = Bl;
		fontType = Font;
		Speed = Sp;
		Face = F;
		fixPosition();
	}
	void fixPosition(){
		Game.setFontSize(fontType);
		strW = Game.getStringWidth(Str);
		w = strW + (Face != null ? FS.x : 0);
		if(Position.x + w/2 > Game.windowSize.x){
			float p = ((Position.x + w/2)-Game.windowSize.x)*1.1f;
			Position.x -= p;
			Target.x -= p;
		}else
		if(Position.x - w/2 < 0){
			float p = (Position.x - w/2)*-1.1f;
			Position.x += p;
			Target.x +=  p;
		}
	}
	float strWA,strW, h2;
	void Draw(){
		Game.setFontSize(fontType);
		strWA = Game.getStringWidth("A");
		strW = Game.getStringWidth(Str);
		FS.Set(strWA*2, strWA*2);
		w = strW + (Face != null ? FS.x : 0);
		h = Game.getStringWidth("AAA");
		h2 = Game.getStringWidth("A");
		Game.drawText(Str, Position.x - w/2, Position.y-h/2, R, G, B, 1);
		Game.setFontSize(fontSize.Normal);
		if(Face != null){
			Game.spriteBatch.draw(Face.Region,
					Position.x - FS.x/2 + strW + FS.x/2,
					Position.y - FS.y/2 + h2,
					0,
					0,
					FS.x,
					FS.y,
					-1,
					-1,
					0);
		}
	}
	void Update(){
		if(!Position.Translate(Target, Gdx.input.isTouched() ? Speed/2 : Speed )) Game.Objects[Index] = null;
		Draw();
	}
}

class disappearT extends objectT {
	Vec2 Position   	= new Vec2(0, 0);
	Vec2 Target			= new Vec2(0, 0);
	Vec2 Size			= new Vec2(0, 0);
	Vec2 reachSize		= new Vec2(0, 0);
	float origDist 		= 0;
	float Angle 		= 0;
	boolean reachMsg 	= false;
	int	Index			= -1;
	textureT Texture;
	long Init;
	long timeOut = 100;
	GameT Game;
	boolean Just = false;
	void Draw(){	
		Game.spriteBatch.draw(Texture.Region,
				Position.x + Size.x/2,
				Position.y + Size.y/2,
				Size.x/4,
				Size.y/4,
				Size.x,
				Size.y,
				-1,
				-1,
				0);
	}
	disappearT(GameT G, Vec2 P, int T){
		Game = G;
		Type = T;
		if(Type == objectType.Sword){
			Texture	= Game.swordTexture;
			Target.Set(Game.Hud.objectPickSword);
		}else
		if(Type == objectType.Shield){
			Texture	= Game.shieldTexture;
			Target.Set(Game.Hud.objectPickShield);
		}else
		if(Type == objectType.Coin){
			Texture	= Game.coinTexture;
			Target.Set(Game.Hud.objectPickCoin);
		}else
		if(Type == objectType.Heart){
			Texture	= Game.heartTexture;
			Target.Set(Game.Hud.objectPickHeart);
		}
		Target.x -= G.Grid.objectSize.x/2.0f;
		Target.y -= G.Grid.objectSize.x/2.0f;
		Target.x -= Game.disappearSize.x/4f;
        Target.y -= Game.disappearSize.y/4f;
		Size.Set(Game.gridObjectSize);
		origDist = Tools.getDistance(P, Target);
		Position.Set(P);
		Position.x += Game.gridPosition.x + G.Grid.objectSize.x/2.0f - Size.x/2f;
		Position.y += Game.gridPosition.y + G.Grid.objectSize.y/2.0f - Size.y/2f;
		Init = Tools.getTicks();
		++Game.toDestroydisappears;
	}
	
	void Update(){
		Draw();
		Position.Translate(Target, Gdx.input.isTouched() ? 0.08f*2 : 0.08f);
		if((Tools.getTicks()-Init)/2>timeOut){
			Size.Translate(Game.disappearSize, 0.25f);
			if(!Just){
				Just = true;
			}
		}
		if(Tools.getTicks()-Init>timeOut && !reachMsg){
			Game.playerPick.ppDoNext = true;
			reachMsg = true;
			if(Type == objectType.Sword){
				++Game.Player.currentAttack;
				long id = Game.swordPickSFX.Play();
				Game.swordPickSFX.setVolume(id, 0.5f);
				Game.swordPickSFX.setPitch(id, 0.8f);
			}else
			if(Type == objectType.Shield){
				Game.Player.addDefense(1);
				long id = Game.shieldPickSFX.Play();
				Game.shieldPickSFX.setVolume(id, 0.5f);
				Game.shieldPickSFX.setPitch(id, 1f);
			}else
			if(Type == objectType.Coin){
				++Game.Player.Money;
				long id = Game.coinPickSFX.Play();
				Game.coinPickSFX.setVolume(id, 0.5f);
				Game.coinPickSFX.setPitch(id, 0.8f);
			}else
			if(Type == objectType.Heart){
				Game.Player.addHealth(1);
				long id = Game.heartPickSFX.Play();
				Game.heartPickSFX.setVolume(id, 1f);
				Game.heartPickSFX.setPitch(id, 0.8f);
			}
		}
		if(Position.x == Target.x && Position.y == Target.y){
			Game.Score += 1;
			--Game.toDestroydisappears;
			Game.disappearObjects[Index] = null;
		}
	}
	
}


class gridObjectT extends objectT{
	Vec2 Position 		= new Vec2(0, 0);
	Vec2 realPosition   = new Vec2(0, 0);
	Vec2 nextPosition	= new Vec2(0, 0);
	int	Index			= -1;
	int gameIndex		= -1;
	float Angle 		= 0;
	GameT Game;
	
	void Move(){
		if(!Game.objectsMoveDown) return;
		if(Position.y + Game.gridObjectSize.y >= Game.gridSize.y) return;
		nextPosition.Set(Position.x, Position.y + Game.gridObjectSize.y);
		if(Game.Grid.isEmpty(nextPosition)){
			Position.y += Game.gridObjectSize.y;
		}
	}
	int updateGrid(){
		return Game.Grid.updateObject(this, Index);
	}
	void setPosition(Vec2 P){
		Position = P;
		realPosition.x = P.x;
		realPosition.y = P.y;
	}
}

class pickObjectT extends gridObjectT {
	Vec2 realSize		= new Vec2(0, 0);
	textureT Texture;
	int Times = 1;
	Vec2 timesP = new Vec2();
	void Draw(){
		Game.spriteBatch.setColor(0, 0, 0, 0.35f);	
		Game.spriteBatch.draw(Texture.Region,
				Game.gridPosition.x + realPosition.x + Game.gridObjectSize.x/2,
				Game.gridPosition.y + realPosition.y + Game.gridObjectSize.y/2,
				realSize.x/4 + realSize.x*.035f,
				realSize.y/4 + realSize.y*.035f,
				realSize.x + realSize.x*.05f,
				realSize.y + realSize.y*.05f,
				-1,
				-1,
				0);
		Game.spriteBatch.setColor(1, 1, 1, 1);
		if(Game.Step == GameT.stepPlayerPick && Game.playerPick.ppTouches.size()>=1 && Game.playerPick.ppType != Type &&
				!(Game.playerPick.ppType == objectType.Monster && Type == objectType.Sword)){
			Game.spriteBatch.setColor(.30f, .30f, .30f, 1);
		}
		Game.spriteBatch.draw(Texture.Region,
				Game.gridPosition.x + realPosition.x + Game.gridObjectSize.x/2,
				Game.gridPosition.y + realPosition.y + Game.gridObjectSize.y/2,
				realSize.x/4,
				realSize.y/4,
				realSize.x,
				realSize.y,
				-1,
				-1,
				0);
		if(Times > 1){
			//Game.setFontSize(fontSize.Small);
			String Str = String.valueOf(Times);
			timesP.x = Game.gridPosition.x + realPosition.x + Game.gridObjectSize.x - Game.getStringWidth(Str)*1.5f;
			timesP.y = Game.gridPosition.y + realPosition.y + Game.gridObjectSize.y - Game.getStringHeight("AAA")/1.6f;
			Game.drawText("x"+Str, timesP, 0.5f, 0.5f, 0.5f, 1);
			//Game.setFontSize(fontSize.Normal);
		}
	}
	Vec2 Biggie = new Vec2();
	boolean initBiggie = false;		

	void Update(){
		if(!initBiggie){
			Biggie.Set(Game.gridObjectSize);
			Biggie.x *= 1.15f;
			Biggie.y *= 1.15f;
			initBiggie = true;
		}
		realPosition.Translate(Position, Gdx.input.isTouched() ? 0.135f*2 : 0.135f);
		if(Game.playerPick.isInPPTouches(Index))
			realSize.Translate(Biggie, 0.45f);
		else
			realSize.Translate(Game.gridObjectSize, 0.45f);
		Move();
		Draw();
		Index = updateGrid();
	}
}

class swordT extends pickObjectT {
	swordT(GameT G){
		Game = G;
		Texture	= Game.swordTexture;
		Type = objectType.Sword;
	}
}

class shieldT extends pickObjectT {
	shieldT(GameT G){
		Game = G;
		Texture	= Game.shieldTexture;
		Type = objectType.Shield;
	}
}

class coinT extends pickObjectT {
	coinT(GameT G){
		Game = G;
		Texture	= Game.coinTexture;
		Type = objectType.Coin;
	}
}	


class heartT extends pickObjectT {
	heartT(GameT G){
		Game = G;
		Texture	= Game.heartTexture;
		Type = objectType.Heart;
	}
}

class monsterDiedT extends objectT {
	Vec2 realPosition  	= new Vec2(0, 0);
	Vec2 realSize		= new Vec2(0, 0);
	Vec2 reachSize		= new Vec2(0, 0);
	Vec2 Inertia		= new Vec2(0, 0);
	Vec2 realInertia	= new Vec2(0, 0);
	float Angle 		= 0;
	int	Index			= -1;
	textureT Texture;
	GameT Game;
	
	void Draw(){	
		Game.spriteBatch.draw(Texture.Region,
				realPosition.x + Game.gridObjectSize.x/2,
				realPosition.y + Game.gridObjectSize.y/2,
				realSize.x/4,
				realSize.y/4,
				realSize.x,
				realSize.y,
				-1,
				-1,
				Angle);
	}

	
	monsterDiedT(GameT G, Vec2 P, textureT T){
		Game = G;
		realSize.Set(Game.gridObjectSize);
		reachSize.x = realSize.x*1.20f;
		reachSize.y = realSize.y*1.20f;
		realPosition.Set(P);
		realPosition.x += Game.gridPosition.x;
		realPosition.y += Game.gridPosition.y;
		realInertia.y = -8;
		if(realPosition.x < Game.windowSize.x/2){
			Angle = 45;
			realInertia.x = 15;
			Inertia.y = 10;
		}else{
			Angle = -45;
			realInertia.x = -15;
			Inertia.y = 10;
		}
		Texture = T;
		Game.Score += 5;
	}
	
	void Update(){		
		realSize.Translate(reachSize, 0.30f);
		if(realPosition.y > Game.windowSize.y + Game.Grid.objectSize.y){
			Game.Objects[Index] = null;
		}
		realInertia.Translate(Inertia, 0.15f);
		realPosition.x += realInertia.x;
		realPosition.y += realInertia.y;
		Draw();
	}
}

class monsterT extends gridObjectT {
	public String Name;
	public int Element;
	public int Health;
	public int initHealth;
	public int Attack;
	public int Defense;
	long initT;
	boolean Tgger;
	int State = monsterState.Normal;
	int Level = 1;
	int initTurn = 1;
	int currentDamage   = 0;
	boolean Done = false;
	boolean Just = false;
	int attackType = playerAttackType.Normal;
	int currentState = 0;
	boolean isFrozen = false;
	int frozenTimeout = 200;
	int frozeTurn = 0;
	int Money = 0;
	boolean Boss = false;
	monsterTypeT Self;
	textureT texAttack;
	textureT texDefense;
	textureT texNormal;
	textureT texDied;
	textureT Current 	= null;
	Vec2 realSize  		= new Vec2(0, 0);
	Vec2 healthP		= new Vec2(0, 0);
	void setBattleState(int S){
		Game.slashEffect.Reset();
		currentState = S; 
	}
	void drawBattleState(float x, float y){
		if(currentState == battleState.None) return; 
		TextureRegion P = Game.fireballEffect.Draw();
		if(currentState == battleState.Burn){
			Game.spriteBatch.setColor(1, 1, 1, 0.5f);
		}
		if(currentState == battleState.Cold){
			P = Game.blizzardEffect.Draw();
			Game.spriteBatch.setColor(1, 1, 1, 0.5f);
		}
		if(currentState == battleState.Slash){
			P = Game.slashEffect.Draw();
			Game.spriteBatch.setColor(1, 1, 1, 1f);
		}
		if(currentState == battleState.Spine){
			P = Game.spineEffect.Draw();
			Game.spriteBatch.setColor(1, 1, 1, 1f);
		}
		if(currentState == battleState.Bubbles){
			P = Game.bubblesEffect.Draw();
			Game.spriteBatch.setColor(1, 1, 1, 0.5f);
		}
		
		Game.spriteBatch.draw(P, x,	y, realSize.x/4, realSize.y/4, realSize.x, realSize.y, -1, -1, 0);
		Game.spriteBatch.setColor(1, 1, 1, 1f);
	}
	public void doAttack(){
		initT 	= Tools.getTicks();
		State 	= monsterState.Attack; 
		Done    = false;
		Just 	= false;
	}
	private void changeToDied(){
		Current = Health == 0 ? texDied : Current;
	}
	private void Attacking(){
		if(State != monsterState.Attack) return;
		long T = Tools.getTicks()-initT;
		if(Gdx.input.isTouched()) T *= 2;
		if(!Just){
			long id = Game.enemyAttackSFX.Play();
			Game.enemyAttackSFX.setVolume(id, 0.5f);
			Game.enemyAttackSFX.setPitch(id, 1.3f);
			Just = true;
		}
		if(T >= 75){
			realSize.x = Game.gridObjectSize.x*1.20f;
			realSize.y = Game.gridObjectSize.y*1.20f;
			Current = texAttack;
			changeToDied();
		}
		if(T >= 75 && T <= 275){
			realPosition.x = (float) (Position.x + (Math.random()*5 - 5));
			realPosition.y = (float) (Position.y + (Math.random()*5 - 5));
		}
		if(T >= 475){
			Done = true;
			State = monsterState.Normal;
			Current = texNormal;
			changeToDied();
			Just = false;
		}		
	}
	
	public void doDefense(int Damage){
		initT 		= Tools.getTicks();
		State 		= monsterState.Defense; 
		Done    	= false;
		attackType	= playerAttackType.Normal;
		doDamage(Damage);
	}
	
	public void doDefenseSkill(int Damage){
		initT 		= Tools.getTicks();
		State 		= monsterState.Defense; 
		Done    	= false;
		attackType	= playerAttackType.Skill;
		doDamage(Damage);
	}
	
	private void Defending(){
		if(State != monsterState.Defense || Done == true) return;
		long T = Tools.getTicks()-initT;
		long Q = 0;
		if(Gdx.input.isTouched()) T *= 2;
		if(!Just && attackType == playerAttackType.Normal){
			long id = Game.playerAttackSFX.Play();
			Game.playerAttackSFX.setVolume(id, 0.5f);
			Game.playerAttackSFX.setPitch(id, 1.1f);
			Just = true;
		}else{
			Q = 150;
		}
		if(T >= 75){
			Current = texDefense;
			changeToDied();
		}
		if(T >= 75+Q && T <= 275+Q){
			realPosition.x = (float) (Position.x + (Math.random()*5 - 5));
			realPosition.y = (float) (Position.y + (Math.random()*5 - 5));
		}
		if(T >= 475+Q){
			Done = true;
			State = monsterState.Normal;
			Current = texNormal;
			changeToDied();
			Just = false;
		}
	}
	
	public boolean isDead(){
		return Health<=0;
	}
	
	public boolean isDone(){
		return Done;
	}
	
	public int getState(){
		return State;
	}
	
	public void doDamage(int Dmg){
		Dmg -= Defense;
		//Dmg = 30000;
		if(Dmg<0) Dmg = 0;
		currentDamage = Dmg;
		boolean plusScore = false;
		if(initHealth == Health && Dmg >= Health){
			Game.Score += 20;
			Tools.Print("Killed once!");
			plusScore = true;
			Game.createEffectText("SCORE +20", Game.gridPToP(Position), 0, 1f, 0, fontSize.Normal, 0.08f);
		}
		Health -= Dmg;
		if(Health<0) Health = 0;
		if(Health == 0){
			if (Boss){
				Game.changeStage();
				Game.Social.giveAchivement(com.nite.rid.Social.achBossKiller);
			}
			if(Money>0){
				Vec2 P = Game.gridPToP(Position);
				if(plusScore){
					P.y -= Game.getStringHeight("A")*1.5f;
				}
				Game.createEffectText("+"+String.valueOf(Money), Game.coinTexture, P, 1, 1, 0, fontSize.Normal, 0.08f);
			}
			Game.Player.Money += Money;
			++Game.enemyKilled;
			++Game.totalMonstersKilled;

			isFrozen = false;
			if(Game.currentTurn - initTurn == 1){
				Game.Score += 30;
			}else	
			if(Game.currentTurn - initTurn == 2){
				Game.Score += 15;
			}
		}	
	}
	
	public void Draw(){
		float x = Game.gridPosition.x + realPosition.x + Game.gridObjectSize.x/2;
		float y = Game.gridPosition.y + realPosition.y + Game.gridObjectSize.y/2;
		if(Current == null) return;
		if(State == monsterState.Normal){
			Game.spriteBatch.setColor(0, 0, 0, 0.35f);	
			Game.spriteBatch.draw(Current.Region,
					x,
					y,
					realSize.x/4 + realSize.x*.035f,
					realSize.y/4 + realSize.y*.035f,
					realSize.x + realSize.x*.05f,
					realSize.y + realSize.y*.05f,
					-1,
					-1,
					0);
		}
		Game.spriteBatch.setColor(1, 1, 1, 1);
		if(isFrozen){
			Game.spriteBatch.draw(texAttack.Region,
					x,
					y,
					realSize.x/4,
					realSize.y/4,
					realSize.x,
					realSize.y,
					-1,
					-1,
					0);
		}else{
			if(Game.Step == GameT.stepPlayerPick && Game.playerPick.ppTouches.size()>=1){
				if(Game.playerPick.ppType != Type && Game.playerPick.ppType != objectType.Sword)
					Game.spriteBatch.setColor(.30f, .30f, .30f, 1);
			}
			Game.spriteBatch.draw(Current.Region,
					x,
					y,
					realSize.x/4,
					realSize.y/4,
					realSize.x,
					realSize.y,
					-1,
					-1,
					0);
		}
		drawBattleState(x, y);
		if(isFrozen){
			Game.spriteBatch.setColor(1, 1, 1, 0.5f);
			Game.spriteBatch.draw(Game.freezeEffect.Region,
					x,
					y,
					realSize.x/4 + realSize.x*.035f,
					realSize.y/4 + realSize.y*.035f,
					realSize.x + realSize.x*.05f,
					realSize.y + realSize.y*.05f,
					-1,
					-1,
					0);
		}
		Game.spriteBatch.setColor(1, 1, 1, 1);
		if(State == monsterState.Defense){
			String P = String.valueOf(currentDamage*-1);
			Game.drawText(P, x - Game.getStringWidth(P)/2, y - Game.getStringHeight("A")/2, 1, 0, 0, 1);
		}
		String Str = String.valueOf(Health);
		healthP.x = Game.gridPosition.x + realPosition.x + Game.gridObjectSize.x - Game.getStringWidth(Str);
		healthP.y = Game.gridPosition.y + realPosition.y + Game.gridObjectSize.y - Game.getStringHeight("AAA")/1.6f;
		Game.drawText(Str, healthP, 0.0f, 1f, 0, 1);
	}
	void Freeze(int timeOut){
		frozeTurn = Game.currentTurn;
		frozenTimeout = timeOut;
		isFrozen = true;
	}
	public void Update(){
		if(isFrozen){
			if(Game.currentTurn - frozeTurn >=  frozenTimeout){
				isFrozen = false;
			}
		}
		Defending();
		Attacking();
		if(State == monsterState.Normal){
			realPosition.Translate(Position, 0.135f);
			if(Game.playerPick.isInPPTouches(Index))
				realSize.Translate(Biggie, 0.45f);
			else
				realSize.Translate(Game.gridObjectSize, 0.45f);
		}
		Move();
		Draw();
		Index = updateGrid();
	}
	
	Vec2 Biggie = new Vec2();
	private void Init(int PLv, int Turn){
		Biggie.Set(Game.gridObjectSize);
		Biggie.x *= 1.15f;
		Biggie.y *= 1.15f;
		Health 	= (int) (8*(Boss ? 3 : 1) + Tools.Random(3, 6) 		+ 2*(Turn/10) 		+		6*(Turn/20)*(Boss ? 1.5f : 1));
		Attack 	= (int) (2*(Boss ? 2 : 1) + Tools.Random(1, 3) 		+ 2*(Turn/8) 		+ 		3*(Turn/20)*(Boss ? 1.5f : 1)); 
		Defense = (int) (2*(Boss ? 2 : 1) + Tools.Random(1, PLv) 	+  (Turn/10)		+ 		2*(Turn/30)*(Boss ? 1.5f : 1));
		Level = PLv;
		Current = texNormal;
		initHealth = Health;
		initTurn = Turn;
		int mMoney = (PLv + 1 + Turn/25)*(Boss ? 3 : 1);
		int minMoney = mMoney/4;
		Money = Tools.Random(minMoney, mMoney);
	}
	public int getAttackDamage(){
		int Att = Attack;
		int d = Game.Player.getTotalDamage(Att);
		String Str = String.valueOf(d*-1);
		if(d == 0) Str = "-"+String.valueOf(d*-1);
		Game.createEffectText(Str, Game.heartTexture, Game.gridPToP(Position), 1f, 0, 0, fontSize.Normal, 0.11f);
		return Att;
	}
	public int getEXP(){
		return 15 + initTurn + initTurn*Level*2*(Boss ? 3 : 1)*(1 + initTurn/25);
	}
	void PlayDied(){
		Self.Died.Play();
	}
	monsterT(GameT G, String N, int E, monsterTypeT  T, int PLv, int Turn, boolean B){
		Self		= T;
		Game 		= G;
		Name 		= N;
		Type		= objectType.Monster;
		Element 	= E;
		texAttack  	= B ? T.Boss.Attack : T.Common.Attack; 
		texNormal  	= B ? T.Boss.Normal : T.Common.Normal;
		texDefense  = B ? T.Boss.Defense : T.Common.Defense;
		texDied		= B ? T.Boss.Died : T.Common.Died;
		Boss 		= B;
		Init(PLv, Turn);
		++Game.currentEnemyNumber;
	}
}