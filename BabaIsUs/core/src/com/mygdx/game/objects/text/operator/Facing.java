package com.mygdx.game.objects.text.operator;

import com.mygdx.game.Location;
import com.mygdx.game.objects.text.Operator;
import com.mygdx.game.rule.LogicHashtable;
import com.mygdx.game.rule.RuleSet;

public class Facing extends Operator {
	
	public Facing(Location loc, int orientation) {
		super(loc, orientation);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isOn() {
		return true;
	}

}
