package com.mygdx.game.objects.text.property;

import com.mygdx.game.Location;
import com.mygdx.game.objects.text.Property;

public class You extends Property {

	public You(Location loc, int orientation) {
		super(loc, orientation);
		// TODO Auto-generated constructor stub
	}

	public String[] getSpriteUsed(){
		String[] spriteUsed = new String[2];
		spriteUsed[0]="You0";
		spriteUsed[1]="You1";
		return(spriteUsed);
	}

}