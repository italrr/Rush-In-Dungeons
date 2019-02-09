package com.nite.rid;

import java.util.ArrayList;

import com.badlogic.gdx.Preferences;

class playerSkillSlotT {
	skillT Skill = null;
}

class playerEquipSlotT {
	equipT Equip = null;
}

class playerGenericSlot {
	equipT Equip = null;
	skillT Skill = null;
}

class equipTypeT {
	static final int Sword 	= 0;
	static final int Shield = 1;
	static final int Armor 	= 2;
}

class equipT {
	textureT Icon = null;
	String Name;
	int plusHealth;
	int plusAttack;
	int plusDefense;
	int Type;
	boolean Rare;
	int Price = 5;
	String Description;
	GameT Game;
	int Element = elementType.Normal;
	String typeName;
	String getPrefix(){
		int Random = Tools.Random(0, 4);
		if(Type == equipTypeT.Sword){
			if(Random == 0){
				return "Hard";
			}else
			if(Random == 1){
				return "Oblivion";
			}else
			if(Random == 2){
				return "Ragnarok";
			}else
			if(Random == 3){
				return "Gloom";
			}else
			if(Random == 4){
				return "Strong";
			}
		}else
		if(Type == equipTypeT.Shield){
			if(Random == 0){
				return "Impenetrable";
			}else
			if(Random == 1){
				return "Glory";
			}else
			if(Random == 2){
				return "Wonder";
			}else
			if(Random == 3){
				return "Strong";
			}else
			if(Random == 4){
				return "Shiny";
			}
		}else
		if(Type == equipTypeT.Armor){
			if(Random == 0){
				return "Of Defense";
			}else
			if(Random == 1){
				return "Glory";
			}else
			if(Random == 2){
				return "Solid";
			}else
			if(Random == 3){
				return "Rough";
			}else
			if(Random == 4){
				return "Shiny";
			}
		}
		return "NULL";
	}
	String getMightyWord(){
		if(!Special) return "Regular";
		int Random = Tools.Random(0, 3);
		if(Random == 0){
			return "Mighty";
		}else
		if(Random == 1){
			return "Special";	
		}else
		if(Random == 2){
			return "Rare";	
		}else
		if(Random == 3){
			return "Epic";	
		}
		return "NULL";
	}
	String getTypeName(){
		return typeName;
	}
	void makeIcon(String typeName){
		if(Type == equipTypeT.Sword){
			if(typeName.equals("Sabre")){
				Icon = Game.sabreTexture;
			}else
			if(typeName.equals("Katana")){
				Icon = Game.katanaTexture;
			}else
			if(typeName.equals("Machete")){
				Icon = Game.macheteTexture;
			}else
			if(typeName.equals("Sword")){
				Icon = Game.sword2Texture;
			}else
			if(typeName.equals("Rapier")){
				Icon = Game.rapierTexture;
			}else
			if(typeName.equals("Blade")){
				Icon = Game.bladeTexture;
			}else
			if(typeName.equals("Falchion")){
				Icon = Game.falchionTexture;
			}
		}else
		if(Type == equipTypeT.Armor){
			Icon = Game.armorTexture;
		}
		if(Type == equipTypeT.Shield){
			Icon = Game.shield2Texture;
		}
	}
	void makeType(){
		Element = elementType.Normal;
		int R = Tools.Random(1, 10);
		if(R >= 9){
			Element = elementType.getRandomElement();
		}
		if(Type == equipTypeT.Sword){
			Icon = Game.swordTexture;
			int i = Tools.Random(1, 7);
			if(i == 1){
				typeName = "Sabre";
				Icon = Game.sabreTexture;
			}else
			if(i == 2){
				typeName = "Katana";
				Icon = Game.katanaTexture;
			}else
			if(i == 3){
				typeName = "Machete";
				Icon = Game.macheteTexture;
			}else
			if(i == 4){
				typeName = "Sword";
				Icon = Game.sword2Texture;
			}else
			if(i == 5){
				typeName = "Rapier";
				Icon = Game.rapierTexture;
			}else
			if(i == 6){
				typeName = "Blade";
				Icon = Game.bladeTexture;
			}else
			if(i == 7){
				typeName = "Falchion";
				Icon = Game.falchionTexture;
			}
		}else
		if(Type == equipTypeT.Armor){
			Icon = Game.armorTexture;
			typeName = "Armor";
		}else
		if(Type == equipTypeT.Shield){
			Icon = Game.shield2Texture;
			typeName = "Shield";
		}
	}
	void Init(){
		makeType();
		makeName();
		makeDescription();
	}
	boolean Special;
	String Inc(int i){
		return i<0 ? "decreases" : "increases";
	}
	void makeName(){
		Name = getPrefix()+" "+getTypeName();
	}
	void makeDescription(){
		if(Special)
			Description = "This "+getMightyWord()+" "+getTypeName()+" \n";
		else
			Description = "This "+getTypeName()+" ";
		boolean Did = false;
		if(plusHealth != 0){
			Description += Inc(plusHealth)+" "+(Special ? "" : "\n")+"your health "+(Special ? "\n" : "")+"by "+String.valueOf((int)Tools.Positive(plusHealth));
			Did = true;
		}
		if(plusAttack != 0){
			if(Did == true){
				Description += plusDefense != 0 ? ", \n" : " and \n";
			}
			Description += Inc(plusAttack)+" "+(Special ? "" : "\n")+"your attack "+(Special ? "\n" : "")+"by "+String.valueOf((int)Tools.Positive(plusAttack));
			Did = true;
		}
		if(plusDefense != 0){
			if(Did == true){
				Description += " and \n";
			}
			Description += Inc(plusDefense)+" "+(Special ? "" : "\n")+"your defense "+(Special ? "\n" : "")+"by "+String.valueOf((int)Tools.Positive(plusDefense));
		}
		if(Type == equipTypeT.Sword){
			Description += ". \nElement: "+elementType.getElementName(Element)+".";
		}else
			Description += ".";
	}
	equipT(GameT G, String N, String TyN, String Desc, boolean Sp, int pPrice, int pHealth, int pAttack, int pDfense, int Ty, int El){
		Game		= G;
		plusHealth	= pHealth;
		plusAttack	= pAttack;
		plusDefense	= pDfense;
		Type		= Ty;
		Price		= pPrice;
		Special		= Sp;
		Description	= Desc;
		Name 		= N;
		typeName	= TyN;
		Element		= El;
		makeIcon(typeName);
	}
	equipT(GameT G, boolean Sp, int pPrice, int pHealth, int pAttack, int pDfense, int Ty){
		Game		= G;
		plusHealth	= pHealth;
		plusAttack	= pAttack;
		plusDefense	= pDfense;
		Type		= Ty;
		Price		= pPrice;
		Special		= Sp;
		Init();
	}
}

