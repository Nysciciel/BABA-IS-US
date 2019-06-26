package com.mygdx.game.history;

import com.mygdx.game.Location;
import com.mygdx.game.objects.Item;

public class Death extends Change {

	private int orientation;
	
	public Death(Item item, Location location, int orientation) {
		super(item, location);
		this.orientation = orientation;
	}

	@Override
	public void revertChange() {
		item.setOrientation(orientation);
		location.add(item);
		item.setLocation(location);
	}

}
