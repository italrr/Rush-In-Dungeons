package com.nite.rid;

import java.util.ArrayList;

class skillSystemT {
	GameT Game;
	skillT Used = null;
	ArrayList<Integer> objectsInFade = new ArrayList<Integer>();
	boolean isInFadeIn(int i){
		for(int j=0; j<objectsInFade.size(); ++j){
			if(objectsInFade.get(j) == i) return true;
		}
		return false;
	}
	void addObjects(){
		if(!Used.isFadeIn()) return;
		for(int i=0; i<Game.objectListSize; ++i){
			if(Game.Objects[i] == null) continue;
			if(Game.Objects[i].Type == Used.getFadeInType()){
				objectsInFade.add(i);
			}
		}
	}
	void Done(){
		objectsInFade.clear();
	}
	skillSystemT(GameT G){
		Game = G;
	}
	skillT getSkill(String Name){
		if(Name.equals("Fireball")) return new fireBallSkillT(Game);
		if(Name.equals("Heal")) return new healUpSkillT(Game);
		if(Name.equals("Greedy Raid")) return new greedyRaidSkillT(Game);
		if(Name.equals("Freeze")) return new freezeSkillT(Game);
		if(Name.equals("RAGE")) return new rageSkillT(Game);
		if(Name.equals("King Of Hearts")) return new kingOfHeartsSkillT(Game);
		if(Name.equals("Multiply Attack")) return new multiplyAttackSkillT(Game);
		if(Name.equals("Blizzard")) return new blizzardSkillT(Game);
		if(Name.equals("Waterize")) return new waterizeSkillT(Game);
		if(Name.equals("Firerize")) return new firerizeSkillT(Game);
		if(Name.equals("Grassize")) return new grassizeSkillT(Game);
		if(Name.equals("Multiply Defense")) return new multiplyDefenseSkillT (Game);
		if(Name.equals("Multiply gens")) return new multiplygensSkillT (Game);
		if(Name.equals("Multiply Hearts")) return new multiplyHealSkillT  (Game);
		if(Name.equals("No element")) return new noElementSkillT  (Game);
		if(Name.equals("Spine Shot")) return new spineSkillT  (Game);
		if(Name.equals("Attack Boost")) return new attackBoostSkillT  (Game);
		if(Name.equals("Flee")) return new runawaySkillT  (Game);
		if(Name.equals("Gamble")) return new gambleSkillT  (Game);
		if(Name.equals("Bubbles")) return new bubblesSkillT  (Game);
		return null;
	}
	skillT makeSkillLvUpRandom(){
		skillT Sk = null;
		int S = Tools.Random(1, 11);
		if(S == 1){
			Sk = new fireBallSkillT(Game);
		}else
		if(S == 2){
			Sk = new freezeSkillT(Game);
		}else
		if(S == 3){
			Sk = new rageSkillT(Game);
		}else
		if(S == 4){
			Sk = new blizzardSkillT(Game);
		}else
		if(S == 5){
			Sk = new spineSkillT(Game);
		}else
		if(S == 6){
			Sk = new attackBoostSkillT(Game);
		}else
		if(S == 7){
			Sk = new attackBoostSkillT(Game);
		}else
		if(S == 8){
			Sk = new runawaySkillT(Game);
		}else
		if(S == 9){
			Sk = new gambleSkillT(Game);
		}else
		if(S == 10){
			Sk = new healUpSkillT(Game);
		}else
		if(S == 11){
			Sk = new bubblesSkillT(Game);
		}
		return Sk;
	}
	static final int highP = 45;
	static final int lowP = 30;
	
	skillT makeSkillRandom(){
		skillT Sk = null;
		int S = Tools.Random(1, 20);
		if(S == 1){
			Sk = new fireBallSkillT(Game);
		}else
		if(S == 2){
			Sk = new healUpSkillT(Game);
		}else
		if(S == 3){
			Sk = new greedyRaidSkillT(Game);
		}else
		if(S == 4){
			Sk = new freezeSkillT(Game);
		}else
		if(S == 5){
			Sk = new rageSkillT(Game);
		}else
		if(S == 6){
			Sk = new kingOfHeartsSkillT(Game);
		}else
		if(S == 7){
			Sk = new multiplyAttackSkillT(Game);
		}else
		if(S == 8){
			Sk = new blizzardSkillT(Game);
		}else
		if(S == 9){
			Sk = new waterizeSkillT(Game);
		}else
		if(S == 10){
			Sk = new grassizeSkillT(Game);
		}else
		if(S == 11){
			Sk = new firerizeSkillT(Game);
		}else
		if(S == 12){
			Sk = new multiplyDefenseSkillT (Game);
		}else
		if(S == 13){
			Sk = new multiplygensSkillT  (Game);
		}else
		if(S == 14){
			Sk = new multiplyHealSkillT (Game);
		}else
		if(S == 15){
			Sk = new noElementSkillT(Game);
		}else
		if(S == 16){
			Sk = new spineSkillT(Game);
		}else
		if(S == 17){
			Sk = new attackBoostSkillT(Game);
		}else
		if(S == 18){
			Sk = new runawaySkillT(Game);
		}else
		if(S == 19){
			Sk = new gambleSkillT(Game);
		}else
		if(S == 20){
			Sk = new gambleSkillT(Game);
		}
		return Sk;
	}
}

interface skillT {
	boolean isDone();
	void doUse();
	void Using();
	boolean isFadeIn();
	int getFadeInType();
	int getCoolDown();
	void setHit(int h);
	int getHit();
	int getTurnUsed();
	void setTurnUsed(int T);
	String getName();
	String getDescription();
	int getPrice();
	boolean isReady();
	textureT getIcon();
}

class skillBodyT{
	String Name;
	String Description;
	int turnUsed;
	int turnCoolDown;
	int Hit;
	int Price;
	int Element;
	boolean skillInFade;
	boolean Done;
	int fadeInType;
	GameT Game;
	textureT Icon;
}

class blizzardSkillT extends skillBodyT implements skillT {
	long initT;
	boolean Just = false;
	public String getName(){
		return Name;
	}
	public int getPrice(){
		return Price;
	}
	public String getDescription(){
		return Description;
	}
	public int getHit(){
		return Hit;
	}
	ArrayList<Integer> monsterList = new ArrayList<Integer>();
	blizzardSkillT(GameT G){
		Game			= G;
		Hit				= 8*(1 + Game.currentTurn/skillSystemT.lowP);
		turnCoolDown	= 9;
		Name 			= "Blizzard";
		Description		= "Freeze your enemies with \nan instant shot of cold \nice! 10% chanceof freezing \nyour enemy for 2 turns. \n[Damage: Attack+"+String.valueOf(Hit)+" | \nCooldown: "+String.valueOf(turnCoolDown-1)+" | \nElement: Water]";
		Element			= elementType.Water;
		skillInFade 	= true;
		fadeInType 		= objectType.Monster;
		Icon			= Game.blizzardTexture;
		turnUsed		= 0;
		Price			= 10*(1 + Game.currentTurn/skillSystemT.lowP);
	}
	public void setHit(int h){
		Hit = h;
		Description		= "Freeze your enemies with \nan instant shot of cold \nice! 10% chanceof freezing \nyour enemy for 2 turns. \n[Damage: Attack+"+String.valueOf(Hit)+" | \nCooldown: "+String.valueOf(turnCoolDown-1)+" | \nElement: Water]";
	}
	public void setTurnUsed(int T){
		turnUsed = T;
	}
	public int getTurnUsed(){
		return turnUsed;
	}
	public textureT getIcon(){
		return Icon;
	}
	public int getFadeInType(){
		return fadeInType;
	}
	public boolean isFadeIn(){
		return skillInFade;
	}
	public int getCoolDown(){
		return turnCoolDown - (Game.currentTurn - turnUsed);
	}
	public boolean isDone(){
		return Done;
	}
	public void doUse(){
		if(Game.currentTurn - turnUsed < turnCoolDown && turnUsed != 0) return;
		turnUsed = Game.currentTurn;
		initT = Tools.getTicks()-initT;
		Just = false;
		Game.noMonsters = false;
		Game.alvEnemies = Game.currentEnemyNumber;
		monsterList.clear();		
		for(int i=0; i<Game.objectListSize; ++i){
			if(Game.Objects[i] == null) continue;
			if(Game.Objects[i].Type == objectType.Monster){
				monsterList.add(i);
			}
		}
		Done = false;
	}
	
