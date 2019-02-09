package com.nite.rid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL10;


class SpriteButtonSkillT extends spriteButtonT {
	playerSkillSlotT Slot;
	boolean Press = true;
	SpriteButtonSkillT(GameT G, textureT Face, playerSkillSlotT Skill) {
		super(G, Face);
		Slot = Skill;
		Size.x = 64;
		Size.y = 64;
		updateSkill();
	}
	
	void updateSkill(){
		Texture = Slot.Skill == null ? null : Slot.Skill.getIcon();
	}

	void isReady(){
		if (Slot.Skill == null) return;
		if(Slot.Skill.isReady()) return;
		Game.setFontSize(fontSize.Normal);
		String Str = String.valueOf(Slot.Skill.getCoolDown()); 
		float R = 0.368627f, G = 0.721568f, B = 0.011764f;
		float h = Game.getStringWidth("A");
		float x = Position.x;
		float y = Position.y + Size.y/2 + h/2;
		Game.spriteBatch.begin();
		Game.drawText(Str, x, y, R, G, B, 1);
		Game.spriteBatch.end();
		Game.setFontSize(fontSize.Normal);
	}
	
	void updateColoring(){
		if (Slot.Skill == null) return;
		if(Slot.Skill.isReady()){
			R 			= 1f;
			G 			= 1f;
			B 			= 1f;
			texAlpha 	= 1f;	
		}else{
			R 			= 0.5f;
			G 			= 0.5f;
			B 			= 0.5f;
			texAlpha 	= 0.5f;
		}
	}
	
	void Update(){
		updateColoring();
		updateSkill();
		if(Press && isPressed() && Slot.Skill != null && Game.Step == GameT.stepPlayerPick){
			Game.Skills.Used = Slot.Skill;
			if(Game.Skills.Used.isReady()){
				Game.Skills.Used.doUse();
				Game.Skills.addObjects();
				if(Slot.Skill.isFadeIn()){
					Game.changeStep(GameT.stepSkillShadowIn);
					Game.SBG.sceneBackground = true;
				}else{
					Game.changeStep(GameT.stepSkill);
				}
			}
		}
		super.Update();
		isReady();
	}
}

class SpriteButtonEquipT extends spriteButtonT {
	playerEquipSlotT Slot;
	SpriteButtonEquipT(GameT G, textureT Face, playerEquipSlotT Equip) {
		super(G, Face);
		Slot = Equip;
		Size.x = 64;
		Size.y = 64;
		updateSkill();
	}
	
	void updateSkill(){
		Texture = Slot.Equip == null ? null : Slot.Equip.Icon;
	}
	
	void Update(){
		updateSkill();
		super.Update();
	}
}

class SpriteButtonShopT extends spriteButtonT {
	boolean isSKill = true;
	playerGenericSlot Slot;
	SpriteButtonShopT(GameT G, textureT Face) {
		super(G, Face);
		Slot = new playerGenericSlot();
		Size.x = 64;
		Size.y = 64;
		updateSkill();
	}
	boolean isEmpty(){
		return Slot.Equip == null && Slot.Skill == null;
	}
	void setEquip(equipT E){
		isSKill = false;
		Slot.Equip = E;
	}
	void setSkill(skillT S){
		isSKill = true;
		Slot.Skill = S;
	}
	void setEmpty(){
		Slot.Equip = null;
		Slot.Skill = null;
	}
	int getPrice(){
		if(!isEmpty()){
			return isSKill ? Slot.Skill.getPrice() : Slot.Equip.Price ;
		}
		return 0;
	}
	String getName(){
		if(!isEmpty()){
			return isSKill ? Slot.Skill.getName() : Slot.Equip.Name;
		}
		return "NULL";
	}
	String getDescription(){
		if(!isEmpty()){
			return isSKill ? Slot.Skill.getDescription() : Slot.Equip.Description ;
		}
		return "NULL";
	}
	void updateSkill(){
		if(isSKill)
			Texture = Slot.Skill == null ? null : Slot.Skill.getIcon();
		else
			Texture = Slot.Equip == null ? null : Slot.Equip.Icon;
	}
	
	void Update(){
		updateSkill();
		super.Update();
	}
}

class inGameMenuT {
	dynamicWindowT Window;
	Vec2 Position = new Vec2();
	Vec2 Size = new Vec2();
	GameT Game;
	letterByLetterT Title;
	Vec2 titleSize = new Vec2();
	Vec2 titlePosition = new Vec2();
	buttonT Continue;
	buttonT Mute;
	buttonT Exit;
	buttonT goBack;
	float Alpha;	
	inGameMenuT(GameT G){
		Game = G;
		Window = new dynamicWindowT(Game);
		Size.Set(Game.windowSize.x*.75f, Game.windowSize.y*.55f);
		Position.Set(Game.windowSize.x/2 - Size.x/2, Position.y =  Game.windowSize.y/2 - Size.y/2);
		Window.Size.Set(Size);
		
		Window.R = .15f; Window.G = .15f;	Window.B = .15f;
		Title = new letterByLetterT(Game);
		Title.setText("MENU");
		Game.setFontSize(fontSize.Big);
		titleSize.Set(Game.getStringWidth(Title.toMessage), Game.getStringHeight(Title.toMessage));
		titlePosition.Set(Position.x + Size.x/2 - titleSize.x/2, Position.y + titleSize.y*.10f);
		Continue = new buttonT(Game, "CONTINUE", fontSize.Normal);
		goBack = new buttonT(Game, "MAIN MENU", fontSize.Normal);
		Mute = new buttonT(Game, (Game.Volume == 0 ? "UNMUTE" : "MUTE"), fontSize.Normal);
		Exit = new buttonT(Game, "EXIT", fontSize.Normal);
		
		float dY = Mute.Size.y*6f;
		
		Continue.setSize(goBack.Size);
		Continue.Position.Set(Position.x + Size.x/2 - Continue.Size.x/2, Position.y + Size.y/2 - dY/2);
		Continue.R = .15f; Continue.G = .15f; Continue.B = .15f;
		Continue.BR = 0.02f; Continue.BG = 0.02f; Continue.BB = 0.02f;
		
		Mute.setSize(goBack.Size);
		Mute.Position.Set(Position.x + Size.x/2 - Continue.Size.x/2, Position.y + Size.y/2 - dY/2 + Mute.Size.y*2f);
		Mute.R = .15f; Mute.G = .15f; Mute.B = .15f;
		Mute.BR = 0.02f; Mute.BG = 0.02f; Mute.BB = 0.02f;

		Exit.setSize(goBack.Size);
		Exit.Position.Set(Position.x + Size.x/2 - Continue.Size.x/2, Position.y + Size.y/2 - dY/2 + Mute.Size.y*4f);
		Exit.R = .15f; Exit.G = .15f; Exit.B = .15f;
		Exit.BR = 0.02f; Exit.BG = 0.02f; Exit.BB = 0.02f;
		
		goBack.Position.Set(Position.x + Size.x/2 - Continue.Size.x/2, Position.y + Size.y/2 - dY/2 + Mute.Size.y*6f);
		goBack.R = .15f; goBack.G = .15f; goBack.B = .15f;
		goBack.BR = 0.02f; goBack.BG = 0.02f; goBack.BB = 0.02f;
		
		Game.setFontSize(fontSize.Normal);
	}
	void Start(){
		Window.Position.Set(Position);
		Window.Start();
		Window.Open();
		Title.Start();

	}
	void End(){
		
	}
	void Draw(){
		
	}
	void Update(){

		Game.spriteBatch.begin();
		Game.spriteBatch.setColor(0f, 0f, 0f, Alpha/100f);
		Game.spriteBatch.draw(Game.whiteTexture.Region, 0, 0,	0, 0,Game.windowSize.x,  Game.windowSize.y,	1, 1,	0);
		Game.spriteBatch.setColor(1, 1, 1, 1);
		Game.spriteBatch.end();
	    
	    Window.Draw();
		Window.Update();
		Game.spriteBatch.begin();
		if(Window.isOpen()){
			Title.Update();
		}
		Draw();
		if(Window.isOpen()) Title.Draw(titlePosition.x, titlePosition.y, 1, 1, 1, 1);
		Game.spriteBatch.end();
		if(Window.isOpen()){
			Continue.Update();
			Mute.Update();
			Exit.Update();
			goBack.Update();
		}
		if(Window.toOpen){
			Alpha = Tools.Translate(Alpha, 90, 0.15f);
		}else{
			Alpha = Tools.Translate(Alpha, 0, 0.15f);
		}
		if(goingMainMenu && Game.Transition.isInBlack()){
			Game.Save("game.sav");
			Game.inStartScreen = true;
			Game.startScreen.Start();
			goingMainMenu = false;
		}
		if(goBack.isPressed() && Window.isOpen() && !goingMainMenu){
			goingMainMenu = true;
			Game.Transition.Transition();
		}
		if(Exit.isPressed() && Window.isOpen() && !goingMainMenu){
			Game.Save("game.sav");
			Gdx.app.exit();
		}
		if(Continue.isPressed() && Window.isOpen() && !goingMainMenu){
			Window.Close();
		}
		if(Mute.isPressed() && !goingMainMenu){
			Mute.setText((Game.Volume == 0 ? "MUTE" : "UNMUTE"));
			Mute.setSize(goBack.Size);
			Game.Volume = (Game.Volume == 0 ? 1 : 0);
		}
	}
	boolean goingMainMenu = false;
	boolean isDone(){
		return Window.isClose();
	}
}

