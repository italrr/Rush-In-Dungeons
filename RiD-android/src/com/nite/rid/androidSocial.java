package com.nite.rid;

import com.nite.rid.Social;
import com.swarmconnect.Swarm;
import com.swarmconnect.SwarmAchievement;
import com.swarmconnect.SwarmLeaderboard;

public class androidSocial implements  Social {
	public void submitScore(int Score){
		if(!Init) return;
		SwarmLeaderboard.submitScore(16062, Score);
	}
	public void showLeaderboard(){
		if(!Init) return;
		Swarm.showLeaderboards();
	}
	private boolean Init = false;
	public boolean isInit(){
		return Init;
	}
	private void LogIn(){
		Swarm.setAllowGuests(true);
		Swarm.init(Activity, 11188, "9f526df1f5b986504ee55a04ac6d2d57");
		Init = Swarm.isLoggedIn();
	}
	public void showDashBoard(){
		if(!Init) return;
		Swarm.showDashboard();
	}
	public void showAchivements(){
		if(!Init) return;
		Swarm.showAchievements();
	}
	public void giveAchivement(int Achiv){
		if(!Init) return;
		if(Achiv == achNoMonsters){
			SwarmAchievement.unlock(20296);
		}		
		if(Achiv == achBossKiller){
			SwarmAchievement.unlock(20298);
		}
		if(Achiv == achMonsterHunter){
			SwarmAchievement.unlock(20300);
		}
		if(Achiv == achDungeonAddict){
			SwarmAchievement.unlock(20302);
		}
		if(Achiv == achHardToKill){
			SwarmAchievement.unlock(20306);
		}
		if(Achiv == achWorthyWarrior){
			SwarmAchievement.unlock(20308);
		}
		if(Achiv == achPotentialWarrior){
			SwarmAchievement.unlock(20310);
		}		
		if(Achiv == achDungeonRaider){
			SwarmAchievement.unlock(20312);
		}	
		if(Achiv == achGreedyRaider){
			SwarmAchievement.unlock(20314);
		}
		if(Achiv == achFireFighter){
			SwarmAchievement.unlock(20316);
		}		
		if(Achiv == achSuperEffective){
			SwarmAchievement.unlock(20318);
		}		
		if(Achiv == achPhotosynthesis){
			SwarmAchievement.unlock(20320);
		}
		if(Achiv == achKilledDiablo){
			SwarmAchievement.unlock(20322);
		}
		if(Achiv == achKilledGlitch){
			SwarmAchievement.unlock(20324);
		}
		if(Achiv == achKillerInstict){
			SwarmAchievement.unlock(20324);
		}		
		
		return;
	}
	public void Init(){
		setActive();
		LogIn();
	}
	MainActivity Activity;
	public void setActivity(MainActivity Act){
		Activity = Act;
	}
	public void setActive(){
		Swarm.setActive(Activity);
		if(Init){
			LogIn();
		}
	}
	public void setInActive(){
		Swarm.setInactive(Activity);
	}
}
