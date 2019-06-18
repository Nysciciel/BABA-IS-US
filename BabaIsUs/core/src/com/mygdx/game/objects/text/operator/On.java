package com.mygdx.game.objects.text.operator;

import com.mygdx.game.Level;
import com.mygdx.game.objects.text.Operator;

public class On extends Operator {

	public On(Level lvl, int x, int y, int orientation) {
		super(lvl, x, y, orientation);
	}
	
	@Override
	public boolean isOn() {
		return true;
	}

}
