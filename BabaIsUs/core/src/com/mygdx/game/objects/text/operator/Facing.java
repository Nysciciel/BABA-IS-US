package com.mygdx.game.objects.text.operator;

import com.mygdx.game.Location;
import com.mygdx.game.objects.text.Operator;

public class Facing extends Operator {

	public Facing(Location loc, int x, int y, int orientation) {
		super(loc, x, y, orientation);
	}
	
	@Override
	public boolean isOn() {
		return true;
	}

}
