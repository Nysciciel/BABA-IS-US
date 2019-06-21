package com.mygdx.game.objects;

import com.mygdx.game.Location;

public class Keke extends Item{

	public Keke(Location loc, int orientation) {
		super(loc, orientation);
		// TODO Auto-generated constructor stub
	}

	public String[] getSpriteUsed(){
		String[] spriteUsed = new String[2];
		spriteUsed[0]="Keke0-0";
		spriteUsed[1]="Keke0-1";
		return(spriteUsed);
	}

}
