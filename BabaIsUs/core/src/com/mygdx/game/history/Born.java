package com.mygdx.game.history;

import com.mygdx.game.Location;
import com.mygdx.game.objects.Item;

public class Born extends Change {

	public Born(Item item, Location location) {
		super(item, location);
	}

	@Override
	public void revertChange() {
		location.del(item);
	}

}
