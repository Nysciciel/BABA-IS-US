package com.mygdx.game.objects;

import com.mygdx.game.Location;

public class Keke extends Item{

	public Keke(Location loc, int orientation) {
		super(loc, orientation);
		isTextureOriented =true;
		prio = 3;
	}
}
