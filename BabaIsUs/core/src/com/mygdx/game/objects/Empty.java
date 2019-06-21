package com.mygdx.game.objects;

import com.mygdx.game.*;

public class Empty extends Item{

	public Empty(Location loc, int orientation) {
		super(loc, orientation);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isempty() {
		return true;
	}

	public String[] getSpriteUsed(){
		String[] spriteUsed = new String[1];
		spriteUsed[0]="Empty0";
		return(spriteUsed);
	}

}
