package com.mygdx.game.objects.text;

import com.mygdx.game.Location;

public abstract class Property extends Text {
	
	public Property(Location loc, int orientation) {
		super(loc, orientation);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isProperty() {
		return true;
	}
}