class playerT {
	int Exp;
	int expNextLevel;
	int Level;
	int maxHP;
	int HP;
	int Money;
	int Attack;
	int Defense;
	int currentAttack;
	int currentDefense;
	int restartDefense;
	boolean toRestartDef;
	int expToAdd;
	float toAdd; 
	float Next;
	int newHealth 		= 0;
	int newAttack 		= 0;
	int newDefense 		= 0;
	int nextLevelExp 	= 0;
	playerSkillSlotT Skill1 	= new playerSkillSlotT();
	playerSkillSlotT Skill2 	= new playerSkillSlotT();
	playerSkillSlotT Skill3		= new playerSkillSlotT();
	playerEquipSlotT Equip1		= new playerEquipSlotT();
	playerEquipSlotT Equip2		= new playerEquipSlotT();
	playerEquipSlotT Equip3		= new playerEquipSlotT();
	GameT Game;
	void addPlus(equipT Equip){
		if(Equip.plusAttack != 0){
			Attack += Equip.plusAttack;
		}
		if(Equip.plusDefense != 0){
			Defense += Equip.plusDefense;
		}
		if(Equip.plusHealth != 0){
			HP += Equip.plusHealth;
		}
		currentAttack 	= Attack;
		currentDefense 	= Defense;
	}
	void removePlus(equipT Equip){
		if(Equip.plusAttack != 0){
			Attack -= Equip.plusAttack;
		}
		if(Equip.plusDefense != 0){
			Defense -= Equip.plusDefense;
		}
		if(Equip.plusHealth != 0){
			HP -= Equip.plusHealth;
		}
		currentAttack 	= Attack;
		currentDefense 	= Defense;
	}
	void Equip(equipT Equip){
		addPlus(Equip);	
	}
	
