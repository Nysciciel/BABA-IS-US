package com.mygdx.game.objects.text.property;

import com.mygdx.game.Location;
import com.mygdx.game.objects.text.Property;

public class Win extends Property {

	public Win(Location loc, int orientation) {
		super(loc, orientation);
		// TODO Auto-generated constructor stub
	}

	public String[] getSpriteUsed(){
		String[] spriteUsed = new String[2];
		spriteUsed[0]="Win0";
		spriteUsed[1]="Win1";
		return(spriteUsed);
	}

}
