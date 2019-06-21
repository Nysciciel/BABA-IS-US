package com.mygdx.game.objects.text.relation;

import com.mygdx.game.Location;
import com.mygdx.game.objects.text.Relation;

public class Is extends Relation {
	
	public Is(Location loc, int orientation) {
		super(loc, orientation);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isIs(){
		return true;
	}

	public String[] getSpriteUsed(){
		String[] spriteUsed = new String[2];
		spriteUsed[0]="Is0-0";
		spriteUsed[1]="Is0-1";
		return(spriteUsed);
	}

}