	void unEquip(int Position){
		if(Position == 0 && Equip1.Equip != null){
			if(Equip1.Equip.Type == equipTypeT.Sword)removeElement(Equip1.Equip.Element);
			removePlus(Equip1.Equip); Equip1.Equip = null;
		}else
		if(Position == 1 && Equip2.Equip != null){
			if(Equip2.Equip.Type == equipTypeT.Sword)removeElement(Equip2.Equip.Element);
			removePlus(Equip2.Equip); Equip2.Equip = null;
		}else
		if(Position == 2 && Equip3.Equip != null){
			if(Equip3.Equip.Type == equipTypeT.Sword)removeElement(Equip3.Equip.Element);
			removePlus(Equip3.Equip); Equip3.Equip = null;
		}
	}
	void giveEquip(equipT Equip, int Position){
		if(Equip == null) return;
		unEquip(Position);
		Equip(Equip);
		if(Position == 0){
			Equip1.Equip = Equip;
		}else
		if(Position == 1){
			Equip2.Equip = Equip;
		}else
		if(Position == 2){
			Equip3.Equip = Equip;
		}
		if(Equip.Type == equipTypeT.Sword) giveElement(Equip.Element);
	}
	void giveEquipNoBuff(equipT Equip, int Position){
		if(Equip == null) return;
		//unEquip(Position);
		//Equip(Equip);
		if(Position == 0){
			Equip1.Equip = Equip;
		}else
		if(Position == 1){
			Equip2.Equip = Equip;
		}else
		if(Position == 2){
			Equip3.Equip = Equip;
		}
		if(Equip.Type == equipTypeT.Sword) giveElement(Equip.Element);
	}
	void removeSkill(int Position){
		if(Position == 0){
			Skill1.Skill = null;
		}else
		if(Position == 1){
			Skill2.Skill = null;
		}else
		if(Position == 2){
			Skill3.Skill = null;
		}
	}
	void removeSkill(skillT S){
		if(Skill1.Skill == S){
			Skill1.Skill = null;
		}else
		if(Skill2.Skill == S){
			Skill2.Skill = null;
		}else
		if(Skill3.Skill == S){
			Skill3.Skill = null;
		}
	}
	void giveSkill(skillT Skill, int Position){
		if(Skill == null) return;
		if(Position == 0){
			Skill1.Skill = Skill;
		}else
		if(Position == 1){
			Skill2.Skill = Skill;
		}else
		if(Position == 2){
			Skill3.Skill = Skill;
		}
	}
	int getSkillSlotEmpty(){
		if(Skill1.Skill == null) return 0;
		if(Skill2.Skill == null) return 1;
		if(Skill3.Skill == null) return 2;	
		return -1;
	}
	int getEquipSlotEmpty(){
		if(Equip1.Equip == null) return 0;
		if(Equip2.Equip == null) return 1;
		if(Equip3.Equip == null) return 2;
		return -1;
	}
	void clearSlots(){
		removeSkill(0);
		removeSkill(1);
		removeSkill(2);
		unEquip(0);
		unEquip(1);
		unEquip(2);
	}
	void saveSkill(Preferences Sv, int Slot){
		skillT S = null;
		if(Slot == 1){
			if (Skill1.Skill == null){
				Sv.putString("player.skill"+String.valueOf(Slot)+"name", "null"); return;
			}else
				S = Skill1.Skill;
		}
		if(Slot == 2){
			if (Skill2.Skill == null){
				Sv.putString("player.skill"+String.valueOf(Slot)+"name", "null"); return;
			}else
				S = Skill2.Skill;
		}
		
		if(Slot == 3){
			if (Skill3.Skill == null){
				Sv.putString("player.skill"+String.valueOf(Slot)+"name", "null"); return;
			}else
				S = Skill3.Skill;
		}
		Sv.putString("player.skill"+String.valueOf(Slot)+"name", S.getName());
		Sv.putInteger("player.skill"+String.valueOf(Slot)+"cooldown", S.getTurnUsed());
		Sv.putInteger("player.skill"+String.valueOf(Slot)+"hit", S.getHit());
	}
	void saveEquip(Preferences Sv, int Slot){
		equipT Eq = null;
		if(Slot == 1){
			if (Equip1.Equip == null){
				Sv.putString("player.equip"+String.valueOf(Slot)+"name", "null"); return;
			}else
				Eq = Equip1.Equip;
		}
		if(Slot == 2){
			if (Equip2.Equip == null){
				Sv.putString("player.equip"+String.valueOf(Slot)+"name", "null"); return;
			}else
				Eq = Equip2.Equip;
		}
		
		if(Slot == 3){
			if (Equip3.Equip == null){
				Sv.putString("player.equip"+String.valueOf(Slot)+"name", "null"); return;
			}else
				Eq = Equip3.Equip;
		}
		
		Sv.putString("player.equip"+String.valueOf(Slot)+"name", Eq.Name);
		Sv.putString("player.equip"+String.valueOf(Slot)+"+description", Eq.Description);
		Sv.putString("player.equip"+String.valueOf(Slot)+"typename", Eq.typeName);
		Sv.putInteger("player.equip"+String.valueOf(Slot)+"plusattack", Eq.plusAttack);
		Sv.putInteger("player.equip"+String.valueOf(Slot)+"plusdefense", Eq.plusDefense);
		Sv.putInteger("player.equip"+String.valueOf(Slot)+"plushealth", Eq.plusHealth);
		Sv.putInteger("player.equip"+String.valueOf(Slot)+"price", Eq.Price);
		Sv.putInteger("player.equip"+String.valueOf(Slot)+"element", Eq.Element);
		Sv.putBoolean("player.equip"+String.valueOf(Slot)+"special", Eq.Special);
		Sv.putInteger("player.equip"+String.valueOf(Slot)+"type", Eq.Type);
	}
	void Save(Preferences Sv){
		/* Save self */
		Sv.putInteger("player.exp", Exp);
		Sv.putInteger("player.nextexp", expNextLevel);
		Sv.putInteger("player.level", Level);
		Sv.putInteger("player.maxhp", maxHP);
		Sv.putInteger("player.hp", HP);
		Sv.putInteger("player.money", Money);
		Sv.putInteger("player.attack", Attack);
		Sv.putInteger("player.defense", Defense);
		Sv.putInteger("player.currentattack", currentAttack);
		Sv.putInteger("player.currentdefense", currentDefense);
		Sv.putInteger("player.restartdefense", restartDefense);
		Sv.putBoolean("player.torestartdefense", toRestartDef);
		Sv.putInteger("player.exptoadd", expToAdd);
		Sv.putInteger("player.exptoadd", expToAdd);
		Sv.putFloat("player.toadd", toAdd);
		Sv.putFloat("player.next", Next);
		Sv.putInteger("player.newhealth", newHealth);
		Sv.putInteger("player.newattack", newAttack);
		Sv.putInteger("player.newdefense", newDefense);
		Sv.putInteger("player.nextlevelexp", nextLevelExp);
		/* Save skills */
		saveSkill(Sv, 1);
		saveSkill(Sv, 2);
		saveSkill(Sv, 3);	
		/* Save equips */
		saveEquip(Sv, 1);
		saveEquip(Sv, 2);
		saveEquip(Sv, 3);
	}
	equipT loadEquip(Preferences Sv, int Slot){
		String typeName, Description, Name = Sv.getString("player.equip"+String.valueOf(Slot)+"name", "null");
		int plusAttack, plusDefense, plusHealth, Element, Price, Type;
		Boolean Special;
		if(Name.equals("null")) return null;
		Description = Sv.getString("player.equip"+String.valueOf(Slot)+"+description", "null");
		plusAttack = Sv.getInteger("player.equip"+String.valueOf(Slot)+"plusattack", 0);
		typeName = Sv.getString("player.equip"+String.valueOf(Slot)+"typename", "null");
		plusDefense = Sv.getInteger("player.equip"+String.valueOf(Slot)+"plusdefense",0);
		plusHealth = Sv.getInteger("player.equip"+String.valueOf(Slot)+"plushealth", 0);
		Special = Sv.getBoolean("player.equip"+String.valueOf(Slot)+"special", false);
		Element = Sv.getInteger("player.equip"+String.valueOf(Slot)+"element", 0);
		Price = Sv.getInteger("player.equip"+String.valueOf(Slot)+"price", 0);
		Type = Sv.getInteger("player.equip"+String.valueOf(Slot)+"type", 0);
		return new equipT(Game, Name, typeName, Description, Special, Price, plusHealth, plusAttack, plusDefense, Type, Element);
	}
	skillT loadSkill(Preferences Sv, int Slot){
		int CoolDown, Hit;
		String Name = Sv.getString("player.skill"+String.valueOf(Slot)+"name", "null");
		if(Name.equals ("null")) return null;
		CoolDown = Sv.getInteger("player.skill"+String.valueOf(Slot)+"cooldown", 0);
		Hit = Sv.getInteger("player.skill"+String.valueOf(Slot)+"hit", 0);
		skillT S = Game.Skills.getSkill(Name);
		S.setTurnUsed(CoolDown);
		S.setHit(Hit);
		return S;
	}
	void Load(Preferences Sv){
		/* Load self */
		Exp = Sv.getInteger("player.exp", Exp);
		expNextLevel = Sv.getInteger("player.nextexp", expNextLevel);
		Level = Sv.getInteger("player.level", Level);
		maxHP = Sv.getInteger("player.maxhp", maxHP);
		HP = Sv.getInteger("player.hp", HP);
		Money = Sv.getInteger("player.money", Money);
		Attack = Sv.getInteger("player.attack", Attack);
		Defense = Sv.getInteger("player.defense", Defense);
		currentAttack = Sv.getInteger("player.currentattack", currentAttack);
		currentDefense = Sv.getInteger("player.currentdefense", currentDefense);
		restartDefense = Sv.getInteger("player.restartdefense", restartDefense);
		toRestartDef = Sv.getBoolean("player.torestartdefense", toRestartDef);
		expToAdd = Sv.getInteger("player.exptoadd", expToAdd);
		expToAdd = Sv.getInteger("player.exptoadd", expToAdd);
		toAdd = Sv.getFloat("player.toadd", toAdd);
		Next = Sv.getFloat("player.next", Next);
		newHealth = Sv.getInteger("player.newhealth", newHealth);
		newAttack = Sv.getInteger("player.newattack", newAttack);
		newDefense = Sv.getInteger("player.newdefense", newDefense);
		nextLevelExp =Sv.getInteger("player.nextlevelexp", nextLevelExp);
		giveEquipNoBuff(loadEquip(Sv, 1), 0);
		giveEquipNoBuff(loadEquip(Sv, 2), 1);
		giveEquipNoBuff(loadEquip(Sv, 3), 2);
		giveSkill(loadSkill(Sv, 1), 0);
		giveSkill(loadSkill(Sv, 2), 1);
		giveSkill(loadSkill(Sv, 3), 2);
	}
	void Restart(){
		clearSlots();
		Exp					= 0;
		expNextLevel		= 100;
		Level				= 1;
		maxHP				= 30;
		HP					= maxHP;
		Money				= 10;
		Attack				= 5;
		Defense				= 2;
		currentAttack		= 5;
		currentDefense		= 2;
		restartDefense 		= 0;
		toRestartDef    	= false;
		expToAdd			= 0;
		toAdd				= 0; 
		Next				= 0;
		newHealth 			= 0;
		newAttack 			= 0;
		newDefense 			= 0;
		nextLevelExp 		= 0;
	}
	playerT(GameT G){
		Game = G;
		Restart();
	}
	
