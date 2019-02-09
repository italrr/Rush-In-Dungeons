package com.nite.rid;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;

class ppSystemT {
	GameT Game;
	boolean ppFailTocuch = false;
	ArrayList<Integer> ppTouches = new ArrayList<Integer>();
	int ppType = -1;
	boolean ppDoNext = true;
	void Clear(){
		ppFailTocuch = false;
		ppType = -1;
		ppTouches.clear();
		ppDoNext 			= true;
	}
	ppSystemT(GameT G){
		Game = G;
	}
	boolean isGIndexInPPTouches(int i){
		for(int j=0; j<ppTouches.size(); ++j){
			if(Game.Grid.Objects[ppTouches.get(j)].gameIndex == i) return true;
		}
		return false;
	}
	Vec2 V = new Vec2();
	int getIndexFromTouch(){
		V.Set(Gdx.input.getX(), Gdx.input.getY());
		Game.unProject(V);
		Vec2 RQ = new Vec2(Game.gridPosition.x+Game.gridSize.x, Game.gridPosition.y+Game.gridSize.y);
		if(Tools.isInRegion(V, Game.gridPosition, RQ)){
			V.x -= Game.gridPosition.x;
			V.y -= Game.gridPosition.y;
			return Game.Grid.getPositionFinger(V);
		}
		return -1;
	}
	int iInPPTouches(int i){
		for(int j = 0; j<ppTouches.size(); ++j){
			if(ppTouches.get(j) == i) return j;
		}
		return -1;
	}
	boolean isInPPTouches(int i){
		for(int j = 0; j<ppTouches.size(); ++j){
			if(ppTouches.get(j) == i) return true;
		}
		return false;
	}
	boolean isPPMissmatch(int i){
		if(ppTouches.size() == 0){
			ppType = Game.Grid.Objects[i].Type;
			return false;
		}
		if(Game.Grid.Objects[i].Type == objectType.Sword  && ppType == objectType.Monster) return false;
		if(Game.Grid.Objects[i].Type == objectType.Monster && ppType == objectType.Sword) return false;
		return Game.Grid.Objects[i].Type != ppType;
	}
	
	boolean isPPBeforeLast(int i){
		if(ppTouches.size()>1){
			if(ppTouches.get(ppTouches.size()-2) == i){
				return true;
			}
		}
		return false;
	}
	boolean lackOfMonsters(){
		if(ppType == objectType.Sword){
			boolean noMonster = true;
			for(int j = 0; j<ppTouches.size(); ++j){
				if(Game.Grid.Objects[ppTouches.get(j)].Type == objectType.Monster){
					noMonster = false;
					break;
				}
			}
			if(noMonster) return true;
		}
		return false;
	}
	boolean updatePlayerPick(){
		boolean wasTouched = Gdx.input.isTouched();
		if(wasTouched && ppFailTocuch){
			if(Gdx.input.justTouched()) ppFailTocuch = false;
			return false;
		}
		/* Picked not enough */
		if(!wasTouched && ppTouches.size()>0 && ppTouches.size()<2){
			ppTouches.clear();
			wasTouched = false;
			ppFailTocuch = false;
			return false;
		}else
		/* Done picking */
		if(!wasTouched && ppTouches.size()>=2){
			if(lackOfMonsters()){
				ppTouches.clear();
				wasTouched = false;
				ppFailTocuch = false;
				return false;
			}
			return true;
		}else
		/* Do Picking */
		if(wasTouched){
			int i = getIndexFromTouch();
			if (i == Game.Grid.pIsOutside){
//				wasTouched = false;
//				ppFailTocuch = true;
//				ppTouches.clear();
				return false;
			}
			if(i != Game.Grid.pIsUnknown){
				if(isPPMissmatch(i)){
					return false;
				}
				if(isPPBeforeLast(i)){
					ppTouches.remove(ppTouches.size() - 1);
					return false;
				}
				if(isInPPTouches(i)){
					if(i == ppTouches.get(ppTouches.size()-1)){
						return false;
					}
					int k = iInPPTouches(i);
					for(int p = k; p<ppTouches.size(); ++p){
						ppTouches.remove(p);
						--p;
					}
					return false;
				}
				if(ppTouches.size()>0){
					int l = ppTouches.get(ppTouches.size()-1);
					if(!Game.Grid.isPNextToQ(i, l)){
						//wasTouched = false;
						//ppFailTocuch = true;
						//ppTouches.clear();
						return false;
					}
				}
				ppTouches.add(i);
			}
		}
		return false;
	}
}