package com.mygdx.game.objects.text;

import com.mygdx.game.Level;

public abstract class Property extends Text {

	public Property(Level lvl, int x, int y, int orientation) {
		super(lvl, x, y, orientation);

	}
	
	@Override
	public boolean isProperty() {
		return true;
	}


}
