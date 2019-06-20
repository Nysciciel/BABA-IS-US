package com.mygdx.game.objects.text;

import com.mygdx.game.Level;
import com.mygdx.game.Location;

public abstract class Operator extends Text {

	public Operator(Location loc, int x, int y, int orientation) {
		super(loc, x, y, orientation);

	}

	@Override
	public boolean isOperator() {
		return true;
	}
}
