package com.mygdx.game.objects;

import com.mygdx.game.*;

public class Rock extends Item {

	public Rock(Location loc, int orientation) {
		super(loc, orientation);
		}

	public String[] getSpriteUsed(){
		String[] spriteUsed = new String[2];
		spriteUsed[0]="Rock0";
		spriteUsed[1]="Rock1";
		return(spriteUsed);
	}
	
	@Override
	public String toString() {
		return "r";
	}

}
