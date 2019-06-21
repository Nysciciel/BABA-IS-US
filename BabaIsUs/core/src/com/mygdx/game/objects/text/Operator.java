package com.mygdx.game.objects.text;

import com.mygdx.game.Level;
import com.mygdx.game.Location;
import com.mygdx.game.rule.LogicHashtable;
import com.mygdx.game.rule.RuleSet;

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