	public boolean isReady(){
		return !(Game.currentTurn - turnUsed < turnCoolDown && turnUsed != 0);
	}
	
	public void Using(){
		long T = Tools.getTicks()-initT;
		boolean everythingDone = false;
		int expGain = 0;
		if(T >= 75){
			if(!Just){
				long id = Game.freezeSFX.Play();
				Game.freezeSFX.setVolume(id, 1.5f);
				Game.freezeSFX.setPitch(id, 1.2f);
				for(int i=0; i<monsterList.size(); ++i){
					monsterT M = (monsterT)Game.Objects[monsterList.get(i)];
					int Eff = elementType.isEffective(elementType.Water, M.Element);
					if(Eff == elementType.effFactor.Effective){
						Vec2 P = Game.gridPToP(M.Position); P.y -= i*16;
						Game.createEffectText("Effective!", P.x, P.y, 0.98431f, 0, 0.07450980f, fontSize.Italic, 0.09f);
					}else
					if(Eff == elementType.effFactor.Uneffective){
						Vec2 P = Game.gridPToP(M.Position); P.y -= i*16;
						Game.createEffectText("Uneffective", P.x, P.y, 0.9607f, 0.8901f, 0.019607f, fontSize.Italic, 0.05f);						
					}
					int Dmg = elementType.getDamage(Eff, Hit + Game.Player.currentAttack);
					M.setBattleState(battleState.Cold);
					M.doDefenseSkill(Dmg);
				}
				expGain = 0;
				Just = true;
			}
		}
		if(T >= 75){
			everythingDone = true;
			for(int i=0; i<monsterList.size(); ++i){
				monsterT M = (monsterT)Game.Objects[monsterList.get(i)];
				if(!M.isDone()){
					everythingDone = false;
					break;
				}
			}
		}
		if(everythingDone){
			for(int i=0; i<monsterList.size(); ++i){
				monsterT M = (monsterT)Game.Objects[monsterList.get(i)];
				if(M.isDead()){
					Game.enemyQueue.removeEnemy(monsterList.get(i));
					expGain += M.getEXP();
					Game.createDeadMonster(M);
					Game.destroyGridObject(M.Index);
					--Game.currentEnemyNumber;
					monsterList.remove(i);
					i = -1;
				}else
				if(M.isDone()){
					if(Tools.Random(1, 10) == 10) M.Freeze(2);
					M.setBattleState(battleState.None);
					monsterList.remove(i);
					 i = -1;
				}
			}
			if(monsterList.size() > 0) return;
			if(expGain == 0){
				Done = true;
				return;
			}
			Game.Player.addEXP(expGain);
			Game.changeStep(GameT.stepGetEXP);
			expGain = 0;
			
		}
	}
}

class spineSkillT extends skillBodyT implements skillT {
	long initT;
	boolean Just = false;
	public String getName(){
		return Name;
	}
	public int getHit(){
		return Hit;
	}
	public int getPrice(){
		return Price;
	}
	public String getDescription(){
		return Description;
	}
	ArrayList<Integer> monsterList = new ArrayList<Integer>();
	spineSkillT(GameT G){
		Game			= G;
		Hit				= 5*(1 + Game.currentTurn/skillSystemT.lowP);;
		turnCoolDown	= 11;
		Name 			= "Spine Shot";
		Description		= "Pitch your enemies with \nseveral spine shots. \n[Damage: Attack+"+String.valueOf(Hit)+" | \nCooldown: "+String.valueOf(turnCoolDown-1)+" turns | \nElement: Grass]";
		Element			= elementType.Grass;
		skillInFade 	= true;
		fadeInType 		= objectType.Monster;
		Icon			= Game.spinesTexture;
		turnUsed		= 0;
		Price			= 10*(1 + Game.currentTurn/skillSystemT.lowP);;
	}
	public void setHit(int h){
		Hit = h;
		Description		= "Pitch your enemies with \nseveral spine shots. \n[Damage: Attack+"+String.valueOf(Hit)+" | \nCooldown: "+String.valueOf(turnCoolDown-1)+" turns | \nElement: Grass]";
	}
	public void setTurnUsed(int T){
		turnUsed = T;
	}
	public int getTurnUsed(){
		return turnUsed;
	}
	public textureT getIcon(){
		return Icon;
	}
	public int getFadeInType(){
		return fadeInType;
	}
	public boolean isFadeIn(){
		return skillInFade;
	}
	public int getCoolDown(){
		return turnCoolDown - (Game.currentTurn - turnUsed);
	}
	public boolean isDone(){
		return Done;
	}
	public void doUse(){
		if(Game.currentTurn - turnUsed < turnCoolDown && turnUsed != 0) return;
		turnUsed = Game.currentTurn;
		initT = Tools.getTicks()-initT;
		Just = false;
		Game.noMonsters = false;
		Game.alvEnemies = Game.currentEnemyNumber;
		monsterList.clear();		
		for(int i=0; i<Game.objectListSize; ++i){
			if(Game.Objects[i] == null) continue;
			if(Game.Objects[i].Type == objectType.Monster){
				monsterList.add(i);
			}
		}
		Done = false;
	}
	
	public boolean isReady(){
		return !(Game.currentTurn - turnUsed < turnCoolDown && turnUsed != 0);
	}
	long T2 = 0;
	public void Using(){
		long T = Tools.getTicks()-initT;
		boolean everythingDone = false;
		int expGain = 0;
		if(T >= 75){
			if(!Just){
				long id = Game.spineShotSFX.Play();
				Game.spineShotSFX.setVolume(id, 0.5f);
				Game.spineShotSFX.setPitch(id, 1.2f);
				for(int i=0; i<monsterList.size(); ++i){
					monsterT M = (monsterT)Game.Objects[monsterList.get(i)];
					int Eff = elementType.isEffective(elementType.Grass, M.Element);
					if(Eff == elementType.effFactor.Effective){
						Vec2 P = Game.gridPToP(M.Position); P.y -= i*16;
						Game.createEffectText("Effective!", P.x, P.y, 0.98431f, 0, 0.07450980f, fontSize.Italic, 0.09f);
					}else
					if(Eff == elementType.effFactor.Uneffective){
						Vec2 P = Game.gridPToP(M.Position); P.y -= i*16;
						Game.createEffectText("Uneffective", P.x, P.y, 0.9607f, 0.8901f, 0.019607f, fontSize.Italic, 0.05f);						
					}
					int Dmg = elementType.getDamage(Eff, Hit + Game.Player.currentAttack);
					M.setBattleState(battleState.Spine);
					M.doDefenseSkill(Dmg);
				}
				expGain = 0;
				Just = true;
			}
		}
		if(T >= 75){
			everythingDone = true;
			for(int i=0; i<monsterList.size(); ++i){
				monsterT M = (monsterT)Game.Objects[monsterList.get(i)];
				if(!M.isDone()){
					everythingDone = false;
					break;
				}
			}
		}
		if(Tools.getTicks()-T2 >= 60 && !everythingDone){
			Game.spineShotSFX.Play();
			T2 = Tools.getTicks();
		}
		if(everythingDone){
			for(int i=0; i<monsterList.size(); ++i){
				monsterT M = (monsterT)Game.Objects[monsterList.get(i)];
				if(M.isDead()){
					Game.enemyQueue.removeEnemy(monsterList.get(i));
					expGain += M.getEXP();
					Game.createDeadMonster(M);
					Game.destroyGridObject(M.Index);
					--Game.currentEnemyNumber;
					monsterList.remove(i);
					i = -1;
				}else
				if(M.isDone()){
					M.setBattleState(battleState.None);
					monsterList.remove(i);
					 i = -1;
				}
			}
			if(monsterList.size() > 0) return;
			if(expGain == 0){
				Done = true;
				return;
			}
			Game.Player.addEXP(expGain);
			Game.changeStep(GameT.stepGetEXP);
			expGain = 0;
			
		}
	}
}

