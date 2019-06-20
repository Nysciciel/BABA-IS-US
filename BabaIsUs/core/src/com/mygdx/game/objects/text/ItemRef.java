package com.mygdx.game.objects.text;

import com.mygdx.game.Level;
import com.mygdx.game.Location;
import com.mygdx.game.objects.Item;

public abstract class ItemRef extends com.mygdx.game.objects.text.Text {
	
	public ItemRef(Location loc, int x, int y, int orientation) {
		super(loc, x, y, orientation);
	}
	
	@Override
	public boolean isItemRef() {
		return true;
	}
	
	@Override
	public String toString() {
		
		String s = this.getName();
		
		if (this.isItemRef()) {
			return s.substring(0, s.length()-5);
		}
		
		return s;
	}
	
	@Override
	public Class getRefClass() {
		try {
			return Class.forName(getName().substring(0, getName().length()-5));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

}
