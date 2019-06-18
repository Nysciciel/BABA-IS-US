package com.mygdx.game.objects.text.operator;

import com.mygdx.game.Level;
import com.mygdx.game.Location;
import com.mygdx.game.objects.text.Operator;

public class Not extends Operator {

	public Not(Location loc, int x, int y, int orientation) {
		super(loc, x, y, orientation);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isNot() {
		return true;
	}
}
