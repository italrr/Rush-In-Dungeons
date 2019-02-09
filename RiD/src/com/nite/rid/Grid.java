package com.nite.rid;

class gridT {
	Vec2 Size;
	int gridSize;
	Vec2 objectSize;
	Vec2 gridPosition;
	Vec2 tileSize;
	gridObjectT []Objects;
	Vec2 []Positions;
	int pIsOutside = -1;
	int pIsUnknown = -2;
	float maxDistancePQ;
	gridT(Vec2 Sze, Vec2 objSize, Vec2 gP){
		Size = Sze;
		gridPosition = gP;
		objectSize = objSize;
		tileSize = new Vec2(Size.x / objectSize.x, Size.y / objectSize.y);
		gridSize = (int)((Size.x/objectSize.x)*(Size.y/objectSize.y));
		Objects = new gridObjectT[gridSize];
		Positions = new Vec2[gridSize];
		initPositions();
		maxDistancePQ = Tools.getDistance(getPosition(0), getPosition(1 + (int)tileSize.x));
	}		
	
	void Clear(){
		for(int i=0; i<gridSize; ++i){
			Objects[i] = null;
		}
	}
	
	Vec2 getPosition(int i){
		Vec2  P = new Vec2();
		if (i>=gridSize) return P;
		return Positions[i];
	}
	
	Vec2 snapPosition(Vec2 P){
		Vec2 n = new Vec2(P.x, P.y);
		n.x = (float) (Math.floor(P.x/objectSize.x) * objectSize.x);
		n.y = (float) (Math.floor(P.y/objectSize.y) * objectSize.y);
		return n;
	}
	
	int getPosition(Vec2 P){
		if(P.x < 0 || P.y < 0 || P.x > Size.x || P.y > Size.y) return -1;
		int x = (int)(P.x/objectSize.x);
		int y = (int)(P.y/objectSize.y);
		return x + y*(int)tileSize.x;
	}
	
	boolean isPNextToQ(int Pi, int Qi){
		return Tools.getDistance(getPosition(Qi), getPosition(Pi)) <= maxDistancePQ;
	}
	
	int getPositionFinger(Vec2 P){
		if(P.x < 0 || P.y < 0 || P.x > Size.x || P.y > Size.y) return pIsOutside;		
		int x = (int)(P.x/objectSize.x);
		int y = (int)(P.y/objectSize.y);
		int i = x + y*(int)tileSize.x;
		Vec2 Q = new Vec2(getPosition(i));
		Q.x += objectSize.x / 2.0f;
		Q.y += objectSize.y / 2.0f;
		if (Tools.getDistance(P, Q) > objectSize.x*.435f) return pIsUnknown;
		return i;
	}
	
	
	boolean isEmpty(Vec2 Position){
		int i = getPosition(Position);
		return Objects[i] == null;
	}
	
	int updateObject(gridObjectT Object, int i){
		int r = getPosition(Object.Position);
		if(r == i) return r;
		if(i != -1) Objects[i] = null;
		Objects[r] = Object;
		return r;
	}

	private void initPositions(){
		for(int i=0; i<gridSize; ++i){
			float wX = Size.x;
			float Column = 0;
			float eachPerRow = 0;
			float ind = (float)i;
			while(wX >= objectSize.x){
				wX -= objectSize.x;
				eachPerRow++;
			}
			while (ind >= eachPerRow){
				ind -= eachPerRow;
				Column++;
			}
			Vec2 k = new Vec2();
			k.x = ind*objectSize.x;
			k.y = Column*objectSize.y;
			Positions[i] = new Vec2(k);
		}
	}
}