package com.nite.rid;

public interface Social {
	public void submitScore(int Score);
	public void showLeaderboard();
	public void Init();
	public void giveAchivement(int Achiv);
	public boolean isInit();
	public void showDashBoard();
	public void showAchivements();
	public static final int achNoMonsters 		= 0;
	public static final int achBossKiller 		= 1;
	public static final int achMonsterHunter 	= 2;
	public static final int achDungeonAddict 	= 3;
	public static final int achHardToKill	 	= 4;
	public static final int achWorthyWarrior	= 5;
	public static final int achPotentialWarrior	= 6;
	public static final int achDungeonRaider	= 7;
	public static final int achGreedyRaider		= 8;
	public static final int achFireFighter		= 9;
	public static final int achSuperEffective	= 10;
	public static final int achPhotosynthesis	= 11;
	public static final int achKilledDiablo		= 12;
	public static final int achKilledGlitch		= 13;
	public static final int achKillerInstict	= 14;
	
}