class bubblesSkillT extends skillBodyT implements skillT {
	long initT;
	boolean Just = false;
	public String getName(){
		return Name;
	}
	public int getHit(){
		return Hit;
	}
	public int getPrice(){
		return Price;
	}
	public String getDescription(){
		return Description;
	}
	ArrayList<Integer> monsterList = new ArrayList<Integer>();
	bubblesSkillT(GameT G){
		Game			= G;
		Hit				= 7*(1 + Game.currentTurn/skillSystemT.lowP);;
		turnCoolDown	= 9;
		Name 			= "Bubbles";
		Description		= "Kill your enemies with an \ninstant shot of bubbles! \n[Damage: Attack+"+String.valueOf(Hit)+" \nCooldown: "+String.valueOf(turnCoolDown-1)+" turns | \nElement: Water]";
		Element			= elementType.Water;
		skillInFade 	= true;
		fadeInType 		= objectType.Monster;
		Icon			= Game.bubblesTexture;
		turnUsed		= 0;
		Price			= 10*(1 + Game.currentTurn/skillSystemT.lowP);
	}
	public void setHit(int h){
		Hit = h;
		Description		= "Kill your enemies with an \ninstant shot of bubbles! \n[Damage: Attack+"+String.valueOf(Hit)+" \nCooldown: "+String.valueOf(turnCoolDown-1)+" turns | \nElement: Water]";
	}
	public void setTurnUsed(int T){
		turnUsed = T;
	}
	public int getTurnUsed(){
		return turnUsed;
	}
	public textureT getIcon(){
		return Icon;
	}
	public int getFadeInType(){
		return fadeInType;
	}
	public boolean isFadeIn(){
		return skillInFade;
	}
	public int getCoolDown(){
		return turnCoolDown - (Game.currentTurn - turnUsed);
	}
	public boolean isDone(){
		return Done;
	}
	public void doUse(){
		if(Game.currentTurn - turnUsed < turnCoolDown && turnUsed != 0) return;
		turnUsed = Game.currentTurn;
		initT = Tools.getTicks()-initT;
		Just = false;
		monsterList.clear();	
		Game.noMonsters = false;
		Game.alvEnemies = Game.currentEnemyNumber;
		for(int i=0; i<Game.objectListSize; ++i){
			if(Game.Objects[i] == null) continue;
			if(Game.Objects[i].Type == objectType.Monster){
				monsterList.add(i);
			}
		}
		Done = false;
	}
	
	public boolean isReady(){
		return !(Game.currentTurn - turnUsed < turnCoolDown && turnUsed != 0);
	}
	
	public void Using(){
		long T = Tools.getTicks()-initT;
		boolean everythingDone = false;
		int expGain = 0;
		if(T >= 75){
			if(!Just){
				long id = Game.bubblesSFX.Play();
				//Game.bubblesSFX.setVolume(id, 0.5f);
				Game.bubblesSFX.setPitch(id, 1.7f);
				for(int i=0; i<monsterList.size(); ++i){
					monsterT M = (monsterT)Game.Objects[monsterList.get(i)];
					int Eff = elementType.isEffective(elementType.Water, M.Element);
					if(Eff == elementType.effFactor.Effective){
						Vec2 P = Game.gridPToP(M.Position); P.y -= i*16;
						Game.createEffectText("Effective!", P.x, P.y, 0.98431f, 0, 0.07450980f, fontSize.Italic, 0.09f);
					}else
					if(Eff == elementType.effFactor.Uneffective){
						Vec2 P = Game.gridPToP(M.Position); P.y -= i*16;
						Game.createEffectText("Uneffective", P.x, P.y, 0.9607f, 0.8901f, 0.019607f, fontSize.Italic, 0.05f);						
					}
					int Dmg = elementType.getDamage(Eff, Hit + Game.Player.currentAttack);
					M.setBattleState(battleState.Bubbles);
					M.doDefenseSkill(Dmg);
				}
				expGain = 0;
				Just = true;
			}
		}
		if(T >= 75){
			everythingDone = true;
			for(int i=0; i<monsterList.size(); ++i){
				monsterT M = (monsterT)Game.Objects[monsterList.get(i)];
				if(!M.isDone()){
					everythingDone = false;
					break;
				}
			}
		}
		if(everythingDone){
			for(int i=0; i<monsterList.size(); ++i){
				monsterT M = (monsterT)Game.Objects[monsterList.get(i)];
				if(M.isDead()){
					Game.enemyQueue.removeEnemy(monsterList.get(i));
					expGain += M.getEXP();
					Game.createDeadMonster(M);
					Game.destroyGridObject(M.Index);
					--Game.currentEnemyNumber;
					monsterList.remove(i);
					i = -1;
				}else
				if(M.isDone()){
					M.setBattleState(battleState.None);
					monsterList.remove(i);
					 i = -1;
				}
			}
			if(monsterList.size() > 0) return;
			if(expGain == 0){
				Done = true;
				return;
			}
			Game.Player.addEXP(expGain);
			Game.changeStep(GameT.stepGetEXP);
			expGain = 0;
			
		}
	}
}

class fireBallSkillT extends skillBodyT implements skillT {
	long initT;
	boolean Just = false;
	public String getName(){
		return Name;
	}
	public int getHit(){
		return Hit;
	}
	public int getPrice(){
		return Price;
	}
	public String getDescription(){
		return Description;
	}
	ArrayList<Integer> monsterList = new ArrayList<Integer>();
	fireBallSkillT(GameT G){
		Game			= G;
		Hit				= 5*(1 + Game.currentTurn/skillSystemT.lowP);;
		turnCoolDown	= 9;
		Name 			= "Fireball";
		Description		= "Burn your enemies with an \ninstant blast of fire! \n[Damage: Attack+"+String.valueOf(Hit)+" \nCooldown: "+String.valueOf(turnCoolDown-1)+" turns | \nElement: Fire]";
		Element			= elementType.Fire;
		skillInFade 	= true;
		fadeInType 		= objectType.Monster;
		Icon			= Game.fireBallTexture;
		turnUsed		= 0;
		Price			= 10*(1 + Game.currentTurn/skillSystemT.lowP);
	}
	public void setHit(int h){
		Hit = h;
		Description		= "Burn your enemies with an \ninstant blast of fire! \n[Damage: Attack+"+String.valueOf(Hit)+" \nCooldown: "+String.valueOf(turnCoolDown-1)+" turns | \nElement: Fire]";
	}
	public void setTurnUsed(int T){
		turnUsed = T;
	}
	public int getTurnUsed(){
		return turnUsed;
	}
	public textureT getIcon(){
		return Icon;
	}
	public int getFadeInType(){
		return fadeInType;
	}
	public boolean isFadeIn(){
		return skillInFade;
	}
	public int getCoolDown(){
		return turnCoolDown - (Game.currentTurn - turnUsed);
	}
	public boolean isDone(){
		return Done;
	}
	public void doUse(){
		if(Game.currentTurn - turnUsed < turnCoolDown && turnUsed != 0) return;
		turnUsed = Game.currentTurn;
		initT = Tools.getTicks()-initT;
		Just = false;
		monsterList.clear();	
		Game.noMonsters = false;
		Game.alvEnemies = Game.currentEnemyNumber;
		for(int i=0; i<Game.objectListSize; ++i){
			if(Game.Objects[i] == null) continue;
			if(Game.Objects[i].Type == objectType.Monster){
				monsterList.add(i);
			}
		}
		Done = false;
	}
	
