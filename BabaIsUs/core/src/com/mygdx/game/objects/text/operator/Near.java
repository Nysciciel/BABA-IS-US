package com.mygdx.game.objects.text.operator;

import com.mygdx.game.Location;
import com.mygdx.game.objects.text.Operator;

public class Near extends Operator {

	public Near(Location loc, int orientation) {
		super(loc, orientation);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isOn() {
		return true;
	}

	public String[] getSpriteUsed(){
		String[] spriteUsed = new String[2];
		spriteUsed[0]="Near0-0";
		spriteUsed[1]="Near0-1";
		return(spriteUsed);
	}

}