class inGameSkillT {
	dynamicWindowT Window;
	Vec2 Position = new Vec2();
	Vec2 Size = new Vec2();
	GameT Game;
	letterByLetterT Title;
	Vec2 titleSize = new Vec2();
	Vec2 titlePosition = new Vec2();
	buttonT Back;
	buttonT Forget;

	float Alpha;
	SpriteButtonSkillT skill1;
	SpriteButtonSkillT skill2;
	SpriteButtonSkillT skill3;
	
	int Selected = 0;
	inGameSkillT(GameT G){
		Game = G;
		Window = new dynamicWindowT(Game);
		Size.Set(Game.windowSize.x*.95f, Game.windowSize.y*.65f);
		Position.Set(Game.windowSize.x/2 - Size.x/2, Position.y =  Game.windowSize.y/2 - Size.y/2.35f);
		Window.Size.Set(Size);
		Window.R = .15f; Window.G = .15f;	Window.B = .15f;
		Title = new letterByLetterT(Game);
		Title.setText("SKILL");
		Game.setFontSize(fontSize.Big);
		titleSize.Set(Game.getStringWidth(Title.toMessage), Game.getStringHeight(Title.toMessage));
		titlePosition.Set(Position.x + Size.x/2 - titleSize.x/2, Position.y + titleSize.y*.10f);	
		Back = new buttonT(Game, "Back", fontSize.Normal);
		Back.Position.Set(Position.x + Size.x*.75f - Back.Size.x/2, Position.y + Size.y - Back.Size.y*1.5f);
		Back.R = .15f; Back.G = .15f; Back.B = .15f;
		Back.BR = 0.02f; Back.BG = 0.02f; Back.BB = 0.02f;
		Forget = new buttonT(Game, "Forget", fontSize.Normal);
		Forget.Position.Set(Position.x + Size.x*.25f - Back.Size.x/2, Position.y + Size.y - Back.Size.y*1.5f);
		Forget.R = .15f; Forget.G = .15f; Forget.B = .15f;
		Forget.BR = 0.02f; Forget.BG = 0.02f; Forget.BB = 0.02f;		
		skill1 = new SpriteButtonSkillT(Game, null, Game.Player.Skill1);
		skill2 = new SpriteButtonSkillT(Game, null, Game.Player.Skill2);
		skill3 = new SpriteButtonSkillT(Game, null, Game.Player.Skill3);
		
		skill1.Position.Set(Position.x + Size.x/4 - 64/2, Position.y + titleSize.y - 64/2);
		skill2.Position.Set(Position.x + Size.x/2 - 64/2, Position.y + titleSize.y - 64/2);
		skill3.Position.Set(Position.x + Size.x*.75f - 64/2, Position.y + titleSize.y - 64/2);
		
		skill1.TR = Window.R; skill1.TG = Window.G; skill1.TB = Window.B;
		skill2.TR = Window.R; skill2.TG = Window.G; skill2.TB = Window.B;
		skill3.TR = Window.R; skill3.TG = Window.G; skill3.TB = Window.B;
		
		skill1.minAlpha = 50; skill1.maxAlpha = 100;
		skill2.minAlpha = 50; skill2.maxAlpha = 100;
		skill3.minAlpha = 50; skill3.maxAlpha = 100;
		
		Game.setFontSize(fontSize.Normal);
		doSelect(0);
	}
	void Start(){
		Window.Position.Set(Position);
		Window.Start();
		Window.Open();
		Title.Start();

	}
	void End(){
		
	}
	void Draw(){
		
	}
	void drawRectangle(){
		playerSkillSlotT Skill = Game.Player.Skill1;
		SpriteButtonSkillT skillB = skill1;
		if (Selected == 0){
			Skill = Game.Player.Skill1;
			skillB = skill1;
		}else
		if (Selected == 1){
			Skill = Game.Player.Skill2;
			skillB = skill2;
		}else
		if (Selected == 2){
			Skill = Game.Player.Skill3;
			skillB = skill3;
		}
	    
		Game.spriteBatch.begin();
		Game.spriteBatch.setColor(1f, 0f, 0f, 0.85f);
		Game.spriteBatch.draw(Game.whiteTexture.Region, skillB.Position.x, skillB.Position.y,	0, 0,64,  64,	1, 1,	0);
		Game.spriteBatch.setColor(1, 1, 1, 1);
		Game.spriteBatch.end();
	    
	    
	    if(Skill.Skill != null){
	    	Game.spriteBatch.begin();
	    	float wx = Game.getStringWidth(Skill.Skill.getName());
	    	Game.drawText(Skill.Skill.getName(), Position.x + Size.x/2 - wx/2, Position.y + titleSize.y + 64, 1,1,1,1);
	    	Game.setFontSize(fontSize.Small);
	    	Game.drawTextMultiLine(Skill.Skill.getDescription(), Position.x + Size.x/2 - 400/2f, Position.y + titleSize.y + 64*1.5f, 1,1,1,1);
	    	Game.setFontSize(fontSize.Normal);
	    	Game.spriteBatch.end();
	    	Forget.Update();
	    	if(Forget.isPressed()){
	    		Game.Player.removeSkill(Selected);
	    	}
	    }
	}
	