	public boolean isReady(){
		return !(Game.currentTurn - turnUsed < turnCoolDown && turnUsed != 0);
	}
	
	public void Using(){
		long T = Tools.getTicks()-initT;
		boolean everythingDone = false;
		int expGain = 0;
		if(T >= 75){
			if(!Just){
				long id = Game.fireBallSFX.Play();
				Game.fireBallSFX.setVolume(id, 0.5f);
				Game.fireBallSFX.setPitch(id, 1.2f);
				for(int i=0; i<monsterList.size(); ++i){
					monsterT M = (monsterT)Game.Objects[monsterList.get(i)];
					int Eff = elementType.isEffective(elementType.Fire, M.Element);
					if(Eff == elementType.effFactor.Effective){
						Vec2 P = Game.gridPToP(M.Position); P.y -= i*16;
						Game.createEffectText("Effective!", P.x, P.y, 0.98431f, 0, 0.07450980f, fontSize.Italic, 0.09f);
					}else
					if(Eff == elementType.effFactor.Uneffective){
						Vec2 P = Game.gridPToP(M.Position); P.y -= i*16;
						Game.createEffectText("Uneffective", P.x, P.y, 0.9607f, 0.8901f, 0.019607f, fontSize.Italic, 0.05f);						
					}
					int Dmg = elementType.getDamage(Eff, Hit + Game.Player.currentAttack);
					M.setBattleState(battleState.Burn);
					M.doDefenseSkill(Dmg);
				}
				expGain = 0;
				Just = true;
			}
		}
		if(T >= 75){
			everythingDone = true;
			for(int i=0; i<monsterList.size(); ++i){
				monsterT M = (monsterT)Game.Objects[monsterList.get(i)];
				if(!M.isDone()){
					everythingDone = false;
					break;
				}
			}
		}
		if(everythingDone){
			for(int i=0; i<monsterList.size(); ++i){
				monsterT M = (monsterT)Game.Objects[monsterList.get(i)];
				if(M.isDead()){
					Game.enemyQueue.removeEnemy(monsterList.get(i));
					expGain += M.getEXP();
					Game.createDeadMonster(M);
					Game.destroyGridObject(M.Index);
					--Game.currentEnemyNumber;
					monsterList.remove(i);
					i = -1;
				}else
				if(M.isDone()){
					M.setBattleState(battleState.None);
					monsterList.remove(i);
					 i = -1;
				}
			}
			if(monsterList.size() > 0) return;
			if(expGain == 0){
				Done = true;
				return;
			}
			Game.Player.addEXP(expGain);
			Game.changeStep(GameT.stepGetEXP);
			expGain = 0;
			
		}
	}
}

class healUpSkillT extends skillBodyT implements skillT {
	int hpToAdd = 0;
	healUpSkillT(GameT G){
		Game			= G;
		Name 			= "Heal";
		turnCoolDown	= 11;
		Description		= "Recover 50% of your \nhealth. \n[Cooldown: "+String.valueOf(turnCoolDown-1)+" turns]";
		fadeInType 		= objectType.Unknown;
		skillInFade 	= true;
		Price			= 10*(1 + Game.currentTurn/skillSystemT.lowP);;
		Icon			= Game.healUpTexture;
		turnUsed		= 0;
	}
	public int getHit(){
		return Hit;
	}
	public void setHit(int h){
		
	}
	public int getTurnUsed(){
		return turnUsed;
	}
	public void setTurnUsed(int T){
		turnUsed = T;
	}
	public String getDescription(){
		return Description;
	}
	public String getName(){
		return Name;
	}
	public textureT getIcon(){
		return Icon;
	}
	public int getPrice(){
		return Price;
	}
	public int getFadeInType(){
		return fadeInType;
	}
	public boolean isFadeIn(){
		return skillInFade;
	}
	public int getCoolDown(){
		return turnCoolDown - (Game.currentTurn - turnUsed);
	}
	public boolean isDone(){
		return Done;
	}
	public void doUse(){
		if(Game.currentTurn - turnUsed < turnCoolDown && turnUsed != 0) return;
		turnUsed = Game.currentTurn;
		hpToAdd = (int) Tools.Round(Game.Player.maxHP*0.50f);
		long id = Game.healUpSFX.Play();
		Game.healUpSFX.setPitch(id, 0.5f);
		Game.healUpSFX.setVolume(id, 3f);
		Last = -1;
		Done = false;
	}
	float heartX;
	float heartY;
	void Draw(){
		Game.setFontSize(fontSize.Big);
		Game.spriteBatch.begin();
		float strW = Game.getStringWidth("+");
		float strH = Game.getStringHeight("A");
		float sprW = 128, sprH = 128;
		float w = sprW + strW*2.5f, h = strH;
		float x = Game.windowSize.x/2 - w/2.5f, y = Game.windowSize.y/2 - h/2;
		Game.drawText("+", x, y - strH/2, 0, 1, 0, 1);
		Game.setFontSize(fontSize.Normal);
		heartX = x+strW*2.5f;
		heartY = y;
		Game.spriteBatch.draw(Game.heartTexture.Region, x+strW*2.5f, y, sprW/4, sprH/4, sprW, sprH, 1, 1, 180);
		Game.drawText(String.valueOf(hpToAdd), x+strW*2.5f + sprW/4, y + sprH/4, 0, 1, 0, 1);
		Game.spriteBatch.end();
	}
	public boolean isReady(){
		return !(Game.currentTurn - turnUsed < turnCoolDown && turnUsed != 0);
	}
	int Last = -1;
	public void Using(){
		if(hpToAdd == 0 && Game.toDestroydisappears == 0) Done = true;
		Draw();
		if(hpToAdd == 0) return;
		if(Last == -1){
			Vec2 P = new Vec2();
			Last = Game.createDisapper(objectType.Heart, P);
			((disappearT)Game.disappearObjects[Last]).Position.Set(heartX - 128/4, heartY - 128/4);
			((disappearT)Game.disappearObjects[Last]).Size.Set(128, 128);
			--hpToAdd;
		}else{
			if(((disappearT)Game.disappearObjects[Last]).Just) Last = -1;
		}
	}
}

