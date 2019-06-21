package com.mygdx.game.objects;

import com.mygdx.game.*;

public class Skull extends Item{

	public Skull(Location loc, int orientation) {
		super(loc, orientation);
		// TODO Auto-generated constructor stub
	}
<<<<<<< HEAD
	
	@Override
	public boolean isDefeat() {
		return false;
	}
	
	@Override
	public boolean isMove() {
		return false;
	}
	
	@Override
	public String toString() {
		return "s";
	}

	public String[] getSpriteUsed(){
		String[] spriteUsed = new String[2];
		spriteUsed[0]="Skull" + orientation + "-0";
		spriteUsed[1]="Skull" + orientation + "-1";
		return(spriteUsed);
	}

}
