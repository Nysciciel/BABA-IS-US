package com.mygdx.game.objects.text.relation;

import com.mygdx.game.Level;
import com.mygdx.game.Location;
import com.mygdx.game.objects.text.Relation;

public class Is extends Relation {

	public Is(Location loc, int x, int y, int orientation) {
		super(loc, x, y, orientation);
	}

	
	@Override
	public boolean isIs() {
		return true;
	}

}

