package com.mygdx.game.objects.text.item_ref;

import com.mygdx.game.Location;
import com.mygdx.game.objects.text.ItemRef;

public class KekeText extends ItemRef {

	public KekeText(Location loc, int orientation) {
		super(loc, orientation);
		// TODO Auto-generated constructor stub
	}

	public String[] getSpriteUsed(){
		String[] spriteUsed = new String[2];
		spriteUsed[0]="KekeText0";
		spriteUsed[1]="KekeText1";
		return(spriteUsed);
	}

}