	void doSelect(int i){
		Selected = i;
		if(i == 0){
			skill1.Selected = true;
			skill2.Selected = false;
			skill3.Selected = false;
		}else
		if(i == 1){
			skill1.Selected = false;
			skill2.Selected = true;
			skill3.Selected = false;
		}else
		if(i == 2){
			skill1.Selected = false;
			skill2.Selected = false;
			skill3.Selected = true;
		}else{
			skill1.Selected = false;
			skill2.Selected = false;
			skill3.Selected = false;
		}
	}
	
	void Update(){
	    
		Game.spriteBatch.begin();
		Game.spriteBatch.setColor(0f, 0f, 0f, Alpha/100f);
		Game.spriteBatch.draw(Game.whiteTexture.Region, 0, 0,	0, 0,Game.windowSize.x,  Game.windowSize.y,	1, 1,	0);
		Game.spriteBatch.setColor(1, 1, 1, 1);
		Game.spriteBatch.end();
	    
	    Window.Draw();
		Window.Update();
		Game.spriteBatch.begin();
		if(Window.isOpen()){
			Title.Update();
		}
		Draw();
		if(Window.isOpen()) Title.Draw(titlePosition.x, titlePosition.y, 1, 1, 1, 1);
		Game.spriteBatch.end();
		if(Window.isOpen()){
			drawRectangle();
			skill1.Update();
			skill2.Update();
			skill3.Update();
			if(skill1.isPressed()) doSelect(0);
			if(skill2.isPressed()) doSelect(1);
			if(skill3.isPressed()) doSelect(2);
			Back.Update();
		}
		if(Window.toOpen){
			Alpha = Tools.Translate(Alpha, 90, 0.15f);
		}else{
			Alpha = Tools.Translate(Alpha, 0, 0.15f);
		}
		if(Back.isPressed() && Window.isOpen()){
			Window.Close();
		}
	}
	boolean isDone(){
		return Window.isClose();
	}
}

class inGameEquipT {
	dynamicWindowT Window;
	Vec2 Position = new Vec2();
	Vec2 Size = new Vec2();
	GameT Game;
	letterByLetterT Title;
	Vec2 titleSize = new Vec2();
	Vec2 titlePosition = new Vec2();
	buttonT Back;
	buttonT Toss;

	float Alpha;
	SpriteButtonEquipT Equip1;
	SpriteButtonEquipT Equip2;
	SpriteButtonEquipT Equip3;
	
	int Selected = 0;
	inGameEquipT(GameT G){
		Game = G;
		Window = new dynamicWindowT(Game);
		Size.Set(Game.windowSize.x*.95f, Game.windowSize.y*.65f);
		Position.Set(Game.windowSize.x/2 - Size.x/2, Position.y =  Game.windowSize.y/2 - Size.y/2.35f);
		Window.Size.Set(Size);
		
		Window.R = .15f; Window.G = .15f;	Window.B = .15f;
		Title = new letterByLetterT(Game);
		Title.setText("EQUIP");
		Game.setFontSize(fontSize.Big);
		titleSize.Set(Game.getStringWidth(Title.toMessage), Game.getStringHeight(Title.toMessage));
		titlePosition.Set(Position.x + Size.x/2 - titleSize.x/2, Position.y + titleSize.y*.10f);	
	
		
		Back = new buttonT(Game, "Back", fontSize.Normal);
		Back.Position.Set(Position.x + Size.x*.75f - Back.Size.x/2, Position.y + Size.y - Back.Size.y*1.5f);
		Back.R = .15f; Back.G = .15f; Back.B = .15f;
		Back.BR = 0.02f; Back.BG = 0.02f; Back.BB = 0.02f;
		Toss = new buttonT(Game, "Toss", fontSize.Normal);
		Toss.Position.Set(Position.x + Size.x*.25f - Back.Size.x/2, Position.y + Size.y - Back.Size.y*1.5f);
		Toss.R = .15f; Toss.G = .15f; Toss.B = .15f;
		Toss.BR = 0.02f; Toss.BG = 0.02f; Toss.BB = 0.02f;
		
		Equip1 = new SpriteButtonEquipT(Game, null, Game.Player.Equip1);
		Equip2 = new SpriteButtonEquipT(Game, null, Game.Player.Equip2);
		Equip3 = new SpriteButtonEquipT(Game, null, Game.Player.Equip3);
		
		Equip1.Position.Set(Position.x + Size.x/4 - 64/2, Position.y + titleSize.y - 64/2);
		Equip2.Position.Set(Position.x + Size.x/2 - 64/2, Position.y + titleSize.y - 64/2);
		Equip3.Position.Set(Position.x + Size.x*.75f - 64/2, Position.y + titleSize.y - 64/2);		
		
		Equip1.TR = Window.R; Equip1.TG = Window.G; Equip1.TB = Window.B;
		Equip2.TR = Window.R; Equip2.TG = Window.G; Equip2.TB = Window.B;
		Equip3.TR = Window.R; Equip3.TG = Window.G; Equip3.TB = Window.B;
		
		Equip1.minAlpha = 50; Equip1.maxAlpha = 100;
		Equip2.minAlpha = 50; Equip2.maxAlpha = 100;
		Equip3.minAlpha = 50; Equip3.maxAlpha = 100;
		
		Game.setFontSize(fontSize.Normal);
		doSelect(0);
	}
	void Start(){
		Window.Position.Set(Position);
		Window.Start();
		Window.Open();
		Title.Start();

	}
	void End(){
		
	}
	void Draw(){
		
	}
	void drawRectangle(){
		playerEquipSlotT Equip = Game.Player.Equip1;
		SpriteButtonEquipT EquipB = Equip1;
		if (Selected == 0){
			Equip = Game.Player.Equip1;
			EquipB = Equip1;
		}else
		if (Selected == 1){
			Equip = Game.Player.Equip2;
			EquipB = Equip2;
		}else
		if (Selected == 2){
			Equip = Game.Player.Equip3;
			EquipB = Equip3;
		}
    
		Game.spriteBatch.begin();
		Game.spriteBatch.setColor(1f, 0f, 0f, 0.85f);
		Game.spriteBatch.draw(Game.whiteTexture.Region, EquipB.Position.x, EquipB.Position.y,	0, 0,64,  64,	1, 1,	0);
		Game.spriteBatch.setColor(1, 1, 1, 1);
		Game.spriteBatch.end();
	    
	    if(Equip.Equip != null){
	    	Game.spriteBatch.begin();
	    	float wx = Game.getStringWidth(Equip.Equip.Name);
	    	Game.drawText(Equip.Equip.Name, Position.x + Size.x/2 - wx/2, Position.y + titleSize.y + 64, 1,1,1,1);
	    	Game.setFontSize(fontSize.Small);
	    	Game.drawTextMultiLine(Equip.Equip.Description, Position.x + Size.x/2 - 400/2f, Position.y + titleSize.y + 64*1.5f, 1,1,1,1);
	    	Game.setFontSize(fontSize.Normal);
	    	Game.spriteBatch.end();
	    	Toss.Update();
	    	if(Toss.isPressed()){
	    		Game.Player.unEquip(Selected);
	    	}
	    }
	}
	
	void doSelect(int i){
		Selected = i;
		if(i == 0){
			Equip1.Selected = true;
			Equip2.Selected = false;
			Equip3.Selected = false;
		}else
		if(i == 1){
			Equip1.Selected = false;
			Equip2.Selected = true;
			Equip3.Selected = false;
		}else
		if(i == 2){
			Equip1.Selected = false;
			Equip2.Selected = false;
			Equip3.Selected = true;
		}else{
			Equip1.Selected = false;
			Equip2.Selected = false;
			Equip3.Selected = false;
		}
	}
	
