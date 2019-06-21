package com.mygdx.game.objects;

import com.mygdx.game.*;

public class Skull extends Item{

	public Skull(Location loc, int orientation) {
		super(loc, orientation);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean isDefeat() {
		return false;
	}
	
	@Override
	public boolean isMove() {
		return false;
	}

}