class kingOfHeartsSkillT extends skillBodyT implements skillT {
	kingOfHeartsSkillT(GameT G){
		Game			= G;
		turnCoolDown	= 11;
		Name 			= "King Of Hearts";
		Description		= "Collect all the hearts \non the field. \n[Cooldown: "+String.valueOf(turnCoolDown-1)+" turns]";
		skillInFade 	= false;
		Price			= 10*(1 + Game.currentTurn/skillSystemT.lowP);
		Icon			= Game.kingofHeartsTexture;
		turnUsed		= 0;
	}
	public void setHit(int h){
		
	}
	public int getHit(){
		return Hit;
	}
	public textureT getIcon(){
		return Icon;
	}
	public String getDescription(){
		return Description;
	}
	public void setTurnUsed(int T){
		turnUsed = T;
	}
	public int getTurnUsed(){
		return turnUsed;
	}
	public String getName(){
		return Name;
	}
	public int getFadeInType(){
		return fadeInType;
	}
	public boolean isFadeIn(){
		return skillInFade;
	}
	public int getCoolDown(){
		return turnCoolDown - (Game.currentTurn - turnUsed);
	}
	public int getPrice(){
		return Price;
	}
	public boolean isDone(){
		return Done;
	}
	public void doUse(){
		if(Game.currentTurn - turnUsed < turnCoolDown && turnUsed != 0) return;
		turnUsed = Game.currentTurn;
		for(int i=0; i<5; ++i){
			long id = Game.healUpSFX.Play();
			Game.healUpSFX.setPitch(id, 1.2f-((i+1)/2));
			Game.healUpSFX.setVolume(id, 1.2f);
		}
	}

	public boolean isReady(){
		return !(Game.currentTurn - turnUsed < turnCoolDown && turnUsed != 0);
	}
	public void Using(){
		for(int i=0; i<Game.Grid.gridSize; ++i){
			if(Game.Objects[i] == null) continue;
			if(Game.Objects[i].Type != objectType.Heart) continue;
			Game.playerPick.ppTouches.add(((gridObjectT)Game.Objects[i]).Index);
		}
		Game.Step = GameT.stepPPShadowIn;
		Game.SBG.sceneBackground = true;
	}
}

class firerizeSkillT extends skillBodyT implements skillT {
	firerizeSkillT(GameT G){
		Game			= G;
		turnCoolDown	= 16;
		Name 			= "Firerize";
		Description		= "Change your current \nweapon's element to fire. \nYou need a weapon in order \nto use this skill. \n[Only one use skill]";
		skillInFade 	= false;
		Price			= 10*(1 + Game.currentTurn/skillSystemT.lowP);
		Icon			= Game.firerizeTexture;
		turnUsed		= 0;
	}
	public int getHit(){
		return Hit;
	}
	public void setHit(int h){
		
	}
	public textureT getIcon(){
		return Icon;
	}
	public String getDescription(){
		return Description;
	}
	public void setTurnUsed(int T){
		turnUsed = T;
	}
	public int getTurnUsed(){
		return turnUsed;
	}
	public String getName(){
		return Name;
	}
	public int getFadeInType(){
		return fadeInType;
	}
	public boolean isFadeIn(){
		return skillInFade;
	}
	public int getCoolDown(){
		return turnCoolDown - (Game.currentTurn - turnUsed);
	}
	public int getPrice(){
		return Price;
	}
	public boolean isDone(){
		return Done;
	}
	public void doUse(){
		//if(Game.currentTurn - turnUsed < turnCoolDown && turnUsed != 0) return;
		//turnUsed = Game.currentTurn;
	}

	public boolean isReady(){
		return !(Game.currentTurn - turnUsed < turnCoolDown && turnUsed != 0);
	}
	public void Using(){
		equipT C = Game.Player.getCurrentWeapon();
		if(C == null){
			Game.errorSFX.Play();
			Game.Step = GameT.stepPlayerPick;
			return;
		}
		C.Element = elementType.Fire;
		Game.Player.giveElement(elementType.Fire);
		C.makeDescription();
		Game.Step = GameT.stepPlayerPick;
		Game.enemyQueue.Queue.clear();
		Game.Player.removeSkill(this);
	}
}

class grassizeSkillT extends skillBodyT implements skillT {
	grassizeSkillT(GameT G){
		Game			= G;
		turnCoolDown	= 16;
		Name 			= "Grassize";
		Description		= "Change your current \nweapon's element to grass. \nYou need a weapon in \norder to use this skill. \n[Only one use skill]";
		skillInFade 	= false;
		Price			= 10*(1 + Game.currentTurn/skillSystemT.lowP);
		Icon			= Game.grassrizeTexture;
		turnUsed		= 0;
	}
	public int getHit(){
		return Hit;
	}
	public void setHit(int h){
		
	}
	public textureT getIcon(){
		return Icon;
	}
	public String getDescription(){
		return Description;
	}
	public void setTurnUsed(int T){
		turnUsed = T;
	}
	public int getTurnUsed(){
		return turnUsed;
	}
	public String getName(){
		return Name;
	}
	public int getFadeInType(){
		return fadeInType;
	}
	public boolean isFadeIn(){
		return skillInFade;
	}
	public int getCoolDown(){
		return turnCoolDown - (Game.currentTurn - turnUsed);
	}
	public int getPrice(){
		return Price;
	}
	public boolean isDone(){
		return Done;
	}
	public void doUse(){
		//if(Game.currentTurn - turnUsed < turnCoolDown && turnUsed != 0) return;
		//turnUsed = Game.currentTurn;
	}

	public boolean isReady(){
		return !(Game.currentTurn - turnUsed < turnCoolDown && turnUsed != 0);
	}
	public void Using(){
		equipT C = Game.Player.getCurrentWeapon();
		if(C == null){
			Game.errorSFX.Play();
			Game.Step = GameT.stepPlayerPick;
			return;
		}
		C.Element = elementType.Grass;
		Game.Player.giveElement(elementType.Grass);
		C.makeDescription();
		Game.Step = GameT.stepPlayerPick;
		Game.enemyQueue.Queue.clear();
		Game.Player.removeSkill(this);
	}
}

class waterizeSkillT extends skillBodyT implements skillT {
	waterizeSkillT(GameT G){
		Game			= G;
		turnCoolDown	= 16;
		Name 			= "Waterize";
		Description		= "Change your current \nweapon's element to water. \nYou need a weapon in order \nto use this skill. \n[Only one use skill]";
		skillInFade 	= false;
		Price			= 10*(1 + Game.currentTurn/skillSystemT.lowP);
		Icon			= Game.waterizeTexture;
		turnUsed		= 0;
	}
	public int getHit(){
		return Hit;
	}
	public void setHit(int h){
		
	}
	public textureT getIcon(){
		return Icon;
	}
	public String getDescription(){
		return Description;
	}
	public void setTurnUsed(int T){
		turnUsed = T;
	}
	public int getTurnUsed(){
		return turnUsed;
	}
	public String getName(){
		return Name;
	}
	public int getFadeInType(){
		return fadeInType;
	}
	public boolean isFadeIn(){
		return skillInFade;
	}
	public int getCoolDown(){
		return turnCoolDown - (Game.currentTurn - turnUsed);
	}
	public int getPrice(){
		return Price;
	}
	public boolean isDone(){
		return Done;
	}
	public void doUse(){
		//if(Game.currentTurn - turnUsed < turnCoolDown && turnUsed != 0) return;
		//turnUsed = Game.currentTurn;
	}

	public boolean isReady(){
		return !(Game.currentTurn - turnUsed < turnCoolDown && turnUsed != 0);
	}
	public void Using(){
		equipT C = Game.Player.getCurrentWeapon();
		if(C == null){
			Game.errorSFX.Play();
			Game.Step = GameT.stepPlayerPick;
			return;
		}
		C.Element = elementType.Water;
		Game.Player.giveElement(elementType.Water);
		C.makeDescription();
		Game.Step = GameT.stepPlayerPick;
		Game.enemyQueue.Queue.clear();
		Game.Player.removeSkill(this);
	}
}