	void Update(){
	    
		Game.spriteBatch.begin();
		Game.spriteBatch.setColor(0f, 0f, 0f, Alpha/100f);
		Game.spriteBatch.draw(Game.whiteTexture.Region, 0,0,	0, 0,Game.windowSize.x,  Game.windowSize.y,	1, 1,	0);
		Game.spriteBatch.setColor(1, 1, 1, 1);
		Game.spriteBatch.end();
	    
	    Window.Draw();
		Window.Update();
		Game.spriteBatch.begin();
		if(Window.isOpen()){
			Title.Update();
		}
		Draw();
		if(Window.isOpen()) Title.Draw(titlePosition.x, titlePosition.y, 1, 1, 1, 1);
		Game.spriteBatch.end();
		if(Window.isOpen()){
			drawRectangle();
			Equip1.Update();
			Equip2.Update();
			Equip3.Update();
			if(Equip1.isPressed()) doSelect(0);
			if(Equip2.isPressed()) doSelect(1);
			if(Equip3.isPressed()) doSelect(2);
			Back.Update();
		}
		if(Window.toOpen){
			Alpha = Tools.Translate(Alpha, 90, 0.15f);
		}else{
			Alpha = Tools.Translate(Alpha, 0, 0.15f);
		}
		if(Back.isPressed() && Window.isOpen()){
			Window.Close();
		}
	}
	boolean isDone(){
		return Window.isClose();
	}
}


class shopT {
	dynamicWindowT Window;
	Vec2 Position = new Vec2();
	Vec2 Size = new Vec2();
	GameT Game;
	letterByLetterT Title;
	Vec2 titleSize = new Vec2();
	Vec2 titlePosition = new Vec2();
	buttonT Back;
	
	buttonT Sell;
	buttonT sellIt;
	buttonT buyIt;
	buttonT Buy;

	float Alpha;
	SpriteButtonEquipT Equip1;
	SpriteButtonEquipT Equip2;
	SpriteButtonEquipT Equip3;
	boolean doSell = false;
	int Selected = 0;
	
	public static int buySlots = 10;
	SpriteButtonShopT []shopSlot;
	int nextTurn = 0;
	
	void Save(Preferences Sv){
		Sv.putInteger("shop.nextTurn", nextTurn);
		for(int i=0; i<buySlots; ++i){
			String ind = "shop.slot"+String.valueOf(i);
			Sv.putString(ind, shopSlot[i].isEmpty() ? "empty" : shopSlot[i].isSKill ?  "skill" : "equip" );
			if(shopSlot[i].isEmpty())continue;
			if(shopSlot[i].isSKill){
				Sv.putString(ind+"name", shopSlot[i].Slot.Skill.getName());
				Sv.putInteger(ind+"cooldown", shopSlot[i].Slot.Skill.getTurnUsed());
				Sv.putInteger(ind+"hit", shopSlot[i].Slot.Skill.getHit());
			}else{
				Sv.putString(ind+"name", shopSlot[i].Slot.Equip.Name);
				Sv.putString(ind+"+description", shopSlot[i].Slot.Equip.Description);
				Sv.putString(ind+"typename", shopSlot[i].Slot.Equip.typeName);
				Sv.putInteger(ind+"plusattack", shopSlot[i].Slot.Equip.plusAttack);
				Sv.putInteger(ind+"plusdefense", shopSlot[i].Slot.Equip.plusDefense);
				Sv.putInteger(ind+"plushealth", shopSlot[i].Slot.Equip.plusHealth);
				Sv.putInteger(ind+"price", shopSlot[i].Slot.Equip.Price);
				Sv.putInteger(ind+"element", shopSlot[i].Slot.Equip.Element);
				Sv.putBoolean(ind+"special", shopSlot[i].Slot.Equip.Special);
				Sv.putInteger(ind+"type", shopSlot[i].Slot.Equip.Type);
			}
		}
	}
	
	void Load(Preferences Sv){
		nextTurn = Sv.getInteger("shop.nextTurn", nextTurn);
		for(int i=0; i<buySlots; ++i){
			String ind = "shop.slot"+String.valueOf(i);
			String t = Sv.getString(ind, "empty");
			if(t.equals("empty")){
				shopSlot[i].setEmpty();
			}else
			if(t.equals("skill")){
				skillT Sk = Game.Skills.getSkill(Sv.getString(ind+"name", "null"));
				Sk.setTurnUsed(Sv.getInteger(ind+"cooldown", 0));
				Sk.setHit(Sv.getInteger(ind+"hit", 0));
				shopSlot[i].setSkill(Sk);
			}else{
				String typeName, Description, Name = Sv.getString(ind+"name", "");
				int plusAttack, plusDefense, plusHealth, Element, Price, Type; Boolean Special;
				Description = Sv.getString(ind+"+description", "");
				plusAttack = Sv.getInteger(ind+"plusattack", 0);
				typeName = Sv.getString(ind+"typename", "");
				plusDefense = Sv.getInteger(ind+"plusdefense", 0);
				plusHealth = Sv.getInteger(ind+"plushealth", 0);
				Special = Sv.getBoolean(ind+"special", false);
				Element = Sv.getInteger(ind+"element", 0);
				Price = Sv.getInteger(ind+"price", 0);
				Type = Sv.getInteger(ind+"type", 0);
				equipT E = new equipT(Game, Name, typeName, Description, Special, Price, plusHealth, plusAttack, plusDefense, Type, Element);
				shopSlot[i].setEquip(E);
			}
		}
	}
	
