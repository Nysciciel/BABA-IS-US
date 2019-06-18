package com.mygdx.game.objects.text.operator;

import com.mygdx.game.*;
import com.mygdx.game.objects.text.Operator;

public class And extends Operator {

	public And(Location loc, int x, int y, int orientation) {
		super(loc, x, y, orientation);

	}
	
	@Override
	public boolean isAnd() {
		return true;
	}
}
