package com.mygdx.game.objects.text;

import com.mygdx.game.Level;
import com.mygdx.game.objects.Item;

public abstract class ItemRef extends com.mygdx.game.objects.text.Text {
	
	public ItemRef(Level lvl, int x, int y, int orientation) {
		super(lvl, x, y, orientation);
	}
	
	@Override
	public boolean isItemRef() {
		return true;
	}

}