	shopT(GameT G){
		Game = G;
		Window = new dynamicWindowT(Game);
		Size.Set(Game.windowSize.x*.95f, Game.windowSize.y*.70f);
		Position.Set(Game.windowSize.x/2 - Size.x/2, Position.y =  Game.windowSize.y/2 - Size.y/2.35f);
		Window.Size.Set(Size);
		Window.R = .15f; Window.G = .15f;	Window.B = .15f;
		
		Title = new letterByLetterT(Game);
		Title.setText("SHOP");
		Game.setFontSize(fontSize.Big);
		titleSize.Set(Game.getStringWidth(Title.toMessage), Game.getStringHeight(Title.toMessage));
		titlePosition.Set(Position.x + Size.x/2 - titleSize.x/2, Position.y + titleSize.y*.10f);	
		
		
		sellIt = new buttonT(Game, "Sell It", fontSize.Normal);
		sellIt.Position.Set(Position.x + Size.x*.75f - sellIt.Size.x/2, Position.y + Size.y - sellIt.Size.y/2 - sellIt.Size.y*3f);
		sellIt.R = .15f; sellIt.G = .15f; sellIt.B = .15f;
		sellIt.BR = 0.02f; sellIt.BG = 0.02f; sellIt.BB = 0.02f;

		Back = new buttonT(Game, "Back", fontSize.Normal, sellIt.Size.x, sellIt.Size.y);
		Back.Position.Set(Position.x + Size.x*.75f - sellIt.Size.x/2, Position.y + Size.y - sellIt.Size.y*1.5f);
		Back.R = sellIt.R; Back.G = sellIt.G; Back.B = sellIt.B;
		Back.BR = sellIt.BR; Back.BG = sellIt.BG; Back.BB = sellIt.BB;	
		
		buyIt = new buttonT(Game, "Buy It", fontSize.Normal, sellIt.Size.x, sellIt.Size.y);
		buyIt.Position.Set(Position.x + Size.x*.75f - sellIt.Size.x/2, Position.y + Size.y - sellIt.Size.y/2 - sellIt.Size.y*3f);
		buyIt.R = Back.R; buyIt.G = Back.G; buyIt.B = Back.B;
		buyIt.BR = Back.BR; buyIt.BG = Back.BG; buyIt.BB = Back.BB;
		
		Buy = new buttonT(Game, "Buy", fontSize.Normal, sellIt.Size.x, sellIt.Size.y);
		Buy.Position.Set(Position.x + Size.x*.25f - sellIt.Size.x/2, Position.y + Size.y - sellIt.Size.y*1.5f);
		Buy.R = Back.R; Buy.G = Back.G; Buy.B = Back.B;
		Buy.BR = Back.BR; Buy.BG = Back.BG; Buy.BB = Back.BB;
		
		Sell = new buttonT(Game, "Sell", fontSize.Normal, sellIt.Size.x, sellIt.Size.y);
		Sell.Position.Set(Position.x + Size.x*.25f - sellIt.Size.x/2, Position.y + Size.y - sellIt.Size.y*1.5f);
		Sell.R = Back.R; Sell.G = Back.G; Sell.B = Back.B;
		Sell.BR = Back.BR; Sell.BG = Back.BG; Sell.BB = Back.BB;

		
		
		Equip1 = new SpriteButtonEquipT(Game, null, Game.Player.Equip1);
		Equip2 = new SpriteButtonEquipT(Game, null, Game.Player.Equip2);
		Equip3 = new SpriteButtonEquipT(Game, null, Game.Player.Equip3);
		
		Equip1.Position.Set(Position.x + Size.x/4 - 64/2, Position.y + titleSize.y - 64/2);
		Equip2.Position.Set(Position.x + Size.x/2 - 64/2, Position.y + titleSize.y - 64/2);
		Equip3.Position.Set(Position.x + Size.x*.75f - 64/2, Position.y + titleSize.y - 64/2);
		
		
		Equip1.TR = Window.R; Equip1.TG = Window.G; Equip1.TB = Window.B;
		Equip2.TR = Window.R; Equip2.TG = Window.G; Equip2.TB = Window.B;
		Equip3.TR = Window.R; Equip3.TG = Window.G; Equip3.TB = Window.B;
		
		Equip1.minAlpha = 50; Equip1.maxAlpha = 100;
		Equip2.minAlpha = 50; Equip2.maxAlpha = 100;
		Equip3.minAlpha = 50; Equip3.maxAlpha = 100;
		
		
		shopSlot			= new SpriteButtonShopT[buySlots];
		for(int i=0; i<buySlots; ++i){
			shopSlot[i] = new SpriteButtonShopT(Game, null);
		}
		
		
		for(int i=0; i<buySlots; ++i){
			shopSlot[i].minAlpha = 50; shopSlot[i].maxAlpha = 100;
			shopSlot[i].TR = Window.R; shopSlot[i].TG = Window.G; shopSlot[i].TB = Window.B;
		}
		
		Equip1.minAlpha = 50; Equip1.maxAlpha = 100;
		Equip2.minAlpha = 50; Equip2.maxAlpha = 100;
		Equip3.minAlpha = 50; Equip3.maxAlpha = 100;
		
		float W = (64*1.15f)*7;
		shopSlot[0].Position.Set(Position.x + Size.x/2 - W/2 + 64*1.15f, Position.y + titleSize.y - 64/2);
		shopSlot[1].Position.Set(Position.x + Size.x/2 - W/2 + (64*1.15f)*2, Position.y + titleSize.y - 64/2);
		shopSlot[2].Position.Set(Position.x + Size.x/2 - W/2 + (64*1.15f)*3, Position.y + titleSize.y - 64/2);
		shopSlot[3].Position.Set(Position.x + Size.x/2 - W/2 + (64*1.15f)*4, Position.y + titleSize.y - 64/2);
		shopSlot[4].Position.Set(Position.x + Size.x/2 - W/2 + (64*1.15f)*5, Position.y + titleSize.y - 64/2);
		shopSlot[5].Position.Set(Position.x + Size.x/2 - W/2 + 64*1.15f, Position.y + titleSize.y - 64/2 + 64*1.2f);
		shopSlot[6].Position.Set(Position.x + Size.x/2 - W/2 + (64*1.15f)*2, Position.y + titleSize.y - 64/2 + 64*1.2f);
		shopSlot[7].Position.Set(Position.x + Size.x/2 - W/2 + (64*1.15f)*3, Position.y + titleSize.y - 64/2 + 64*1.2f);
		shopSlot[8].Position.Set(Position.x + Size.x/2 - W/2 + (64*1.15f)*4, Position.y + titleSize.y - 64/2 + 64*1.2f);
		shopSlot[9].Position.Set(Position.x + Size.x/2 - W/2 + (64*1.15f)*5, Position.y + titleSize.y - 64/2 + 64*1.2f);
		
		Game.setFontSize(fontSize.Normal);
		doSelectShopSlot(0);
		doSelectSell(0);
	}
	void Start(){
		Window.Position.Set(Position);
		Window.Start();
		Window.Open();
		Title.Start();

	}
	void End(){
		
	}
	void Draw(){
		
	}
	
	void makeNextTurn(){
		nextTurn = Game.currentTurn + Tools.Random(5, 10);
	}
	boolean isSkillInShop(skillT S){
		for(int i=0; i<buySlots; ++i){
			if(shopSlot[i].isSKill && !shopSlot[i].isEmpty()){
				if(S.getName() == shopSlot[i].getName()) return true;
			}
		}
		return false;
	}
	
	boolean isEquipInShop(equipT Eq){
		for(int i=0; i<buySlots; ++i){
			if(!shopSlot[i].isSKill && !shopSlot[i].isEmpty()){
				if(	Eq.plusAttack 	== shopSlot[i].Slot.Equip.plusAttack 	&&
					Eq.plusDefense 	== shopSlot[i].Slot.Equip.plusDefense &&
					Eq.plusHealth 	== shopSlot[i].Slot.Equip.plusHealth		){
					return true;
				}
			}
		}
		return false;
	}
	
	skillT makeSkill(){
		skillT Sk = Game.Skills.makeSkillRandom();
		return isSkillInShop(Sk) || Game.Player.hasSkill(Sk.getName()) ? makeSkill() : Sk;
	}
	
