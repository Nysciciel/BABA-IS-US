package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.*;

public class Rock extends Item {
	public Rock(Location loc,
			int x, int y, int orientation) {
		super(loc, x, y, orientation);
	}
	@Override
	public boolean isPull() {
		return false;
	}
	@Override
	public boolean isPush() {
		return true;
	}
	@Override
	public boolean isYou() {
		return false;
	}
}