class noElementSkillT extends skillBodyT implements skillT {
	noElementSkillT(GameT G){
		Game			= G;
		turnCoolDown	= 16;
		Name 			= "No element";
		Description		= "Remove your current \nweapon's element. You need \na weapon in order \nto use this skill. \n[Only one use skill]";
		skillInFade 	= false;
		Price			= 8*(1 + Game.currentTurn/skillSystemT.lowP);
		Icon			= Game.noElementTexture;
		turnUsed		= 0;
	}
	public int getHit(){
		return Hit;
	}
	public void setHit(int h){
		
	}
	public textureT getIcon(){
		return Icon;
	}
	public String getDescription(){
		return Description;
	}
	public void setTurnUsed(int T){
		turnUsed = T;
	}
	public int getTurnUsed(){
		return turnUsed;
	}
	public String getName(){
		return Name;
	}
	public int getFadeInType(){
		return fadeInType;
	}
	public boolean isFadeIn(){
		return skillInFade;
	}
	public int getCoolDown(){
		return turnCoolDown - (Game.currentTurn - turnUsed);
	}
	public int getPrice(){
		return Price;
	}
	public boolean isDone(){
		return Done;
	}
	public void doUse(){
		//if(Game.currentTurn - turnUsed < turnCoolDown && turnUsed != 0) return;
		//turnUsed = Game.currentTurn;
	}

	public boolean isReady(){
		return !(Game.currentTurn - turnUsed < turnCoolDown && turnUsed != 0);
	}
	public void Using(){
		equipT C = Game.Player.getCurrentWeapon();
		if(C == null){
			Game.errorSFX.Play();
			Game.Step = GameT.stepPlayerPick;
			return;
		}
		C.Element = elementType.Normal;
		Game.Player.giveElement(elementType.Normal);
		C.makeDescription();
		Game.Step = GameT.stepPlayerPick;
		Game.enemyQueue.Queue.clear();
		Game.Player.removeSkill(this);
	}
}

class multiplyHealSkillT extends skillBodyT implements skillT {
	multiplyHealSkillT(GameT G){
		Game			= G;
		turnCoolDown	= 16;
		Name 			= "Multiply Hearts";
		Description		= "Multiply all the hearts \non the field by 2. \n[Only one use skill]";
		skillInFade 	= false;
		Price			= 15*(1 + Game.currentTurn/skillSystemT.lowP);
		Icon			= Game.multiplyHealTexture;
		turnUsed		= 0;
	}
	public int getHit(){
		return Hit;
	}
	public void setHit(int h){
		
	}
	public textureT getIcon(){
		return Icon;
	}
	public String getDescription(){
		return Description;
	}
	public void setTurnUsed(int T){
		turnUsed = T;
	}
	public int getTurnUsed(){
		return turnUsed;
	}
	public String getName(){
		return Name;
	}
	public int getFadeInType(){
		return fadeInType;
	}
	public boolean isFadeIn(){
		return skillInFade;
	}
	public int getCoolDown(){
		return turnCoolDown - (Game.currentTurn - turnUsed);
	}
	public int getPrice(){
		return Price;
	}
	public boolean isDone(){
		return Done;
	}
	public void doUse(){
		if(Game.currentTurn - turnUsed < turnCoolDown && turnUsed != 0) return;
		turnUsed = Game.currentTurn;
	}

	public boolean isReady(){
		return !(Game.currentTurn - turnUsed < turnCoolDown && turnUsed != 0);
	}
	public void Using(){
		for(int i=0; i<Game.Grid.gridSize; ++i){
			if(Game.Objects[i] == null) continue;
			if(Game.Objects[i].Type != objectType.Heart) continue;
			((pickObjectT)Game.Objects[i]).Times *= 2;
		}
		Game.Step = GameT.stepPlayerPick;
		Game.enemyQueue.Queue.clear();
		Game.Player.removeSkill(this);
	}
}


class multiplygensSkillT extends skillBodyT implements skillT {
	multiplygensSkillT(GameT G){
		Game			= G;
		turnCoolDown	= 16;
		Name 			= "Multiply Gens";
		Description		= "Multiply all the gens \non the field by 2. \n[Only one use skill]";
		skillInFade 	= false;
		Price			= 15*(1 + Game.currentTurn/skillSystemT.lowP);
		Icon			= Game.multiplyCoinsTexture;
		turnUsed		= 0;
	}
	public int getHit(){
		return Hit;
	}
	public void setHit(int h){
		
	}
	public textureT getIcon(){
		return Icon;
	}
	public String getDescription(){
		return Description;
	}
	public void setTurnUsed(int T){
		turnUsed = T;
	}
	public int getTurnUsed(){
		return turnUsed;
	}
	public String getName(){
		return Name;
	}
	public int getFadeInType(){
		return fadeInType;
	}
	public boolean isFadeIn(){
		return skillInFade;
	}
	public int getCoolDown(){
		return turnCoolDown - (Game.currentTurn - turnUsed);
	}
	public int getPrice(){
		return Price;
	}
	public boolean isDone(){
		return Done;
	}
	public void doUse(){
		if(Game.currentTurn - turnUsed < turnCoolDown && turnUsed != 0) return;
		turnUsed = Game.currentTurn;
	}

	public boolean isReady(){
		return !(Game.currentTurn - turnUsed < turnCoolDown && turnUsed != 0);
	}
	public void Using(){
		for(int i=0; i<Game.Grid.gridSize; ++i){
			if(Game.Objects[i] == null) continue;
			if(Game.Objects[i].Type != objectType.Coin) continue;
			((pickObjectT)Game.Objects[i]).Times *= 2;
		}
		Game.Step = GameT.stepPlayerPick;
		Game.enemyQueue.Queue.clear();
		Game.Player.removeSkill(this);
	}
}

class multiplyDefenseSkillT extends skillBodyT implements skillT {
	multiplyDefenseSkillT(GameT G){
		Game			= G;
		turnCoolDown	= 16;
		Name 			= "Multiply Defense";
		Description		= "Multiply all the shields \non the field by 2. \n[Only one use skill]";
		skillInFade 	= false;
		Price			= 15*(1 + Game.currentTurn/skillSystemT.lowP);
		Icon			= Game.multiplyDefenseTexture;
		turnUsed		= 0;
	}
	public int getHit(){
		return Hit;
	}
	public void setHit(int h){
		
	}
	public textureT getIcon(){
		return Icon;
	}
	public String getDescription(){
		return Description;
	}
	public void setTurnUsed(int T){
		turnUsed = T;
	}
	public int getTurnUsed(){
		return turnUsed;
	}
	public String getName(){
		return Name;
	}
	public int getFadeInType(){
		return fadeInType;
	}
	public boolean isFadeIn(){
		return skillInFade;
	}
	public int getCoolDown(){
		return turnCoolDown - (Game.currentTurn - turnUsed);
	}
	public int getPrice(){
		return Price;
	}
	public boolean isDone(){
		return Done;
	}
	public void doUse(){
		if(Game.currentTurn - turnUsed < turnCoolDown && turnUsed != 0) return;
		turnUsed = Game.currentTurn;
	}

	public boolean isReady(){
		return !(Game.currentTurn - turnUsed < turnCoolDown && turnUsed != 0);
	}
	public void Using(){
		for(int i=0; i<Game.Grid.gridSize; ++i){
			if(Game.Objects[i] == null) continue;
			if(Game.Objects[i].Type != objectType.Shield) continue;
			((pickObjectT)Game.Objects[i]).Times *= 2;
		}
		Game.Step = GameT.stepPlayerPick;
		Game.enemyQueue.Queue.clear();
		Game.Player.removeSkill(this);
	}
}


