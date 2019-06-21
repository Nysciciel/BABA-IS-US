package com.mygdx.game.objects.text;

import com.mygdx.game.Level;
import com.mygdx.game.Location;
import com.mygdx.game.objects.Item;
import com.mygdx.game.rule.LogicHashtable;
import com.mygdx.game.rule.RuleSet;

public abstract class ItemRef extends com.mygdx.game.objects.text.Text {

	public ItemRef(Location loc, int orientation) {
		super(loc, orientation);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isItemRef() {
		return true;
	}
	
	@Override
	public String toString() {
		
		String s = this.getName();
		
		if (this.isItemRef()) {
			return s.substring(0, s.length()-4);
		}
		
		return s;
	}
	
	@Override
	public Class getRefClass() {
		try {
			return Class.forName(getName().substring(0, getName().length()-4));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public String getRefName() {
		return getName().substring(0, getName().length()-4);
	}

}
