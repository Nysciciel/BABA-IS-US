package com.mygdx.game.objects.text;

import com.mygdx.game.Level;
 import com.mygdx.game.Location;
import com.mygdx.game.rule.LogicHashtable;
import com.mygdx.game.rule.RuleSet;

public abstract class Relation extends Text {
	

	public Relation(Location loc, LogicHashtable ruleTable, int x, int y, int orientation) {
		super(loc, ruleTable, x, y, orientation);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isRelation() {
		return true;
	}
}

