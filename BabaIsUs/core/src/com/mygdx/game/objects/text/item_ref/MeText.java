package com.mygdx.game.objects.text.item_ref;

import com.mygdx.game.Location;
import com.mygdx.game.objects.text.ItemRef;

public class MeText extends ItemRef {

	public MeText(Location loc, int orientation) {
		super(loc, orientation);
		// TODO Auto-generated constructor stub
	}

	public String[] getSpriteUsed(){
		String[] spriteUsed = new String[2];
		spriteUsed[0]="MeText0";
		spriteUsed[1]="MeText1";
		return(spriteUsed);
	}

}
