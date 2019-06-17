package com.mygdx.game.objects.text;

import com.mygdx.game.Level;
import com.mygdx.game.objects.Item;

public abstract class Text extends Item {
	
	protected String label;

	public Text(Level lvl, int x, int y, int orientation) {
				
		super(lvl, x, y, orientation);
			this.isPush = true;
	}
	

}
