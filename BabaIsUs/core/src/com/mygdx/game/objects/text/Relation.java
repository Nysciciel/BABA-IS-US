package com.mygdx.game.objects.text;

import com.mygdx.game.Level;
 import com.mygdx.game.Location;

public abstract class Relation extends Text {

	public Relation(Location loc, int x, int y, int orientation) {
		super(loc, x, y, orientation);
	}
	
	@Override
	public boolean isRelation() {
		return true;
	}
}