package com.nite.rid;

import com.badlogic.gdx.math.Vector3;

public class Vec2 {
	public float x;
	public float y;
	Vec2(Vec2 v){
		x = v.x;
		y = v.y;
	}
	Vec2(){
		x = 0;
		y = 0;
	}
	Vec2(float X, float Y){
		 x = X;
		 y = Y;
	}
	Vec2(int X, int Y){
		x = X;
		y = Y;
	}
	void Set(float i){
		x = i;
		y = i;
	}
	Vec2(Vector3 v){
		x = v.x;
		y = v.y;
	}
	void Set(Vector3 v){
		x = v.x;
		y = v.y;		
	}
	void Set(Vec2 v){
		Set(v.x, v.y);
	}
	void Set(float X, float Y){
		 x = X;
		 y = Y;		
	}
	String Str(){
		String S = new String();
		S = "{"+String.valueOf(x)+", "+String.valueOf(y)+"}";
		return S;
	}
	boolean Translate(Vec2 P, float Speed){
		if (x == P.x && y == P.y) return false;
		float D = Tools.getDistance(P, this);
		float A = Tools.aTan((P.y-y), (P.x-x));
		D = Tools.Translate(D, 0, Speed);
		x = P.x - Tools.Cosine(A) * D;
		y = P.y - Tools.Sinus(A) * D;
		return true;
	}
}