	equipT makeEquip(){
		int Type = Tools.Random(1, 3);
		int initPrice = 0;
		boolean Rare = Tools.Random(1, 100) >= 99;
		int Att = 0, Def = 0, Hea = 0;
		boolean Nutty = Tools.Random(1, 100) >= 99;
		if(Type-1 == equipTypeT.Sword){
			Att = Tools.Random(1, 3) + 3*(Game.currentTurn/20);
			initPrice = 3 + (int)(Att*1.5f) * (Game.currentTurn>=20 ? Game.currentTurn/20 : 1);
			if(Rare){
				Att = (int) ((Att+1)*1.18f);
				initPrice *= 3;
			}
			if(Nutty){
				Att *= 1.35f;
				if(Tools.Random(1, 10) >= 5){
					Hea -= Att * .55f;
				}else
					Def -= Att * .55f;
				initPrice *= 0.50f;
			}
		}else
		if(Type-1 == equipTypeT.Shield){
			Def = Tools.Random(1, 2) + 2*(Game.currentTurn/20);
			initPrice = 3 + (int)(Def*2f) * (Game.currentTurn>=20 ? Game.currentTurn/20 : 1);
			if(Rare){
				Def = (int) ((Def+1)*1.15f);
				initPrice *= 2;
			}
		}else
		if(Type-1 == equipTypeT.Armor){
			Def = Tools.Random(1, 2) + 2*(Game.currentTurn/30);
			initPrice = 4 + Def*2 * (Game.currentTurn>=20 ? Game.currentTurn/20 : 1);
			if(Rare){
				Def = (int) ((1+Def)*1.10f);
				initPrice *= 2;
			}
		}
		equipT Eq = new equipT(Game, Rare, initPrice, Hea, Att, Def, Type-1);
		return isEquipInShop(Eq) ? makeEquip() : Eq;
	}
	
	void clearSlots(){
		for(int i=0; i<buySlots; ++i){
			shopSlot[i].setEmpty();
		}
	}
	void Restart(){
		clearSlots();
		itemCreator();
		nextTurn = 0;
	}
	int getSkillNumber(){
		int k = 0;
		for(int i=0; i<buySlots; ++i){
			if(!shopSlot[i].isEmpty() && shopSlot[i].isSKill) ++k;
		}
		return k;
	}
	
	int getEquipNumber(){
		int k = 0;
		for(int i=0; i<buySlots; ++i){
			if(!shopSlot[i].isEmpty() && !shopSlot[i].isSKill) ++k;
		}
		return k;
	}
	
	void itemCreator(){
		if(Game.currentTurn < nextTurn) return;
		if(Game.currentTurn>1)
			Game.createEffectText("New stuff at the shop!", Game.windowSize.x/2, Game.windowSize.y/2, 1f, 1f, 0, fontSize.Normal, 0.03f);
		
		for(int i=0; i<buySlots/2; ++i){
			equipT Current = makeEquip();
			while(isEquipInShop(Current)) Current = makeEquip();
			shopSlot[i].setEquip(Current);
		}
		
		for(int i=buySlots/2; i<buySlots; ++i){
			skillT Current = makeSkill();
			while(isSkillInShop(Current)) Current = makeSkill();
			shopSlot[i].setSkill(Current);
		}
		
//		if(getSkillNumber() == 0){
//			shopSlot5.setEmpty();
//			shopSlot5.setSkill(makeSkill());
//		}
		makeNextTurn();
	}
	void doSelectSell(int i){
		Selected = i;
		if(i == 0){
			Equip1.Selected = true;
			Equip2.Selected = false;
			Equip3.Selected = false;
		}else
		if(i == 1){
			Equip1.Selected = false;
			Equip2.Selected = true;
			Equip3.Selected = false;
		}else
		if(i == 2){
			Equip1.Selected = false;
			Equip2.Selected = false;
			Equip3.Selected = true;
		}else{
			Equip1.Selected = false;
			Equip2.Selected = false;
			Equip3.Selected = false;
		}
	}
	void sellScreen(){
		if(Equip1.isPressed()) doSelectSell(0);
		if(Equip2.isPressed()) doSelectSell(1);
		if(Equip3.isPressed()) doSelectSell(2);
		playerEquipSlotT Equip = Game.Player.Equip1;
		SpriteButtonEquipT EquipB = Equip1;
		
		if (Selected == 0){
			Equip = Game.Player.Equip1;
			EquipB = Equip1;
		}else
		if (Selected == 1){
			Equip = Game.Player.Equip2;
			EquipB = Equip2;
		}else
		if (Selected == 2){
			Equip = Game.Player.Equip3;
			EquipB = Equip3;
		}
		
		Game.spriteBatch.begin();
		Game.spriteBatch.setColor(1f, 0f, 0f, 0.85f);
		Game.spriteBatch.draw(Game.whiteTexture.Region, EquipB.Position.x, EquipB.Position.y,	0, 0,64, 64,	1, 1,	0);
		Game.spriteBatch.setColor(1, 1, 1, 1);
		Game.spriteBatch.end();
		
		Equip1.Update();
		Equip2.Update();
		Equip3.Update();
	 
	    if(Equip.Equip != null) sellIt.Update();
	    Game.spriteBatch.begin();
	    if(Equip.Equip != null){	    	
	    	
	    	float wx = Game.getStringWidth(Equip.Equip.Name);	    	
	    	Game.drawText(Equip.Equip.Name, Position.x + Size.x/2 - wx/2, Position.y + titleSize.y + 64, 1,1,1,1);
	    	Game.setFontSize(fontSize.Small);
	    	Game.drawTextMultiLine(Equip.Equip.Description, Position.x + Size.x/2 - 400/2f, Position.y + titleSize.y + 64*1.5f, 1,1,1,1);
	    	Game.setFontSize(fontSize.Normal);
	    	String Str = String.valueOf(Equip.Equip.Price)+" gens";
	    	float x = Position.x + Size.x*.25f - Game.getStringWidth(Str)/2;
	    	float y = Position.y + Size.y - Back.Size.y*3f;
	    	Game.drawText(Str, x, y - Game.getStringHeight("A")/2, 1,1,0,1);
	    	if(sellIt.isPressed()){
	    		Game.Player.Money += Equip.Equip.Price;
	    		Game.Player.unEquip(Selected);
	    		Game.greedRaidSFX.Play();
	    	}
	    }
	    Game.drawText("Your Stuff", 64, Position.y + titleSize.y - 64, 1,1,1,1);
	    Game.spriteBatch.end();
	}
	void Update(){
	    Gdx.gl.glEnable(GL10.GL_BLEND);
	    Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
	    
		Game.spriteBatch.begin();
		Game.spriteBatch.setColor(0f, 0f, 0f, Alpha/100f);
		Game.spriteBatch.draw(Game.whiteTexture.Region, 0,0,	0, 0,Game.windowSize.x,  Game.windowSize.y,	1, 1,	0);
		Game.spriteBatch.setColor(1, 1, 1, 1);
		Game.spriteBatch.end();
	    
	    Window.Draw();
		Window.Update();
		Game.spriteBatch.begin();
		if(Window.isOpen()){
			Title.Update();
		}
		Draw();
		if(Window.isOpen()) Title.Draw(titlePosition.x, titlePosition.y, 1, 1, 1, 1);
		Game.spriteBatch.end();
		if(Window.isOpen()){
			if(doSell){
				Buy.Update();
				if(Buy.isPressed()){
					doSell = false;
					doSelectShopSlot(0);
					doSelectSell(0);
				}
				sellScreen();
			}else{
				Sell.Update();
				if(Sell.isPressed()){
					doSell = true;
					doSelectShopSlot(0);
					doSelectSell(0);
				}
				buyScreen();
			}
			Back.Update();
		}
		if(Window.toOpen){
			Alpha = Tools.Translate(Alpha, 90, 0.15f);
		}else{
			Alpha = Tools.Translate(Alpha, 0, 0.15f);
		}
		if(Back.isPressed() && Window.isOpen()){
			Window.Close();
		}
	}
	void doSelectShopSlot(int k){
		Selected = k;
		for(int i=0; i<buySlots; ++i){
			shopSlot[i].Selected = k == i ? true : false; 
		}
	}
	void buyScreen(){
		SpriteButtonShopT Slot = shopSlot[0];
		for(int i=0; i<buySlots; ++i){
			if(shopSlot[i].isPressed()){
				doSelectShopSlot(i);
				break;
			}
		}
		Slot = shopSlot[Selected];
		
		Game.spriteBatch.begin();
		Game.spriteBatch.setColor(1f, 0f, 0f, 0.85f);
		Game.spriteBatch.draw(Game.whiteTexture.Region, Slot.Position.x, Slot.Position.y,	0, 0,64, 64,	1, 1,	0);
		Game.spriteBatch.setColor(1, 1, 1, 1);
		Game.spriteBatch.end();
		
		for(int i=0; i<buySlots; ++i){
			shopSlot[i].Update();
		}
		
	    if(!Slot.isEmpty()) buyIt.Update();
	    Game.spriteBatch.begin();
	    if(!Slot.isEmpty()){
	    	float wx = Game.getStringWidth(Slot.getName());
	    	Game.drawText(Slot.getName(), Position.x + Size.x/2 - wx/2, Position.y + titleSize.y + 64*2f, 1,1,1,1);
	    	Game.setFontSize(fontSize.Small);
	    	Game.drawTextMultiLine(Slot.getDescription(), Position.x + Size.x/2 - 400/2f, Position.y + titleSize.y + 64*2.5f, 1,1,1,1);
	    	//Game.setFontSize(fontSize.Small);
	    	String Str2 = "Have "+String.valueOf(Game.Player.Money)+" gens";
	    	String Str = "Cost "+String.valueOf(Slot.getPrice())+" gens";
	    	float x = Position.x + Size.x*.05f;
	    	float y = Position.y + Size.y - Back.Size.y*3f;
	    	Game.drawText(Str, x, y - (Game.getStringHeight("A")/2)*2, 1,1,0,1);
	    	Game.drawText(Str2, x, y + Game.getStringHeight("A")/2, 1,1,0,1);
	    	if(buyIt.isPressed()){
	    		if(Game.Player.Money >= Slot.getPrice()){
		    		int S = Slot.isSKill ? Game.Player.getSkillSlotEmpty() : Game.Player.getEquipSlotEmpty();
		    		if(S != -1){
		    			boolean Success = true;
		    			String M = "You can only carry \none ";
			    		if(Slot.isSKill){
			    			Game.Player.giveSkill(Slot.Slot.Skill, S);
			    		}else{
			    			if(Slot.Slot.Equip.Type == equipTypeT.Sword && Game.Player.hasSword()){
			    				M += "weapon.\n";
			    				M += "\nTry tossing your \ncurrent weapon in \nthe equip menu \n";
			    				M += "or selling it to \nmake space for a \nnew one.";
			    				Success = false;
			    			}else
			    			if(Slot.Slot.Equip.Type == equipTypeT.Shield && Game.Player.hasShield()){
			    				M += "shield.\n";
			    				M += "\nTry tossing your \ncurrent shield in \nthe equip menu \n";
			    				M += "or selling it to \nmake space for a \nnew one.";
			    				Success = false;
			    			}else
			    			if(Slot.Slot.Equip.Type == equipTypeT.Armor && Game.Player.hasArmor()){
			    				M += "armor.\n";
			    				M += "\nTry tossing your \ncurrent armor in \nthe equip menu \n";
			    				M += "or selling it to \nmake space for a \nnew one.";
			    				Success = false;
			    			}else{
			    				equipT E = Slot.Slot.Equip;
			    				Game.Player.Money -= Slot.getPrice();
			    				E.Price = (int) Tools.Ceil(E.Price*.75f);
			    				Game.Player.giveEquip(E, S);
			    			}
			    		}
			    		if(Success){
			    			Slot.setEmpty();
			    			Game.boughtSFX.Play();
			    		}else{
			    			Game.Message.Show(M);
			    		}
		    		}else{
		    			String M = "There aren't any "+(Slot.isSKill ? "skill" : "equip")+"\nslot available. \n";
		    			if(Slot.isSKill){
		    				M += "Try forgeting a skill \nin the skill menu to \nmake space for a \nnew one.";
		    			}else{
		    				M += "Try tossing an equip \nin the equip menu \n";
		    				M += "or selling it to make \nspace for a new one.";
		    			}
		    			Game.Message.Show(M);
		    		}
	    		}else{
	    			Game.Message.Show("You can't afford\nthis.");
	    		}
	    	}
	    }
	    
	    Game.drawText("Our Stuff", 64, Position.y + titleSize.y - 64, 1,1,1,1);
	    Game.spriteBatch.end();
		
	}
	
