package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.*;
import com.mygdx.game.rule.LogicHashtable;
import com.mygdx.game.rule.RuleSet;

public class Keke extends Item{
	
	public Keke(Location loc, LogicHashtable ruleTable, int x, int y, int orientation) {
		super(loc, ruleTable, x, y, orientation);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isShift() {
		return true;
	}
	
	@Override
	public boolean isPush() {
		return false;
	}
	
	@Override
	public boolean isWin() {
		return false;
	}
}