class multiplyAttackSkillT extends skillBodyT implements skillT {
	multiplyAttackSkillT(GameT G){
		Game			= G;
		turnCoolDown	= 16;
		Name 			= "Multiply Attack";
		Description		= "Multiply all the swords \non the field by 2. \n[Only one use skill]";
		skillInFade 	= false;
		Price			= 15*(1 + Game.currentTurn/skillSystemT.lowP);
		Icon			= Game.multiplyAttackTexture;
		turnUsed		= 0;
	}
	public int getHit(){
		return Hit;
	}
	public void setHit(int h){
		
	}
	public textureT getIcon(){
		return Icon;
	}
	public String getDescription(){
		return Description;
	}
	public void setTurnUsed(int T){
		turnUsed = T;
	}
	public int getTurnUsed(){
		return turnUsed;
	}
	public String getName(){
		return Name;
	}
	public int getFadeInType(){
		return fadeInType;
	}
	public boolean isFadeIn(){
		return skillInFade;
	}
	public int getCoolDown(){
		return turnCoolDown - (Game.currentTurn - turnUsed);
	}
	public int getPrice(){
		return Price;
	}
	public boolean isDone(){
		return Done;
	}
	public void doUse(){
		if(Game.currentTurn - turnUsed < turnCoolDown && turnUsed != 0) return;
		turnUsed = Game.currentTurn;
	}

	public boolean isReady(){
		return !(Game.currentTurn - turnUsed < turnCoolDown && turnUsed != 0);
	}
	public void Using(){
		for(int i=0; i<Game.Grid.gridSize; ++i){
			if(Game.Objects[i] == null) continue;
			if(Game.Objects[i].Type != objectType.Sword) continue;
			((pickObjectT)Game.Objects[i]).Times *= 2;
		}
		Game.Step = GameT.stepPlayerPick;
		Game.enemyQueue.Queue.clear();
		Game.Player.removeSkill(this);
	}
}

class greedyRaidSkillT extends skillBodyT implements skillT {
	greedyRaidSkillT(GameT G){
		Game			= G;
		turnCoolDown	= 11;
		Name 			= "Greedy Raid";
		Description		= "Collect all the gens on \nthe field. [Cooldown: "+String.valueOf(turnCoolDown-1)+" turns]";
		skillInFade 	= false;
		Price			= 10*(1 + Game.currentTurn/skillSystemT.lowP);
		Icon			= Game.greedRaidTexture;
		turnUsed		= 0;
	}
	public int getHit(){
		return Hit;
	}
	public textureT getIcon(){
		return Icon;
	}
	public void setHit(int h){
		
	}
	public String getDescription(){
		return Description;
	}
	public void setTurnUsed(int T){
		turnUsed = T;
	}
	public int getTurnUsed(){
		return turnUsed;
	}
	public String getName(){
		return Name;
	}
	public int getFadeInType(){
		return fadeInType;
	}
	public boolean isFadeIn(){
		return skillInFade;
	}
	public int getCoolDown(){
		return turnCoolDown - (Game.currentTurn - turnUsed);
	}
	public int getPrice(){
		return Price;
	}
	public boolean isDone(){
		return Done;
	}
	public void doUse(){
		if(Game.currentTurn - turnUsed < turnCoolDown && turnUsed != 0) return;
		turnUsed = Game.currentTurn;
		long id = Game.greedRaidSFX.Play();
		Game.greedRaidSFX.setPitch(id, 1.2f);
		Game.greedRaidSFX.setVolume(id, 1.2f);
	}

	public boolean isReady(){
		return !(Game.currentTurn - turnUsed < turnCoolDown && turnUsed != 0);
	}
	public void Using(){
		for(int i=0; i<Game.Grid.gridSize; ++i){
			if(Game.Objects[i] == null) continue;
			if(Game.Objects[i].Type != objectType.Coin) continue;
			Game.playerPick.ppTouches.add(((gridObjectT)Game.Objects[i]).Index);
		}
		Game.noMonsters = false;
		Game.alvEnemies = Game.currentEnemyNumber;
		Game.Step = GameT.stepPPShadowIn;
		Game.SBG.sceneBackground = true;
	}
}

class freezeSkillT extends skillBodyT implements skillT {
	long initT;
	boolean Just = false;
	ArrayList<Integer> monsterList = new ArrayList<Integer>();
	freezeSkillT(GameT G){
		Game			= G;
		Hit				= 5;
		turnCoolDown	= 16;
		Name 			= "Freeze";
		Description		= "Freeze your enemies for \n2 turns. \n[Cooldown: "+String.valueOf(turnCoolDown-1)+" turns]";
		skillInFade 	= true;
		Price			= 15*(1 + Game.currentTurn/skillSystemT.lowP);
		fadeInType 		= objectType.Monster;
		Icon			= Game.freezeTexture;
		turnUsed		= 0;
	}
	public int getHit(){
		return Hit;
	}
	public void setHit(int h){
		
	}
	public textureT getIcon(){
		return Icon;
	}
	public int getFadeInType(){
		return fadeInType;
	}
	public void setTurnUsed(int T){
		turnUsed = T;
	}
	public int getTurnUsed(){
		return turnUsed;
	}
	public String getDescription(){
		return Description;
	}
	public String getName(){
		return Name;
	}
	public boolean isFadeIn(){
		return skillInFade;
	}
	public int getCoolDown(){
		return turnCoolDown - (Game.currentTurn - turnUsed);
	}
	public int getPrice(){
		return Price;
	}
	public boolean isDone(){
		return Done;
	}
	public void doUse(){
		if(Game.currentTurn - turnUsed < turnCoolDown && turnUsed != 0) return;
		turnUsed = Game.currentTurn;
		initT = Tools.getTicks();
		Just = false;
		monsterList.clear();		
		for(int i=0; i<Game.objectListSize; ++i){
			if(Game.Objects[i] == null) continue;
			if(Game.Objects[i].Type == objectType.Monster){
				monsterList.add(i);
			}
		}
		Done = false;
		long id = Game.freezeSFX.Play();
		Game.freezeSFX.setVolume(id,.8f);
		Game.freezeSFX.setPitch(id, 1.8f);
		for(int i=0; i<monsterList.size(); ++i){
			monsterT M = (monsterT)Game.Objects[monsterList.get(i)];
			M.Freeze(3);
		}
	}
	
	public boolean isReady(){
		return !(Game.currentTurn - turnUsed < turnCoolDown && turnUsed != 0);
	}
	
	public void Using(){
		long T = Tools.getTicks()-initT;
		if(T >= 1000){
			Done = true;
		}
	}
}

class gambleSkillT extends skillBodyT implements skillT {
	long initT;
	boolean Just = false;
	ArrayList<Integer> monsterList = new ArrayList<Integer>();
	gambleSkillT(GameT G){
		Game			= G;
		Hit				= 5;
		Name 			= "Gamble";
		turnCoolDown	= 11;
		Description		= "Rearrange of everything on \nthe field randomly. \n[Cooldown: "+String.valueOf(turnCoolDown-1)+" turns]";
		skillInFade 	= false;
		Price			= 10*(1 + Game.currentTurn/skillSystemT.highP);
		Icon			= Game.gambleTexture;
		turnUsed		= 0;
		Done			= false;
	}
	public void setHit(int h){
		
	}
	public int getHit(){
		return Hit;
	}
	public textureT getIcon(){
		return Icon;
	}
	public int getFadeInType(){
		return fadeInType;
	}
	public boolean isFadeIn(){
		return skillInFade;
	}
	public int getTurnUsed(){
		return turnUsed;
	}
	public void setTurnUsed(int T){
		turnUsed = T;
	}
	public String getName(){
		return Name;
	}
	public int getCoolDown(){
		return turnCoolDown - (Game.currentTurn - turnUsed);
	}
	public int getPrice(){
		return Price;
	}
	public boolean isDone(){
		return Done;
	}
	public String getDescription(){
		return Description;
	}
	public void doUse(){
		if(Game.currentTurn - turnUsed < turnCoolDown && turnUsed != 0) return;
		turnUsed = Game.currentTurn;
		Game.gambleSFX.Play();
	}
	