	boolean isDone(){
		return Window.isClose();
	}
}

class HudT {
	GameT Game;
	Vec2 dissapearSize 		= new Vec2();
	Vec2 objectPickHeart;
	Vec2 objectPickCoin;
	Vec2 objectPickSword;
	Vec2 objectPickShield;
	SpriteButtonSkillT Skill1;
	SpriteButtonSkillT Skill2;
	SpriteButtonSkillT Skill3;
	
	buttonT shopBut;
	buttonT menuBut;
	buttonT equipBut;
	buttonT skillBut;
	
	
	float skillBarP;
	HudT(GameT G, Vec2 dissSize){
		Game = G;
		dissapearSize.Set(dissSize);
		objectPickHeart 	= new Vec2(dissapearSize.x, dissapearSize.y);
		objectPickCoin 		= new Vec2(dissapearSize.x, dissapearSize.y*2);
		objectPickSword 	= new Vec2(dissapearSize.x/2 + Game.windowSize.x/2, dissapearSize.y);
		objectPickShield 	= new Vec2(dissapearSize.x/2 + Game.windowSize.x/2, dissapearSize.y*2);
		skillBarP 			= Game.windowSize.x / 64;
		Skill1 				= new SpriteButtonSkillT(Game, null, Game.Player.Skill1);
		Skill2				= new SpriteButtonSkillT(Game, null, Game.Player.Skill2);
		Skill3				= new SpriteButtonSkillT(Game, null, Game.Player.Skill3);
		float dX			= Game.windowSize.x*.835f;
		float Y				= (dissapearSize.x/2)/3;
		equipBut			= new buttonT(Game, "EQUIP", fontSize.Normal);
		menuBut				= new buttonT(Game, "MENU", fontSize.Normal, equipBut.Size.x, equipBut.Size.y);
		shopBut				= new buttonT(Game, "SHOP", fontSize.Normal, equipBut.Size.x, equipBut.Size.y);
		skillBut			= new buttonT(Game, "SKILL", fontSize.Normal, equipBut.Size.x, equipBut.Size.y);

		float var = .17f;
		float p = Game.windowSize.y*var/2.8f;

		menuBut.Position.Set(dX - equipBut.Size.x/2, Y);
		shopBut.Position.Set(dX - equipBut.Size.x/2, Y + p);
		equipBut.Position.Set(dX - equipBut.Size.x/2, Y + p*2);
		skillBut.Position.Set(dX - equipBut.Size.x/2, Y + p*3);
		equipBut.R = .15f; equipBut.G = .15f; equipBut.B = .15f;
		shopBut.R = equipBut.R; shopBut.G = equipBut.G; shopBut.B = equipBut.B;
		menuBut.R = equipBut.R; menuBut.G  = equipBut.G; menuBut.B  = equipBut.B;
		skillBut.R = equipBut.R; skillBut.G  = equipBut.G; skillBut.B  = equipBut.B;
		
		equipBut.BR = 10f/255f; equipBut.BG = 10f/255f; equipBut.BB = 10f/255f;
		shopBut.BR = equipBut.BR; shopBut.BG = equipBut.BG; shopBut.BB = equipBut.BB;
		menuBut.BR = equipBut.BR; menuBut.BG  = equipBut.BG; menuBut.BB  = equipBut.BB;
		skillBut.BR = equipBut.BR; menuBut.BG  = skillBut.BG; skillBut.BB  = equipBut.BB;
		
		Skill1.Position.x = dissapearSize.x/2;
		Skill2.Position.x = dissapearSize.x/2 + Skill2.Size.x*1.5f;
		Skill3.Position.x = dissapearSize.x/2 + (Skill3.Size.x*1.5f)*2;
		
		Skill1.Position.y = Game.windowSize.y*var;
		Skill2.Position.y = Game.windowSize.y*var;
		Skill3.Position.y = Game.windowSize.y*var;
		
		Skill1.TR = equipBut.R; Skill1.TG = equipBut.G; Skill1.TB = equipBut.B;
		Skill2.TR = equipBut.R; Skill2.TG = equipBut.G; Skill2.TB = equipBut.B;
		Skill3.TR = equipBut.R; Skill3.TG = equipBut.G; Skill3.TB = equipBut.B;
		
		Hy = Game.getStringHeight("AAA");
		S = "EXP";
	}
	String S, SS, St, Str;
	float x, y, w, h, strW, strH, str3W, str3H, Hy;
	
