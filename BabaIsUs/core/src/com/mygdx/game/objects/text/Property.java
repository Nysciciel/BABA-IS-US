package com.mygdx.game.objects.text;

import com.mygdx.game.Level;
import com.mygdx.game.Location;

public abstract class Property extends Text {

	public Property(Location loc, int x, int y, int orientation) {
		super(loc, x, y, orientation);
	}
	
	@Override
	public boolean isProperty() {
		return true;
	}


}

