package com.mygdx.game.objects.text;

import com.mygdx.game.*;
import com.mygdx.game.objects.*;

public abstract class Text extends Item {
	
	protected String label;
	private Class refClass;

	public Text(Location loc, int x, int y, int orientation) {
		super(loc, x, y, orientation);
	}
	
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
	
	public void show() {
		System.out.print(toString());
	}

	public Class getRefClass() {
		return null;
	};
}
