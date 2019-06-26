package com.mygdx.game.history;

import com.mygdx.game.Location;
import com.mygdx.game.objects.Item;

public abstract class Change {

	protected Item item;
	protected Location location;

	public Change(Item item, Location location) {
		this.item = item;
		this.location = location;
	}
	
	public abstract void revertChange();
	
}
