package com.nite.rid;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

class fontSize {
	static final int Small	 	= 0;
	static final int Normal 	= 1;
	static final int Big 		= 2;
	static final int Italic		= 3;
}

class GameT {
	OrthographicCamera Camera;
	
	gridT Grid;
	Vec2 windowSize;
	Vec2 gridPosition;
	Vec2 gridSize;
	Vec2 gridObjectSize;
	Vec2 disappearSize;
	objectT []Objects;
	objectT []disappearObjects;
	int objectListSize;
	int currentEnemyNumber 	= 0;
	int maxEnemyNumber 		= 3;
	int enemyKilled 		= 0;
	int Score 				= 0;
	int currentFontSize 	= fontSize.Normal;
	int currentTurn 		= 0;
	int Step 				= stepCreate;
	int lastStep			= stepCreate;
	int activeObjects		= 0;
	int mActiveObjects		= 0;
	boolean objectsMoveDown = true;
	float Volume			= 1;
	boolean noMonsters 		= false;
	int alvEnemies 			= 0;
	boolean noMonstersBonus = true;
	Vec2 objectPickPlace;
	int yourBest = 0;
	
	int totalMonstersKilled = 0;
	int totalTurnsPassed = 0;
		
	/* Game steps */
	static final int stepPlayerPick 	= 0;
	static final int stepPPShadowIn		= 1;
	static final int stepDoPick			= 2;
	static final int stepPPShadowOut	= 3;
	static final int stepBShadowIn		= 4;
	static final int stepBattle			= 5;
	static final int stepBShadowOut		= 6;
	static final int stepCreate			= 7;
	static final int stepGetEXP			= 8;
	static final int stepLevelUp		= 9;
	static final int stepPlayerDied		= 10;
	static final int stepSkillShadowIn 	= 11;
	static final int stepSkill		 	= 12;
	static final int stepSkillShadowOut	= 13;
	static final int stepInGameMenu		= 14;
	static final int stepInGameEquip	= 15;
	static final int stepInShop			= 16;
	static final int stepInSkill		= 17;
	boolean showMessage = false;
	
	class messageT {
		String Message;
		float R = 0.5f ,G = 0.5f ,B = 0;
		Vec2 Position = new Vec2();
		Vec2 realSize = new Vec2();
		float Offset = 0;
		float Alpha = 0;
		float AlphaE = 0;
		buttonT OK;
		GameT Game;
		void Update(){
			if(!showMessage)
				Alpha = Tools.Translate(Alpha, 0, 0.25f);
			else
				Alpha = Tools.Translate(Alpha, 80, 0.25f);
			if(!showMessage) return;
			Gdx.gl.glEnable(GL10.GL_BLEND);
			Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			
			Game.spriteBatch.begin();
			Game.spriteBatch.setColor(0, 0, 0, Alpha/100f);
			Game.spriteBatch.draw(Game.whiteTexture.Region, 0, 0,	0, 0,windowSize.x, windowSize.y,	1, 1,	0);
			Game.spriteBatch.setColor(1, 1, 1, 1);
			Game.spriteBatch.end();
			
			Game.spriteBatch.begin();
			Game.spriteBatch.setColor(R, G, B, 1);
			Game.spriteBatch.draw(Game.whiteTexture.Region, Position.x - Offset, Position.y - Offset,	0, 0,realSize.x + Offset*2, realSize.y + Offset*2,	1, 1,	0);
			Game.spriteBatch.setColor(1, 1, 1, 1);
			Game.spriteBatch.end();
			
			Game.spriteBatch.begin();
			Game.spriteBatch.setColor(.05f, .05f, .05f, 1f);
			Game.spriteBatch.draw(Game.whiteTexture.Region, Position.x, Position.y,	0, 0,realSize.x, realSize.y,	1, 1,	0);
			Game.spriteBatch.setColor(1, 1, 1, 1);
			Game.spriteBatch.end();
				
		    OK.Update();
	    	setFontSize(fontSize.Small);
	    	spriteBatch.begin();
	    	float wx = Game.getStringWidth(Message);
	    	int i = Message.indexOf("\n");
	    	if(i != -1){
	    		String k = Message.substring(0, i);
	    		wx = Game.getStringWidth(k);
	    	}
	    	drawTextMultiLine(Message, Position.x + realSize.x/2 - wx/2, Position.y +getStringHeight("A")*2, 1,1,1,1);
	    	spriteBatch.end();
	    	setFontSize(fontSize.Normal);
		    if(OK.isPressed()){
		    	showMessage = false;
		    }
		}
		messageT(GameT Ga){
			Game = Ga;
			Offset = 4;
			realSize.Set(windowSize.x*.70f, windowSize.x*.70f);
			Position.Set(windowSize.x/2 - realSize.x/2, windowSize.y/2 - realSize.y/2);
			OK = new buttonT(Game, "OKAY", fontSize.Normal);
			OK.setSize(OK.Size.x*1.15f, OK.Size.y);
			OK.Position.Set(Position.x + realSize.x/2 - OK.Size.x/2, Position.y + realSize.y - OK.Size.y*1.5f);
			OK.R = 0.75f; OK.G = 0.75f; OK.B = 0;
			OK.Bypass = true;
		}
		void Show(String M){
			Message = M;
			showMessage = true;
			errorSFX.Play();
		}
	}
	
	/* Resources */
	ShaderProgram InvertShader;
	SpriteBatch spriteBatch;
	
	textureT swordTexture;
	textureT sword2Texture;
	textureT falchionTexture;
	textureT bladeTexture;
	textureT katanaTexture;
	textureT macheteTexture;
	textureT rapierTexture;
	textureT sabreTexture;
	textureT armorTexture;
	textureT shieldTexture;
	textureT shield2Texture;
	textureT coinTexture;
	textureT heartTexture;
	
	
	textureT firerizeTexture;
	textureT waterizeTexture;
	textureT grassrizeTexture;
	
	textureT fireBallTexture;
	
	textureT runawayTexture;
	textureT healUpTexture;
	textureT greedRaidTexture;
	textureT freezeTexture;
	textureT rageTexture;
	textureT kingofHeartsTexture;
	
	textureT multiplyAttackTexture;
	
	textureT multiplyDefenseTexture;
	textureT multiplyCoinsTexture;
	textureT multiplyHealTexture;
	textureT noElementTexture;
	textureT bubblesTexture;
	textureT spinesTexture;
	textureT attackBoostTexture;
	textureT gambleTexture;
	
	textureT blizzardTexture;
	textureT whiteTexture;
	textureT pathTexture;	
	
	enemyTextureT trollTexture;
	enemyTextureT trollKingTexture;
	
	enemyTextureT slimeTexture;
	enemyTextureT slimeKingTexture;
	
	enemyTextureT flowerTexture;
	enemyTextureT flowerKingTexture;
	
	enemyTextureT dinoTexture;
	enemyTextureT dinoKingTexture;	
	
	enemyTextureT cyclopTexture;
	enemyTextureT cyclopKingTexture;	
	
	enemyTextureT eaterTexture;
	enemyTextureT eaterKingTexture;
	
	enemyTextureT crabTexture;
	enemyTextureT crabKingTexture;
	
	
	enemyTextureT fyroTexture;
	enemyTextureT fyroKingTexture;
	
	BitmapFont Font;
	BitmapFont BigFont;
	BitmapFont italicFont;
	BitmapFont smallFont;
	
	soundT levelUpMusic;
	soundT diedMusic;
	
	soundT enemyAttackSFX;
	soundT playerAttackSFX;
	soundT swordPickSFX;
	soundT coinPickSFX;
	soundT spineShotSFX;
	soundT heartPickSFX;
	soundT trollDiedSFX;
	soundT fyroDiedSFX;
	soundT slimeDiedSFX;
	soundT dinoDiedSFX;
	soundT flowerDiedSFX;
	soundT eaterDiedSFX;
	soundT bubblesSFX;
	soundT crabDiedSFX;
	soundT gambleSFX;
	soundT cyclopDiedSFX;
	soundT shieldPickSFX;
	soundT fireBallSFX;
	soundT healUpSFX;
	soundT greedRaidSFX;
	soundT freezeSFX;
	soundT boughtSFX;
	soundT errorSFX;
	
	animationT blizzardEffect;
	animationT fireballEffect;
	animationT slashEffect;
	animationT bubblesEffect;
	textureT freezeEffect;
	animationT spineEffect;
	
	playerDiedT playerDied;
	enemyQueueT enemyQueue;
	playerT Player;
	levelUpT lvUP;
	HudT Hud;
	sbT SBG;
	ppSystemT playerPick;
	skillSystemT Skills;
	inGameMenuT inGameMenu;
	inGameEquipT inGameEquip;
	shopT Shop;
	inGameSkillT skillMenu;
	messageT Message;

	textureT pathEndTexture;
	textureT pathStepTexture;
	textureT pathArrowTexture;

	textureT forestBackground;
	textureT mountainBackground;
	textureT seaBackground;
	textureT dungeonBackground;
	textureT caveBackground;
	textureT beachBackground;
	
	float realRatio = 0;
	float currentRatio = 0;
	
	void getRealRatio(){
		realRatio = (float)Gdx.graphics.getWidth()/(float)Gdx.graphics.getHeight();
	}
	void getCurrentRatio(){
		currentRatio = windowSize.x/windowSize.y;
	}
	
