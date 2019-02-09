package com.nite.rid;

import java.util.ArrayList;

class enemyQueueT {
	ArrayList<Integer> Queue = new ArrayList<Integer>();
	GameT Game;
	boolean isEnemyQueue(int i){
		for(int j = 0; j<Queue.size(); ++j){
			if(Queue.get(j) == i ){
				return true;
			}
		}
		return false;
	}
	void removeEnemy(int i){
		for(int j = 0; j<Queue.size(); ++j){
			if(Queue.get(j) == i){
				Queue.remove(j);
				return;
			}
		}
	}
	void addEnemiesToQueue(){ 
		for(int i=0; i<Game.objectListSize; ++i){
			if(Game.Objects[i] == null) continue;
			if(Game.Objects[i].Type == objectType.Monster && !((monsterT)Game.Objects[i]).isFrozen){
				Queue.add(i);
			}
		}
	}
	enemyQueueT(GameT G){
		Game = G;
	}
	void Clear(){
		Queue.clear();
	}
}