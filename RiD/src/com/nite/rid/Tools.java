package com.nite.rid;

public class Tools {
	static float getDistance(Vec2 P, Vec2 Q){
		return (float) Math.sqrt(Math.pow(Q.x - P.x, 2) + Math.pow(Q.y - P.y, 2));
	}
	static boolean isInRegion(Vec2 P, Vec2 RP,  Vec2 RQ){
		return P.x>=RP.x && P.x<=RQ.x && P.y>=RP.y && P.y<=RQ.y;
	}
	static float aTan(float y, float x){
		float an = (float) Math.atan2((y*Math.PI)/180.0,(x*Math.PI)/180.0);
		return (float) ((an > 0 ? an : (2*Math.PI + an)) * 360 / (2*Math.PI));
	}
	static float Sinus(float an){
		return (float) Math.sin((an*Math.PI)/180.0);
	}
	static float Cosine(float an){
		return (float) Math.cos((an*Math.PI)/180.0);
	}
	static void Print(String Text){
		System.out.println(Text);
	}
	static long getTicks(){
		return System.currentTimeMillis();
	}
	static float Random(float min, float max){
		return (float) (Math.random()*max + min);
	}
	static int Random(int min, int max){
		return (int) (Math.random()*max + min);
	}
	static float Positive(float n){
		return n<0 ? n*-1 : n;
	}
	static float Round(float n){
		return Math.round(n);
	}
	static float Ceil(float n){
		return (float) Math.ceil(n);
	}
	static float Floor(float n){
		return (float) Math.floor(n);
	}
	static float Translate(float x1, float x2, float Speed){
		if(x1 == x2) return x1;
		if (x1 > x2){
			if (x1-x2 >= 1){
				x1 -= Math.abs(x1-x2)*Speed;
			}else{
				x1 = x2;
			}
		}else{
			if (x2-x1 >= 1){
				x1 += Math.abs(x1-x2)*Speed;
			}else{
				x1 = x2;
			}
		}
		return x1;
	}
}
