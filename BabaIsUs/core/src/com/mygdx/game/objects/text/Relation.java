package com.mygdx.game.objects.text;

import com.mygdx.game.Location;

/**
 * Class of relations like "IS" or "HAS"
 * @author Maxwell
 *
 */
public abstract class Relation extends Text {

	public Relation(Location loc, int orientation) {
		super(loc, orientation);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isRelation() {
		return true;
	}
}

