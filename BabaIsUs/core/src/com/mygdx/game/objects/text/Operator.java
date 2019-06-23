package com.mygdx.game.objects.text;

import com.mygdx.game.Location;

/**
 * Class of "ON", "AND", "NEAR", "FACING"...
 * @author Maxwell
 *
 */
public abstract class Operator extends Text {

	public Operator(Location loc, int orientation) {
		super(loc, orientation);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isOperator() {
		return true;
	}
}