	long turnTouchLast = 0;
	boolean turnTouched = false;
	void Update(){
		equipBut.Update();
		shopBut.Update();
		menuBut.Update();
		skillBut.Update();
		if(menuBut.isPressed() && Game.Step == GameT.stepPlayerPick){
			Game.Step = GameT.stepInGameMenu;
			Game.inGameMenu.Start();
		}
		if(equipBut.isPressed() && Game.Step == GameT.stepPlayerPick){
			Game.Step = GameT.stepInGameEquip;
			Game.inGameEquip.Start();
		}
		if(shopBut.isPressed() && Game.Step == GameT.stepPlayerPick){
			Game.Step = GameT.stepInShop;
			Game.Shop.Start();
		}
		if(skillBut.isPressed() && Game.Step == GameT.stepPlayerPick){
			Game.Step = GameT.stepInSkill;
			Game.skillMenu.Start();
		}		
		if(Gdx.input.isTouched() && !turnTouched && Game.Step == GameT.stepPlayerPick && Game.playerPick.ppTouches.size() == 0){
			turnTouchLast = Tools.getTicks();
			turnTouched = true;
		}
		if((!Gdx.input.isTouched() && turnTouched) || Game.playerPick.ppTouches.size() > 0){
			turnTouched = false;
		}
		
		St = "Lv "+String.valueOf(Game.Player.Level);
		if(turnTouched && Tools.getTicks()-turnTouchLast>200)
			Str = "Turn "+String.valueOf(Game.currentTurn);
		else
			Str = "Score "+String.valueOf(Game.Score);
		Game.setFontSize(fontSize.Small);
		strW = Game.getStringWidth(S);
		strH = Game.getStringHeight(S);
		str3W = Game.getStringWidth(Str);
		str3H = Game.getStringHeight("AAA");
		x = dissapearSize.x/4; y = Game.windowSize.y - str3H/1.5f; w = 48; h = 22;
		
		Skill1.Update();
		Skill2.Update();
		Skill3.Update();
		Game.setFontSize(fontSize.Small);
		Game.spriteBatch.begin();
		
		
		Game.drawText(St, x, y+h/2-strH/4, 0.3215f, 0.91372f, 0.03921f, 1);
		x = Game.windowSize.x / 2 - str3W/2;
		Game.drawText(Str, x, Game.windowSize.y - str3H/1.5f, 0.9490f, 0.1921f, 0, 1);
		x = Game.windowSize.x -  (strW*1.15f+w)*1.15f;
		Game.drawText(S, x, y+h/2-strH/4, 0, 0.46666f, 0.56862f, 1);
		Game.spriteBatch.end();
		Game.setFontSize(fontSize.Normal);
//		Game.spriteBatch.begin();
//		Game.spriteBatch.setColor(0, 0, 0, 1);
//		Game.spriteBatch.draw(Game.whiteTexture.Region, x+strW*1.15f - 3, y -2,	0, 0,w+6, h+4,	1, 1,	0);
//		Game.spriteBatch.setColor(1, 1, 1, 1);
//		Game.spriteBatch.end();
		
		Game.spriteBatch.begin();
		Game.spriteBatch.setColor(0.5f, 0.5f, 0.5f, .5f);
		Game.spriteBatch.draw(Game.whiteTexture.Region, x+strW*1.15f, y,	0, 0,w, h,	1, 1,	0);
		Game.spriteBatch.setColor(1, 1, 1, 1);
		Game.spriteBatch.end();
		
		Game.spriteBatch.begin();
		Game.spriteBatch.setColor(0, 0.85098f, 0.4f, 1);
		Game.spriteBatch.draw(Game.whiteTexture.Region, x+strW*1.15f, y,	0, 0,w*((float)Game.Player.Exp/(float)Game.Player.expNextLevel), h,	1, 1,	0);
		Game.spriteBatch.setColor(1, 1, 1, 1);
		Game.spriteBatch.end();
		
		Game.spriteBatch.begin();
		/* Health */
		Game.spriteBatch.draw(Game.heartTexture.Region, objectPickHeart.x, objectPickHeart.y, 0, 0, dissapearSize.x, dissapearSize.y, 1, 1, 180);
		SS = String.valueOf(Game.Player.HP)+"/"+String.valueOf(Game.Player.maxHP);
		Game.drawText(SS, objectPickHeart.x, objectPickHeart.y - Hy/2, 0.96f, 0.01f, 0.08f, 1f);
		
		/* Coin */
		Game.spriteBatch.draw(Game.coinTexture.Region, objectPickCoin.x, objectPickCoin.y, 0, 0, dissapearSize.x, dissapearSize.y, 1, 1,180);
		SS = String.valueOf(Game.Player.Money);
		Game.drawText(SS, objectPickCoin.x, objectPickCoin.y - Hy/2, 1f, 0.925f, 0f, 1f);
		
		/* Sword */
		Game.spriteBatch.draw(Game.swordTexture.Region, objectPickSword.x, objectPickSword.y, 0, 0, dissapearSize.x, dissapearSize.y, 1, 1, 180);
		SS = String.valueOf(Game.Player.currentAttack);
		Game.drawText(SS, objectPickSword.x, objectPickSword.y - Hy/2, 0.70f, 0.70f, 0.70f, 1f);
		
		/* Shield */
		Game.spriteBatch.draw(Game.shieldTexture.Region, objectPickShield.x, objectPickShield.y, 0, 0, dissapearSize.x, dissapearSize.y, 1, 1,180);
		SS = String.valueOf(Game.Player.currentDefense);
		Game.drawText(SS, objectPickShield.x, objectPickShield.y - Hy/2, 0.50f, 0.50f, 0.50f, 1f);
		Game.spriteBatch.end();			
	}
}
