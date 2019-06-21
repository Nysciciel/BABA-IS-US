package com.mygdx.game.objects;

import com.mygdx.game.Location;

public class Keke extends Item{

	public Keke(Location loc, int orientation) {
		super(loc, orientation);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isShift() {
		return false;
	}
	
	@Override
	public boolean isPush() {
		return false;
	}
	
	@Override
	public boolean isWin() {
		return false;
	}
	
	@Override
	public boolean isYou2() {
		return true;
	}

	public String[] getSpriteUsed(){
		String[] spriteUsed = new String[2];
		spriteUsed[0]="Keke0-0";
		spriteUsed[1]="Keke0-1";
		return(spriteUsed);
	}
	
	@Override
	public String toString() {
		return "k";
	}

}
