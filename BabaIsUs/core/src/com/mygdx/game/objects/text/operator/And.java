package com.mygdx.game.objects.text.operator;

import com.mygdx.game.*;
import com.mygdx.game.objects.text.Operator;

public class And extends Operator {

	public And(Location loc, int orientation) {
		super(loc, orientation);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isAnd() {
		return true;
	}

	public String[] getSpriteUsed(){
		String[] spriteUsed = new String[2];
		spriteUsed[0]="And0-0";
		spriteUsed[1]="And0-1";
		return(spriteUsed);
	}

}
