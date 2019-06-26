package com.mygdx.game.objects;

import com.mygdx.game.*;

public class Empty extends Item{

	public Empty(Location loc, int orientation) {
		super(loc, orientation);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isEmpty() {
		return true;
	}
}