	public boolean isReady(){
		return !(Game.currentTurn - turnUsed < turnCoolDown && turnUsed != 0);
	}
	
	public void Using(){
		for(int i=0; i<Game.Grid.gridSize; ++i){
			int k = -1;
			while(k == -1 || i == k) k = Tools.Random(1, Game.Grid.gridSize)-1;
			Game.swapGridObject(((gridObjectT)Game.Objects[i]).Index, ((gridObjectT)Game.Objects[k]).Index);
		}
		Game.Step = GameT.stepPlayerPick;
		Game.enemyQueue.Queue.clear();
	}
}

class runawaySkillT extends skillBodyT implements skillT {
	long initT;
	boolean Just = false;
	ArrayList<Integer> monsterList = new ArrayList<Integer>();
	runawaySkillT(GameT G){
		Game			= G;
		Hit				= 5;
		Name 			= "Flee";
		turnCoolDown	= 16;
		Description		= "Change all the objects on \nthe field for new ones, \nexcept for the boss. \n[Cooldown: "+String.valueOf(turnCoolDown-1)+" turns]";
		skillInFade 	= false;
		Price			= 12*(1 + Game.currentTurn/skillSystemT.highP);
		Icon			= Game.runawayTexture;
		turnUsed		= 0;
		Done			= false;
	}
	public void setHit(int h){
		
	}
	public int getHit(){
		return Hit;
	}
	public textureT getIcon(){
		return Icon;
	}
	public int getFadeInType(){
		return fadeInType;
	}
	public boolean isFadeIn(){
		return skillInFade;
	}
	public int getTurnUsed(){
		return turnUsed;
	}
	public void setTurnUsed(int T){
		turnUsed = T;
	}
	public String getName(){
		return Name;
	}
	public int getCoolDown(){
		return turnCoolDown - (Game.currentTurn - turnUsed);
	}
	public int getPrice(){
		return Price;
	}
	public boolean isDone(){
		return Done;
	}
	public String getDescription(){
		return Description;
	}
	public void doUse(){
		if(Game.currentTurn - turnUsed < turnCoolDown && turnUsed != 0) return;
		turnUsed = Game.currentTurn;
	}
	
	public boolean isReady(){
		return !(Game.currentTurn - turnUsed < turnCoolDown && turnUsed != 0);
	}
	
	public void Using(){
		for(int i=0; i<Game.objectListSize; ++i){
			if(Game.Objects[i] == null) continue;
			if(Game.Objects[i].Type == objectType.Monster){
				if(((monsterT)Game.Objects[i]).Boss) continue;
				--Game.currentEnemyNumber;
			}
			Game.destroyGridObject(((gridObjectT)Game.Objects[i]).Index);
		}
		Game.noMonstersBonus = false;
		Game.Step = GameT.stepCreate;
		Game.enemyQueue.Queue.clear();
	}
}

class attackBoostSkillT extends skillBodyT implements skillT {
	long initT;
	boolean Just = false;
	ArrayList<Integer> monsterList = new ArrayList<Integer>();
	attackBoostSkillT(GameT G){
		Game			= G;
		Hit				= 5;
		Name 			= "Attack Boost";
		turnCoolDown	= 16;
		Description		= "Duplicate your attack for \none turn. \n[Cooldown: "+String.valueOf(turnCoolDown-1)+" turns]";
		skillInFade 	= false;
		Price			= 12*(1 + Game.currentTurn/skillSystemT.highP);
		Icon			= Game.attackBoostTexture;
		turnUsed		= 0;
		Done			= false;
	}
	public void setHit(int h){
		
	}
	public int getHit(){
		return Hit;
	}
	public textureT getIcon(){
		return Icon;
	}
	public int getFadeInType(){
		return fadeInType;
	}
	public boolean isFadeIn(){
		return skillInFade;
	}
	public int getTurnUsed(){
		return turnUsed;
	}
	public void setTurnUsed(int T){
		turnUsed = T;
	}
	public String getName(){
		return Name;
	}
	public int getCoolDown(){
		return turnCoolDown - (Game.currentTurn - turnUsed);
	}
	public int getPrice(){
		return Price;
	}
	public boolean isDone(){
		return Done;
	}
	public String getDescription(){
		return Description;
	}
	public void doUse(){
		if(Game.currentTurn - turnUsed < turnCoolDown && turnUsed != 0) return;
		turnUsed = Game.currentTurn;
	}
	
	public boolean isReady(){
		return !(Game.currentTurn - turnUsed < turnCoolDown && turnUsed != 0);
	}
	
	public void Using(){
		Game.Player.currentAttack *= 2;
		Game.Step = GameT.stepPlayerPick;
		Game.enemyQueue.Queue.clear();
	}
}

class rageSkillT extends skillBodyT implements skillT {
	long initT;
	boolean Just = false;
	ArrayList<Integer> monsterList = new ArrayList<Integer>();
	rageSkillT(GameT G){
		Game			= G;
		Hit				= 5;
		Name 			= "RAGE";
		turnCoolDown	= 16;
		Description		= "Take all the swords on \nthe field and then \nattack all your enemies in \nthe same turn. \n[Cooldown: "+String.valueOf(turnCoolDown-1)+" turns]";
		skillInFade 	= false;
		Price			= 15*(1 + Game.currentTurn/skillSystemT.highP);
		Icon			= Game.rageTexture;
		turnUsed		= 0;
		Done			= false;
	}
	public int getHit(){
		return Hit;
	}
	public void setHit(int h){
		
	}
	public textureT getIcon(){
		return Icon;
	}
	public int getFadeInType(){
		return fadeInType;
	}
	public boolean isFadeIn(){
		return skillInFade;
	}
	public int getTurnUsed(){
		return turnUsed;
	}
	public void setTurnUsed(int T){
		turnUsed = T;
	}
	public String getName(){
		return Name;
	}
	public int getCoolDown(){
		return turnCoolDown - (Game.currentTurn - turnUsed);
	}
	public int getPrice(){
		return Price;
	}
	public boolean isDone(){
		return Done;
	}
	public String getDescription(){
		return Description;
	}
	public void doUse(){
		if(Game.currentTurn - turnUsed < turnCoolDown && turnUsed != 0) return;
		turnUsed = Game.currentTurn;
	}
	
	public boolean isReady(){
		return !(Game.currentTurn - turnUsed < turnCoolDown && turnUsed != 0);
	}
	
	public void Using(){
		for(int i=0; i<Game.objectListSize; ++i){
			if(Game.Objects[i] == null) continue;
			if(Game.Objects[i].Type == objectType.Sword){
				Game.playerPick.ppTouches.add(((gridObjectT)Game.Objects[i]).Index);
			}
		}
		for(int i=0; i<Game.objectListSize; ++i){
			if(Game.Objects[i] == null) continue;
			if(Game.Objects[i].Type == objectType.Monster){
				Game.playerPick.ppTouches.add(((gridObjectT)Game.Objects[i]).Index);
			}
		}
		Game.noMonsters = false;
		Game.alvEnemies = Game.currentEnemyNumber;
		Game.Step = GameT.stepPPShadowIn;
		Game.SBG.sceneBackground = true;
	}
}