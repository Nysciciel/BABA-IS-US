package com.mygdx.game.objects.text.relation;

import com.mygdx.game.Location;
import com.mygdx.game.objects.text.Relation;

public class Is extends Relation {
	
	public Is(Location loc, int orientation) {
		super(loc, orientation);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isIs(){
		return true;
	}
}