	void addDefense(int D){
		if(!toRestartDef){
			restartDefense = 0;
		}
		toRestartDef = true;
		currentDefense += D;
	}

	void turnEnd(){
		currentAttack 		= Attack;
		if(toRestartDef) ++restartDefense;
		if(restartDefense == 2){
			currentDefense 	= Defense;
			toRestartDef = false;
		}
	}
	ArrayList<Integer> Elements = new ArrayList<Integer>();
	void giveElement(int e){
		Elements.add(e);
	}
	void removeElement(int e){
		for(int i=0; i<Elements.size(); ++i){
			if(Elements.get(i) == e){
				Elements.remove(i);
				return;
			}
		}
	}
	int getElement(){
		if(Elements.size()>0){
			return Elements.get(Elements.size()-1);
		}
		return elementType.Normal;
	}
	void addHealth(int H){
		HP += H;
		if(HP > maxHP) HP = maxHP;
	}
	boolean isDead(){
		return HP == 0;
	}
	int getTotalDamage(int Dmg){
		Dmg -= currentDefense;
		if(Dmg < 0) Dmg = 0;
		return Dmg;
	}
	void doDamage(int Dmg){
		int Def = currentDefense;
		if(Def < 0) Def = 0;
		Dmg -= Def;
		if(Dmg < 0) Dmg = 0;
		HP -= Dmg;
		if(HP < 0){
			HP = 0;
		}
	}
	boolean updateExp(){
		Next = Tools.Translate(Next, toAdd, 0.15f);
		Exp = (int)Next;
		if(toAdd == Next){
			toAdd = 0;
			return true;
		}
		return false;
	}
	skillT freeSkill = null;
	int getSkillN(){
		int n = 0;
		if(Skill1.Skill != null) ++n;
		if(Skill2.Skill != null) ++n;
		if(Skill3.Skill != null) ++n;
		return n;
	}
	boolean hasSword(){
		if(Equip1.Equip != null) if(Equip1.Equip.Type == equipTypeT.Sword) return true;
		if(Equip2.Equip != null) if(Equip2.Equip.Type == equipTypeT.Sword) return true;
		if(Equip3.Equip != null) if(Equip3.Equip.Type == equipTypeT.Sword) return true;
		return false;		
	}
	boolean hasShield(){
		if(Equip1.Equip != null) if(Equip1.Equip.Type == equipTypeT.Shield) return true;
		if(Equip2.Equip != null) if(Equip2.Equip.Type == equipTypeT.Shield) return true;
		if(Equip3.Equip != null) if(Equip3.Equip.Type == equipTypeT.Shield) return true;
		return false;		
	}
	boolean hasArmor(){
		if(Equip1.Equip != null) if(Equip1.Equip.Type == equipTypeT.Armor) return true;
		if(Equip2.Equip != null) if(Equip2.Equip.Type == equipTypeT.Armor) return true;
		if(Equip3.Equip != null) if(Equip3.Equip.Type == equipTypeT.Armor) return true;
		return false;
	}
	equipT getCurrentWeapon(){
		if(Equip1.Equip != null) if(Equip1.Equip.Type == equipTypeT.Sword) return Equip1.Equip;
		if(Equip2.Equip != null) if(Equip2.Equip.Type == equipTypeT.Sword) return Equip2.Equip;
		if(Equip3.Equip != null) if(Equip3.Equip.Type == equipTypeT.Sword) return Equip3.Equip;
		return null;
	}
	boolean hasSkill(String Name){
		if(Skill1.Skill != null) if(Skill1.Skill.getName() == Name) return true;
		if(Skill2.Skill != null) if(Skill2.Skill.getName() == Name) return true;
		if(Skill3.Skill != null) if(Skill3.Skill.getName() == Name) return true;
		return false;
	}
	void doLevelUp(){
		newHealth 	= Tools.Random(1, Level+2);
		newAttack 	= Tools.Random(1, Level);
		newDefense 	= (int) Tools.Random(1, Level == 1 ? 1 : Level/2f);
		Exp = 0;
		expNextLevel = (int)(expNextLevel*2 + Tools.Random(expNextLevel/4, expNextLevel/2));
		//Free Skill!
		if(Tools.Random(1, 20)>=10 && getSkillSlotEmpty() != -1 || getSkillN() == 0){
			freeSkill = Game.Skills.makeSkillLvUpRandom();
			while (hasSkill(freeSkill.getName()) || Game.Shop.isSkillInShop(freeSkill))
				freeSkill = Game.Skills.makeSkillLvUpRandom();
		}
	}
	void applyLevelUp(){
		++Level;
		currentAttack 	-= Attack;
		currentDefense 	-= Defense;
		maxHP 			+= newHealth;
		Attack 			+= newAttack;
		Defense 		+= newDefense;
		currentAttack 	+= Attack;
		currentDefense 	+= Defense;
		addHealth(newHealth);
		addEXP(nextLevelExp);
		newHealth 		= 0;
		newAttack 		= 0;
		newDefense 		= 0;
		if(freeSkill != null){
			int p = getSkillSlotEmpty();
			giveSkill(freeSkill, p);
		}
		freeSkill		= null;
	}
	boolean isExpDone(){
		return nextLevelExp == 0;
	}
	boolean isLvUp(){
		return expNextLevel == Exp;
	}
	void addEXP(int Ex){
		expToAdd = Ex;
		toAdd = Exp+expToAdd;
		if(toAdd>expNextLevel){
			nextLevelExp = (int)toAdd-expNextLevel;
			toAdd = expNextLevel;
		}
		Next = Exp;
	}
}