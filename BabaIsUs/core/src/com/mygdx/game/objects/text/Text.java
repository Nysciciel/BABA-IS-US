package com.mygdx.game.objects.text;

import com.mygdx.game.*;
import com.mygdx.game.objects.*;
import com.mygdx.game.rule.LogicHashtable;
import com.mygdx.game.rule.RuleSet;

public abstract class Text extends Item {
	
	public Text(Location loc, LogicHashtable ruleTable, int x, int y, int orientation) {
		super(loc, ruleTable, x, y, orientation);
		// TODO Auto-generated constructor stub
	}

	protected String label;
	protected Class refClass;

	public boolean isOperator() {
		return false;
	}
	
	public boolean isProperty() {
		return false;
	}
	
	@Override
	public boolean isText() {
		return true;
	}


	public boolean isNot() {
		return false;
	}

	public boolean isOn() {
		return false;
	}

	public boolean isIs() {
		return false;
	}

	@Override
	public boolean isPush() {
		return true;
	}

	
	public boolean isItemRef() {
		return false;
	}
	
	public boolean isRelation() {
		return false;
	}

	public boolean isAnd() {
		return false;
	}
	
	@Override
	public String toString() {
		
		return this.getName();
	}
	
	@Override
	public String getCategory() {
		return "Text";
	}
	
	public void show() {
		System.out.print(toString());
	}

	public Class getRefClass() {
		return null;
	};
}
