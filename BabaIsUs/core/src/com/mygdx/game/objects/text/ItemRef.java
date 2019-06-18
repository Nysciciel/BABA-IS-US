package com.mygdx.game.objects.text;

import com.mygdx.game.Level;
import com.mygdx.game.Location;
import com.mygdx.game.objects.Item;

public abstract class ItemRef extends com.mygdx.game.objects.text.Text {
	
	public ItemRef(Location loc, int x, int y, int orientation) {
		super(loc, x, y, orientation);
	}
	
	@Override
	public boolean isItemRef() {
		return true;
	}

}