	void fixRatio(){ 
		getRealRatio();
		getCurrentRatio();
		if(realRatio == currentRatio) return;
		Vec2 Max = new Vec2(windowSize.x*1.25f, windowSize.y*1.25f);
		Vec2 Current = new Vec2(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		float c = 0;
		if(Current.x < windowSize.x || Current.y < windowSize.y){
			c = 0.05f;
			while(Current.x < windowSize.x || Current.y < windowSize.y){
				Current.x *= (1f+c);
				Current.y *= (1f+c);
				if(Current.x < windowSize.x || Current.y < windowSize.y){
					c += .05f;
					Current.Set(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
				}
			}
		}else{
			c = 0.05f;
			while(Current.x > Max.x || Current.y > Max.y){
				Current.x *= (1f-c);
				Current.y *= (1f-c);
				if(Current.x > Max.x || Current.y > Max.y){
					c += .05f;
					Current.Set(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
				}
			}
		}
		windowSize.Set(Current);
	}
	
	void fixGridSize(){
		if(realRatio == currentRatio) return;
		gridSize.x = Tools.Round(gridSize.x);
		gridSize.y = Tools.Round(gridSize.y);
		if (gridSize.x % 2 != 0) --gridSize.x;
		if (gridSize.y % 2 != 0) --gridSize.y;
	}
	
	void fixObjectSize(){
		if(realRatio == currentRatio) return;
		float mX = windowSize.x*.95f;
		float mY = windowSize.y*.665f;
		if((mX/5)*6 > mY){
			gridObjectSize.x = mY/6;
		}else{
			gridObjectSize.x = mX/5;
		}
		gridObjectSize.x = Tools.Round(gridObjectSize.x);
		gridObjectSize.y = gridObjectSize.x;
	}
	
	textureT Generic;
	void Init(){
		/* Default Window Size */
		windowSize 		= new Vec2(480, 800);
		fixRatio();
		Tools.Print("Window Size: "+windowSize.Str());
		/* Default Object Size */
		gridObjectSize	= new Vec2(88, 88);
		fixObjectSize();
		Tools.Print("Object Size: "+gridObjectSize.Str());
		/* Grid Size */
		gridSize 		= new Vec2(gridObjectSize.y*5, gridObjectSize.y*6);
		Tools.Print("Grid Size: "+gridSize.Str());
		/* Default disappear object size */
		disappearSize	= new Vec2(64, 64);
		gridPosition  	= new Vec2(windowSize.x/2-gridSize.x/2, windowSize.y*.215f + (windowSize.y - (windowSize.y*.149f + 36))/2f-gridSize.y/2f);
		Grid 			= new gridT(gridSize, gridObjectSize, gridPosition);
		objectListSize	= Grid.gridSize*8;
		Objects			= new objectT[objectListSize];
		disappearObjects= new objectT[Grid.gridSize];
		objectPickPlace = new Vec2(windowSize.x/2, gridPosition.y - gridObjectSize.y*2.0f);
		mActiveObjects	= (int) (Grid.tileSize.x*Grid.tileSize.y);
		Camera 			= new OrthographicCamera();
		Camera.setToOrtho(true, windowSize.x, windowSize.y);
		spriteBatch 	= new SpriteBatch();
		spriteBatch.maxSpritesInBatch = Grid.gridSize*4;
		sysLoad 		= new loadT(this);
		SBG 			= new sbT(this);
		Player 			= new playerT(this);
		enemyQueue		= new enemyQueueT(this);
		playerPick		= new ppSystemT(this);
		Skills			= new skillSystemT(this);
		Gdx.gl.glEnable(GL10.GL_LINE_SMOOTH);
		Gdx.gl.glEnable(GL10.GL_POINT_SMOOTH);
		Gdx.gl.glHint(GL10.GL_POLYGON_SMOOTH_HINT, GL10.GL_NICEST);
		Gdx.gl.glHint(GL10.GL_POINT_SMOOTH_HINT, GL10.GL_NICEST);
		p = windowSize.y*var/2.8f;
		Transition = new transtitionT();
		loadSettings();
	}
	
	loadT sysLoad;
	boolean inLoadScreen = true;
	textureT loadLogo;
	class loadT {
		GameT Game;
		long T;
		loadT(GameT G){
			Game = G;
			loadLogo = new textureT("data/load.png", 1, 1, 288, 480, false, true, true); 
			T = Tools.getTicks();
		}
		boolean Done = false;
		void Update(){
			spriteBatch.setProjectionMatrix(Camera.combined);
			spriteBatch.begin();
			spriteBatch.setColor(1, 1, 1, 1);
			spriteBatch.draw(loadLogo.Region, 0, 0, 0, 0, windowSize.x, windowSize.y,	1, 1,	0);
			spriteBatch.end();
			if(Tools.getTicks()-T>250 && !Done){
				Load();
				Transition.Transition();
				Done = true;
			}
			if(Done && Transition.isInBlack()){
				startScreen.Start();
				inStartScreen = true;
				inLoadScreen = false;
			}
			if (Gdx.input.isKeyPressed(Keys.BACK)){
				Gdx.app.exit();
			}
			Transition.Update();
		}
		void Load(){
			Generic					= new textureT("data/generic.png");
			pathTexture 			= new textureT("data/path.png");
			falchionTexture			= new textureT(Generic, 2, 3, 64, 64, true, false, true);
			bladeTexture			= new textureT(Generic, 4, 3, 64, 64, true, false, true);
			katanaTexture			= new textureT(Generic, 3, 3, 64, 64, true, false, true);
			macheteTexture			= new textureT(Generic, 6, 3, 64, 64, true, false, true);
			rapierTexture			= new textureT(Generic, 3, 2, 64, 64, true, false, true);
			sabreTexture			= new textureT(Generic, 4, 2, 64, 64, true, false, true);
			swordTexture			= new textureT(Generic, 1, 3, 64, 64, true, false, true);
			sword2Texture			= new textureT(Generic, 8, 2, 64, 64, true, false, true);
			shieldTexture			= new textureT(Generic, 1, 2, 64, 64, true, false, true);
			shield2Texture			= new textureT(Generic, 5, 2, 64, 64, true, false, true);
			coinTexture				= new textureT(Generic, 7, 3, 64, 64, true, false, true);
			heartTexture			= new textureT(Generic, 2, 2, 64, 64, true, false, true);
			armorTexture			= new textureT(Generic, 5, 3, 64, 64, true, false, true);
			fireBallTexture			= new textureT("data/skill/fireball.png", true, false, true);
			healUpTexture			= new textureT("data/skill/healup.png", true, false, true);
			greedRaidTexture		= new textureT("data/skill/greedyraid.png", true, false, true);
			freezeTexture			= new textureT("data/skill/freeze.png", true, false, true);
			rageTexture				= new textureT("data/skill/rage.png", true, false, true);
			blizzardTexture 		= new textureT("data/skill/blizzard.png", true, false, true);
			kingofHeartsTexture 	= new textureT("data/skill/kingofhearts.png", true, false, true);
			multiplyAttackTexture 	= new textureT("data/skill/multiplyattack.png", true, false, true);
			firerizeTexture 		= new textureT("data/skill/firerize.png", true, false, true);
			waterizeTexture 		= new textureT("data/skill/waterize.png", true, false, true);
			grassrizeTexture 		= new textureT("data/skill/grassize.png", true, false, true);
			multiplyDefenseTexture	= new textureT("data/skill/multiplydefense.png", true, false, true);
			multiplyCoinsTexture	= new textureT("data/skill/multiplycoins.png", true, false, true);
			multiplyHealTexture		= new textureT("data/skill/multiplyheal.png", true, false, true);
			noElementTexture		= new textureT("data/skill/noelement.png", true, false, true);
			spinesTexture			= new textureT("data/skill/spineshot.png", true, false, true);
			attackBoostTexture		= new textureT("data/skill/attack_boost.png", true, false, true);
			runawayTexture			= new textureT("data/skill/runaway.png", true, false, true);
			gambleTexture 			= new textureT("data/skill/gamble.png", true, false, true);
			bubblesTexture			= new textureT("data/skill/bubbles.png", true, false, true);
			pathStepTexture 		= new textureT(pathTexture, 1, 1, 64, 64, true, false, true);
			pathArrowTexture 		= new textureT(pathTexture, 2, 1, 64, 64, true, false, true);
			pathEndTexture 			= new textureT(pathTexture, 3, 1, 64, 64, true, false, true);
			whiteTexture 			= new textureT("data/white.png");
			trollTexture			= new enemyTextureT("data/monsters/troll.png");
			trollKingTexture		= new enemyTextureT("data/monsters/troll_king.png");
			slimeTexture			= new enemyTextureT("data/monsters/slime.png");
			slimeKingTexture		= new enemyTextureT("data/monsters/slime_king.png");
			flowerTexture 			= new enemyTextureT("data/monsters/flower.png");
			dinoTexture 			= new enemyTextureT("data/monsters/dino.png");
			dinoKingTexture 		= new enemyTextureT("data/monsters/dino_king.png");
			cyclopTexture 			= new enemyTextureT("data/monsters/cyclop.png");
			cyclopKingTexture		= new enemyTextureT("data/monsters/cyclop_king.png");
			flowerKingTexture 		= new enemyTextureT("data/monsters/flower_king.png");
			fyroTexture  			= new enemyTextureT("data/monsters/fyro.png");
			fyroKingTexture 		= new enemyTextureT("data/monsters/fyro_king.png");
			eaterTexture 			= new enemyTextureT("data/monsters/eater.png");
			eaterKingTexture 		= new enemyTextureT("data/monsters/eater_king.png");
			crabTexture 			= new enemyTextureT("data/monsters/crab.png");
			crabKingTexture 		= new enemyTextureT("data/monsters/crab_king.png");
			Font 					= new BitmapFont(Gdx.files.internal("data/font/font.fnt"),Gdx.files.internal("data/font/font.png"), true);
			Font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			BigFont 				= new BitmapFont(Gdx.files.internal("data/font/bigfont.fnt"),Gdx.files.internal("data/font/bigfont.png"), true);
			italicFont				= new BitmapFont(Gdx.files.internal("data/font/fonti.fnt"),Gdx.files.internal("data/font/fonti.png"), true);
			smallFont				= new BitmapFont(Gdx.files.internal("data/font/smallfont.fnt"),Gdx.files.internal("data/font/smallfont.png"), true);
			levelUpMusic 			= new soundT(Game,"data/bgm/level_up.ogg");
			diedMusic	 			= new soundT(Game,"data/bgm/game_over.ogg");
			enemyAttackSFX 			= new soundT(Game,"data/sfx/enemy_attack.ogg");
			playerAttackSFX 		= new soundT(Game,"data/sfx/player_attack.ogg");
			swordPickSFX			= new soundT(Game,"data/sfx/sword_pick.ogg");
			coinPickSFX 			= new soundT(Game,"data/sfx/coin_pick.ogg");
			heartPickSFX 			= new soundT(Game,"data/sfx/heart_pick.ogg");
			trollDiedSFX 			= new soundT(Game,"data/sfx/troll_died.ogg");
			slimeDiedSFX			= new soundT(Game,"data/sfx/slime_died.ogg");
			flowerDiedSFX			= new soundT(Game,"data/sfx/flower_died.ogg");
			dinoDiedSFX				= new soundT(Game,"data/sfx/dino_died.ogg");
			fyroDiedSFX				= new soundT(Game,"data/sfx/fyro_died.ogg");
			cyclopDiedSFX			= new soundT(Game,"data/sfx/cyclop_died.ogg");
			shieldPickSFX			= new soundT(Game,"data/sfx/shield_pick.ogg");
			eaterDiedSFX 			= new soundT(Game,"data/sfx/eater_died.ogg");
			crabDiedSFX				= new soundT(Game,"data/sfx/crab_died.ogg");
			fireBallSFX				= new soundT(Game,"data/sfx/fire_ball.ogg");
			healUpSFX    			= new soundT(Game,"data/sfx/healup_spell.ogg");
			greedRaidSFX  			= new soundT(Game,"data/sfx/greedy_raid_skill.ogg");
			freezeSFX				= new soundT(Game,"data/sfx/freeze_spell.ogg");
			boughtSFX				= new soundT(Game,"data/sfx/bought.ogg");
			errorSFX				= new soundT(Game,"data/sfx/error.ogg");
			spineShotSFX			= new soundT(Game,"data/sfx/spineshot.ogg");
			bubblesSFX				= new soundT(Game,"data/sfx/bubbles_skill.ogg");
			gambleSFX				= new soundT(Game,"data/sfx/gamble.ogg");
			stsOpening				= new soundT(Game,"data/bgm/opening.ogg");
			gameStartBGM			= new soundT(Game,"data/bgm/game_start.ogg");
			bubblesEffect			= new animationT("data/effect/bubbles.png", true, false, true, 4, 64, 64, 0.10f);
			slashEffect 			= new animationT("data/effect/slash_anin.png", true, false, true, 4, 64, 64, 0.80f);
			slashEffect.Once 		= true;
			spineEffect				= new animationT("data/effect/spine_anin.png", true, false, true, 4, 64, 64, 0.25f);
			fireballEffect  		= new animationT("data/effect/fireball_anin.png", true, false, true, 3, 64, 64, 0.10f);
			blizzardEffect 			= new animationT("data/effect/blizzard_anin.png", true, false, true, 4, 64, 64, 0.10f);
			freezeEffect 			= new textureT("data/effect/freeze.png", true, false, true);
			forestBackground 		= new textureT("data/background/forest.png", 1, 1, 192, 256, false, true, true);
			mountainBackground 		= new textureT("data/background/mountain.png", 1, 1, 192, 256, false, true, true);
			seaBackground 			= new textureT("data/background/sea.png", 1, 1, 192, 256, false, true, true);
			dungeonBackground 		= new textureT("data/background/dungeon.png", 1, 1, 192, 256, false, true, true);
			caveBackground 			= new textureT("data/background/cave.png", 1, 1, 192, 256, false, true, true);
			beachBackground			= new textureT("data/background/beach.png", 1, 1, 192, 256, false, true, true);
			stsPlayerNormal 		= new textureT("data/start/player_normal.png", 1, 1, 288, 480, false, true, true);
			stsPlayerWorried 		= new textureT("data/start/player_serious.png", 1, 1, 288, 480, false, true, true);
			stsDiablo 				= new textureT("data/start/Diablo.png", 1, 1, 288, 480, false, true, true);
			stsOgre 				= new textureT("data/start/Ogre.png", 1, 1, 288, 480, false, true, true);
			createMonsterType(new monsterTypeT("Troll", trollTexture, trollKingTexture, trollDiedSFX, elementType.Normal));
			createMonsterType(new monsterTypeT("Cyclop", cyclopTexture, cyclopKingTexture, cyclopDiedSFX, elementType.Normal));
			createMonsterType(new monsterTypeT("Slime", slimeTexture, slimeKingTexture, slimeDiedSFX, elementType.Water));
			createMonsterType(new monsterTypeT("Crab", crabTexture, crabKingTexture, crabDiedSFX, elementType.Water));
			createMonsterType(new monsterTypeT("Flower", flowerTexture, flowerKingTexture, flowerDiedSFX, elementType.Grass));
			createMonsterType(new monsterTypeT("Eater", eaterTexture, eaterKingTexture, eaterDiedSFX, elementType.Grass));
			createMonsterType(new monsterTypeT("Lizard", dinoTexture, dinoKingTexture, dinoDiedSFX, elementType.Fire));
			createMonsterType(new monsterTypeT("Fyro", fyroTexture, fyroKingTexture, fyroDiedSFX, elementType.Fire));
			lvUP 					= new levelUpT(Game);
			playerDied 				= new playerDiedT(Game);
			Hud						= new HudT(Game, disappearSize);
			inGameMenu				= new inGameMenuT(Game);
			inGameEquip 			= new inGameEquipT(Game);
			Shop					= new shopT(Game);
			skillMenu				= new inGameSkillT(Game);
			Message					= new messageT(Game);
			startScreen 			= new startScreenT(Game);
			currentBackground 		= beachBackground;
			Restart();
		}
	}
	
	transtitionT Transition;
	textureT stsPlayerNormal;
	textureT stsPlayerWorried;
	soundT stsOpening;
	soundT gameStartBGM;
	textureT stsDiablo;
	textureT stsOgre;
	
	boolean inStartScreen = false;
	startScreenT startScreen;
	class startScreenT {
		Vec2 playerPosition = new Vec2(0,0);
		Vec2 playerNewPosition = new Vec2(0,0);
		long T;
		class BGTypes {
			static final int Diablo 	= 0;
			static final int Ogres 		= 1;
			
		}
		textureT playerFace;
		textureT playerOther;
		int currentBG = BGTypes.Ogres; 
		String Title = "Rush In Dungeons";
		float titleWidth = 0;
		
		GameT Game;
		float titleAlpha = 100;
		boolean playerPressed = false;
		boolean restartTexts = false;
		void updateTitle(){
			Rush.Update();
			if(Rush.isDone()){
				In.Update();
			}
			if(In.isDone()){
				Dungeons.Update();
			}	
			BGAlpha = Tools.Translate(BGAlpha, playerPressed ? 0 : 95, 0.05f);
			titleAlpha = Tools.Translate(titleAlpha, playerPressed ? 0 : 100, 0.05f);
			Dungeons.Draw(windowSize.x/2 - wDun/2, windowSize.y*.10f+playerPosition.y*-1+h*2.15f, 1, 1, 1, titleAlpha/100);
			In.Draw(windowSize.x/2 - wIn/2, windowSize.y*.10f+playerPosition.y*-1+h*1.15f, 1, 1, 1, titleAlpha/100);
			Rush.Draw(windowSize.x/2 - wRus/2, windowSize.y*.10f+playerPosition.y*-1, 1, 1, 1, titleAlpha/100);
		}
		float wDun;
		float wIn;
		float wRus;
		float h;
		float Volume = 100;
		long trackID;
		void Start(){
			Rush.End();
			In.End();
			Dungeons.End();
			Entering = true;
			trackID = stsOpening.Play();
			Volume = 100;
			Continue.Position.Set(windowSize.x/2*(-1)-Leaderboard.Size.x/2, windowSize.y*.40f);
			newGame.Position.Set(windowSize.x/2*(-1)-Leaderboard.Size.x/2, windowSize.y*.40f + Leaderboard.Size.y*1.65f);
			Leaderboard.Position.Set(windowSize.x/2*(-1)-Leaderboard.Size.x/2, windowSize.y*.40f + Leaderboard.Size.y*1.65f*2f);
			Achivements.Position.Set(windowSize.x/2*(-1)-Leaderboard.Size.x/2, windowSize.y*.40f + Leaderboard.Size.y*1.65f*3f);
			Swarm.Position.Set(windowSize.x/2*(-1)-Leaderboard.Size.x/2, windowSize.y*.40f + Leaderboard.Size.y*1.65f*4f);
			Exit.Position.Set(windowSize.x/2*(-1)-Leaderboard.Size.x/2, windowSize.y*.40f + Leaderboard.Size.y*1.65f*5f);
			Continue.setEnable(!isSaveEmpty("game.sav"));
			restartTexts = true;
		}
		void End(){
			Rush.End();
			In.End();
			Dungeons.End();
			Entering = false;
			stsOpening.Stop();
		}
		void doEnd(){
			
		}
		letterByLetterT Rush;
		letterByLetterT In;
		letterByLetterT Dungeons;
		startScreenT(GameT G){
			Game = G;
			playerFace = stsPlayerNormal;
			if (Tools.Random(1, 5)>=3){
				currentBG = BGTypes.Diablo;
			}
			if(currentBG == BGTypes.Diablo){
				playerOther		=  stsDiablo;
				playerFace 		= stsPlayerWorried;
			}else
			if(currentBG == BGTypes.Ogres){
				playerOther		=  stsOgre;
				playerFace 		= stsPlayerNormal;
			}		
			Rush = new letterByLetterT(Game);
			Rush.setText("Rush");
			Rush.fntSize = fontSize.Big;

			In = new letterByLetterT(Game);
			In.setText("In");
			In.fntSize = fontSize.Big;

			Dungeons = new letterByLetterT(Game);
			Dungeons.setText("Dungeons");
			Dungeons.fntSize = fontSize.Big;
			
			setFontSize(fontSize.Big);
			wDun 	= getStringWidth("Dungeons");
			wIn 	= getStringWidth("In");
			wRus 	= getStringWidth("Rush");
			h 		= getStringHeight("AA");
			
			setFontSize(fontSize.Normal);
			cpyRightWidth = getStringWidth(cpyRight); 
			cpyRightHeight = getStringHeight("AA");
			
			
			Leaderboard	= new buttonT(Game, "LEADERBOARD", fontSize.Normal);
			Continue = new buttonT(Game, "CONTINUE", fontSize.Normal, Leaderboard.Size.x, Leaderboard.Size.y);
			newGame	= new buttonT(Game, "NEW GAME", fontSize.Normal, Leaderboard.Size.x, Leaderboard.Size.y);
			Achivements = new buttonT(Game, "ACHIVEMENTS", fontSize.Normal, Leaderboard.Size.x, Leaderboard.Size.y);
			Swarm = new buttonT(Game, "SWARM", fontSize.Normal, Leaderboard.Size.x, Leaderboard.Size.y);
			Exit = new buttonT(Game, "EXIT", fontSize.Normal, Leaderboard.Size.x, Leaderboard.Size.y);
			
			
			Leaderboard.R = .15f; Leaderboard.G = .15f; Leaderboard.B = .15f;
			Leaderboard.BR = 10f/255f; Leaderboard.BG = 10f/255f; Leaderboard.BB = 10f/255f;
			Continue.R = Leaderboard.R; Continue.G = Leaderboard.G; Continue.B = Leaderboard.B;
			Continue.BR = Leaderboard.BR; Continue.BG = Leaderboard.BG; Continue.BB = Leaderboard.BB;
			newGame.R = Leaderboard.R; newGame.G = Leaderboard.G; newGame.B = Leaderboard.B;
			newGame.BR = Leaderboard.BR; newGame.BG = Leaderboard.BG; newGame.BB = Leaderboard.BB;
			Achivements.R = Leaderboard.R; Achivements.G = Leaderboard.G; Achivements.B = Leaderboard.B;
			Achivements.BR = Leaderboard.BR; Achivements.BG = Leaderboard.BG; Achivements.BB = Leaderboard.BB;
			Swarm.R = Leaderboard.R; Swarm.G = Leaderboard.G; Swarm.B = Leaderboard.B;
			Swarm.BR = Leaderboard.BR; Swarm.BG = Leaderboard.BG; Swarm.BB = Leaderboard.BB;
			Exit.R = Leaderboard.R; Exit.G = Leaderboard.G; Exit.B = Leaderboard.B;
			Exit.BR = Leaderboard.BR; Exit.BG = Leaderboard.BG; Exit.BB = Leaderboard.BB;
			
			Buttons = new ArrayList<buttonT>();
			
			Continue.Position.Set(windowSize.x/2*(-1)-Leaderboard.Size.x/2, windowSize.y*.40f);
			Buttons.add(Continue);
			newGame.Position.Set(windowSize.x/2*(-1)-Leaderboard.Size.x/2, windowSize.y*.40f + Leaderboard.Size.y*1.65f);
			Buttons.add(newGame);
			Leaderboard.Position.Set(windowSize.x/2*(-1)-Leaderboard.Size.x/2, windowSize.y*.40f + Leaderboard.Size.y*1.65f*2f);
			Buttons.add(Leaderboard);
			Achivements.Position.Set(windowSize.x/2*(-1)-Leaderboard.Size.x/2, windowSize.y*.40f + Leaderboard.Size.y*1.65f*3f);
			Buttons.add(Achivements);
			Swarm.Position.Set(windowSize.x/2*(-1)-Leaderboard.Size.x/2, windowSize.y*.40f + Leaderboard.Size.y*1.65f*4f);
			Buttons.add(Swarm);
			Exit.Position.Set(windowSize.x/2*(-1)-Leaderboard.Size.x/2, windowSize.y*.40f + Leaderboard.Size.y*1.65f*5f);
			Buttons.add(Exit);
			
			
			for(int i=0; i<3; ++i){
				Positions.add(new Vec2[6]);
			}
			
			for(int j=0; j<6; ++j){
				float k = 1.65f * j;
				Positions.get(Position.Left)[j] = new Vec2(windowSize.x/2*(-1)-Leaderboard.Size.x/2, windowSize.y*.40f + Leaderboard.Size.y*k);
			}
			for(int j=0; j<6; ++j){
				float k = 1.65f * j;
				Positions.get(Position.Center)[j] = new Vec2(windowSize.x/2-Leaderboard.Size.x/2, windowSize.y*.40f + Leaderboard.Size.y*k);
			}
			for(int j=0; j<6; ++j){
				float k = 1.65f * j;
				Positions.get(Position.Right)[j] = new Vec2(windowSize.x/2*3-Leaderboard.Size.x/2, windowSize.y*.40f + Leaderboard.Size.y*k);
			}
		}
		
		buttonT Continue;
		buttonT newGame;
		buttonT Leaderboard;
		buttonT Achivements;
		buttonT Swarm;
		buttonT Exit;
		
		
		class Position {
			static final int Left 		= 0;
			static final int Center 	= 1;
			static final int Right 		= 2;
		}
		int currentPosition = Position.Center;
		ArrayList<Vec2[]> Positions = new ArrayList<Vec2[]>();
		ArrayList<buttonT> Buttons;
		 
		boolean Entering = false;
		float BGEntrance = 100;
		float BGAlpha = 95;
		boolean goingToContinue = false;
		boolean goingNewGame = false;
		void Update(){
			if(restartTexts && Transition.currentAlpha == 0){
				Rush.Start();
				In.Start();
				Dungeons.Start();
				restartTexts = false;
			}
			if(Tools.getTicks()-T>1000){
				int worryFactor = 2;
				if(currentBG == BGTypes.Diablo) worryFactor += 1;
				playerNewPosition.y = playerNewPosition.y < 0 ? worryFactor : -worryFactor; 
				T = Tools.getTicks();
			}

			playerPosition.Translate(playerNewPosition, 0.02f);
			spriteBatch.setProjectionMatrix(Camera.combined);
			spriteBatch.begin();
			spriteBatch.setColor(.05f, .05f, .05f, 1);
			spriteBatch.draw(whiteTexture.Region, 0, 0,	0, 0, windowSize.x, windowSize.y,	1, 1,	0);
			spriteBatch.setColor(0, 0, 0, 0.5f);
			spriteBatch.draw(playerOther.Region, -8, -8, 0, 0, windowSize.x + 17, windowSize.y +16,	1, 1,	0);
			spriteBatch.setColor(1, 1, 1, 1);
			spriteBatch.draw(playerOther.Region, 0, 0, 0, 0, windowSize.x, windowSize.y,	1, 1,	0);
			spriteBatch.draw(playerFace.Region, playerPosition.x, 2+playerPosition.y,	0, 0, windowSize.x, windowSize.y,	1, 1,	0);
			spriteBatch.setColor(.05f, .05f, .05f, BGAlpha/100);
			spriteBatch.draw(whiteTexture.Region, 0, 0,	0, 0, windowSize.x, windowSize.y,	1, 1,	0);
			spriteBatch.setColor(1, 1, 1, 1);
			updateTitle();
			drawText(cpyRight, windowSize.x/2-cpyRightWidth/2, windowSize.y-cpyRightHeight*1.15f, 1, 1, 1, titleAlpha/100);
			
			spriteBatch.end();

			if(goingToContinue && Transition.isInBlack()){
				Game.Load("game.sav");
				inStartScreen = false;
				goingToContinue = false;
				gameStartBGM.Play();
				End();
			}

			if(goingNewGame && Transition.isInBlack()){
				Restart();
				Save("game.sav");
				inStartScreen = false;
				goingNewGame = false;
				gameStartBGM.Play();
				End();
			}
			if(goingNewGame || goingToContinue){
				Volume = Tools.Translate(Volume, 0, 0.18f);
			}
			stsOpening.setVolume(trackID, Volume/100);
			if(Continue.isPressed() && !goingNewGame && !goingToContinue){
				Transition.Transition();
				goingToContinue = true;
			}else
			if(newGame.isPressed() && !goingToContinue && !goingNewGame){
				Transition.Transition();
				goingNewGame = true;
			}else				
			if(Leaderboard.isPressed()){
				if(!Game.Social.isInit()){
					Game.Social.Init();
				}
				Game.Social.showLeaderboard();				
			}else
			if(Achivements.isPressed()){
				if(!Game.Social.isInit()){
					Game.Social.Init();
				}
				Game.Social.showAchivements();
			}else
			if(Swarm.isPressed()){
				if(!Game.Social.isInit()){
					Game.Social.Init();
				}
				Game.Social.showDashBoard();
			}else					
			if(Exit.isPressed()){
				Gdx.app.exit();
			}
			
			if (Gdx.input.isKeyPressed(Keys.BACK)){
				Gdx.app.exit();
			}
			
			if(!Continue.isPress() && !newGame.isPress() && !Leaderboard.isPress() && !Achivements.isPress() &&
				!Swarm.isPress()	&& !Exit.isPress()){
				
				playerPressed = Gdx.input.isTouched();
				
			}
			
			for(int j=0; j<6; ++j){
				int P = currentPosition;
				if(playerPressed) P = Position.Right;
				if(Buttons.get(j).Position.Translate(Positions.get(P)[j], 0.55f)) break;
			}
			
			for(int j=0; j<6; ++j){
				Buttons.get(j).Update();
			}
			Transition.Update();
		}
		String cpyRight = "(c)2014 nite";
		float cpyRightWidth;
		float cpyRightHeight;
	}
	
	class transtitionT {
		float currentAlpha;
		boolean doTransition = false;
		int Step = 0;
		long T;
		boolean isDone(){
			return !doTransition;
		}
		void Transition(){
			doTransition = true;
			Step = 0;
		}
		boolean isInBlack(){
			return Step == 1;
		}
		void Update(){
			if(!doTransition) return;
			if(Step == 0){
				currentAlpha = Tools.Translate(currentAlpha, 100, 0.15f);
				if(currentAlpha == 100){
					T = Tools.getTicks();
					++Step;
				}
			}else
			if(Step == 1){
				if(Tools.getTicks()-T>1000)++Step;
			}else
			if(Step == 2){
				currentAlpha = Tools.Translate(currentAlpha, 0, 0.15f);
				if(currentAlpha == 0) doTransition = false;
			}
			spriteBatch.begin();
			spriteBatch.setColor(0, 0, 0, currentAlpha/100f);
			spriteBatch.draw(whiteTexture.Region, 0, 0,	0, 0, windowSize.x, windowSize.y,	1, 1,	0);
			spriteBatch.setColor(1, 1, 1, 1);
			spriteBatch.end();
		}
	}
	
	ArrayList<monsterTypeT> Monsters = new ArrayList<monsterTypeT>();
	void createMonsterType(monsterTypeT M){
		Monsters.add(M);
	}
	monsterTypeT getMonsterType(String Name){
		for(int i=0; i<Monsters.size(); ++i){
			if(Monsters.get(i).Name.equals(Name)) return Monsters.get(i); 
		}
		return null;
	}
	monsterTypeT getRandomMonsterType(int El){
		ArrayList<monsterTypeT> Ms = new ArrayList<monsterTypeT>();
		for(int i=0; i<Monsters.size(); ++i){
			if(Monsters.get(i).Element == El) Ms.add(Monsters.get(i));
		}
		return Ms.get(Tools.Random(1, Ms.size())-1);
	}
	textureT currentBackground;
	textureT oldBackground;
	float currentAlpha = 100;
	boolean doChangeBackground = false;
	void changeBackground(textureT newBackground){
		currentAlpha = 0;
		oldBackground = currentBackground;
		currentBackground = newBackground;
		doChangeBackground = true;
	}
	class stateTypeT {
		static final int Dungeon 	= 0;
		static final int Forest 	= 1;
		static final int Beach	 	= 2;
		static final int Mountain	= 3;
		static final int Cave		= 4;
		static final int Sea		= 5;
	}
	textureT getStageBackground(int T){
		if(T == stateTypeT.Dungeon) return dungeonBackground;
		if(T == stateTypeT.Forest) return forestBackground;
		if(T == stateTypeT.Beach) return beachBackground;
		if(T == stateTypeT.Mountain) return mountainBackground;
		if(T == stateTypeT.Cave) return caveBackground;
		if(T == stateTypeT.Sea) return seaBackground;
		return null;
	}
	String getStageName(int T){
		if(T == stateTypeT.Dungeon) return "Dungeon";
		if(T == stateTypeT.Forest) return "Forest";
		if(T == stateTypeT.Beach) return "Beach";
		if(T == stateTypeT.Mountain) return "Mountain";
		if(T == stateTypeT.Cave) return "Cave";
		if(T == stateTypeT.Sea) return "Sea";
		return "NULL";
	}
	void changeStage(){
		int t = Tools.Random(1, 6)-1;
		if(currentStage != null){
			t = currentStage.Type;
			while(currentStage.Type == t) t = Tools.Random(1, 6)-1;
		}
		currentStage = new stageT(this, t);
	}
	boolean doChangeStage = false;
	class stageT {
		int Type = -1;
		int nextBoss;
		GameT Game;
		boolean createdBoss = false;
		ArrayList<monsterTypeT> MList = new ArrayList<monsterTypeT>();
		monsterTypeT Head;
		boolean bossReady(){
			return !createdBoss && currentTurn >= nextBoss;
		}
		monsterT createMonster(){
			monsterTypeT M = MList.get(Tools.Random(1, MList.size())-1);
			return new monsterT(Game, M.Name, M.Element, M, Player.Level, currentTurn, false);
		}
		monsterT createBoss(){
			createdBoss = true;
			return new monsterT(Game, Head.Name+" King", Head.Element, Head, Player.Level, currentTurn, true);
		}
		monsterTypeT getRandomMonsterTypeRandom(){
			int K = Tools.Random(1, 100);
			monsterTypeT Master = null;
			if(K >= 1 && K <= 25){
				Master = getRandomMonsterType(elementType.Normal);
			}else
			if(K >= 26 && K <= 50){
				Master = getRandomMonsterType(elementType.Fire);
			}else
			if(K >= 51 && K <= 75){
				Master = getRandomMonsterType(elementType.Grass);
			}else{
				Master = getRandomMonsterType(elementType.Water);
			}
			return Master;
		}
		
		void generateEnemyList(){
			if(Type == stateTypeT.Dungeon){
				int k = 10 - Tools.Random(3, 2);
				Head = getRandomMonsterType(elementType.Normal);
				for(int i=0; i<k; ++i){
					MList.add(Head);
				}
				for(int i=0; i<10-k; ++i){
					MList.add(getRandomMonsterTypeRandom());
				}
			}else
			if(Type == stateTypeT.Forest){
				int k = 10 - Tools.Random(1, 2)-1;
				Head = getRandomMonsterType(elementType.Grass);
				for(int i=0; i<k; ++i){
					MList.add(Head);
				}
				for(int i=0; i<10-k; ++i){
					MList.add(getRandomMonsterTypeRandom());
				}
			}else
			if(Type == stateTypeT.Beach){
				int k = 10 - Tools.Random(1, 2)-1;
				Head = getRandomMonsterType(elementType.Water);
				for(int i=0; i<k; ++i){
					MList.add(Head);
				}
				for(int i=0; i<10-k; ++i){
					MList.add(getRandomMonsterTypeRandom());
				}
			}else
			if(Type == stateTypeT.Mountain){
				int k = 10 - Tools.Random(3, 1);
				Head = getRandomMonsterTypeRandom();
				for(int i=0; i<k; ++i){
					MList.add(Head);
				}
				for(int i=0; i<10-k; ++i){
					MList.add(getRandomMonsterTypeRandom());
				}
			}else
			if(Type == stateTypeT.Cave){
				int k = 10 - Tools.Random(3, 1);
				Head = getRandomMonsterTypeRandom();
				for(int i=0; i<k; ++i){
					MList.add(Head);
				}
				for(int i=0; i<10-k; ++i){
					MList.add(getRandomMonsterTypeRandom());
				}
			}else
			if(Type == stateTypeT.Sea){
				Head = getRandomMonsterType(elementType.Water);
				for(int i=0; i<10; ++i){
					MList.add(Head);
				}
			}
		}
		stageT(GameT G, int T){
			Type 		= T;
			Game		= G;
			nextBoss	= currentTurn + Tools.Random(7, 14) + (Tools.Random(1,5) >= 4 ? Tools.Random(1,5)+1 : 1);
			//Tools.Print("Boss by the turn "+String.valueOf(nextBoss));
			//Tools.Print("Stage: "+getStageName(T));
			generateEnemyList();
			doChangeStage = true;
		}
	}
	stageT currentStage;
	
	int createDisapper(int Type, Vec2 Position){
		for(int i=0; i<Grid.gridSize; ++i){
			if(disappearObjects[i] != null) continue;
			disappearObjects[i] = new disappearT(this, Position, Type);
			((disappearT) disappearObjects[i]).Index = i;
			return i;
		}
		return -1;
	}
	
	int createDeadMonster(monsterT Father){
		for(int i=Grid.gridSize; i<objectListSize; ++i){
			if(Objects[i] != null) continue;
			Objects[i] = new monsterDiedT(this, Father.Position, Father.texDied);
			((monsterDiedT) Objects[i]).Index = i;
			Father.PlayDied();
			return i;
		}
		return -1;
	}
	
	int getEmptyObjectList(){
		for(int i=0; i<objectListSize; ++i){
			if(Objects[i] == null) return i; 
		}
		return -1;
	}
	
	void createGridObject(int Type, Vec2 Positon){
		++activeObjects;
		int i = getEmptyObjectList();
		int Plus = 1;
		switch(Type){
			default:
				--activeObjects;
				return;
			case 0:
				Objects[i] = new swordT(this);
				Plus = 1;
				for(int f=0; f<(currentTurn/8); ++f){
					if(Tools.Random(1, 20) == 20) ++Plus;
				}
				((pickObjectT)Objects[i]).Times = Plus;
			break;
			case 1:
				Objects[i] = new shieldT(this);
				Plus = 1;
				for(int f=0; f<(currentTurn/13); ++f){
					if(Tools.Random(1, 20)>=19) ++Plus;
				}
				((pickObjectT)Objects[i]).Times = Plus;
			break;
			case 2:
				Objects[i] = currentStage.createMonster();
			break;
			case 3:
				Objects[i] = new coinT(this);
				Plus = 1;
				for(int f=0; f<(currentTurn/25); ++f){
					if(Tools.Random(1, 20) == 20) ++Plus;
				}
				((pickObjectT)Objects[i]).Times = Plus;
			break;
			case 4:
				Objects[i] = new heartT(this);
				Plus = 1;
				for(int f=0; f<(currentTurn/8); ++f){
					if(Tools.Random(1, 20)>=19) ++Plus;
				}
				((pickObjectT)Objects[i]).Times = Plus;
			break;
			case 7:
				Objects[i] = currentStage.createBoss();
			break;
		}
		((gridObjectT) Objects[i]).gameIndex = i;
		((gridObjectT) Objects[i]).setPosition(Positon);
	}
	
	void swapGridObject(int IndexA, int IndexB){
		Vec2 P = new Vec2(Grid.Objects[IndexA].Position);
		Grid.Objects[IndexA].Position.Set(Grid.Objects[IndexB].Position);
		Grid.Objects[IndexB].Position.Set(P);
		
		int A = Grid.Objects[IndexA].gameIndex;
		int B = Grid.Objects[IndexB].gameIndex;
		
		gridObjectT K 			= Grid.Objects[IndexA];
		Grid.Objects[IndexA] 	= Grid.Objects[IndexB];
		Grid.Objects[IndexB] 	= K;
		
		objectT L 	= Objects[A];
		Objects[A] 	= Objects[B];
		Objects[B] 	= L;
		
		A = Grid.Objects[IndexA].gameIndex;
		Grid.Objects[IndexA].gameIndex = Grid.Objects[IndexB].gameIndex;
		Grid.Objects[IndexB].gameIndex = A;
		

		A = Grid.Objects[IndexA].Index;
		Grid.Objects[IndexA].Index = Grid.Objects[IndexB].Index;
		Grid.Objects[IndexB].Index = A;

	}
	void destroyGridObject(int Index){
		if(Index == -1) return;
		Objects[Grid.Objects[Index].gameIndex] = null;
		Grid.Objects[Index] = null;
		--activeObjects;
	}
	
	void destroyGridObject(Vec2 Position){
		Position.x -= gridPosition.x;
		Position.y -= gridPosition.y;
		if(Grid.isEmpty(Position)) return;
		int i = Grid.getPosition(Position);
		destroyGridObject(i);
	}
	
	Vector3 vecUnpj = new Vector3();
	void unProject(Vec2 v){
		vecUnpj.x = v.x;
		vecUnpj.y = v.y;
		Camera.unproject(vecUnpj);
		v.x = vecUnpj.x;
		v.y = vecUnpj.y;
	}
	
	void setFontSize(int S){
		currentFontSize = S;
	}
	
	void createEffectText(String Text, textureT F, float x, float y, float Re, float Gr, float Bl, int Font, float Speed){
		for(int i=Grid.gridSize; i<objectListSize; ++i){
			if(Objects[i] != null) continue;
			effectTextT T;
			if(F != null){
				T = new effectTextT(this, Text, F, x, y, Re, Gr, Bl, Font, Speed);
			}else
				T = new effectTextT(this, Text, x, y, Re, Gr, Bl, Font, Speed);	
			Objects[i] = T;				
			((effectTextT) Objects[i]).Index = i;
			return;
		}
	}
	
	void createEffectText(String Text, float x, float y, float Re, float Gr, float Bl, int Font, float Speed){
		createEffectText(Text, null, x, y, Re, Gr, Bl, Font, Speed);
	}
	
	void createEffectText(String Text, Vec2 P, float Re, float Gr, float Bl, int Font, float Speed){
		createEffectText(Text, null, P.x, P.y, Re, Gr, Bl, Font, Speed);
	}

	void createEffectText(String Text, textureT F, Vec2 P, float Re, float Gr, float Bl, int Font, float Speed){
		createEffectText(Text, F, P.x, P.y, Re, Gr, Bl, Font, Speed);
	}

	Vec2 gridPToP(float x, float y){
		Vec2 R = new Vec2();
		R.x = x + gridPosition.x + Grid.objectSize.x/2.0f; 
		R.y = y + gridPosition.y + Grid.objectSize.y/2.0f;
		return R;
	}
	
	Vec2 gridPToP(Vec2 Pos){
		return gridPToP(Pos.x, Pos.y);
	}
	
	void drawText(String Text, float x, float y, float R, float G, float B, float A){
		BitmapFont F = Font;
		if(currentFontSize == fontSize.Big)		F = BigFont;
		if(currentFontSize == fontSize.Italic)	F = italicFont;
		if(currentFontSize == fontSize.Small)	F = smallFont;
		F.setColor(0, 0, 0, A);
		F.draw(spriteBatch, Text, x + 1, y + 1);
		F.setColor(R, G, B, A);		 
		F.draw(spriteBatch, Text, x, y);
	}
	
	void drawText(String Text, Vec2 P, float R, float G, float B, float A){
		drawText(Text, P.x, P.y, R, G ,B, A);
	}
	
	void drawText(String Text, Vec2 P){
		drawText(Text, P, 0f, 0f, 0f, 1f);
	}
	void drawText(String Text, float x, float y){
		drawText(Text, x, y, 0f, 0f, 0f, 1f);
	}

	void drawTextMultiLine(String Text, float x, float y, float R, float G, float B, float A){
		BitmapFont F = Font;
		if(currentFontSize == fontSize.Big)		F = BigFont;
		if(currentFontSize == fontSize.Italic)	F = italicFont;
		if(currentFontSize == fontSize.Small)	F = smallFont;
		F.setColor(0, 0, 0, A);
		F.drawMultiLine(spriteBatch, Text,x+1,y+1);
		F.setColor(R, G, B, A);		 
		F.drawMultiLine(spriteBatch, Text,x,y);
	}
	
	float getStringWidth(String Str){
		BitmapFont F = Font;
		if(currentFontSize == fontSize.Big)		F = BigFont;
		if(currentFontSize == fontSize.Italic)	F = italicFont;
		if(currentFontSize == fontSize.Small)	F = smallFont;
		return F.getBounds(Str).width;
	}
	float getStringHeight(String Str){
		BitmapFont F = Font;
		if(currentFontSize == fontSize.Big)		F = BigFont;
		if(currentFontSize == fontSize.Italic)	F = italicFont;
		if(currentFontSize == fontSize.Small)	F = smallFont;
		return F.getBounds(Str).width;
	}
	boolean updateObjectCreator(){
		boolean stepDone = true;
		for(int i=0; i<(int)Grid.tileSize.x; ++i){
			Vec2 P = new Vec2(Grid.objectSize.x * i, 0);
			if (Grid.isEmpty(P)){
				int whatTo = objectType.Sword;
				double r = Tools.Random(1, 6);
				if(currentStage.bossReady()){
					whatTo = objectType.Boss;
				}else
				if((Tools.Random(1, 35) >= 28 && currentEnemyNumber<maxEnemyNumber && !noMonsters) || (currentEnemyNumber == 0 && !noMonsters)){
					whatTo = objectType.Monster;
				}else
				if(r==1){
					whatTo = objectType.Heart;
				}else
				if(r==2){
					whatTo = objectType.Shield;
				}else
				if(r==3){
					whatTo = objectType.Coin;
				}
				createGridObject(whatTo, P);
				stepDone = false;
			}
		}
		return stepDone;
	}
	Vec2 V = new Vec2();
	void updateShadows(){
		spriteBatch.begin();
		spriteBatch.setColor(Color.YELLOW);
	    for(int i=0; i<playerPick.ppTouches.size(); ++i){
	    	int l = playerPick.ppTouches.get(i);
	    	textureT Piece = i == playerPick.ppTouches.size()-1 ? pathArrowTexture : pathStepTexture;
	    	if(i == 0 && playerPick.ppTouches.size()>1){
	    		Piece = pathEndTexture;
	    	}
	    	float Angle = 0;
	    	if(i <= playerPick.ppTouches.size()-2){
	    		int v = playerPick.ppTouches.get(i+1);
	    		Angle = -1*Tools.aTan( Grid.Objects[l].Position.y-Grid.Objects[v].Position.y,
	    				Grid.Objects[l].Position.x-Grid.Objects[v].Position.x
	    				);
	    	}
	    	if(i == playerPick.ppTouches.size()-1 && playerPick.ppTouches.size()>1){
	    		int v = playerPick.ppTouches.get(i-1);
	    		Angle = -1*Tools.aTan( Grid.Objects[l].Position.y-Grid.Objects[v].Position.y,
	    				Grid.Objects[l].Position.x-Grid.Objects[v].Position.x
	    				);
	    	}
	    	if(playerPick.ppTouches.size() == 1){
	    		V.Set(Gdx.input.getX(), Gdx.input.getY());
	    		unProject(V);
	    		Angle = -1*Tools.aTan(V.y-(gridPosition.y+Grid.Objects[0].Position.y),
	    				V.x-(gridPosition.x +Grid.Objects[0].Position.x)
	    				);
	    	}
			float x = gridPosition.x + Grid.Objects[l].Position.x;
			float y = gridPosition.y + Grid.Objects[l].Position.y;
			spriteBatch.setColor(0, 0, 0, 0.5f);
			spriteBatch.draw(Piece.Region,
					x,
					y,
					(gridObjectSize.x*1.1f)/2,
					(gridObjectSize.y*1.1f)/2,
					gridObjectSize.x*1.1f,
					gridObjectSize.y*1.1f,
					-1,
					-1,
					Angle*-1);
			int El = Player.getElement();
			if(El == elementType.Fire){
				spriteBatch.setColor(0.85882352941f, 0.3137254902f, 0.23137254902f, 1);
			}else
			if(El == elementType.Water){
				spriteBatch.setColor(0.54509803922f, 0.69803921569f, 1f, 1);
			}else
			if(El == elementType.Grass){
				spriteBatch.setColor(0.41176470588f, 0.85882352941f, 0.23137254902f, 1);
			}else
				spriteBatch.setColor(1, 1, 1, 1);
			spriteBatch.draw(Piece.Region,
					x,
					y,
					gridObjectSize.x/2,
					gridObjectSize.y/2,
					gridObjectSize.x,
					gridObjectSize.y,
					-1,
					-1,
					Angle*-1);
	    }
	    spriteBatch.setColor(1, 1, 1, 1);
	    spriteBatch.end();
	}
	
	void updateObjects(){
	    spriteBatch.begin();
	    spriteBatch.setProjectionMatrix(Camera.combined);
	    spriteBatch.enableBlending();
		for(int i=0; i<Grid.gridSize; ++i){
			if (Objects[i] != null){
				if(!playerPick.isGIndexInPPTouches(i) &&
				   !enemyQueue.isEnemyQueue(i) &&
				   !Skills.isInFadeIn(i)) Objects[i].Update();
			}
		}
		
		spriteBatch.end();
		SBG.Update();
		spriteBatch.begin();
		
		
		for(int i=0; i<Skills.objectsInFade.size(); ++i){
			if(Objects[Skills.objectsInFade.get(i)] != null)
				Objects[Skills.objectsInFade.get(i)].Update();
		}
		for(int i=0; i<enemyQueue.Queue.size(); ++i){
			if(Objects[enemyQueue.Queue.get(i)] != null)
				Objects[enemyQueue.Queue.get(i)].Update();
		}
		for(int i=0; i<playerPick.ppTouches.size(); ++i){
			if(Grid.Objects[playerPick.ppTouches.get(i)] == null) continue;
			int p = Grid.Objects[playerPick.ppTouches.get(i)].gameIndex;
			if (Objects[p] != null) Objects[p].Update();
		}
		for(int i=Grid.gridSize; i<objectListSize; ++i){
			if (Objects[i] != null) Objects[i].Update();
		}
		
		spriteBatch.end();
		if(Step != stepPlayerPick) return; 
		updateShadows();
		if(pressedAsMonster && playerPick.ppTouches.size() != 1){
			pressedAsMonster = false;
		}
		if(playerPick.ppTouches.size() == 1 && !pressedAsMonster){
			pressedAsMonsterT = Tools.getTicks();
			pressedAsMonster = true;
		}
		if(playerPick.ppTouches.size() == 1 && pressedAsMonster){
			if(Tools.getTicks()-pressedAsMonsterT>1000){
				if (Grid.Objects[playerPick.ppTouches.get(0)].Type == objectType.Monster){
					monsterT M = (monsterT)Grid.Objects[playerPick.ppTouches.get(0)];
					
					float w = getStringWidth(M.Name);
					float x = windowSize.x*.15f;
					float wi = windowSize.x*.70f;
					float yi = M.Position.y - wi/3;
					
					if(yi<0){
						yi = M.Position.y + wi*1.15f;
					}
					
					spriteBatch.begin();
				
					spriteBatch.setColor(.075f, .075f, .075f, 1);
					spriteBatch.draw(whiteTexture.Region, windowSize.x*.15f - 8, yi-8, 0, 0, windowSize.x*.70f+16, windowSize.x*.70f+16,	1, 1,	0);
					
					spriteBatch.setColor(.15f, .15f, .15f, 1);
					spriteBatch.draw(whiteTexture.Region, windowSize.x*.15f, yi, 0, 0, windowSize.x*.70f, windowSize.x*.70f,	1, 1,	0);
					
					spriteBatch.setColor(0, 0, 0, 0.5f);
					spriteBatch.draw(M.texNormal.Region, windowSize.x/2-gridObjectSize.x/2 + gridObjectSize.x+1, yi + gridObjectSize.y+1, 0, 0, gridObjectSize.x, gridObjectSize.y,	-1, -1,	0);
					spriteBatch.setColor(1, 1, 1, 1);
					spriteBatch.draw(M.texNormal.Region, windowSize.x/2-gridObjectSize.x/2 + gridObjectSize.x, yi + gridObjectSize.y, 0, 0, gridObjectSize.x, gridObjectSize.y,	-1, -1,	0);
					drawText(M.Name, windowSize.x/2 - w/2, yi + gridObjectSize.y*1.05f , 1,1,1,1);
					//Element
					w = getStringWidth("Element");
					drawText("Element", x + wi*.25f - w/2, yi + gridObjectSize.y*1.55f , 1,1,1,1);
					w = getStringWidth(elementType.getElementName(M.Element));
					drawText(elementType.getElementName(M.Element), x + wi*.75f - w/2, yi + gridObjectSize.y*1.55f , 1,1,1,1);
					
					
					//ATK
					w = getStringWidth("Attack");
					drawText("Attack", x + wi*.25f - w/2, yi + gridObjectSize.y*2.05f , 1,1,1,1);
					w = getStringWidth(String.valueOf(M.Attack));
					drawText(String.valueOf(M.Attack), x + wi*.75f - w/2, yi + gridObjectSize.y*2.05f , 1,1,1,1);
					
					//DEF
					w = getStringWidth("Deffense");
					drawText("Deffense", x + wi*.25f - w/2, yi + gridObjectSize.y*2.55f , 1,1,1,1);
					w = getStringWidth(String.valueOf(M.Defense));
					drawText(String.valueOf(M.Defense), x + wi*.75f - w/2, yi + gridObjectSize.y*2.55f , 1,1,1,1);
					
					spriteBatch.end();
				}
			}
		}
	}
	long pressedAsMonsterT = 0;
	boolean pressedAsMonster = false;
	
	
	long stepSwitch;
	long stepSwitchTi = 75;
	
	int toDestroydisappears = 0;
	
	long showTimes = Tools.getTicks();
	
	long objectTime;
	long threadTime;
	long stepsTime;
	long hudTime;
	boolean Done = false;
	
	void changeStep(int New){
		lastStep = Step;
		Step = New;
	}
    
	float var = .17f;
	float p = 0;
	
	void drawGridBack(){
		/* Draw Grid back */

		spriteBatch.begin();
		spriteBatch.setColor(.15f, .15f, .15f, 1);
		spriteBatch.draw(whiteTexture.Region, gridPosition.x -4, gridPosition.y -4,	0, 0, gridSize.x +8, gridSize.y +8,	1, 1,	0);
		spriteBatch.setColor(1, 1, 1, 1);
		spriteBatch.end();
		
		if(doChangeBackground && oldBackground != null){
			currentAlpha = Tools.Translate(currentAlpha, 100, .03f);
			spriteBatch.begin();
			spriteBatch.setColor(1, 1, 1, 1);
			spriteBatch.draw(oldBackground.Region, gridPosition.x, gridPosition.y,	0, 0,gridSize.x, gridSize.y,	1, 1,	0);
			spriteBatch.setColor(1, 1, 1, 1);
			spriteBatch.end();
			if(currentAlpha == 100) doChangeBackground = false;
		}
		
		spriteBatch.begin();
		spriteBatch.setColor(1, 1, 1, currentAlpha/100);
		spriteBatch.draw(currentBackground.Region, gridPosition.x, gridPosition.y,	0, 0,gridSize.x, gridSize.y,	1, 1,	0);
		spriteBatch.setColor(1, 1, 1, 1);
		spriteBatch.end();
		
		spriteBatch.begin();
		spriteBatch.setColor(.90f, .90f, .90f, 0.4f);
		spriteBatch.draw(whiteTexture.Region, gridPosition.x, gridPosition.y,	0, 0,gridSize.x, gridSize.y,	1, 1,	0);
		spriteBatch.setColor(1, 1, 1, 1);
		spriteBatch.end();
		
	}
	
	void Update(){
		threadTime = Tools.getTicks();
		//FPS.logFrame();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    Gdx.gl.glEnable(GL10.GL_BLEND);
	    Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
	   
	    if(inLoadScreen){
	    	sysLoad.Update();
	    	return;
	    }
	    if(inStartScreen){
	    	startScreen.Update();
	    	return;
	    }
	    
		spriteBatch.begin();
		spriteBatch.setColor(.05f, .05f, .05f, 1);
		spriteBatch.draw(whiteTexture.Region, 0, 0,	0, 0, windowSize.x, windowSize.y,	1, 1,	0);
		spriteBatch.setColor(1, 1, 1, 1);
		
		spriteBatch.setColor(.10f, .10f, .10f, 1);
		spriteBatch.draw(whiteTexture.Region, 0, 0,	0, 0, windowSize.x, p*4.30f + 6 ,	1, 1,	0);
		spriteBatch.setColor(1, 1, 1, 1);
		
		spriteBatch.setColor(.15f, .15f, .15f, 1);
		spriteBatch.draw(whiteTexture.Region, 0, p*4.27f,	0, 0, windowSize.x, 12,	1, 1,	0);
		spriteBatch.setColor(1, 1, 1, 1);
		spriteBatch.end();
	    
	    hudTime = Tools.getTicks();
	    Hud.Update();
	    hudTime = Tools.getTicks() - hudTime;
	    
	    stepsTime = Tools.getTicks();
	    drawGridBack();
	    long stepSwitchT = stepSwitchTi;
	    
	    if(Gdx.input.isTouched()){
	    	stepSwitchT /= 2;
	    }
	    
//		if(Gdx.app.getType() == ApplicationType.Desktop){
//			if (Gdx.input.isKeyPressed(Keys.R)){
//				Restart();
//			}
//			if (Gdx.input.isKeyPressed(Keys.S)){
//				Save("save.sav");
//			}
//			if (Gdx.input.isKeyPressed(Keys.L)){
//				Load("save.sav");
//			
//			}
//			if (Gdx.input.isKeyPressed(Keys.LEFT)){
//				if (currentTurn>0){
//					--currentTurn;
//				}
//			
//			}
//			if (Gdx.input.isKeyPressed(Keys.RIGHT)){
//				++currentTurn;
//				
//			}
//			if (Gdx.input.isKeyPressed(Keys.SPACE)){
//				Player.Money += 100;
//				
//			}
//			if (Gdx.input.isKeyPressed(Keys.K)){
//				Player.currentAttack *= 2;
//				
//			}
//		}
	    
		/* Player picks */
		if(Step == stepPlayerPick){
			if (Gdx.input.isKeyPressed(Keys.BACK)){
				Save("game.sav");
				Gdx.app.exit();
			}
			noMonsters = false;
			if (playerPick.updatePlayerPick()){
				changeStep(stepPPShadowIn);
				SBG.sceneBackground = true;
				alvEnemies = currentEnemyNumber;
			}
		}else
		/* Player Pick Shadow In */
		if(Step == stepPPShadowIn){
			if(SBG.isIn() && stepSwitch == 0){
				stepSwitch = Tools.getTicks();
			}
			if(Tools.getTicks()-stepSwitch>stepSwitchT && SBG.isIn()){
				stepSwitch = 0;
				changeStep(stepDoPick);
			}
		}else
		/* Do Player Pick*/
		if(Step == stepDoPick){
			objectsMoveDown = false;
			if(playerPick.ppTouches.size() == 0 && toDestroydisappears<=0){
				if(stepSwitch == 0){
					stepSwitch = Tools.getTicks();
				}
				if(Tools.getTicks()-stepSwitch>stepSwitchT*1.5 && stepSwitch != 0){
				    enemyQueue.addEnemiesToQueue();
					if(enemyQueue.Queue.size()>0){
						changeStep(stepPPShadowOut);
					}else{
						changeStep(stepCreate);
						objectsMoveDown = true;
					}
					SBG.sceneBackground = false;
					stepSwitch = 0;
					playerPick.ppDoNext = true;
				}
			}
			if(playerPick.ppDoNext == true && playerPick.ppTouches.size()>0){
				int i = playerPick.ppTouches.get(0);
				if(Grid.Objects[i].Type == objectType.Monster){
					if(stepSwitch == 0){
						stepSwitch = 1;
						monsterT M = (monsterT)Grid.Objects[i];
						int Eff = elementType.isEffective(Player.getElement(), M.Element);
						int Dmg = elementType.getDamage(Eff, Player.currentAttack);
						M.setBattleState(battleState.Slash);
						M.doDefense(Dmg);	
						if(Eff == elementType.effFactor.Effective){
							if(M.Element == elementType.Fire &&  Player.getElement() == elementType.Water){
								Social.giveAchivement(com.nite.rid.Social.achFireFighter);
							}
							if(M.Element == elementType.Grass &&  Player.getElement() == elementType.Fire){
								Social.giveAchivement(com.nite.rid.Social.achSuperEffective);
							}
							if(M.Element == elementType.Water &&  Player.getElement() == elementType.Grass){
								Social.giveAchivement(com.nite.rid.Social.achSuperEffective);
							}							
							Vec2 P = gridPToP(M.Position);
							if(M.isDead()) P.y += getStringHeight("A");
							createEffectText("Effective!", P.x, P.y, 0.98431f, 0, 0.07450980f, fontSize.Normal, 0.09f);
						}else
						if(Eff == elementType.effFactor.Uneffective){
							Vec2 P = gridPToP(M.Position);
							if(M.isDead()) P.y += getStringHeight("A");
							createEffectText("Uneffective", P.x, P.y, 0.9607f, 0.8901f, 0.019607f, fontSize.Normal, 0.05f);						
						}					
					}
					if(stepSwitch == 1 && ((monsterT)Grid.Objects[i]).isDone()){
						((monsterT)Grid.Objects[i]).setBattleState(battleState.None);
						if(((monsterT)Grid.Objects[i]).isDead()){
							Player.addEXP(((monsterT)Grid.Objects[i]).getEXP());
							changeStep(stepGetEXP);
							createDeadMonster((monsterT)Grid.Objects[i]);
							destroyGridObject(i);
							--currentEnemyNumber;
						}
						playerPick.ppTouches.remove(0);
						stepSwitch = 0;
					}
				}else{
					createDisapper(Grid.Objects[i].Type, Grid.Objects[i].Position);
					--((pickObjectT)Grid.Objects[i]).Times;
					if(((pickObjectT)Grid.Objects[i]).Times <= 0){
						playerPick.ppTouches.remove(0);
						destroyGridObject(i);
					}
					playerPick.ppDoNext = false;
				}
			}
		}else
		/* Player Pick Shadow Out */
		if(Step == stepPPShadowOut){
			if(SBG.isOut() && stepSwitch == 0){
				stepSwitch = Tools.getTicks();
			}
			if(Tools.getTicks()-stepSwitch>stepSwitchT && SBG.isOut()){
				changeStep(stepBShadowIn);
				SBG.sceneBackground = true;
				stepSwitch = 0;
			}
		}else
		/* Battle Shadow In */
		if(Step == stepBShadowIn){
			if(SBG.isIn() && stepSwitch == 0){
				stepSwitch = Tools.getTicks();
			}
			if(Tools.getTicks()-stepSwitch>stepSwitchT && SBG.isIn()){
				changeStep(stepBattle);
				stepSwitch = 0;
			}
		}else
		/* Enemy attacks */
		if(Step == stepBattle){
			if(enemyQueue.Queue.size()>0){
				if(stepSwitch == 0){
					stepSwitch = 1;
					((monsterT)Objects[enemyQueue.Queue.get(0)]).doAttack();
				}
				if(stepSwitch == 1 && ((monsterT)Objects[enemyQueue.Queue.get(0)]).isDone()){
					Player.doDamage(((monsterT)Objects[enemyQueue.Queue.get(0)]).getAttackDamage());
				    if(Player.isDead()){
				    	changeStep(stepPlayerDied);
				    	playerDied.Start();
				    }
					enemyQueue.Queue.remove(0);
					stepSwitch = 0;
				}
			}
			if(enemyQueue.Queue.size() == 0 && !Player.isDead()){
				changeStep(stepBShadowOut);
				SBG.sceneBackground = false;
				objectsMoveDown = true;
				stepSwitch = 0;
			}
		}else
		/* Battle Shadow Out */
		if(Step == stepBShadowOut){
			if(SBG.isOut() && stepSwitch == 0){
				stepSwitch = Tools.getTicks();
			}
			if(Tools.getTicks()-stepSwitch>stepSwitchT && SBG.isOut()){
				changeStep(stepCreate);
				stepSwitch = 0;
			}
		}else
		/* Empty slots are filled with new objects */
		if(Step == stepCreate){
		    if(alvEnemies == maxEnemyNumber && currentEnemyNumber == 0 && noMonstersBonus){
		    	Score += 50;
		    	noMonstersBonus = false;
		    	noMonsters = true;
		    	createEffectText("NO MONTERS!", windowSize.x/2, windowSize.y/2, 1f, 1f, 0, fontSize.Normal, 0.05f);
		    	createEffectText("SCORE +50", windowSize.x/2, windowSize.y/2 - getStringHeight("A")*2, 0, 1f, 0, fontSize.Normal, 0.05f);
		    	Tools.Print("NO MONSTERS!");
		    	Social.giveAchivement(com.nite.rid.Social.achNoMonsters);
		    }
			updateObjectCreator();
			if(activeObjects == mActiveObjects){
				noMonsters = false;
				noMonstersBonus = true;
				++currentTurn;
				if(currentTurn >= 80){
					Social.giveAchivement(com.nite.rid.Social.achHardToKill);
				}
				if(currentTurn >= 150){
					Social.giveAchivement(com.nite.rid.Social.achWorthyWarrior);
				}
				++totalTurnsPassed;
				changeStep(stepPlayerPick);
				Player.turnEnd();
				if(doChangeStage){
					changeBackground(getStageBackground(currentStage.Type));
					doChangeStage = false;
				}
				if(firstGame){
					Save("game.sav");
					firstGame = false;
				}
			}
		}else
		if(Step == stepGetEXP){
			if(Player.updateExp()){
				if(Player.isLvUp()){
					Step = stepLevelUp;
					lvUP.Start();
					Player.doLevelUp();
				}else
					Step = lastStep;
			}
		}else
		if(Step == stepLevelUp){
			if(lvUP.isDone()){
				Step = lastStep;
				lvUP.End();
				Player.applyLevelUp();
				if(!Player.isExpDone()){
					Step = stepGetEXP;
				}
			}
		}else
		if(Step == stepPlayerDied){
			if(Score > yourBest){
				yourBest = Score;
				Social.submitScore(Score);
			}
			if(playerDied.isDone()){
				playerDied.End();
				Restart();
				Save("game.sav");
				return;
			}
		}else
		if(Step == stepSkillShadowIn){
			if(SBG.isIn() && stepSwitch == 0){
				stepSwitch = Tools.getTicks();
			}
			if(Tools.getTicks()-stepSwitch>stepSwitchT && SBG.isIn()){
				changeStep(stepSkill);
				stepSwitch = 0;
			}
		}else
		if(Step == stepSkill){
			if(Skills.Used.isDone()){
				if(Skills.Used.isFadeIn()){
					changeStep(stepSkillShadowOut);
					SBG.sceneBackground = false;
				}else{
					Skills.Done();
					if(enemyQueue.Queue.size()>0){
						changeStep(stepBShadowIn);
					}else{
						noMonstersBonus = false;
						changeStep(stepCreate);
						objectsMoveDown = true;
					}
				}
				//noMonsters = false;
				//alvEnemies = currentEnemyNumber;
				enemyQueue.addEnemiesToQueue();
			}
		}else
		if(Step == stepSkillShadowOut){
			if(SBG.isOut() && stepSwitch == 0){
				stepSwitch = Tools.getTicks();
			}
			if(Tools.getTicks()-stepSwitch>stepSwitchT && SBG.isOut()){
				if(enemyQueue.Queue.size()>0){
					changeStep(stepBShadowIn);
					SBG.sceneBackground = true;
				}else{
					changeStep(stepCreate);
					objectsMoveDown = true;
				}
				stepSwitch = 0;
				Skills.Done();
			}
		}
		Shop.itemCreator();
		
		stepsTime = Tools.getTicks() - stepsTime;
		objectTime = Tools.getTicks();
	    updateObjects();
	    if(Step == stepLevelUp) lvUP.Update();
	    if(Step == stepPlayerDied) playerDied.Update();
	    
	    if(Step == stepSkill){
	    	Skills.Used.Using();
	    }
	    spriteBatch.begin();
		for(int i=0; i<Grid.gridSize; ++i){
			if (disappearObjects[i] != null){
				disappearObjects[i].Update();
			}
		}
		spriteBatch.end(); 
		if(Step == stepInGameMenu){
			inGameMenu.Update();
			if(inGameMenu.isDone()){
				Step = stepPlayerPick;
			}
		}
		if(Step == stepInGameEquip){
			inGameEquip.Update();
			if(inGameEquip.isDone()){
				Step = stepPlayerPick;
			}
		}	
		if(Step == stepInShop){
			Shop.Update();
			if(Shop.isDone()){
				Step = stepPlayerPick;
			}
		}
		if(Step == stepInSkill){
			skillMenu.Update();
			if(skillMenu.isDone()){
				Step = stepPlayerPick;
			}
		}
		Transition.Update();
		if(totalTurnsPassed >= 100 && Social.isInit()){
			Social.giveAchivement(com.nite.rid.Social.achDungeonAddict);
		}
		if(totalMonstersKilled >= 10 && Social.isInit()){
			Social.giveAchivement(com.nite.rid.Social.achMonsterHunter);
		}
		if(totalMonstersKilled >= 50 && Social.isInit()){
			Social.giveAchivement(com.nite.rid.Social.achKillerInstict);
		}
		if(Score >= 1500 && Social.isInit()){
			Social.giveAchivement(com.nite.rid.Social.achPotentialWarrior);
		}
		if(Player.Money >= 500 && Social.isInit()){
			Social.giveAchivement(com.nite.rid.Social.achDungeonRaider);
		}
		if(Player.Money >= 1000 && Social.isInit()){
			Social.giveAchivement(com.nite.rid.Social.achGreedyRaider);
		}
		Message.Update();		
	    objectTime = Tools.getTicks() - objectTime;
	    threadTime = Tools.getTicks() - threadTime;
//	    if(Tools.getTicks() - showTimes >= 1000){
//	    	Tools.Print("Steps Time: "+String.valueOf(stepsTime));
//	    	Tools.Print("Object Time: "+String.valueOf(objectTime));
//	    	Tools.Print("HUD Time: "+String.valueOf(hudTime));
//	    	Tools.Print("Thread Time: "+String.valueOf(threadTime));
//	    	showTimes = Tools.getTicks();
//	    }
	}
	monsterTypeT monsterFromName(String Name){
		for(int i=0; i<Monsters.size(); ++i){
			if(Name.equals(Monsters.get(i).Name)){
				return Monsters.get(i); 
			}
		}
		return null;
	}
	
	
	void loadGrid(Preferences Sv){
		for(int i=0; i<(int)Grid.tileSize.x; ++i){
			for(int j=0; j<(int)Grid.tileSize.y; ++j){
				String Ind = "game.grid.object"+String.valueOf(i)+String.valueOf(j);
				String T = Sv.getString(Ind, "NULL");
				Vec2 P = new Vec2(Grid.objectSize.x * i, Grid.objectSize.x * j);
				int k = getEmptyObjectList();
				if(T.equals("monster")){
					boolean Boss = Sv.getBoolean(Ind+"Boss", false);
					String Name = Sv.getString(Ind+"name", "");
					Objects[k] = new monsterT(this, Name, 0, getMonsterType(Name), 0, 0, Boss);
					monsterT Current = (monsterT) Objects[k];
					Current.Name = Name + (Boss ? "King" : "");
					Current.Health = Sv.getInteger(Ind+"health", 0);
					Current.Element = Sv.getInteger(Ind+"element", 0);
					Current.initHealth = Sv.getInteger(Ind+"initHealth", 0);
					Current.Attack = Sv.getInteger(Ind+"Attack", 0);
					Current.Defense = Sv.getInteger(Ind+"Defense", 0);
					Current.initT = Sv.getLong(Ind+"initT",  0);
					Current.Tgger = Sv.getBoolean(Ind+"Tgger", false);
					Current.State = Sv.getInteger(Ind+"State", 0);
					Current.Level = Sv.getInteger(Ind+"Level", 0);
					Current.initTurn = Sv.getInteger(Ind+"initTurn", 0);
					Current.currentDamage = Sv.getInteger(Ind+"currentDamage", 0);
					Current.Done = Sv.getBoolean(Ind+"Done", false);
					Current.Just = Sv.getBoolean(Ind+"Just", false);
					Current.attackType = Sv.getInteger(Ind+"attackType", 0);
					Current.currentState = Sv.getInteger(Ind+"currentState", 0);
					Current.isFrozen = Sv.getBoolean(Ind+"isFrozen", false);
					Current.frozenTimeout = Sv.getInteger(Ind+"frozenTimeout", 0);
					Current.frozeTurn = Sv.getInteger(Ind+"frozeTurn", 0);
					Current.Money = Sv.getInteger(Ind+"Money", 0);
				}else
				if(T.equals("coin")){
					Objects[k] = new coinT(this);									
					((pickObjectT)Objects[k]).Times = Sv.getInteger(Ind+"x", 1);
				}else
				if(T.equals("heart")){
					Objects[k] = new heartT(this);			
					((pickObjectT)Objects[k]).Times = Sv.getInteger(Ind+"x", 1);
				}else
				if(T.equals("shield")){
					Objects[k] = new shieldT(this);				
					((pickObjectT)Objects[k]).Times = Sv.getInteger(Ind+"x", 1);
				}else
				if(T.equals("sword")){
					Objects[k] = new swordT(this);			
					((pickObjectT)Objects[k]).Times = Sv.getInteger(Ind+"x", 1);
				}
				if(Objects[k] == null)	continue;
				if(!T.equals("monster")) ((pickObjectT)Objects[k]).Times = Sv.getInteger(Ind+"x", 1);
				((gridObjectT) Objects[k]).setPosition(P);	
				((gridObjectT) Objects[k]).gameIndex = k;
			}
		}
	}
	
	
	void saveGrid(Preferences Sv){
		for(int i=0; i<(int)Grid.tileSize.x; ++i){
			for(int j=0; j<(int)Grid.tileSize.y; ++j){
				Vec2 P = new Vec2(Grid.objectSize.x * i, Grid.objectSize.y * j);
				int ind = Grid.getPosition(P);
				String Ind = "game.grid.object"+String.valueOf(i)+String.valueOf(j);
				if(Grid.Objects[ind] == null) continue;
				if(Grid.Objects[ind].Type == objectType.Monster){
					Sv.putString(Ind, "monster");
					monsterT Current = ((monsterT)Grid.Objects[ind]);
					Sv.putString(Ind+"name", Current.Self.Name);
					Sv.putInteger(Ind+"health", Current.Health);
					Sv.putInteger(Ind+"element", Current.Element);
					Sv.putInteger(Ind+"initHealth", Current.initHealth);
					Sv.putInteger(Ind+"Attack", Current.Attack);
					Sv.putInteger(Ind+"Defense", Current.Defense);
					Sv.putLong(Ind+"initT", Current.initT);
					Sv.putBoolean(Ind+"Tgger", Current.Tgger);
					Sv.putInteger(Ind+"State", Current.State);
					Sv.putInteger(Ind+"Level", Current.Level);
					Sv.putInteger(Ind+"initTurn", Current.initTurn);
					Sv.putInteger(Ind+"currentDamage", Current.currentDamage);
					Sv.putBoolean(Ind+"Done", Current.Done);
					Sv.putBoolean(Ind+"Just", Current.Just);
					Sv.putInteger(Ind+"attackType", Current.attackType);
					Sv.putInteger(Ind+"currentState", Current.currentState);
					Sv.putBoolean(Ind+"isFrozen", Current.isFrozen);
					Sv.putInteger(Ind+"frozenTimeout", Current.frozenTimeout);
					Sv.putInteger(Ind+"frozeTurn", Current.frozeTurn);
					Sv.putInteger(Ind+"Money", Current.Money);
					Sv.putBoolean(Ind+"Boss", Current.Boss);
				}else
				if(Grid.Objects[ind].Type == objectType.Coin){
					Sv.putString(Ind, "coin");
					Sv.putInteger(Ind+"x", ((pickObjectT)Grid.Objects[ind]).Times);
				}else
				if(Grid.Objects[ind].Type == objectType.Heart){
					Sv.putString(Ind, "heart");
					Sv.putInteger(Ind+"x", ((pickObjectT)Grid.Objects[ind]).Times);
				}else
				if(Grid.Objects[ind].Type == objectType.Shield){
					Sv.putString(Ind, "shield");
					Sv.putInteger(Ind+"x", ((pickObjectT)Grid.Objects[ind]).Times);
				}else
				if(Grid.Objects[ind].Type == objectType.Sword){
					Sv.putString(Ind, "sword");
					Sv.putInteger(Ind+"x", ((pickObjectT)Grid.Objects[ind]).Times);
				}			
			}			
		}
	}
	void Save(String File){
		Preferences Sv = Gdx.app.getPreferences(File);
		Sv.clear();
		Player.Save(Sv);
		Shop.Save(Sv);
		saveGrid(Sv);
		Sv.putString("___RID___", "SAVEGAME");
		Sv.putInteger("game.objectListSize", objectListSize);
		Sv.putInteger("game.currentEnemyNumber", currentEnemyNumber);
		Sv.putInteger("game.maxEnemyNumber", maxEnemyNumber);
		Sv.putInteger("game.enemyKilled", enemyKilled);
		Sv.putInteger("game.Score", Score);
		Sv.putInteger("game.currentFontSize", currentFontSize);
		Sv.putInteger("game.currentTurn", currentTurn);
		Sv.putInteger("game.activeObjects", activeObjects);
		Sv.putInteger("game.mActiveObjects", mActiveObjects);
		Sv.putBoolean("game.objectsMoveDown", objectsMoveDown);
		Sv.putBoolean("game.noMonstersBonus", noMonstersBonus);
		Sv.putInteger("game.alvEnemies", alvEnemies);
		Sv.putInteger("game.stage.Type", currentStage.Type);
		Sv.putInteger("game.stage.nextBoss", currentStage.nextBoss);
		Sv.putBoolean("game.stage.createdBoss", currentStage.createdBoss);
		Sv.putString("game.stage.Head", currentStage.Head.Name);
		Sv.putString("SAVE", "SAVE");
		for(int i=0; i<currentStage.MList.size(); ++i){
			Sv.putString("game.stage.MList"+String.valueOf(i), currentStage.MList.get(i).Name);
		}
		Sv.flush();		
		Tools.Print("Saved");
	}
	void saveSettings(){
		Preferences Sv = Gdx.app.getPreferences("settings.cfg");
		Sv.putFloat("game.Volume", Volume);
		Sv.putInteger("game.totalTurnsPassed", totalTurnsPassed);
		Sv.putInteger("game.totalMonstersKilled", totalMonstersKilled);
		Sv.putInteger("game.yourBest", yourBest);
		Sv.flush();		
	}
	void loadSettings(){
		Preferences Sv = Gdx.app.getPreferences("settings.cfg");
		Volume = Sv.getFloat("game.Volume", Volume);
		totalTurnsPassed = Sv.getInteger("game.totalTurnsPassed", totalTurnsPassed);
		totalMonstersKilled = Sv.getInteger("game.totalMonstersKilled", totalMonstersKilled);
		yourBest = Sv.getInteger("game.yourBest", yourBest);
	}
	boolean firstGame = false;
	void saveEmptyGame(String File){
		Preferences Sv = Gdx.app.getPreferences("settings.cfg");
		Sv.putString("SAVE", "NOSAVE");
		Sv.flush();	
	}
	boolean isSaveEmpty(String File){
		Preferences Sv = Gdx.app.getPreferences(File);
		String State = Sv.getString("SAVE", "NULL");
		return State.equals("NOSAVE") || State.equals("NULL");
	}
	void Load(String File){
		Restart();
		Step = stepPlayerPick;
		Preferences Sv = Gdx.app.getPreferences(File);
		if(Sv.getString("___RID___", "NULL").equals("NULL")){
			Step = stepCreate;
			firstGame = true;
			return;
		}
		Player.Load(Sv);
		Shop.Load(Sv);
		loadGrid(Sv);
		objectListSize = Sv.getInteger("game.objectListSize", objectListSize);
		currentEnemyNumber = Sv.getInteger("game.currentEnemyNumber", currentEnemyNumber);
		maxEnemyNumber = Sv.getInteger("game.maxEnemyNumber", maxEnemyNumber);
		enemyKilled = Sv.getInteger("game.enemyKilled", enemyKilled);
		Score = Sv.getInteger("game.Score", Score);
		currentFontSize = Sv.getInteger("game.currentFontSize", currentFontSize);
		currentTurn = Sv.getInteger("game.currentTurn", currentTurn);
		activeObjects = Sv.getInteger("game.activeObjects", activeObjects);
		mActiveObjects = Sv.getInteger("game.mActiveObjects", mActiveObjects);
		objectsMoveDown = Sv.getBoolean("game.objectsMoveDown", objectsMoveDown);
		alvEnemies = Sv.getInteger("game.alvEnemies", alvEnemies);
		noMonstersBonus = Sv.getBoolean("game.noMonstersBonus", noMonstersBonus);
		currentStage = new stageT(this, Sv.getInteger("game.stage.Type", currentStage.Type));
		currentStage.nextBoss 	= Sv.getInteger("game.stage.nextBoss", currentStage.nextBoss);
		currentStage.createdBoss = Sv.getBoolean("game.stage.createdBoss", currentStage.createdBoss);
		currentStage.Head		= monsterFromName(Sv.getString("game.stage.Head", currentStage.Head.Name));
		changeBackground(getStageBackground(currentStage.Type));		
		int k = currentStage.MList.size();
		currentStage.MList.clear();
		for(int i=0; i<k; ++i){
			currentStage.MList.add(monsterFromName(Sv.getString("game.stage.MList"+String.valueOf(i), currentStage.Head.Name)));
		}	
		objectsMoveDown = false;
		Tools.Print("Loaded");
		
	}
	void Restart(){
		Step = stepCreate;
		currentFontSize = fontSize.Normal;
		currentEnemyNumber = 0;
		currentTurn = 0;
		activeObjects = 0;
		playerPick.Clear();
		objectsMoveDown = true;
		enemyQueue.Clear();
		for(int i=0; i<objectListSize; ++i){
			Objects[i] = null;
		}
		enemyKilled = 0;
		Score = 0;
		changeStage();
		SBG.sceneBackground = false;
		Grid.Clear();
		Player.Restart();
		Shop.Restart();
		stepSwitchTi = 150;
		toDestroydisappears = 0;
		showTimes = Tools.getTicks();
		noMonsters 		= false;
		alvEnemies 			= 0;
		noMonstersBonus = true;
	}
	Social Social = null;
	GameT(Social Soc){
		Social = Soc;
		Init();
	}
}