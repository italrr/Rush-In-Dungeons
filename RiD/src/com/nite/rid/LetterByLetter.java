package com.nite.rid;

class letterByLetterT {
	String Message = "";
	String toMessage = "N U L L";
	int cuLttr = 0;
	long lastCu = 0;
	int fntSize = fontSize.Big;
	long letterTime = 30;
	GameT Game;
	letterByLetterT(GameT G){
		Game 	= G;
	}
	void Draw(float x, float y, float R, float G, float B, float A){
	    Game.setFontSize(fntSize);
	    Game.drawText(Message, x, y, R, G, B, A);
	    Game.setFontSize(fontSize.Normal);
	}
	boolean Go = false;
	void Update(){
		if(!Go) return;
		if(toMessage.length() != Message.length() && Tools.getTicks()-lastCu > letterTime){
			Message += toMessage.substring(cuLttr, cuLttr+1);
			++cuLttr;
			lastCu = Tools.getTicks();
		}
	}
	boolean isDone(){
		return toMessage.length() == Message.length();
	}
	void End(){
		Message = "";
		cuLttr = 0;
		lastCu = 0;
		Go = false;
	}
	void setText(String Text){
		toMessage = Text;
	}
	void Start(){
		Go = true;
		Message = "";
		cuLttr = 0;
		lastCu = 0;
	}
}